package domain;

import java.util.Objects;

public class Product {
    private final String name;
    private final Quantity quantity;

    private Product(final String name, final Quantity quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static Product of(final String name, final Quantity quantity) {
        return new Product(name, quantity);
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
}
