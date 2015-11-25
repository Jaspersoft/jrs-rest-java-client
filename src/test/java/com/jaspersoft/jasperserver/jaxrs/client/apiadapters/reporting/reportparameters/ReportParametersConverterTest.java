package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters;

import com.jaspersoft.jasperserver.dto.reports.ReportParameters;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlOption;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlState;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersConverter}
 *
 * @deprecated should be deleted after deleted deprecated {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersConverter
 */
@PrepareForTest({ReportParametersConverter.class})
public class ReportParametersConverterTest extends PowerMockTestCase {

    @Test
    public void should_convert_InputControlStates_into_map() {

        /* Given */
        List<InputControlState> inputControlsIds = new ArrayList<InputControlState>() {{
            add(new InputControlState().setId("id1"));
            add(new InputControlState().setId("id2"));
        }};

        /* When */
        Map<String, Object> retrieved = ReportParametersConverter.getValueMapFromInputControlStates(inputControlsIds);

        /* Then */
        Assert.assertNotNull(retrieved);
        assertTrue(retrieved.size() == 2);
    }

    @Test
    public void should_convert_InputControlStates_into_map_when_InputControlStates_has_values() {

        /* Given */
        List<InputControlState> inputControlsIds = new ArrayList<InputControlState>() {{
            add(new InputControlState().setId("id1").setValue("value1"));
            add(new InputControlState().setId("id2").setValue("value2"));
        }};

        /* When */
        Map<String, Object> retrieved = ReportParametersConverter.getValueMapFromInputControlStates(inputControlsIds);

        /* Then */
        Assert.assertNotNull(retrieved);
        assertTrue(retrieved.size() == 2);
    }

    @Test
    public void should_return_proper_ReportParameters() {

        /* Given */
        List<InputControlState> inputControlsIds = new ArrayList<InputControlState>() {{
            add(new InputControlState().setId("id1").setValue("value1"));
            add(new InputControlState().setId("id2").setValue("value2"));
        }};

        /* When */
        ReportParameters retrieved = ReportParametersConverter.toReportParameters(inputControlsIds);

        /* Then */
        assertNotNull(retrieved);
        assertTrue(retrieved.getReportParameters().size() == 2);
    }

    @Test
    public void should_create_not_null_instance() {
        assertNotNull(new ReportParametersConverter());
    }

    @Test
    public void should_return_converted_params() {

        InputControlOption option1 = new InputControlOption().setSelected(true);
        option1.setValue("OptionValue");
        List<InputControlOption> inputControlOptions = new ArrayList<InputControlOption>();
        inputControlOptions.add(option1);


        InputControlState inputControlState = new InputControlState();
        inputControlState.setId("1");
        inputControlState.setOptions(inputControlOptions);

        List<InputControlState> states = new ArrayList<InputControlState>();
        states.add(inputControlState);

        Map<String, Object> retrieved = ReportParametersConverter.getValueMapFromInputControlStates(states);

        assertNotNull(retrieved);
        String[] options = (String[]) retrieved.get("1");
        assertEquals(options[0], "OptionValue");
    }
}