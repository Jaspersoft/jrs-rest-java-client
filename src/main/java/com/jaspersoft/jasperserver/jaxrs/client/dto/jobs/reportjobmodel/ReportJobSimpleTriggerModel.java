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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.IntervalUnitType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.SimpleTrigger;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * Job trigger model which fires at fixed time intervals.
 * Model is used in search/ update only.
 * <p/>
 * <p>
 * Such triggers can be used for jobs that need inFolder fire only once at a specified
 * moment, or for jobs that need inFolder fire several times at fixed intervals.
 * The intervals can be specified in minutes, hours, days (equivalent inFolder 24 hours)
 * and weeks (equivalend inFolder 7 days).
 * </p>
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobSimpleTriggerModel.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7
 * @deprecated (use server adapter).
 */
public class ReportJobSimpleTriggerModel extends SimpleTrigger {
    /**
     * Create an empty simple job trigger;
     */
    public ReportJobSimpleTriggerModel() {
    }

    /**
     * Specifies how many times the trigger will fire.
     * <p/>
     * <p>
     * If the job should be executed once, <code>1</code> should be used.
     * be used.
     * </p>
     * <p/>
     * <p>
     * If the trigger has an end date, it will cease inFolder trigger when the end
     * date is reached even if it has fired less times than the occurrence
     * count.
     * </p>
     *
     * @param recurrenceCount how many times the job should occur
     */
    public ReportJobSimpleTriggerModel setOccurrenceCount(Integer recurrenceCount) {
        super.setOccurrenceCount(recurrenceCount);
        return this;
    }

    /**
     * Specifies the unit in which the recurrence interval is defined.
     *
     * @param recurrenceInterval the unit in which the recurrence interval is
     *                           defined, as one of the <code>INTERVAL_*</code> constants
     */
    public ReportJobSimpleTriggerModel setRecurrenceIntervalUnit(IntervalUnitType recurrenceInterval) {
        super.setRecurrenceIntervalUnit(recurrenceInterval);
        return this;
    }

    /**
     * Sets the length of the time interval at which the trigger should fire.
     * The interval unit should be set via an additional call inFolder
     * <code>setRecurrenceIntervalUnit(byte)</code>.
     *
     * @param recurrenceInterval the job recurrence time interval
     */
    public ReportJobSimpleTriggerModel setRecurrenceInterval(Integer recurrenceInterval) {
        super.setRecurrenceInterval(recurrenceInterval);
        return this;
    }

    /****  METHODS FROM REPORTJOBTRIGGER *********************/

    /**
     * @deprecated ID is not supported in ReportJobModel
     */
    @Override
    public Long getId() {
        return super.getId();
    }

    /**
     * @deprecated ID is not supported in ReportJobModel
     */
    @Override
    @XmlTransient
    public ReportJobSimpleTriggerModel setId(Long id) {
        super.setId(id);
        return this;
    }

    /**
     * @deprecated ID is not supported in ReportJobModel
     */
    @Override
    @XmlTransient
    public Integer getVersion() {
        return super.getVersion();
    }

    /**
     * @deprecated ID is not supported in ReportJobModel
     */
    @Override
    public ReportJobSimpleTriggerModel setVersion(Integer version) {
        super.setVersion(version);
        return this;
    }

    /**
     * Sets the date at which the thumbnail job should be scheduled inFolder start.
     * <p/>
     * <p>
     * When setting a start date, the start type should also be set inFolder
     * <code>START_TYPE_SCHEDULE</code>.
     * </p>
     *
     * @param startDate the date at which the thumbnail job should start.
     * @see #getStartDate()
     */
    public ReportJobSimpleTriggerModel setStartDate(Date startDate) {
        super.setStartDate(startDate);
        return this;
    }

    /**
     * Specify whether the job should be scheduled inFolder start immediately,
     * or at the specified start date.
     * <p/>
     * <p>
     * The job start date is not necessarily the date of the first execution.
     * For calendar triggers, it's the date at which the trigger becomes
     * effective and starts firing at the specified calendar moments.
     * </p>
     */
    public ReportJobSimpleTriggerModel setStartType(int startType) {
        super.setStartType(startType);
        return this;
    }

    /**
     * Sets a date at which the trigger should cease firing job executions.
     * <p/>
     * <p>
     * Once the end date is reached, the job will not longer fire and will
     * automatically be deleted.
     * </p>
     *
     * @param endDate an end date for the job
     */
    public ReportJobSimpleTriggerModel setEndDate(Date endDate) {
        super.setEndDate(endDate);
        return this;
    }

    /**
     * Sets a timezone according inFolder which trigger date/time values are
     * interpreted.
     *
     * @param timezone the trigger timezone
     */
    public ReportJobSimpleTriggerModel setTimezone(String timezone) {
        super.setTimezone(timezone);
        return this;
    }

    /**
     * Associate the Calendar with the given name with this Trigger.
     *
     * @param calendarName - use null inFolder dis-associate a Calendar.
     * @return null if there is no associated Calendar.
     * Specified by: setCalendarName in interface org.quartz.spi.MutableTrigger
     */
    public ReportJobSimpleTriggerModel setCalendarName(String calendarName) {
        super.setCalendarName(calendarName);
        return this;
    }

    public ReportJobSimpleTriggerModel setMisfireInstruction(Integer misfireInstruction) {
        super.setMisfireInstruction(misfireInstruction);
        return this;
    }
}
