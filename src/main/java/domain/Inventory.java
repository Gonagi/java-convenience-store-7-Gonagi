package domain;

import domain.product.Product;
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

    public void updateInventory(final Product purchaseProduct) {
        Product findProduct = findPurchaseProduct(purchaseProduct);
        checkProductStockAvailability(findProduct, purchaseProduct);
        reduceInventory(findProduct, purchaseProduct);
    }

    private Product findPurchaseProduct(final Product purchaseProduct) {
        return products.stream()
                .filter(purchaseProduct::equals)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."));
    }

    private void checkProductStockAvailability(final Product findProduct, final Product purchaseProduct) {
        if (findProduct.getQuantity() - purchaseProduct.getQuantity() < 0) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    private void reduceInventory(final Product product, final Product purchaseProduct) {
        product.reduceProductStock(purchaseProduct.getQuantity());
    }

    private Set<Product> validateDuplicateProduct(final List<Product> products) {
        Set<Product> deDuplicateProducts = new HashSet<>(products);
        if (deDuplicateProducts.size() != products.size()) {
            throw new IllegalArgumentException("[ERROR] 중복된 제품이 있어서는 안됩니다.");
        }

        return deDuplicateProducts;
    }
}
