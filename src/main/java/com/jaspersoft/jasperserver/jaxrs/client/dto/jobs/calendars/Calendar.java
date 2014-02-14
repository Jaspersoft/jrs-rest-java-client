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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Calendar calendar = (Calendar) o;

        if (calendarType != null ? !calendarType.equals(calendar.calendarType) : calendar.calendarType != null)
            return false;
        if (description != null ? !description.equals(calendar.description) : calendar.description != null)
            return false;
        if (timeZone != null ? !timeZone.equals(calendar.timeZone) : calendar.timeZone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = calendarType != null ? calendarType.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (timeZone != null ? timeZone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "calendarType='" + calendarType + '\'' +
                ", description='" + description + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
