package domain.inventory;

import domain.product.Product;
import domain.product.Products;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Inventory {
    private final Set<Product> products;

    private Inventory(final Set<Product> products) {
        this.products = validateDuplicateProduct(products);
    }

    public static Inventory from(final Products products) {
        return new Inventory(products.getProducts());
    }

    public Product findProductByNameAndPromotion(final Product purchaseProduct) {
        return products.stream()
                .filter(product -> product.isSameName(purchaseProduct.getName()))
                .filter(purchaseProduct::equals)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."));
    }

    public Product findRegularProductByName(final Product requestProduct) {
        return products.stream()
                .filter(product -> product.isSameName(requestProduct.getName()))
                .filter(product -> !product.isPromotion())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 일반 상품입니다."));
    }

    public Product findPromotionProductByName(final Product requestProduct) {
        return products.stream()
                .filter(product -> product.isSameName(requestProduct.getName()))
                .filter(Product::isPromotion)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션 상품입니다."));
    }

    private Set<Product> validateDuplicateProduct(final Set<Product> products) {
        Set<Product> deDuplicateProducts = new HashSet<>(products);
        if (deDuplicateProducts.size() != products.size()) {
            throw new IllegalArgumentException("[ERROR] 중복된 제품이 있어서는 안됩니다.");
        }

        return deDuplicateProducts;
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }
}
