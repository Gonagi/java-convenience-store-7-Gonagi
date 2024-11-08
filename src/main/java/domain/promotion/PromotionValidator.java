package domain.promotion;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class PromotionValidator {
    public static void validateElementsCount(final List<String> promotionsElements) {
        if (promotionsElements.size() < 5) {
            throw new NoSuchElementException("[ERROR] \"이름, buy, get, 시작 날짜, 끝나는 날짜\" 중 누락된 요소가 있습니다.");
        }
        if (promotionsElements.size() > 5) {
            throw new NoSuchElementException("[ERROR] \"이름, buy, get, 시작 날짜, 끝나는 날짜\" 외 추가된 요소가 있습니다.");
        }
    }

    public static void validateElementBlankOrNull(final List<String> promotionsElements) {
        long blankCount = promotionsElements.stream()
                .filter(element -> element == null || element.isBlank())
                .count();

        if (blankCount > 0) {
            throw new IllegalArgumentException("[ERROR] 요소들 중 누락된 값이 있습니다.");
        }
    }

    public static void validateAvailableDate(final LocalDate startDate, final LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new DateTimeException("[ERROR] start_date가 end_date보다 이후의 날짜로 설정되어 있습니다.");
        }
    }
}
