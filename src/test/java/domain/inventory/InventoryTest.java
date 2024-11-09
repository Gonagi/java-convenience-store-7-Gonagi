package domain.inventory;

import domain.product.Product;
import domain.product.Product.Builder;
import domain.product.Products;
import domain.product.ProductsFactory;
import domain.product.Quantity;
import domain.promotion.Promotions;
import java.io.FileNotFoundException;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryTest {
    Promotions promotions;
    Products products;
    Inventory inventory;

    @BeforeEach
    void setUp() {
        String promotionFilePath = "src/test/resources/promotions.md";
        String productFilePath = "src/test/resources/products.md";

        try {
            promotions = Promotions.from(promotionFilePath);
            products = ProductsFactory.createProductsByFile(productFilePath, promotions);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        inventory = Inventory.from(products);
    }

    @Test
    void 같은_상품이_존재해서는_안된다() {
        Product product = new Builder("사이다", Quantity.from(1)).build();
        Product sameProduct = new Builder("사이다", Quantity.from(2)).build();
        Products sameProducts = ProductsFactory.createProductsByProducts(Arrays.asList(product, sameProduct));

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Inventory.from(sameProducts))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_상품은_구매할_수_없다() {
        Product purchaseProduct = new Builder("민트초코", Quantity.from(1)).build();

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> inventory.updateInventory(purchaseProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 재고_이상의_수량을_초과하여_구매할_수_없다() {
        Product purchaseProduct = new Builder("물", Quantity.from(100)).build();

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> inventory.updateInventory(purchaseProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
