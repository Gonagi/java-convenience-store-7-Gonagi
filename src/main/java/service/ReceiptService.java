package service;

import domain.product.Product;
import domain.product.Quantity;
import receipt.Receipt;

public class ReceiptService {
    private final Receipt receipt;

    public ReceiptService(Receipt receipt) {
        this.receipt = receipt;
    }

    public void addBuyingProducts(final Product product, final int quantity) {
        Product buyingProduct = new Product.Builder(product.getName(), Quantity.from(quantity))
                .price(product.getPrice()).build();

        receipt.addBuyingProducts(buyingProduct);
    }

    public void addFreebieProduct(final Product product, final int quantity) {
        Product freebieProduct = new Product.Builder(product.getName(), Quantity.from(quantity))
                .price(product.getPrice()).build();

        receipt.addFreebieProducts(freebieProduct);
    }

    public void updateMembershipDiscount() {
        receipt.calculateMembershipDiscount();
    }

    public void updateFinalPaymentAmount() {
        receipt.calculateFinalPaymentAmount();
    }
}