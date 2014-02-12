package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.MonthsSortedSetWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.WeekDaysSortedSetWrapper;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.SortedSet;

@XmlRootElement(name = "calendarTrigger")
public class CalendarTrigger extends JobTrigger {

    private String minutes;
    private String hours;
    private String daysType;
    private WeekDaysSortedSetWrapper weekDays;
    private String monthDays;
    private MonthsSortedSetWrapper months;

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

    public String getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(String monthDays) {
        this.monthDays = monthDays;
    }

    public String getDaysType() {
        return daysType;
    }

    public void setDaysType(String daysType) {
        this.daysType = daysType;
    }

    public WeekDaysSortedSetWrapper getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(WeekDaysSortedSetWrapper weekDays) {
        this.weekDays = weekDays;
    }

    public MonthsSortedSetWrapper getMonths() {
        return months;
    }

    public void setMonths(MonthsSortedSetWrapper months) {
        this.months = months;
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
