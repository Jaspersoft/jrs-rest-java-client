package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlOption;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlState;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControl;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.inputcontrols.InputControlStateListWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ReportParametersServiceTest extends Assert {

    private JasperserverRestClient client;
    private List<ReportInputControl> inputControls;

    public ReportParametersServiceTest() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testGetInputControlsStructure() {
        ReportInputControlsListWrapper inputControls =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .reportParameters()
                        .get()
                        .getEntity();
        assertNotEquals(inputControls, null);
        this.inputControls = inputControls.getInputParameters();
    }

    @Test(dependsOnMethods = "testGetInputControlsStructure")
    public void testReorderInputControls(){
        ReportInputControl inputControl0 = inputControls.get(0);
        inputControls.add(0, inputControls.get(1));
        inputControls.remove(1);
        inputControls.add(1, inputControl0);
        inputControls.remove(2);

        ReportInputControlsListWrapper inputControls =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .reportParameters()
                        .reorder(new ReportInputControlsListWrapper(this.inputControls))
                        .getEntity();
        assertNotEquals(inputControls, null);
    }

    @Test
    public void testGetInputControlsStructureForSpecifiedInputControls() {
        ReportInputControlsListWrapper inputControls =
                client.authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .report("/reports/samples/Cascading_multi_select_report")
                        .reportParameters("Cascading_name_single_select")
                        .get()
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
                        .reportParameters()
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
                        .reportParameters("Cascading_name_single_select")
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
                        .reportParameters()
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
                        .reportParameters("Cascading_name_single_select")
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

}
