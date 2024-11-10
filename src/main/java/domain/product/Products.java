package domain.product;

import java.util.Collections;
import java.util.Set;

public class Products {
    private final Set<Product> products;

    Products(final Set<Product> products) {
        this.products = products;
    }

    public void addProduct(final Product product) {
        products.add(product);
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }
}
