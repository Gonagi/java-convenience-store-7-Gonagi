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
            if (purchaseQuantity < product.getPromotionBuy()) {
                handlePromotionUnderQuantity(product, purchaseQuantity, receiptService);
                break;
            }
            processPromotion(product, purchaseQuantity, receiptService);
            purchaseQuantity -= product.getPromotionBuy();
        }
    }

    private void handlePromotionUnderQuantity(final Product product, long purchaseQuantity,
                                              final ReceiptService receiptService) {
        String response = inputService.inputPromotionApplied(product);
        if ("Y".equals(response)) {
            applyPromotion(product, receiptService);
        }
        if ("N".equals(response)) {
            handleRegularPricePayment(product, purchaseQuantity, receiptService);
        }
    }

    private void applyPromotion(Product product, ReceiptService receiptService) {
        long buy = product.getPromotionBuy();
        long get = product.getPromotionGet();
        receiptService.addBuyingProducts(product, buy);
        receiptService.addFreebieProduct(product, get);
    }

    private void handleRegularPricePayment(Product product, long purchaseQuantity,
                                           ReceiptService receiptService) {
        String response = inputService.inputRegularPricePaymentOption(product);
        if ("Y".equals(response)) {
            receiptService.addBuyingProducts(product, purchaseQuantity);
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
