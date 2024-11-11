package domain.inventory;

import constant.ErrorMessage;
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
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_FOUND.getMessage()));
    }

    public Product findProductByName(final Product requestProduct) {
        Optional<Product> promotionProduct = findPromotionProductByName(requestProduct);
        if (promotionProduct.isPresent()) {
            return promotionProduct.get();
        }

        Optional<Product> expiredPromotionProduct = findExpiredPromotionProductByName(requestProduct);
        return expiredPromotionProduct.orElseGet(() -> findRegularProductByName(requestProduct)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.EXCEEDS_STOCK_QUANTITY.getMessage())));
    }

    public long getProductStock(final Product requestProduct) {
        long count = 0;
        count += getProductQuantity(findPromotionProductByName(requestProduct));
        count += getProductQuantity(findExpiredPromotionProductByName(requestProduct));
        count += getProductQuantity(findRegularProductByName(requestProduct));
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
                .filter(Product::checkPromotionDate)
                .findFirst();
    }

    private Optional<Product> findExpiredPromotionProductByName(final Product requestProduct) {
        return products.stream()
                .filter(product -> product.isSameName(requestProduct.getName()))
                .filter(Product::isPromotion)
                .filter(product -> !product.checkPromotionDate())
                .findFirst();
    }

    private Set<Product> validateDuplicateProduct(final Set<Product> products) {
        Set<Product> deDuplicateProducts = new LinkedHashSet<>(products);
        if (deDuplicateProducts.size() != products.size()) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_PRODUCT_NOT_ALLOWED.getMessage());
        }

        return deDuplicateProducts;
    }

    private long getProductQuantity(Optional<Product> product) {
        return product.map(Product::getQuantity).orElse(0L);
    }

    public Set<Product> getProducts() {
        return new LinkedHashSet<>(products);
    }
}
