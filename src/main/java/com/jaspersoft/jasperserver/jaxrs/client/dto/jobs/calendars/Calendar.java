package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reportJobCalendar")
public abstract class Calendar {

    protected static final String TYPE_ANNUAL = "annual";
    protected static final String TYPE_BASE = "base";
    protected static final String TYPE_CRON = "cron";
    protected static final String TYPE_DAILY = "daily";
    protected static final String TYPE_HOLIDAY = "holiday";
    protected static final String TYPE_MONTHLY = "monthly";
    protected static final String TYPE_WEEKLY = "weekly";

    protected String calendarType;
    protected String description;
    protected String timeZone;

    protected Calendar(){}

    public String getCalendarType() {
        return calendarType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

}
