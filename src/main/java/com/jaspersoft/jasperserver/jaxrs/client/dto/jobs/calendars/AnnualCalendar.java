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
}
