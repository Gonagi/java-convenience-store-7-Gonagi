package domain.promotion;

import java.time.LocalDate;
import java.util.Objects;

public class Promotion {
    private final String name;
    private final long buy;
    private final long get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(final String name, final long buy, final long get,
                      final LocalDate startDate, final LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion of(final String name, final long buy, final long get,
                               final LocalDate startDate, final LocalDate endDate) {
        return new Promotion(name, buy, get, startDate, endDate);
    }

    public String getName() {
        return name;
    }

    public long getBuy() {
        return buy;
    }

    public long getGet() {
        return get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Promotion otherPromotion = (Promotion) obj;
        return Objects.equals(name, otherPromotion.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
