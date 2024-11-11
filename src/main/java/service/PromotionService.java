package service;

import domain.product.Product;

public class PromotionService {
    private final InputService inputService;

    public PromotionService(InputService inputService) {
        this.inputService = inputService;
    }

    public void processPromotionProduct(final Product product, long purchaseQuantity,
                                        final ReceiptService receiptService) {
        while (purchaseQuantity > 0) {
            long buy = product.getPromotionBuy();
            long get = product.getPromotionGet();

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

    private void processPromotion(final Product product, final long remainingQuantity,
                                  final ReceiptService receiptService) {
        long buy = product.getPromotionBuy();
        long get = product.getPromotionGet();
        long purchasableQuantity = Math.min(buy, remainingQuantity);

        receiptService.addBuyingProducts(product, purchasableQuantity);
        receiptService.addFreebieProduct(product, get);
    }
}
