package domain.product;

import java.util.LinkedList;
import java.util.List;

public class Products {
    private final List<Product> products;

    Products(final List<Product> products) {
        this.products = products;
    }

    public void addProduct(final Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return new LinkedList<>(products);
    }
}
