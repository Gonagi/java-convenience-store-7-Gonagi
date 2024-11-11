package domain.receipt;

import domain.product.Product;
import domain.product.Products;
import domain.product.ProductsFactory;
import java.util.LinkedList;
import java.util.List;

public class Receipt {
    private final Products buyingProducts;
    private final Products freebieProducts;
    private final Amount amount;

    public Receipt() {
        this.buyingProducts = ProductsFactory.createProductsByProducts(new LinkedList<>());
        this.freebieProducts = ProductsFactory.createProductsByProducts(new LinkedList<>());
        this.amount = new Amount();
    }

    public void addBuyingProducts(final Product product) {
        buyingProducts.addProduct(product);
        amount.addTotalPurchaseAmount(product.getPrice() * product.getQuantity());
    }

    public void addFreebieProducts(final Product product) {
        this.freebieProducts.addProduct(product);
        amount.addPromotionDiscount(product.getPrice() * product.getQuantity());
    }

    public void addPromotionPurchaseAmount(final Product product) {
        amount.addPromotionPurchaseAmount(product.getPrice() * product.getQuantity());
    }

    public void calculateMembershipDiscount() {
        amount.calculateMembershipDiscount();
    }

    public void calculateFinalPaymentAmount() {
        amount.calculateFinalPaymentAmount();
    }

    public List<Product> getBuyingProducts() {
        return buyingProducts.getProducts();
    }

    public List<Product> getFreebieProducts() {
        return freebieProducts.getProducts();
    }

    public long getTotalPurchaseAmount() {
        return amount.getTotalPurchaseAmount();
    }

    public long getPromotionDiscount() {
        return amount.getPromotionDiscount();
    }

    public long getMembershipDiscount() {
        return amount.getMembershipDiscount();
    }

    public long getFinalPaymentAmount() {
        return amount.getFinalPaymentAmount();
    }
}
