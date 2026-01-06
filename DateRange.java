public class DateRange {
    private LocalDate start;
    private LocalDate end;

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public boolean overlaps(DateRange other) {
        if //add logic for checking if start/end lies within other
    }
}