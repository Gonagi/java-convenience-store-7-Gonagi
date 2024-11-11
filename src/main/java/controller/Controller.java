package controller;

import domain.inventory.Inventory;
import domain.product.Product;
import domain.product.Products;
import domain.product.ProductsFactory;
import service.InputService;
import service.InventoryService;
import service.OutputService;
import service.PromotionService;
import service.ReceiptService;

public class Controller {
    private final InputService inputService;
    private final OutputService outputService;
    private final Inventory inventory;
    private final InventoryService inventoryService;
    private final ReceiptService receiptService;
    private final PromotionService promotionService;

    public Controller(InputService inputService, OutputService outputService,
                      Inventory inventory, InventoryService inventoryService, ReceiptService receiptService,
                      PromotionService promotionService) {
        this.inputService = inputService;
        this.outputService = outputService;
        this.inventory = inventory;
        this.inventoryService = inventoryService;
        this.receiptService = receiptService;
        this.promotionService = promotionService;
    }

    public void run() {
        outputService.printBeforeTransaction(inventory);
        startOrderProcessing();
        processMembershipDiscount();
        outputService.printAfterTransaction(receiptService.getReceipt());
        processAdditionalPurchase();
    }

    private void startOrderProcessing() {
        while (true) {
            try {
                processOrder();
                break;
            } catch (IllegalArgumentException e) {
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
        Product findProduct = inventory.findProductByName(purchaseProduct);
        long purchaseQuantity = purchaseProduct.getQuantity();

        processProduct(findProduct, purchaseQuantity);
        receiptService.updateFinalPaymentAmount();
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
        long productStock = inventory.getProductStock(product);
        if (quantity > productStock) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }
}
