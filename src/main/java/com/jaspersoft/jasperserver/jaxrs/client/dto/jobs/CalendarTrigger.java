/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.DaysByteXmlAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.MonthsByteXmlAdapter;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/**
 * @deprecated (use server DTO).
 */
@XmlRootElement(name = "calendarTrigger")
public class CalendarTrigger extends JobTrigger {

    private String minutes;
    private String hours;
    private CalendarDaysType daysType;
    private SortedSet<Byte> weekDays;
    private String monthDays;
    private SortedSet<Byte> months;

    public CalendarTrigger() {
        super();
    }

    public CalendarTrigger(CalendarTrigger other) {
        super(other);
        this.minutes = other.minutes;
        this.hours = other.hours;
        this.daysType = other.daysType;
        this.weekDays = (other.weekDays != null) ? new TreeSet<Byte>(other.weekDays) : null;
        this.monthDays = other.monthDays;
        this.months = (other.months != null) ? new TreeSet<Byte>(other.months) : null;
    }

    public String getMinutes() {
        return minutes;
    }

    public CalendarTrigger setMinutes(String minutes) {
        this.minutes = minutes;
        return this;
    }

    public String getHours() {
        return hours;
    }

    public CalendarTrigger setHours(String hours) {
        this.hours = hours;
        return this;
    }

    public String getMonthDays() {
        return monthDays;
    }

    public CalendarTrigger setMonthDays(String monthDays) {
        this.monthDays = monthDays;
        return this;
    }

    public CalendarDaysType getDaysType() {
        return daysType;
    }

    public CalendarTrigger setDaysType(CalendarDaysType daysType) {
        this.daysType = daysType;
        return this;
    }

    @XmlJavaTypeAdapter(DaysByteXmlAdapter.class)
    public SortedSet<Byte> getWeekDays() {
        return weekDays;
    }

    public CalendarTrigger setWeekDays(SortedSet<Byte> weekDays) {
        this.weekDays = weekDays;
        return this;
    }

    @XmlJavaTypeAdapter(MonthsByteXmlAdapter.class)
    public SortedSet<Byte> getMonths() {
        return months;
    }

    public CalendarTrigger setMonths(SortedSet<Byte> months) {
        this.months = months;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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
        int result = minutes != null ? minutes.hashCode() : 0;
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
                ", daysType='" + daysType + '\'' +
                ", weekDays=" + weekDays +
                ", monthDays='" + monthDays + '\'' +
                ", months=" + months +
                "} " + super.toString();
    }
}
