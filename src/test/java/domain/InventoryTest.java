package domain;

import domain.product.Product;
import domain.product.Product.Builder;
import domain.product.Quantity;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryTest {
    Inventory inventory;

    @BeforeEach
    void setUp() {
        Product water = new Builder("물", Quantity.from(10)).build();
        Product coke = new Builder("콜라", Quantity.from(8)).build();

        inventory = Inventory.from(Arrays.asList(water, coke));
    }

    @Test
    void 같은_상품이_존재해서는_안된다() {
        Product product = new Builder("사이다", Quantity.from(1)).build();
        Product sameProduct = new Builder("사이다", Quantity.from(2)).build();
        List<Product> products = Arrays.asList(product, sameProduct);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Inventory.from(products))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_상품은_구매할_수_없다() {
        Product purchaseProduct = new Builder("오렌지주스", Quantity.from(1)).build();

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
