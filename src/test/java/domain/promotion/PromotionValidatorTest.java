package domain.promotion;

import java.time.DateTimeException;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class PromotionValidatorTest {
    private static Stream<Arguments> provideMissingTestCases() {
        return Stream.of(
                Arguments.of(",2,1,2024-01-01,2024-12-31", "name"),
                Arguments.of("탄산2+1,,1,2024-01-01,2024-12-31", "buy"),
                Arguments.of("탄산2+1,2,,2024-01-01,2024-12-31", "get"),
                Arguments.of("탄산2+1,2,1,,2024-12-31", "start_date")
        );
    }

    @MethodSource("provideMissingTestCases")
    @ParameterizedTest(name = "입력값: \"{0}\", 누락된값: \"{1}\"")
    void 프로모션정보에_누락된_요소가_있으면_예외가_발생한다(String promotionInformation, String missingInformation) {
        Assertions.assertThatThrownBy(() -> PromotionParser.from(promotionInformation))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ValueSource(strings = {
            "탄산2+1,2,1,2024-01-01",
            "탄산2+1,탄산2+1,2,1,2024-01-01,2024-12-31"
    })
    @ParameterizedTest
    void 요소가_누락되거나_추가되면_예외가_발생합니다(String promotionInformation) {
        Assertions.assertThatThrownBy(() -> PromotionParser.from(promotionInformation))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void start_date가_end_date보다_이후의_날짜로_설정되어_있으면_예외가_발생한다() {
        String invalidDatePromotion = "탄산2+1,2,1,2024-11-20,2024-11-08";
        Assertions.assertThatThrownBy(() -> PromotionParser.from(invalidDatePromotion))
                .isInstanceOf(DateTimeException.class);
    }

}
