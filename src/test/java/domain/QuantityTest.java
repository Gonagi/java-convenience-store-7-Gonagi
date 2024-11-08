package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class QuantityTest {
    @Test
    void 상품_수량이_0미만이면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> Quantity.from(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
