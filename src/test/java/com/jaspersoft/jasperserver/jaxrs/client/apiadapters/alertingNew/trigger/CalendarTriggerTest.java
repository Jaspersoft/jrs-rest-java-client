package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alertingNew.trigger;

import com.jaspersoft.jasperserver.dto.alerting.ClientAlertCalendarTrigger;
import com.jaspersoft.jasperserver.dto.alerting.ClientCalendarDaysType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class CalendarTriggerTest {

    private ClientAlertCalendarTrigger calendarTrigger = new ClientAlertCalendarTrigger();

    @BeforeMethod
    public void before() {

        calendarTrigger.
                setMinutes("1-10").
                setHours("8-16").
                setDaysType(ClientCalendarDaysType.ALL).
                setWeekDays(new TreeSet<Byte>(asList((byte) 10, (byte) 21, (byte) 12))).
                setMonthDays("1,3,5-22").
                setMonths(new TreeSet<Byte>(asList((byte)1, (byte)11, (byte)12))).
                setCalendarName("Calendar name").
                setEndDate(new Date()).
                setId(Long.valueOf(1000)).
                setMisfireInstruction(0).
                setStartDate(new Date()).
                setStartType(1).
                setTimezone("Some time zone").
                setVersion(1);
    }

    @Test
    public void should_clone_object() {
        ClientAlertCalendarTrigger cloned = (ClientAlertCalendarTrigger) calendarTrigger.deepClone();
        assertEquals(cloned, calendarTrigger);
    }
}