package inventory;

import domain.Inventory;
import domain.Product;
import domain.Quantity;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryTest {
    Inventory inventory;

    @BeforeEach
    void setUp() {
        Product water = Product.of("물", Quantity.from(10));
        Product coke = Product.of("콜라", Quantity.from(8));

        inventory = Inventory.from(Arrays.asList(water, coke));
    }

    @Test
    void 같은_상품이_존재해서는_안된다() {
        Product product = Product.of("사이다", Quantity.from(1));
        Product sameProduct = Product.of("사이다", Quantity.from(2));
        List<Product> products = Arrays.asList(product, sameProduct);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Inventory.from(products))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_상품은_구매할_수_없다() {
        Product purchaseProduct = Product.of("오렌지주스", Quantity.from(1));

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> inventory.updateInventory(purchaseProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 재고_이상의_수량을_초과하여_구매할_수_없다() {
        Product purchaseProduct = Product.of("물", Quantity.from(100));

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> inventory.updateInventory(purchaseProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
