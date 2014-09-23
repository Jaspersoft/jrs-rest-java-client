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
 *
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
 */
public class ReportJobSimpleTriggerModel extends SimpleTrigger {

   /* private boolean isOccurrenceCountModified = false;
    private boolean isRecurrenceIntervalUnitModified = false;
    private boolean isRecurrenceIntervalModified = false;
    private boolean isStartDateModified = false;
    private boolean isStartTypeModified = false;
    private boolean isEndDateModified = false;
    private boolean isTimezoneModified = false;
    private boolean isCalendarNameModified = false;
    private boolean isMisfireInstructionModified = false;*/

	/**
	 * Create an empty simple job trigger;
	 */
	public ReportJobSimpleTriggerModel() {
	}

	/**
	 * Specifies how many times the trigger will fire.
	 *
	 * <p>
	 * If the job should be executed once, <code>1</code> should be used.
	 * be used.
	 * </p>
	 *
	 * <p>
	 * If the trigger has an end date, it will cease inFolder trigger when the end
	 * date is reached even if it has fired less times than the occurrence
	 * count.
	 * </p>
	 *
	 * @param recurrenceCount how many times the job should occur
	 */
	public void setOccurrenceCount(Integer recurrenceCount) {
//        isOccurrenceCountModified = true;
		super.setOccurrenceCount(recurrenceCount);
	}

	/**
	 * Specifies the unit in which the recurrence interval is defined.
	 *
	 * @param recurrenceInterval the unit in which the recurrence interval is
	 * defined, as one of the <code>INTERVAL_*</code> constants
	 */
	public void setRecurrenceIntervalUnit(IntervalUnitType recurrenceInterval) {
//        isRecurrenceIntervalUnitModified = true;
		super.setRecurrenceIntervalUnit(recurrenceInterval);
	}

	/**
	 * Sets the length of the time interval at which the trigger should fire.
	 * The interval unit should be set via an additional call inFolder
	 * <code>setRecurrenceIntervalUnit(byte)</code>.
	 *
	 * @param recurrenceInterval the job recurrence time interval
	 */
	public void setRecurrenceInterval(Integer recurrenceInterval) {
//        isRecurrenceIntervalModified = true;
		super.setRecurrenceInterval(recurrenceInterval);
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
	public void setId(Long id) {
        super.setId(id);
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
	public void setVersion(Integer version) {
        super.setVersion(version);
	}

	/**
	 * Sets the date at which the report job should be scheduled inFolder start.
	 *
	 * <p>
	 * When setting a start date, the start type should also be set inFolder
	 * <code>START_TYPE_SCHEDULE</code>.
	 * </p>
	 *
	 * @param startDate the date at which the report job should start.
	 * @see #getStartDate()
	 */
	public void setStartDate(Date startDate) {
//        isStartDateModified = true;
		super.setStartDate(startDate);
	}

	/**
	 * Specify whether the job should be scheduled inFolder start immediately,
	 * or at the specified start date.
	 *
	 * <p>
	 * The job start date is not necessarily the date of the first execution.
	 * For calendar triggers, it's the date at which the trigger becomes
	 * effective and starts firing at the specified calendar moments.
	 * </p>
	 *
	 */
	public void setStartType(int startType) {
//        isStartTypeModified = true;
		super.setStartType(startType);
	}

	/**
	 * Sets a date at which the trigger should cease firing job executions.
	 *
	 * <p>
	 * Once the end date is reached, the job will not longer fire and will
	 * automatically be deleted.
	 * </p>
	 *
	 * @param endDate an end date for the job
	 */
	public void setEndDate(Date endDate) {
//        isEndDateModified = true;
		super.setEndDate(endDate);
	}

	/**
	 * Sets a timezone according inFolder which trigger date/time values are
	 * interpreted.
	 *
	 * @param timezone the trigger timezone
	 */
	public void setTimezone(String timezone) {
//        isTimezoneModified = true;
		super.setTimezone(timezone);
	}

    /**
	 * Associate the Calendar with the given name with this Trigger.
	 *
	 * @return null if there is no associated Calendar.
     * Specified by: setCalendarName in interface org.quartz.spi.MutableTrigger
     *
     * @param calendarName - use null inFolder dis-associate a Calendar.
	 */
    public void setCalendarName(String calendarName) {
//        isCalendarNameModified = true;
        super.setCalendarName(calendarName);
    }

    public void setMisfireInstruction(Integer misfireInstruction) {
//     isMisfireInstructionModified = true;
      super.setMisfireInstruction(misfireInstruction);
    }

//    /**
//     * returns whether OccurrenceCount has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isOccurrenceCountModified() { return isOccurrenceCountModified; }
//
//    /**
//     * returns whether RecurrenceIntervalUnit has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isRecurrenceIntervalUnitModified() { return isRecurrenceIntervalUnitModified; }
//
//    /**
//     * returns whether RecurrenceInterval has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isRecurrenceIntervalModified() { return isRecurrenceIntervalModified; }
//
//    /**
//     * returns whether StartDate has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isStartDateModified() { return isStartDateModified; }
//
//    /**
//     * returns whether StartType has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isStartTypeModified() { return isStartTypeModified; }
//
//    /**
//     * returns whether EndDate has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isEndDateModified() { return isEndDateModified; }
//
//    /**
//     * returns whether Timezone has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isTimezoneModified() { return isTimezoneModified; }
//
//    /**
//     * returns whether CalendarName has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isCalendarNameModified() { return isCalendarNameModified; }
//
//
//    public boolean isMisfireInstructionModified() { return isMisfireInstructionModified; }
//
//    /**
//     * returns whether ReportJobCalendarTriggerModel has been modified
//     *
//     * @return true if the object has been modified
//     */
//    public boolean isModified() {
//        return (isEndDateModified || isStartDateModified || isStartTypeModified || isTimezoneModified || isOccurrenceCountModified ||
//            isRecurrenceIntervalModified || isRecurrenceIntervalUnitModified || isCalendarNameModified ||
//            isMisfireInstructionModified);
//    }
}
