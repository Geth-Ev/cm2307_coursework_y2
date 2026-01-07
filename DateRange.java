public class DateRange {
    private LocalDate start;
    private LocalDate end;

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public boolean overlaps(DateRange other) {
        return !(other.start.isAfter(start) && other.end.isBefore(end))
    }
}