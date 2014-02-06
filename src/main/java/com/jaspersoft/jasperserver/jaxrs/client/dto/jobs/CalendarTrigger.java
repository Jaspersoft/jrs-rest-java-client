package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.SortedSet;

@XmlRootElement(name = "calendarTrigger")
public class CalendarTrigger extends JobTrigger {

    private String minutes;
    private String hours;
    private Byte daysType;
    private SortedSet<String> weekDays;
    private String monthDays;
    private SortedSet<String> months;

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public Byte getDaysType() {
        return daysType;
    }

    public void setDaysType(Byte daysType) {
        this.daysType = daysType;
    }

    public SortedSet<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(SortedSet<String> weekDays) {
        this.weekDays = weekDays;
    }

    public String getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(String monthDays) {
        this.monthDays = monthDays;
    }

    public SortedSet<String> getMonths() {
        return months;
    }

    public void setMonths(SortedSet<String> months) {
        this.months = months;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CalendarTrigger that = (CalendarTrigger) o;

        if (daysType != null ? !daysType.equals(that.daysType) : that.daysType != null) return false;
        if (hours != null ? !hours.equals(that.hours) : that.hours != null) return false;
        if (minutes != null ? !minutes.equals(that.minutes) : that.minutes != null) return false;
        if (monthDays != null ? !monthDays.equals(that.monthDays) : that.monthDays != null) return false;
        if (months != null ? !months.equals(that.months) : that.months != null) return false;
        if (weekDays != null ? !weekDays.equals(that.weekDays) : that.weekDays != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (minutes != null ? minutes.hashCode() : 0);
        result = 31 * result + (hours != null ? hours.hashCode() : 0);
        result = 31 * result + (daysType != null ? daysType.hashCode() : 0);
        result = 31 * result + (weekDays != null ? weekDays.hashCode() : 0);
        result = 31 * result + (monthDays != null ? monthDays.hashCode() : 0);
        result = 31 * result + (months != null ? months.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CalendarTrigger{" +
                "minutes='" + minutes + '\'' +
                ", hours='" + hours + '\'' +
                ", daysType=" + daysType +
                ", weekDays=" + weekDays +
                ", monthDays='" + monthDays + '\'' +
                ", months=" + months +
                '}';
    }
}
