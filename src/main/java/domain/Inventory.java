package domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Inventory {
    private final Set<Product> products;

    private Inventory(final List<Product> products) {
        this.products = validateDuplicateProduct(products);
    }

    public static Inventory from(final List<Product> products) {
        return new Inventory(products);
    }

    private Set<Product> validateDuplicateProduct(final List<Product> products) {
        Set<Product> deDuplicateProducts = new HashSet<>(products);
        if (deDuplicateProducts.size() != products.size()) {
            throw new IllegalArgumentException("[ERROR] 중복된 제품이 있어서는 안됩니다.");
        }

        return deDuplicateProducts;
    }

}
