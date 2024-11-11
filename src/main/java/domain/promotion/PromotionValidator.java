package domain.promotion;

import constant.ErrorMessage;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class PromotionValidator {
    public static void validateElementsCount(final List<String> promotionsElements) {
        if (promotionsElements.size() < 5) {
            throw new NoSuchElementException(ErrorMessage.MISSING_PROMOTION_FIELDS.getMessage());
        }
        if (promotionsElements.size() > 5) {
            throw new NoSuchElementException(ErrorMessage.ADDITIONAL_FIELDS_IN_PROMOTION.getMessage());
        }
    }

    public static void validateElementBlankOrNull(final List<String> promotionsElements) {
        long blankCount = promotionsElements.stream()
                .filter(element -> element == null || element.isBlank())
                .count();

        if (blankCount > 0) {
            throw new IllegalArgumentException(ErrorMessage.MISSING_VALUES_IN_FIELDS.getMessage());
        }
    }

    public static void validateAvailableDate(final LocalDate startDate, final LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new DateTimeException(ErrorMessage.START_DATE_AFTER_END_DATE.getMessage());
        }
    }
}
