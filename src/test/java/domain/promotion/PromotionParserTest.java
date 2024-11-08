package domain.promotion;

import java.time.DateTimeException;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class PromotionParserTest {
    private static Stream<Arguments> provideInvalidNumberTestCases() {
        return Stream.of(
                Arguments.of("탄산2+1,two,1,2024-01-01,2024-12-31", "two"),
                Arguments.of("탄산2+1,2,one,2024-01-01,2024-12-31", "one"),
                Arguments.of("탄산2+1,2.0,1,2024-01-01,2024-12-31", "2.0")
        );
    }

    @ValueSource(strings = {
            "탄산2+1,2,1,2024-01-01,2024-12-31",
            "MD추천상품,1,1,2024-01-01,2024-12-31",
            "반짝할인,1,1,2024-11-01,2024-11-30"
    })
    @ParameterizedTest(name = "입력값: \"{0}\"")
    void 프로모션_문자열로_프로모션_정보를_생성한다(String promotionInformation) {
        PromotionParser.createPromotionByParser(promotionInformation);
    }

    @MethodSource("provideInvalidNumberTestCases")
    @ParameterizedTest(name = "입력값: \"{0}\", 유효하지 않은 값: \"{1}\"")
    void buy_또는_get_값들_중_유효하지_않은_값이_있으면_예외가_발생한다(String promotionInformation, String invalidNumber) {
        Assertions.assertThatThrownBy(() -> PromotionParser.createPromotionByParser(promotionInformation))
                .isInstanceOf(NumberFormatException.class);
    }

    @ValueSource(strings = {
            "탄산2+1,2,1,20240101,20241231",
            "탄산2+1,2,1,2024.01.01,2024.12.31",
            "반짝할인,1,1,24-11-01,24-11-30"
    })
    @ParameterizedTest(name = "입력값: \"{0}\"")
    void start_date_end_date_값들_중_유효하지_않은_값이_있으면_예외가_발생한다(String promotionInformation) {
        Assertions.assertThatThrownBy(() -> PromotionParser.createPromotionByParser(promotionInformation))
                .isInstanceOf(DateTimeException.class);
    }
}
