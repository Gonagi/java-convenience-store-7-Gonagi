package domain;

import java.util.Objects;

public class Product {
    private final String name;
    private final Stock stock;

    private Product(final String name, final Stock stock) {
        this.name = name;
        this.stock = stock;
    }

    public static Product of(final String name, final Stock stock) {
        return new Product(name, stock);
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
