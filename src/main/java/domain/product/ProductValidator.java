package domain.product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductValidator {
    public static void validateElementsCount(final List<String> productElements) {
        if (productElements.size() < 4) {
            throw new NoSuchElementException("[ERROR] \"이름, price, quantity, 프로모션\" 중 누락된 요소가 있습니다.");
        }
        if (productElements.size() > 4) {
            throw new NoSuchElementException("[ERROR] \"이름, price, quantity, 프로모션\" 외 추가된 요소가 있습니다.");
        }
    }

    public static void validateElementBlankOrNull(final List<String> productElements) {
        long blankCount = productElements.stream()
                .filter(element -> element == null || element.isBlank())
                .count();

        if (blankCount > 0) {
            throw new IllegalArgumentException("[ERROR] 요소들 중 누락된 값이 있습니다.");
        }
    }
}
