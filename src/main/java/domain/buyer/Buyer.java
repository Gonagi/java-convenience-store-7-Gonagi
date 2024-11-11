package domain.buyer;

import domain.product.Product;
import domain.product.Products;
import domain.product.ProductsFactory;
import java.util.Set;

public class Buyer {
    private final Products products;

    private Buyer(final Products products) {
        this.products = products;
    }

    public static Buyer buyProducts(final Set<Product> requestProducts) {
        Products buyingProducts = ProductsFactory.createProductsByProducts(requestProducts);
        return new Buyer(buyingProducts);
    }

    public Set<Product> getProducts() {
        return products.getProducts();
    }
}
