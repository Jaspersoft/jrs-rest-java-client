package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlOption;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlState;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlStateListWrapper;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientConfiguration;
import com.sun.deploy.util.ArrayUtil;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class InputControlsTest extends Assert {

    private JasperserverRestClient client;

    public InputControlsTest() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testGetInputControlsStructure() {
        ReportInputControlsListWrapper inputControls =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .inputControls()
                        .get()
                        .getEntity();
        assertNotEquals(inputControls, null);
    }

    @Test
    public void testGetReportInputParametersViaPost(){
        ReportInputControlsListWrapper inputControls =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .inputControls()
                        .secureGet()
                        .getEntity();
        assertNotEquals(inputControls, null);
    }

    @Test
    public void testGetInputControlsStructureForSpecifiedInputControls() {
        ReportInputControlsListWrapper inputControls =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .inputControls("Cascading_name_single_select")
                        .get()
                        .getEntity();
        assertNotEquals(inputControls, null);
        assertEquals(inputControls.getInputParameters().size(), 1);
    }

    @Test
    public void testGetInputControlsStructureForSpecifiedInputControlsViaPost() {
        ReportInputControlsListWrapper inputControls =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .inputControls("Cascading_name_single_select")
                        .secureGet()
                        .getEntity();
        assertNotEquals(inputControls, null);
        assertEquals(inputControls.getInputParameters().size(), 1);
    }

    @Test
    public void testGetInputControlsValues() {
        InputControlStateListWrapper inputControlsValues =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .inputControls()
                        .values()
                        .get()
                        .getEntity();
        assertNotEquals(inputControlsValues, null);
    }

    @Test
    public void testGetInputControlsValuesForSpecifiedInputControls() {
        InputControlStateListWrapper inputControlsValues =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .inputControls("Cascading_name_single_select")
                        .values()
                        .get()
                        .getEntity();
        assertNotEquals(inputControlsValues, null);
        assertEquals(inputControlsValues.getInputControlStateList().size(), 1);
    }

    @Test
    public void testUpdateInputControlsValues() {

        InputControlStateListWrapper inputControlsValues =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .inputControls()
                        .values()
                        .parameter("Cascading_name_single_select", "A & U Stalker Telecommunications, Inc")
                        .update()
                        .getEntity();
        assertNotEquals(inputControlsValues, null);
        InputControlState inputControlState =
                findControl(inputControlsValues.getInputControlStateList(), "Cascading_name_single_select");
        assertEquals(isItemsSelected(inputControlState.getOptions(), "A & U Stalker Telecommunications, Inc"), true);
    }

    @Test
    public void testUpdateInputControlsValuesForSpecifiedInputControls() {

        InputControlStateListWrapper inputControlsValues =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .inputControls("Cascading_name_single_select")
                        .values()
                        .parameter("Cascading_name_single_select", "A & U Stalker Telecommunications, Inc")
                        .update()
                        .getEntity();
        assertNotEquals(inputControlsValues, null);
        List<InputControlState> inputControlStateList = inputControlsValues.getInputControlStateList();
        assertEquals(inputControlStateList.size(), 1);
        InputControlState inputControlState = inputControlStateList.get(0);
        assertEquals(isItemsSelected(inputControlState.getOptions(), "A & U Stalker Telecommunications, Inc"), true);
    }

    private boolean isItemsSelected(List<InputControlOption> options, String item){
        boolean isItemFound = false;
        boolean isItemSelected = false;
        for (InputControlOption option : options){
                if (item.equals(option.getLabel())){
                    isItemFound = true;
                    isItemSelected = option.isSelected();
                }
        }
        return isItemFound && isItemSelected;
    }

    private InputControlState findControl(List<InputControlState> controls, String id){
        for (InputControlState control : controls){
            if (id.equals(control.getId()))
                return control;
        }
        return null;
    }

    @Test(enabled = false)
    public void testInputControls() {
        client.authenticate("jasperadmin", "jasperadmin")
                .reportingService()
                .report("/reports/samples/Cascading_multi_select_report_files/Cascading_name_single_select")
                .inputControls()
                .parameter("Country_multi_select", "Mexico")
                .secureGet();
    }

    @Test(enabled = false)
    public void testInputControlsValues() {
        client.authenticate("jasperadmin", "jasperadmin")
                .reportingService()
                .report("/reports/samples/Cascading_multi_select_report_files/Cascading_name_single_select")
                .inputControls()
                .values()
                .parameter("Country_multi_select", "Mexico")
                .update();
    }

}
