package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlStateListWrapper;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

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
