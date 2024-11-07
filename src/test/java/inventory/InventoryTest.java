package inventory;

import domain.Inventory;
import domain.Product;
import domain.Stock;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class InventoryTest {
    @Test
    void 같은_상품이_존재해서는_안된다() {
        Product product = Product.of("사이다", Stock.from(1));
        Product sameProduct = Product.of("사이다", Stock.from(2));
        List<Product> products = Arrays.asList(product, sameProduct);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Inventory.from(products))
                .isInstanceOf(IllegalArgumentException.class);
    }


}
