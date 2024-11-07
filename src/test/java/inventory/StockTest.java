package inventory;

import domain.Stock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StockTest {
    @Test
    void 상품_수량이_0미만이면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> Stock.from(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
