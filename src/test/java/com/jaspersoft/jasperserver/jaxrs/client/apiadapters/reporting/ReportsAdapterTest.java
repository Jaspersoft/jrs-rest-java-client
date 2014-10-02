package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReorderingReportParametersAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

@PrepareForTest({ReorderingReportParametersAdapter.class, ReportParametersAdapter.class, /*RunReportAdapter.class,*/ ReportsAdapter.class})
public class ReportsAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private ReorderingReportParametersAdapter adapterMock;

    @Mock
    private RunReportAdapter reportAdapterMock;

    @Mock
    private ReportParametersAdapter parametersAdapterMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_session_to_parent_class() {

        /* When */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, "reportUnitUri");

        /* Than */
        assertSame(adapterSpy.getSessionStorage(), sessionStorageMock);
        String reportUnitUri = Whitebox.getInternalState(adapterSpy, "reportUnitUri");
        assertEquals(reportUnitUri, "reportUnitUri");
    }

    @Test
    public void should_return_proper_ReorderingReportParametersAdapter_object() throws Exception {

        /* When */
        PowerMockito.whenNew(ReorderingReportParametersAdapter.class).withArguments(sessionStorageMock, "reportUnitUri").thenReturn(adapterMock);

        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, "reportUnitUri");
        ReorderingReportParametersAdapter retrieved = adapterSpy.reportParameters();

        assertSame(retrieved, adapterMock);
        verifyNew(ReorderingReportParametersAdapter.class, times(1)).withArguments(sessionStorageMock, "reportUnitUri");
    }

    @Test
    public void should_return_proper_ReportParametersAdapter_instance() throws Exception {

        /* Given */
        ReportParametersAdapter parametersAdapterMock = mock(ReportParametersAdapter.class);
        whenNew(ReportParametersAdapter.class).withArguments(sessionStorageMock, "reportUnitUri", "1;2;3;4;5;").thenReturn(parametersAdapterMock);
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, "reportUnitUri");

        /* When */
        ReportParametersAdapter retrieved = adapterSpy.reportParameters("1", "2", "3", "4", "5");

        /* Than */
        assertSame(retrieved, parametersAdapterMock);
        verifyNew(ReportParametersAdapter.class, times(1)).withArguments(sessionStorageMock, "reportUnitUri", "1;2;3;4;5;");
    }

    @Test
    public void should_convert_params_into_the_adapter_with_pages() throws Exception {

        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, "reportUnitUri");

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun(ReportOutputFormat.PDF, 1, 2, 3);

        /* Than */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(retrieved, "reportUnitUri"), "reportUnitUri");
        assertEquals(Whitebox.getInternalState(retrieved, "pages"), new String[]{"1", "2", "3"});
    }

    @Test
    public void should_convert_params_into_the_adapter_without_pages() throws Exception {

        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, "reportUnitUri");

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun(ReportOutputFormat.PDF, new PageRange(1, 10));

        /* Than */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(retrieved, "reportUnitUri"), "reportUnitUri");
        assertEquals(Whitebox.getInternalState(retrieved, "pages"), new String[]{"1-10"});
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, /*formatMock, */reportAdapterMock, parametersAdapterMock, adapterMock);
    }
}