package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import java.util.Date;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class SimpleTriggerTest {

    private SimpleTrigger simpleTrigger = new SimpleTrigger();

    @BeforeMethod
    public void before() {

        simpleTrigger.
                setOccurrenceCount(10).
                setRecurrenceInterval(20).
                setRecurrenceIntervalUnit(IntervalUnitType.DAY).
                setCalendarName("Calendar name").
                setEndDate(new Date()).
                setId(Long.valueOf(1000)).
                setMisfireInstruction(10).
                setStartDate(new Date()).
                setStartType(1).
                setTimezone("Some time zone").
                setVersion(1);
    }

    @Test
    public void should_clone_object() {
        SimpleTrigger cloned = (SimpleTrigger) simpleTrigger.deepClone();

        assertEquals(cloned, simpleTrigger);
    }

}