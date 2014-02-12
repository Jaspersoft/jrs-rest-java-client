/*
 * Copyright (C) 2005 - 2012 Jaspersoft Corporation. All rights reserved.
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.CalendarTrigger;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.MonthsSortedSetWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.WeekDaysSortedSetWrapper;

import javax.xml.bind.annotation.XmlTransient;
import java.util.SortedSet;


/**
 * Job trigger model that fires at specified calendar moments.  Model is used in search/ update only.
 *
 * <p>
 * Calendar triggers model can be used to define jobs that occur on specific month or
 * week days at certain time(s) of the day.
 * </p>
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobCalendarTriggerModel.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7
 */
public class ReportJobCalendarTriggerModel  extends CalendarTrigger {

    private boolean isMinutesModified = false;
    private boolean isHoursModified = false;
    private boolean isDaysTypeModified = false;
    private boolean isMonthDaysModified = false;
    private boolean isMonthsModified = false;
    private boolean isWeekDaysModified = false;
    private boolean isStartDateModified = false;
    private boolean isStartTypeModified = false;
    private boolean isEndDateModified = false;
    private boolean isTimezoneModified = false;
    private boolean isCalendarNameModified = false;
    private boolean isMisfireInstructionModified = false;

	/**
	 * Specifies the pattern that determines the minutes part of the trigger
	 * fire times.
	 *
	 * The pattern can consist of the following tokens:
	 * <ul>
	 * <li>
	 * A single minute value between <code>0</code> and <code>59</code>.
	 * </li>
	 * <li>
	 * A minutes range, for instance <code>0-10</code> which means that the
	 * trigger should fire every minute starting from HH:00 to HH:10.
	 * </li>
	 * <li>
	 * Minute values and ranges can be concatenated using commas as separators.
	 * </li>
	 * <li>
	 * A minute value with an increment, for instance 5/10 which means that the
	 * trigger would fire every 10 minutes starting from HH:05.
	 * </li>
	 * <li>
	 * <code>*</code> which means the the job would fire every minute of the hour.
	 * </li>
	 * </ul>
	 *
	 * @param minutes the minutes pattern to be used for the trigger
	 */
	public void setMinutes(String minutes) {
        isMinutesModified = true;
		super.setMinutes(minutes);
	}

	/**
	 * Specifies the pattern that determines the hours at which the trigger
	 * should fire.
	 *
	 * The pattern can consist of the following tokens:
	 * <ul>
	 * <li>
	 * A single hour value between <code>0</code> and <code>23</code>.
	 * </li>
	 * <li>
	 * A hours range, for instance <code>8-16</code> which means that the
	 * trigger should fire every hour starting from 8 AM to 4 PM.
	 * </li>
	 * <li>
	 * Hour values and ranges can be concatenated using commas as separators.
	 * </li>
	 * <li>
	 * A hour value with an increment, for instance 10/2 which means that the
	 * trigger would fire every 2 hours starting from 10 AM.
	 * </li>
	 * <li>
	 * <code>*</code> which means the the job would fire every hour.
	 * </li>
	 * </ul>
	 *
	 * @param hours the hours pattern to be used for the trigger
	 */
	public void setHours(String hours) {
        isHoursModified = true;
		super.setHours(hours);
	}

	/**
	 * Sets the type of days on which the trigger should fire.
	 */
	public void setDaysType(String daysType) {
        isDaysTypeModified = true;
		super.setDaysType(daysType);
	}

	/**
	 * Specifies the pattern that determines the month days on which the trigger
	 * should fire.
	 *
	 * The pattern can consist of the following tokens:
	 * <ul>
	 * <li>
	 * A single day value between <code>1</code> and <code>31</code>.
	 * </li>
	 * <li>
	 * A days range, for instance <code>2-5</code> which means that the
	 * trigger should fire every on each day starting from 2nd to 5th.
	 * </li>
	 * <li>
	 * Day values and ranges can be concatenated using commas as separators.
	 * </li>
	 * <li>
	 * A day value with an increment, for instance 1/5 which means that the
	 * trigger would fire every 5 days starting on 1st of the month.
	 * </li>
	 * <li>
	 * <code>*</code> which means the the job would fire every day.
	 * </li>
	 * </ul>
	 *
	 * @param monthDays the month days pattern to be used for the trigger
	 */
	public void setMonthDays(String monthDays) {
        isMonthDaysModified = true;
		super.setMonthDays(monthDays);
	}

	/**
	 * Sets the months on which the trigger should fire.
	 *
	 * <p>
	 * The months are specified as <code>java.lang.Byte</code> values between
	 * <code>0</code> (Jan) and <code>11</code> (Dec).
	 * </p>
	 *
	 * @param months the months as <code>java.lang.Byte</code> values
	 */
    public void setMonths(MonthsSortedSetWrapper months) {
        isMonthsModified = true;
        super.setMonths(months);
	}

	/**
	 * Sets the week days on which the trigger should fire.
	 *
	 * <p>
	 * The days are specified as <code>java.lang.Byte</code> values between
	 * <code>1</code> (Sunday) and <code>7</code> (Saturday).
	 * </p>
	 *
	 * @param weekDays the week days as <code>java.lang.Byte</code> values
	 */
	public void setWeekDays(WeekDaysSortedSetWrapper weekDays) {
        isWeekDaysModified = true;
		super.setWeekDays(weekDays);
	}

    /****  METHODS FROM REPORTJOBTRIGGER *********************/

    /**
     * @deprecated ID is not supported in ReportJobModel
     */
    @Override
    @XmlTransient
	public Long getId() {
		return super.getId();
	}
    /**
     * @deprecated ID is not supported in ReportJobModel
     */
    @Override
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
	 * Sets the date at which the report job should be scheduled to start.
	 *
	 * <p>
	 * When setting a start date, the start type should also be set to
	 * <code>START_TYPE_SCHEDULE</code>.
	 * </p>
	 *
	 * @param startDate the date at which the report job should start.
	 * @see #getStartDate()
	 */
	public void setStartDate(String startDate) {
        isStartDateModified = true;
		super.setStartDate(startDate);
	}

	/**
	 * Specify whether the job should be scheduled to start immediately,
	 * or at the specified start date.
	 *
	 * <p>
	 * The job start date is not necessarily the date of the first execution.
	 * For calendar triggers, it's the date at which the trigger becomes
	 * effective and starts firing at the specified calendar moments.
	 * </p>
	 *
	 */
	public void setStartType(byte startType) {
        isStartTypeModified = true;
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
	public void setEndDate(String endDate) {
        isEndDateModified = true;
		super.setEndDate(endDate);
	}

	/**
	 * Sets a timezone according to which trigger date/time values are
	 * interpreted.
	 *
	 * @param timezone the trigger timezone
	 */
	public void setTimezone(String timezone) {
        isTimezoneModified = true;
		super.setTimezone(timezone);
	}

    /**
	 * Associate the Calendar with the given name with this Trigger.
	 *
	 * @return null if there is no associated Calendar.
     * Specified by: setCalendarName in interface org.quartz.spi.MutableTrigger
     *
     * @param calendarName - use null to dis-associate a Calendar.
	 */
    public void setCalendarName(String calendarName) {
        isCalendarNameModified = true;
        super.setCalendarName(calendarName);
    }


    public void setMisfireInstruction(Integer misfireInstruction) {
      isMisfireInstructionModified = true;
      super.setMisfireInstruction(misfireInstruction);
    }

    /**
     * returns whether Minutes has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isMinutesModified() { return isMinutesModified; }

    /**
     * returns whether Hours has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isHoursModified() { return isHoursModified; }

    /**
     * returns whether DaysType has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isDaysTypeModified() { return isDaysTypeModified; }

    /**
     * returns whether MonthDays has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isMonthDaysModified() { return isMonthDaysModified; }

    /**
     * returns whether Months has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isMonthsModified() { return isMonthsModified; }

    /**
     * returns whether WeekDays has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isWeekDaysModified() { return isWeekDaysModified; }

    /**
     * returns whether StartDate has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isStartDateModified() { return isStartDateModified; }

    /**
     * returns whether StartType has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isStartTypeModified() { return isStartTypeModified; }

    /**
     * returns whether EndDate has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isEndDateModified() { return isEndDateModified; }

    /**
     * returns whether Timezone has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isTimezoneModified() { return isTimezoneModified; }

    /**
     * returns whether CalendarName has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isCalendarNameModified() { return isCalendarNameModified; }

    public boolean isMisfireInstructionModified() { return isMisfireInstructionModified; }

     /**
     * returns whether ReportJobCalendarTriggerModel has been modified
     *
     * @return true if the object has been modified
     */
    public boolean isModified() {
        return (isDaysTypeModified || isEndDateModified || isHoursModified || isMinutesModified || isMonthDaysModified ||
                isMonthsModified || isStartDateModified || isStartTypeModified || isTimezoneModified ||
                isWeekDaysModified  || isCalendarNameModified || isMisfireInstructionModified );
    }


}
