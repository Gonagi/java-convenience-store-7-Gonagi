package domain.promotion;


import domain.utils.Parser;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

public class PromotionParser {
    public static Promotion createPromotionByParser(final String promotionsInformation) {
        List<String> promotionElements = parseElements(promotionsInformation);
        return createPromotionByElements(promotionElements);
    }

    private static Promotion createPromotionByElements(final List<String> promotionElements) {
        String name = promotionElements.get(0);
        int buy = parseNumber(promotionElements.get(1));
        int get = parseNumber(promotionElements.get(2));
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

    private static int parseNumber(final String number) {
        try {
            return Parser.parseNumber(number);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("[ERROR] buy, get 값들 중 유효하지 않은 값이 있습니다.");
        }
    }

    private static LocalDate parseDate(final String date) {
        try {
            return Parser.parseDate(date);
        } catch (DateTimeException e) {
            throw new DateTimeException("[ERROR] start_date, end_date 값들 중 유효하지 않은 값이 있습니다.");
        }
    }
}
