package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.ExcludeDaysWrapper;


public class AnnualCalendar extends Calendar {

    public AnnualCalendar() {
        super();
        this.calendarType = TYPE_ANNUAL;
    }

    private ExcludeDaysWrapper excludeDays = new ExcludeDaysWrapper();
    // true, if excludeDays is sorted
    private Boolean dataSorted;

    public ExcludeDaysWrapper getExcludeDays() {
        return excludeDays;
    }

    public void setExcludeDays(ExcludeDaysWrapper excludeDays) {
        this.excludeDays = excludeDays;
    }

    public Boolean getDataSorted() {
        return dataSorted;
    }

    public void setDataSorted(Boolean dataSorted) {
        this.dataSorted = dataSorted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AnnualCalendar that = (AnnualCalendar) o;

        if (dataSorted != null ? !dataSorted.equals(that.dataSorted) : that.dataSorted != null) return false;
        if (excludeDays != null ? !excludeDays.equals(that.excludeDays) : that.excludeDays != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (excludeDays != null ? excludeDays.hashCode() : 0);
        result = 31 * result + (dataSorted != null ? dataSorted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnnualCalendar{" +
                "excludeDays=" + excludeDays +
                ", dataSorted=" + dataSorted +
                '}';
    }
}
