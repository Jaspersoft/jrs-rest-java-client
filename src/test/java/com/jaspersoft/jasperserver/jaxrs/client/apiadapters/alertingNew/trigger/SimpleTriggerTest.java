package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alertingNew.trigger;

import com.jaspersoft.jasperserver.dto.alerting.ClientAlertSimpleTrigger;
import com.jaspersoft.jasperserver.dto.alerting.ClientIntervalUnitType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

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

    private ClientAlertSimpleTrigger simpleTrigger = new ClientAlertSimpleTrigger();

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
        ClientAlertSimpleTrigger cloned = (ClientAlertSimpleTrigger) simpleTrigger.deepClone();

        assertEquals(cloned, simpleTrigger);
    }

}