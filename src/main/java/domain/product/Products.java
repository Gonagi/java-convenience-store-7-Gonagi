package domain.product;

import java.util.Collections;
import java.util.List;

public class Products {
    private final List<Product> products;

    Products(final List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
