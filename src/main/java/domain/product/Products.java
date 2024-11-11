package domain.product;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Products {
    private final List<Product> products;

    Products(final List<Product> products) {
        this.products = products;
    }

    public void addProduct(final Product product) {
        products.add(product);
    }

    public Set<Product> getProducts() {
        return new LinkedHashSet<>(products);
    }
}
