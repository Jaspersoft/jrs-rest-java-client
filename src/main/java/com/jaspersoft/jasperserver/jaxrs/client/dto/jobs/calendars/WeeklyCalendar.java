package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Arrays;

public class WeeklyCalendar extends Calendar {

    @XmlElementWrapper(name = "excludeDaysFlags")
    @XmlElement(name = "excludeDayFlag")
    private boolean[] excludeDaysFlags;

    public WeeklyCalendar() {
        super();
        this.calendarType = TYPE_WEEKLY;
    }

    public boolean[] getExcludeDaysFlags() {
        return excludeDaysFlags;
    }

    public void setExcludeDaysFlags(boolean[] excludeDaysFlags) {
        this.excludeDaysFlags = excludeDaysFlags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        WeeklyCalendar calendar = (WeeklyCalendar) o;

        if (!Arrays.equals(excludeDaysFlags, calendar.excludeDaysFlags)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (excludeDaysFlags != null ? Arrays.hashCode(excludeDaysFlags) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeeklyCalendar{" +
                "excludeDaysFlags=" + excludeDaysFlags +
                "} " + super.toString();
    }
}
