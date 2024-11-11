//package service;
//
//import domain.buyer.Buyer;
//import domain.inventory.Inventory;
//import domain.product.Product;
//
//public class BuyerService {
//    private final Buyer buyer;
//    private final Inventory inventory;
//    private final InventoryService inventoryService;
//    private final ReceiptService receiptService;
//    private final PromotionService promotionService;
//
//    public BuyerService(final Buyer buyer, final Inventory inventory,
//                        final InventoryService inventoryService,
//                        final ReceiptService receiptService,
//                        final PromotionService promotionService) {
//        this.buyer = buyer;
//        this.inventory = inventory;
//        this.inventoryService = inventoryService;
//        this.receiptService = receiptService;
//        this.promotionService = promotionService;
//    }
//
//    public void requestProduct(final Product purchaseProduct) {
//        Product findProduct = inventory.findProductByName(purchaseProduct);
//        int purchaseQuantity = purchaseProduct.getQuantity();
//
//        processProduct(findProduct, purchaseQuantity);
//
//        receiptService.updateFinalPaymentAmount();
//        inventoryService.reduceProductStock(findProduct, purchaseQuantity);
//    }
//
//    private void processProduct(final Product product, final int quantity) {
//        if (product.isPromotion()) {
//            promotionService.processPromotionProduct(product, quantity, receiptService);
//            return;
//        }
//        receiptService.addBuyingProducts(product, quantity);
//    }
//}
