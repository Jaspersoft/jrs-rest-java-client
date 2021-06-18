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

import com.jaspersoft.jasperserver.dto.common.DeepCloneable;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.NoTimezoneDateToStringXmlAdapter;

import java.lang.reflect.Constructor;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

public abstract class JobTrigger implements DeepCloneable<JobTrigger> {

    /**
     * Start type that indicates that the job should be scheduled inFolder start
     * immediately.
     *
     * @see #getStartType()
     */
    public static final byte START_TYPE_NOW = 1;

    /**
     * Start type that indicates that the job should be scheduled inFolder start
     * at the specified start date.
     *
     * @see #getStartType()
     * @see #getStartDate()
     */
    public static final byte START_TYPE_SCHEDULE = 2;

    private Long id;
    private Integer version;
    private String timezone;
    private String calendarName;
    private int startType;
    private Date startDate;
    private Date endDate;
    private Integer misfireInstruction;

    public JobTrigger() {
    }

    public JobTrigger(JobTrigger other) {
        this.calendarName = other.calendarName;
        this.endDate = (other.endDate != null) ? new Date(other.endDate.getTime()) : null;
        this.id = other.id;
        this.misfireInstruction = other.misfireInstruction;
        this.startDate = (other.startDate != null) ? new Date(other.startDate.getTime()) : null;
        this.startType = other.startType;
        this.timezone = other.timezone;
        this.version = other.version;
    }

    public Long getId() {
        return id;
    }

    public JobTrigger setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public JobTrigger setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public JobTrigger setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public JobTrigger setCalendarName(String calendarName) {
        this.calendarName = calendarName;
        return this;
    }

    public int getStartType() {
        return startType;
    }

    public JobTrigger setStartType(int startType) {
        this.startType = startType;
        return this;
    }

    @XmlJavaTypeAdapter(NoTimezoneDateToStringXmlAdapter.class)
    public Date getStartDate() {
        return startDate;
    }

    public JobTrigger setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    @XmlJavaTypeAdapter(NoTimezoneDateToStringXmlAdapter.class)
    public Date getEndDate() {
        return endDate;
    }

    public JobTrigger setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public Integer getMisfireInstruction() {
        return misfireInstruction;
    }

    public JobTrigger setMisfireInstruction(Integer misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
        return this;
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

    @Override
    public JobTrigger deepClone() {
            Class<? extends JobTrigger> thisClass = this.getClass();

            JobTrigger instance = null;
            try {
                Constructor<? extends JobTrigger> constructor = thisClass.getConstructor(thisClass);
                instance = constructor.newInstance(this);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
            return instance;
        }
}
