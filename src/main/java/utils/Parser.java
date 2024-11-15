package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public final class Parser {
    private static final String COMMA = ",";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Parser() {
    }

    public static List<String> parseElements(final String information) {
        return Arrays.asList(information.split(COMMA));
    }

    public static long parseNumber(final String number) {
        return Long.parseLong(number);
    }

    public static LocalDate parseDate(final String date) {
        return LocalDate.parse(date, DATE_TIME_FORMATTER);
    }

    public static String[] splitInputByComma(final String input) {
        return input.split(COMMA);
    }

    public static String removeFrontBracket(final String input) {
        return input.replace("[", "").trim();
    }

    public static String removeBackBracket(final String input) {
        return input.replace("]", "").trim();
    }
}
