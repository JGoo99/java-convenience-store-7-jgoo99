package store.model;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final long buyCnt;
    private final long getCnt;
    private final LocalDate start;
    private final LocalDate end;

    public Promotion(String name, long buyCnt, long getCnt, LocalDate start, LocalDate end) {
        this.name = name;
        this.buyCnt = buyCnt;
        this.getCnt = getCnt;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public boolean withinPeriod(LocalDate date) {
        return date.isAfter(start.minusDays(1)) && date.isBefore(end.plusDays(1));
    }
}
