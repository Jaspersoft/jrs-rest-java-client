package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobsNew.trigger;

import com.jaspersoft.jasperserver.dto.job.ClientIntervalUnitType;
import com.jaspersoft.jasperserver.dto.job.ClientJobSimpleTrigger;
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

    private ClientJobSimpleTrigger simpleTrigger = new ClientJobSimpleTrigger();

    @BeforeMethod
    public void before() {

        simpleTrigger.
                setOccurrenceCount(10).
                setRecurrenceInterval(20).
                setRecurrenceIntervalUnit(ClientIntervalUnitType.DAY).
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
        ClientJobSimpleTrigger cloned = (ClientJobSimpleTrigger) simpleTrigger.deepClone();

        assertEquals(cloned, simpleTrigger);
    }

}