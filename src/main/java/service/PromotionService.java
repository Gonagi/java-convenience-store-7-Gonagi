//package service;
//
//import domain.product.Product;
//
//public class PromotionService {
//    public void processPromotionProduct(final Product product, int purchaseQuantity,
//                                        final ReceiptService receiptService) {
//        while (purchaseQuantity > 0) {
//            processPromotion(product, purchaseQuantity, receiptService);
//            purchaseQuantity -= product.getPromotionBuy();
//        }
//    }
//
//    private void processPromotion(final Product product, final int remainingQuantity,
//                                  final ReceiptService receiptService) {
//        int buy = product.getPromotionBuy();
//        int get = product.getPromotionGet();
//        int purchasableQuantity = Math.min(buy, remainingQuantity);
//
//        receiptService.addBuyingProducts(product, purchasableQuantity);
//        receiptService.addFreebieProduct(product, get);
//    }
//}

package service;

import domain.product.Product;

public class PromotionService {
    private final InputService inputService;

    public PromotionService(InputService inputService) {
        this.inputService = inputService;
    }

    public void processPromotionProduct(final Product product, int purchaseQuantity,
                                        final ReceiptService receiptService) {
        while (purchaseQuantity > 0) {
            int buy = product.getPromotionBuy();
            int get = product.getPromotionGet();

            if (purchaseQuantity < buy) {
                String response = inputService.inputPromotionApplied(product);
                if ("Y".equals(response)) {
                    receiptService.addBuyingProducts(product, buy);
                    receiptService.addFreebieProduct(product, get);
                    break;
                } else {
                    response = inputService.inputRegularPricePaymentOption(product);
                    if ("Y".equals(response)) {
                        receiptService.addBuyingProducts(product, purchaseQuantity);
                    }
                    break;
                }
            } else {
                processPromotion(product, purchaseQuantity, receiptService);
                purchaseQuantity -= buy;
            }
        }
    }

    private void processPromotion(final Product product, final int remainingQuantity,
                                  final ReceiptService receiptService) {
        int buy = product.getPromotionBuy();
        int get = product.getPromotionGet();
        int purchasableQuantity = Math.min(buy, remainingQuantity);

        receiptService.addBuyingProducts(product, purchasableQuantity);
        receiptService.addFreebieProduct(product, get);
    }
}
