package domain.inventory;

import domain.product.Product;
import domain.product.Products;
import java.util.LinkedHashSet;
import java.util.Optional;
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

    public Product findProductByName(final Product requestProduct) {
        Optional<Product> promotionProduct = findPromotionProductByName(requestProduct);

        return promotionProduct.orElseGet(() -> findRegularProductByName(requestProduct)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.")));
    }

    public int getProductStock(final Product requestProduct) {
        int count = 0;
        Optional<Product> promotionProduct = findPromotionProductByName(requestProduct);
        Optional<Product> regularProduct = findRegularProductByName(requestProduct);
        if (promotionProduct.isPresent()) {
            count += promotionProduct.get().getQuantity();
        }
        if (regularProduct.isPresent()) {
            count += regularProduct.get().getQuantity();
        }
        return count;
    }

    private Optional<Product> findRegularProductByName(final Product requestProduct) {
        return products.stream()
                .filter(product -> product.isSameName(requestProduct.getName()))
                .filter(product -> !product.isPromotion())
                .findFirst();
    }

    private Optional<Product> findPromotionProductByName(final Product requestProduct) {
        return products.stream()
                .filter(product -> product.isSameName(requestProduct.getName()))
                .filter(Product::isPromotion)
                .findFirst();
    }

    private Set<Product> validateDuplicateProduct(final Set<Product> products) {
        Set<Product> deDuplicateProducts = new LinkedHashSet<>(products);
        if (deDuplicateProducts.size() != products.size()) {
            throw new IllegalArgumentException("[ERROR] 중복된 제품이 있어서는 안됩니다.");
        }

        return deDuplicateProducts;
    }

    public Set<Product> getProducts() {
        return new LinkedHashSet<>(products);
    }
}
