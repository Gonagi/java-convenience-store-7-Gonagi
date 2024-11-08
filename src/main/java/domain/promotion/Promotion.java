package domain.promotion;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(final String name, final int buy, final int get,
                      final LocalDate startDate, final LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion of(final String name, final int buy, final int get,
                               final LocalDate startDate, final LocalDate endDate) {
        return new Promotion(name, buy, get, startDate, endDate);
    }
}
