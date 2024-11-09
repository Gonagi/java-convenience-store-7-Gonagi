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

    public void updateInventoryForRegular(final Product purchaseProduct) {
        Product findProduct = findProductByNameAndPromotion(purchaseProduct);
        checkRegularProductStockAvailability(findProduct, purchaseProduct);
        reduceProductStock(findProduct, purchaseProduct);
    }

    public void updateInventoryForPromotion(final Product purchaseProduct) {
        Product findProduct = findProductByNameAndPromotion(purchaseProduct);
        checkPromotionProductStockAvailability(findProduct, purchaseProduct);
        reduceProductStock(findProduct, purchaseProduct);
    }

    public Product findProductByNameAndPromotion(final Product purchaseProduct) {
        return products.stream()
                .filter(product -> product.isSameName(purchaseProduct.getName()))
                .filter(purchaseProduct::equals)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."));
    }

    private void checkRegularProductStockAvailability(final Product findProduct, final Product purchaseProduct) {
        if (findProduct.getQuantity() < purchaseProduct.getQuantity()) {
            throw new IllegalArgumentException("[ERROR] 일반 상품 재고가 부족합니다.");
        }
    }

    private void checkPromotionProductStockAvailability(final Product findProduct, final Product purchaseProduct) {
        if (findProduct.getQuantity() < purchaseProduct.getQuantity()) {
            throw new IllegalArgumentException("[ERROR] 프로모션 상품 재고가 부족합니다.");
        }
    }

    private void reduceProductStock(final Product product, final Product purchaseProduct) {
        product.reduceProductStock(purchaseProduct.getQuantity());
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
