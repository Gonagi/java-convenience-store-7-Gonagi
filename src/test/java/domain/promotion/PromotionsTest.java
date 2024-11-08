package domain.promotion;

import domain.utils.Parser;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PromotionsTest {
    @Test
    void 잘못된_파일_경로가_주어지면_예외가_발생해야_한다() {
        String wrongFilePath = "wrongFilePath";

        Assertions.assertThatThrownBy(() -> Promotions.from(wrongFilePath))
                .isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void 파일경로로_프로모션_파일을_읽어온다() {
        String filePath = "src/test/resources/promotions.md";

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {
            Promotions promotions = Promotions.from(filePath);

            Assertions.assertThat(promotions.getPromotions()).isEqualTo(createPromotions());
        });
    }

    List<Promotion> createPromotions() {
        Promotion promotion1 = Promotion.of("탄산2+1", 2, 1,
                Parser.parseDate("2024-01-01"), Parser.parseDate("2024-12-31"));
        Promotion promotion2 = Promotion.of("MD추천상품", 1, 1,
                Parser.parseDate("2024-01-01"), Parser.parseDate("2024-12-31"));
        Promotion promotion3 = Promotion.of("반짝할인", 1, 1,
                Parser.parseDate("2024-11-01"), Parser.parseDate("2024-11-30"));

        return new ArrayList<>(Arrays.asList(promotion1, promotion2, promotion3));
    }
}
