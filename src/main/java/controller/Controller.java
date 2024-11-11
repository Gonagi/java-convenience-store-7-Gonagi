package controller;

import constant.ErrorMessage;
import domain.product.Product;
import domain.product.Products;
import domain.product.ProductsFactory;
import java.time.DateTimeException;
import service.InputService;
import service.InventoryService;
import service.OutputService;
import service.PromotionService;
import service.ReceiptService;

public class Controller {
    private final InputService inputService;
    private final OutputService outputService;
    private final InventoryService inventoryService;
    private final ReceiptService receiptService;
    private final PromotionService promotionService;

    public Controller(InputService inputService, OutputService outputService,
                      InventoryService inventoryService, ReceiptService receiptService,
                      PromotionService promotionService) {
        this.inputService = inputService;
        this.outputService = outputService;
        this.inventoryService = inventoryService;
        this.receiptService = receiptService;
        this.promotionService = promotionService;
    }

    public void run() {
        outputService.printBeforeTransaction(inventoryService.getInventory());
        startOrderProcessing();

        processMembershipDiscount();
        receiptService.updateFinalPaymentAmount();
        outputService.printAfterTransaction(receiptService.getReceipt());
        processAdditionalPurchase();
    }

    private void startOrderProcessing() {
        while (true) {
            try {
                processOrder();
                break;
            } catch (IllegalArgumentException | DateTimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processOrder() {
        String[] inputOrders = inputService.inputOrder();
        Products purchaseProducts = ProductsFactory.createProductsByInput(inputOrders);
        for (Product purchaseProduct : purchaseProducts.getProducts()) {
            processSingleProduct(purchaseProduct);
        }
    }

    private void processSingleProduct(Product purchaseProduct) {
        Product findProduct = inventoryService.findProductByName(purchaseProduct);
        long purchaseQuantity = purchaseProduct.getQuantity();

        processProduct(findProduct, purchaseQuantity);
//        processMembershipDiscount();

//        receiptService.updateFinalPaymentAmount();
        inventoryService.reduceProductStock(findProduct, purchaseQuantity);
    }

    private void processProduct(final Product product, final long quantity) {
        checkProductStock(product, quantity);
        if (product.isPromotion() && product.checkPromotionDate()) {
            promotionService.processPromotionProduct(product, quantity, receiptService);
            return;
        }
        receiptService.addBuyingProducts(product, quantity);
    }

    private void processMembershipDiscount() {
        String membershipDiscountInput = inputService.inputMembershipDiscountApplied();
        if ("Y".equals(membershipDiscountInput)) {
            receiptService.updateMembershipDiscount();
        }
    }

    private void processAdditionalPurchase() {
        String additionalPurchaseInput = inputService.inputAdditionalPurchaseOption();
        if ("Y".equals(additionalPurchaseInput)) {
            run();
        }
    }

    private void checkProductStock(final Product product, final long quantity) {
        long productStock = inventoryService.getProductStock(product);
        if (quantity > productStock) {
            throw new IllegalArgumentException(ErrorMessage.EXCEEDS_STOCK_QUANTITY.getMessage());
        }
    }
}
