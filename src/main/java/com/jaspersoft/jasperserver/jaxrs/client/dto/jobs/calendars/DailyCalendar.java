package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars;

public class DailyCalendar extends Calendar {

    private java.util.Calendar rangeStartingCalendar;
    private java.util.Calendar rangeEndingCalendar;
    private Boolean invertTimeRange;

    public DailyCalendar() {
        super();
        this.calendarType = TYPE_DAILY;
    }

    public java.util.Calendar getRangeStartingCalendar() {
        return rangeStartingCalendar;
    }

    public void setRangeStartingCalendar(java.util.Calendar rangeStartingCalendar) {
        this.rangeStartingCalendar = rangeStartingCalendar;
    }

    public java.util.Calendar getRangeEndingCalendar() {
        return rangeEndingCalendar;
    }

    public void setRangeEndingCalendar(java.util.Calendar rangeEndingCalendar) {
        this.rangeEndingCalendar = rangeEndingCalendar;
    }

    public Boolean getInvertTimeRange() {
        return invertTimeRange;
    }

    public void setInvertTimeRange(Boolean invertTimeRange) {
        this.invertTimeRange = invertTimeRange;
    }
}
