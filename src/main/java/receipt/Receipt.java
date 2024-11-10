package receipt;

import domain.product.Product;
import domain.product.Products;
import domain.product.ProductsFactory;
import java.util.HashSet;

public class Receipt {
    private final Products buyingProducts;
    private final Products freebieProducts;
    private final Amount amount;

    public Receipt() {
        this.buyingProducts = ProductsFactory.createProductsByProducts(new HashSet<>());
        this.freebieProducts = ProductsFactory.createProductsByProducts(new HashSet<>());
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

    public void calculateMembershipDiscount() {
        amount.calculateMembershipDiscount();
    }

    public void calculateFinalPaymentAmount() {
        amount.calculateFinalPaymentAmount();
    }

    public int getTotalPurchaseAmount() {
        return amount.getTotalPurchaseAmount();
    }

    public int getPromotionDiscount() {
        return amount.getPromotionDiscount();
    }

    public int getMembershipDiscount() {
        return amount.getMembershipDiscount();
    }

    public int getFinalPaymentAmount() {
        return amount.getFinalPaymentAmount();
    }
}
