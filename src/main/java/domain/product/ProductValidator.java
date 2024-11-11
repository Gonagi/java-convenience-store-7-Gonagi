package domain.product;

import constant.ErrorMessage;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductValidator {
    public static void validateElementsCount(final List<String> productElements) {
        if (productElements.size() < 4) {
            throw new NoSuchElementException(ErrorMessage.MISSING_PRODUCT_FIELDS.getMessage());
        }
        if (productElements.size() > 4) {
            throw new NoSuchElementException(ErrorMessage.ADDITIONAL_FIELDS_IN_PRODUCT.getMessage());
        }
    }

    public static void validateElementBlankOrNull(final List<String> productElements) {
        long blankCount = productElements.stream()
                .filter(element -> element == null || element.isBlank())
                .count();

        if (blankCount > 0) {
            throw new IllegalArgumentException(ErrorMessage.MISSING_VALUES_IN_FIELDS.getMessage());
        }
    }
}
