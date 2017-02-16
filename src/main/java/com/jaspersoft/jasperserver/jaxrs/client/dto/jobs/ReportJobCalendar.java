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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.ExcludeDaysXmlAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.TimeZoneXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportJobCalendar.java 23844 2012-05-22 06:23:41Z ykovalchyk $
 * @deprecated (use server DTO).
 */
@XmlRootElement
public class ReportJobCalendar {

    private String calendarType;
    // base fields
    private ReportJobCalendar baseCalendar;
    private String description;
    private TimeZone timeZone;

    //annual calendar's fields
    //excludeDays field used for holiday calendar too
    private ArrayList<Calendar> excludeDays = new ArrayList<Calendar>();
    // true, if excludeDays is sorted
    private Boolean dataSorted;

    //cron calendar's fields
    private String cronExpression;

    //daily calendar's fields
    private Calendar rangeStartingCalendar;
    private Calendar rangeEndingCalendar;
    private Boolean invertTimeRange;

    //monthly calendar's fields
    // An array inFolder store a months days which are inFolder be excluded.
    // java.util.Calendar.get( ) as index.
    private boolean[] excludeDaysFlags;

    public ReportJobCalendar() {
    }

    public ReportJobCalendar(ReportJobCalendar other) {
        this.baseCalendar = (other.baseCalendar != null) ? new ReportJobCalendar(other.baseCalendar) : null;
        this.calendarType = other.calendarType;
        this.cronExpression = other.cronExpression;
        this.dataSorted = other.dataSorted;
        this.description = other.description;
        if (other.excludeDays != null) {
            this.excludeDays = new ArrayList<Calendar>();
            for (Calendar excludeDay : other.excludeDays) {
                this.excludeDays.add((Calendar) excludeDay.clone());
            }
        }
        if (other.excludeDaysFlags != null) {
            this.excludeDaysFlags = new boolean[other.excludeDaysFlags.length];
            for (int i = 0; i < other.excludeDaysFlags.length; i++) {
                this.excludeDaysFlags[i] = other.excludeDaysFlags[i];
            }
        }
        this.invertTimeRange = other.invertTimeRange;
        this.rangeEndingCalendar = (other.rangeEndingCalendar != null) ?
                (Calendar) other.rangeEndingCalendar.clone() : null;
        this.rangeStartingCalendar = (other.rangeStartingCalendar != null) ?
                (Calendar) other.rangeStartingCalendar.clone() : null;
        this.timeZone = (other.timeZone != null) ? (TimeZone) other.timeZone.clone() : null;
    }

    public String getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(String calendarType) {
        this.calendarType = calendarType;
    }

    public ReportJobCalendar getBaseCalendar() {
        return baseCalendar;
    }

    public void setBaseCalendar(ReportJobCalendar baseCalendar) {
        this.baseCalendar = baseCalendar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @XmlJavaTypeAdapter(TimeZoneXmlAdapter.class)
    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @XmlJavaTypeAdapter(ExcludeDaysXmlAdapter.class)
    public ArrayList<Calendar> getExcludeDays() {
        return excludeDays;
    }

    public void setExcludeDays(ArrayList<Calendar> excludeDays) {
        this.excludeDays = excludeDays;
    }

    public Boolean isDataSorted() {
        return dataSorted;
    }

    public void setDataSorted(Boolean dataSorted) {
        this.dataSorted = dataSorted;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Calendar getRangeStartingCalendar() {
        return rangeStartingCalendar;
    }

    public void setRangeStartingCalendar(Calendar rangeStartingCalendar) {
        this.rangeStartingCalendar = rangeStartingCalendar;
    }

    public Calendar getRangeEndingCalendar() {
        return rangeEndingCalendar;
    }

    public void setRangeEndingCalendar(Calendar rangeEndingCalendar) {
        this.rangeEndingCalendar = rangeEndingCalendar;
    }

    public Boolean isInvertTimeRange() {
        return invertTimeRange;
    }

    public void setInvertTimeRange(Boolean invertTimeRange) {
        this.invertTimeRange = invertTimeRange;
    }

    @XmlElementWrapper(name = "excludeDaysFlags")
    @XmlElement(name = "excludeDayFlag")
    public boolean[] getExcludeDaysFlags() {
        return excludeDaysFlags;
    }

    public void setExcludeDaysFlags(boolean[] excludeDaysFlags) {
        this.excludeDaysFlags = excludeDaysFlags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportJobCalendar)) return false;

        ReportJobCalendar that = (ReportJobCalendar) o;

        if (baseCalendar != null ? !baseCalendar.equals(that.baseCalendar) : that.baseCalendar != null) return false;
        if (calendarType != null ? !calendarType.equals(that.calendarType) : that.calendarType != null) return false;
        if (cronExpression != null ? !cronExpression.equals(that.cronExpression) : that.cronExpression != null)
            return false;
        if (dataSorted != null ? !dataSorted.equals(that.dataSorted) : that.dataSorted != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (excludeDays != null ? !excludeDays.equals(that.excludeDays) : that.excludeDays != null) return false;
        if (!Arrays.equals(excludeDaysFlags, that.excludeDaysFlags)) return false;
        if (invertTimeRange != null ? !invertTimeRange.equals(that.invertTimeRange) : that.invertTimeRange != null)
            return false;
        if (rangeEndingCalendar != null ? !rangeEndingCalendar.equals(that.rangeEndingCalendar) : that.rangeEndingCalendar != null)
            return false;
        if (rangeStartingCalendar != null ? !rangeStartingCalendar.equals(that.rangeStartingCalendar) : that.rangeStartingCalendar != null)
            return false;
        if (timeZone != null ? !timeZone.equals(that.timeZone) : that.timeZone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = calendarType != null ? calendarType.hashCode() : 0;
        result = 31 * result + (baseCalendar != null ? baseCalendar.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (timeZone != null ? timeZone.hashCode() : 0);
        result = 31 * result + (excludeDays != null ? excludeDays.hashCode() : 0);
        result = 31 * result + (dataSorted != null ? dataSorted.hashCode() : 0);
        result = 31 * result + (cronExpression != null ? cronExpression.hashCode() : 0);
        result = 31 * result + (rangeStartingCalendar != null ? rangeStartingCalendar.hashCode() : 0);
        result = 31 * result + (rangeEndingCalendar != null ? rangeEndingCalendar.hashCode() : 0);
        result = 31 * result + (invertTimeRange != null ? invertTimeRange.hashCode() : 0);
        result = 31 * result + (excludeDaysFlags != null ? Arrays.hashCode(excludeDaysFlags) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportJobCalendar{" +
                "calendarType='" + calendarType + '\'' +
                ", baseCalendar=" + baseCalendar +
                ", description='" + description + '\'' +
                ", timeZone=" + timeZone +
                ", excludeDays=" + excludeDays +
                ", dataSorted=" + dataSorted +
                ", cronExpression='" + cronExpression + '\'' +
                ", rangeStartingCalendar=" + rangeStartingCalendar +
                ", rangeEndingCalendar=" + rangeEndingCalendar +
                ", invertTimeRange=" + invertTimeRange +
                ", excludeDaysFlags=" + Arrays.toString(excludeDaysFlags) +
                '}';
    }
}
