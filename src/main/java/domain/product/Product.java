package domain.product;

import domain.promotion.Promotion;
import java.util.Objects;

public class Product {
    private final String name;
    private final int price;
    private final Quantity quantity;
    private final Promotion promotion;


    private Product(final Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.promotion = builder.promotion;
    }

    public void reduceProductStock(final int purchaseQuantity) {
        quantity.reduceQuantity(purchaseQuantity);
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Product otherProduct = (Product) obj;
        return Objects.equals(name, otherProduct.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public static class Builder {
        String name;
        int price;
        Quantity quantity;
        Promotion promotion;

        public Builder(final String name, final Quantity quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        Builder price(final int price) {
            this.price = price;
            return this;
        }

        Builder promotion(final Promotion promotion) {
            this.promotion = promotion;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
