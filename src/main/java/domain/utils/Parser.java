package domain.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private static final String COMMA = ",";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static List<String> parseElements(final String information) {
        return Arrays.asList(information.split(COMMA));
    }

    public static int parseNumber(final String number) {
        return Integer.parseInt(number);
    }

    public static LocalDate parseDate(final String date) {
        return LocalDate.parse(date, DATE_TIME_FORMATTER);
    }
}
