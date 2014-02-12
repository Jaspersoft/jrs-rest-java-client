/*
* Copyright (C) 2005 - 2009 Jaspersoft Corporation. All rights  reserved.
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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.adapters.ExcludeDaysXmlAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.adapters.TimeZoneXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportJobCalendar.java 23844 2012-05-22 06:23:41Z ykovalchyk $
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
    // An array to store a months days which are to be excluded.
    // java.util.Calendar.get( ) as index.
    private boolean[] excludeDaysFlags;


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
}
