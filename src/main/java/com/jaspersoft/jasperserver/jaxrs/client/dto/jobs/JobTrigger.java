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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.NoTimezoneDateToStringXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

public abstract class JobTrigger {

    private Long id;
    private Integer version;
    private String timezone;
    private String calendarName;
    private int startType;
    private Date startDate;
    private Date endDate;
    private Integer misfireInstruction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public int getStartType() {
        return startType;
    }

    public void setStartType(int startType) {
        this.startType = startType;
    }

    @XmlJavaTypeAdapter(NoTimezoneDateToStringXmlAdapter.class)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlJavaTypeAdapter(NoTimezoneDateToStringXmlAdapter.class)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getMisfireInstruction() {
        return misfireInstruction;
    }

    public void setMisfireInstruction(Integer misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobTrigger)) return false;

        JobTrigger trigger = (JobTrigger) o;

        if (startType != trigger.startType) return false;
        if (calendarName != null ? !calendarName.equals(trigger.calendarName) : trigger.calendarName != null)
            return false;
        if (endDate != null ? !endDate.equals(trigger.endDate) : trigger.endDate != null) return false;
        if (id != null ? !id.equals(trigger.id) : trigger.id != null) return false;
        if (misfireInstruction != null ? !misfireInstruction.equals(trigger.misfireInstruction) : trigger.misfireInstruction != null)
            return false;
        if (startDate != null ? !startDate.equals(trigger.startDate) : trigger.startDate != null) return false;
        if (timezone != null ? !timezone.equals(trigger.timezone) : trigger.timezone != null) return false;
        if (version != null ? !version.equals(trigger.version) : trigger.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (timezone != null ? timezone.hashCode() : 0);
        result = 31 * result + (calendarName != null ? calendarName.hashCode() : 0);
        result = 31 * result + startType;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (misfireInstruction != null ? misfireInstruction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobTrigger{" +
                "id=" + id +
                ", version=" + version +
                ", timezone='" + timezone + '\'' +
                ", calendarName='" + calendarName + '\'' +
                ", startType=" + startType +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", misfireInstruction=" + misfireInstruction +
                '}';
    }
}
