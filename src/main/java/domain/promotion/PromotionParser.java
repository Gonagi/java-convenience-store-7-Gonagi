package domain.promotion;


import constant.ErrorMessage;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import utils.Parser;

public class PromotionParser {
    public static Promotion createPromotionByParser(final String promotionsInformation) {
        List<String> promotionElements = parseElements(promotionsInformation);
        return createPromotionByElements(promotionElements);
    }

    private static Promotion createPromotionByElements(final List<String> promotionElements) {
        String name = promotionElements.get(0);
        long buy = parseNumber(promotionElements.get(1));
        long get = parseNumber(promotionElements.get(2));
        LocalDate startDate = parseDate(promotionElements.get(3));
        LocalDate endDate = parseDate(promotionElements.get(4));
        PromotionValidator.validateAvailableDate(startDate, endDate);
        return Promotion.of(name, buy, get, startDate, endDate);
    }

    private static List<String> parseElements(final String promotionsInformation) {
        List<String> promotionsElements = Parser.parseElements(promotionsInformation);
        PromotionValidator.validateElementsCount(promotionsElements);
        PromotionValidator.validateElementBlankOrNull(promotionsElements);

        return promotionsElements;
    }

    private static long parseNumber(final String number) {
        try {
            return Parser.parseNumber(number);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(ErrorMessage.INVALID_PROMOTION_VALUES.getMessage());
        }
    }

    private static LocalDate parseDate(final String date) {
        try {
            return Parser.parseDate(date);
        } catch (DateTimeException e) {
            throw new DateTimeException(ErrorMessage.INVALID_PROMOTION_DATES.getMessage());
        }
    }
}
