package service;

import domain.product.Product;
import domain.product.Quantity;

public class PromotionService {
    private final InputService inputService;

    public PromotionService(InputService inputService) {
        this.inputService = inputService;
    }

    public void processPromotionProduct(final Product product, long purchaseQuantity,
                                        final ReceiptService receiptService) {
        long productSet = product.getPromotionBuy() + product.getPromotionGet();
        long quantityInStock = product.getQuantity();
        if (handleInsufficientStock(product, purchaseQuantity, quantityInStock, receiptService)) {
            return;
        }
        if (processExactProductSet(product, purchaseQuantity, productSet, receiptService)) {
            return;
        }
        if (promptApplyPromotion(product, purchaseQuantity, productSet, receiptService)) {
            return;
        }
        processRemainingProducts(product, purchaseQuantity, productSet, receiptService);
    }

    private boolean handleInsufficientStock(final Product product, final long purchaseQuantity,
                                            final long quantityInStock,
                                            final ReceiptService receiptService) {
        if (quantityInStock >= purchaseQuantity) {
            return false;
        }
        String response = inputService.inputRegularPricePaymentOption(product.getName(),
                purchaseQuantity - quantityInStock);
        receiptService.addBuyingProducts(product, quantityInStock);
        if ("Y".equals(response)) {
            addRegularPriceProduct(product, purchaseQuantity - quantityInStock, receiptService);
        }
        long freebies = calculateFreebie(product, purchaseQuantity,
                product.getPromotionBuy() + product.getPromotionGet());
        receiptService.addFreebieProduct(product, freebies);
        return true;
    }

    private void addRegularPriceProduct(final Product product, final long quantity,
                                        final ReceiptService receiptService) {
        Product regularProduct = new Product.Builder(product.getName(), Quantity.from(quantity))
                .price(product.getPrice()).build();
        receiptService.addBuyingProducts(regularProduct, quantity);
    }

    private boolean processExactProductSet(final Product product, final long purchaseQuantity, final long productSet,
                                           ReceiptService receiptService) {
        if (purchaseQuantity % productSet != 0) {
            return false;
        }
        long freebies = calculateFreebie(product, purchaseQuantity, productSet);
        receiptService.addBuyingProducts(product, purchaseQuantity);
        receiptService.addFreebieProduct(product, freebies);
        return true;
    }

    private boolean promptApplyPromotion(final Product product, final long purchaseQuantity, final long productSet,
                                         final ReceiptService receiptService) {
        if (purchaseQuantity % productSet != product.getPromotionBuy()) {
            return false;
        }
        String response = inputService.inputPromotionApplied(product);
        long additionalFreebies = "Y".equals(response) ? product.getPromotionGet() : 0;
        receiptService.addBuyingProducts(product, purchaseQuantity);
        long freebies = calculateFreebie(product, purchaseQuantity, productSet) + additionalFreebies;
        receiptService.addFreebieProduct(product, freebies);
        return true;
    }

    private void processRemainingProducts(final Product product, final long purchaseQuantity, final long productSet,
                                          final ReceiptService receiptService) {
        long freebies = calculateFreebie(product, purchaseQuantity, productSet);
        long effectivePurchaseQuantity = purchaseQuantity - freebies;
        receiptService.addBuyingProducts(product, effectivePurchaseQuantity);
        receiptService.addFreebieProduct(product, freebies);
    }

    private long calculateFreebie(final Product product, final long purchaseQuantity, final long productSet) {
        return product.getPromotionGet() * (purchaseQuantity / productSet);
    }
}
