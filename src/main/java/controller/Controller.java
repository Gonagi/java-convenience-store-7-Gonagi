package controller;

import domain.inventory.Inventory;
import domain.product.Product;
import domain.product.Products;
import domain.product.ProductsFactory;
import service.InputService;
import service.InventoryService;
import service.PromotionService;
import service.ReceiptService;
import view.InputView;
import view.OutputView;

public class Controller {
    private final InputView inputView;
    private final InputService inputService;
    private final OutputView outputView;
    private final Inventory inventory;
    private final InventoryService inventoryService;
    private final ReceiptService receiptService;
    private final PromotionService promotionService;

    public Controller(InputView inputView, InputService inputService, OutputView outputView,
                      Inventory inventory, InventoryService inventoryService, ReceiptService receiptService,
                      PromotionService promotionService) {
        this.inputView = inputView;
        this.inputService = inputService;
        this.outputView = outputView;
        this.inventory = inventory;
        this.inventoryService = inventoryService;
        this.receiptService = receiptService;
        this.promotionService = promotionService;
    }

    public void run() {
        String[] inputOrders;
        Products purchaseProducts;
        boolean validInput = false;

        outputView.printStartMessage();
        outputView.printInventory(inventory);
        while (!validInput) {
            try {
                inputOrders = inputService.inputOrder();
                purchaseProducts = ProductsFactory.createProductsByInput(inputOrders);
                for (Product purchaseProduct : purchaseProducts.getProducts()) {
                    Product findProduct = inventory.findProductByName(purchaseProduct);
                    long purchaseQuantity = purchaseProduct.getQuantity();
                    processProduct(findProduct, purchaseQuantity);
                    receiptService.updateFinalPaymentAmount();
                    inventoryService.reduceProductStock(findProduct, purchaseQuantity);
                }
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        String membershipDiscountInput = inputService.inputMembershipDiscountApplied();
        if ("Y".equals(membershipDiscountInput)) {
            receiptService.updateMembershipDiscount();
        }
        outputView.printReceipt(receiptService.getReceipt());

        String additionalPurchaseInput = inputService.inputAdditionalPurchaseOption();
        if ("Y".equals(additionalPurchaseInput)) {
            run();
        }
//        outputView.printReceipt(receiptService.getReceipt());
//        inputService.inputMembershipDiscountApplied();
//        inputService.inputAdditionalPurchaseOption();
    }

    private void processProduct(final Product product, final long quantity) {
        long productStock = inventory.getProductStock(product);

        if (quantity > productStock) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }

        if (product.isPromotion() && product.checkPromotionDate()) {
            promotionService.processPromotionProduct(product, quantity, receiptService);
            return;
        }
        receiptService.addBuyingProducts(product, quantity);
    }
}
