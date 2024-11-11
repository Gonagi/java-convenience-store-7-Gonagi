package service;

import domain.product.Product;
import domain.product.Product.Builder;
import domain.product.Quantity;

public class PromotionService {
    private final InputService inputService;

    public PromotionService(InputService inputService) {
        this.inputService = inputService;
    }

    public void processPromotionProduct(final Product product, long purchaseQuantity,
                                        final ReceiptService receiptService) {

        long productSet = product.getPromotionBuy() + product.getPromotionGet();
        long buy = product.getPromotionBuy() * (purchaseQuantity / productSet);
        long get = product.getPromotionGet() * (purchaseQuantity / productSet);

        long quantity = product.getQuantity();
        if (quantity < purchaseQuantity) {
            String response = inputService.inputRegularPricePaymentOption(product.getName(),
                    purchaseQuantity - quantity);
            if ("Y".equals(response)) {
                receiptService.addBuyingProducts(product, quantity);
                receiptService.addFreebieProduct(product, get);
                Product regularProduct = new Builder(product.getName(),
                        Quantity.from(purchaseQuantity - quantity)).price(product.getPrice()).build();
                receiptService.addBuyingProducts(regularProduct, purchaseQuantity - quantity);
                return;
            }
            if ("N".equals(response)) {
                receiptService.addBuyingProducts(product, purchaseQuantity);
                receiptService.addFreebieProduct(product, get);
                return;
            }
        }

        if (purchaseQuantity % productSet == 0) {
            receiptService.addBuyingProducts(product, purchaseQuantity);
            receiptService.addFreebieProduct(product, get);
            return;
        }
        if (purchaseQuantity % productSet == product.getPromotionBuy()) {
            String response = inputService.inputPromotionApplied(product);
            if ("Y".equals(response)) {
                receiptService.addBuyingProducts(product, purchaseQuantity + product.getPromotionGet());
                receiptService.addFreebieProduct(product, get + product.getPromotionGet());
            }
            if ("N".equals(response)) {
                receiptService.addBuyingProducts(product, purchaseQuantity);
                receiptService.addFreebieProduct(product, get);
            }
            return;
        }
        receiptService.addBuyingProducts(product, purchaseQuantity - get);
        receiptService.addFreebieProduct(product, get);
    }
}
