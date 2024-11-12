package service;

import camp.nextstep.edu.missionutils.Console;
import domain.product.Product;
import domain.product.Product.Builder;
import domain.product.Quantity;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import view.InputView;

class InputServiceTest {
    InputView inputView;
    InputService inputService;

    @BeforeEach
    void setUp() {
        inputView = new InputView();
        inputService = new InputService(inputView);
    }

    @AfterEach
    void closeConsole() {
        Console.close();
    }

    @DisplayName("[상품명-수량]형식이 아니면 예외가 발생한다.")
    @ValueSource(strings = {"[콜라-10] [사이다-3]", "콜라-10,사이다-3",
            "[콜라:10],[사이다:3]", "[콜라10],[사이다3]"})
    @ParameterizedTest(name = "입력값: \"{0}\"")
    void 잘못된_형식을_입력하면_예외가_발생한다(String input) {
        SystemIn(input);
        Assertions.assertThatThrownBy(() -> inputService.inputOrder())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ValueSource(strings = {"Yes", "No", "YES", "NO", "O", "X", "y", "n"})
    @ParameterizedTest(name = "입력값: \"{0}\"")
    void 여부에_대한_답변으로_Y_N_가_입력되지않으면_예외가_발생한다(String input) {
        Product product = new Builder("사이다", Quantity.from(1)).build();
        SystemIn(input);
        Assertions.assertThatThrownBy(() -> inputService.inputPromotionApplied(product))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @ValueSource(strings = {"Y", "N"})
    @ParameterizedTest(name = "입력값: \"{0}\"")
    void 여부에_대한_답변은_Y_N_만_입력가능하다(String input) {
        Product product = new Builder("사이다", Quantity.from(1)).build();
        SystemIn(input);
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {
            inputService.inputPromotionApplied(product);
        });
    }

    void SystemIn(final String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }
}


