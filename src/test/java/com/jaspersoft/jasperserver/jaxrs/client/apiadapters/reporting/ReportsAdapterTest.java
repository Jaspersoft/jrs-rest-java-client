package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportoptions.ReportOptionsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportoptions.ReportOptionsUtil;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReorderingReportParametersAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

@PrepareForTest({ReorderingReportParametersAdapter.class, ReportParametersAdapter.class, /*RunReportAdapter.class,*/ ReportsAdapter.class, ReportOptionsUtil.class})
public class ReportsAdapterTest extends PowerMockTestCase {

    public static final String REPORT_UNIT_URI = "reportUnitUri";
    public static final String OPTIONS_ID = "optionsId";
    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private ReorderingReportParametersAdapter reportParametersAdapterMock;

    @Mock
    private ReportOptionsAdapter reportOptionsAdapterMock;

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
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* Then */
        assertSame(adapterSpy.getSessionStorage(), sessionStorageMock);
        String reportUnitUri = Whitebox.getInternalState(adapterSpy, REPORT_UNIT_URI);
        assertEquals(reportUnitUri, REPORT_UNIT_URI);
    }

    @Test
    public void should_return_proper_ReportOptionsAdapter_object() throws Exception {

        /* When */
        PowerMockito.whenNew(ReportOptionsAdapter .class).withArguments(sessionStorageMock, REPORT_UNIT_URI).thenReturn(reportOptionsAdapterMock);

        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);
        ReportOptionsAdapter retrieved = adapterSpy.reportOptions();

        assertSame(retrieved, reportOptionsAdapterMock);
        verifyNew(ReportOptionsAdapter.class, times(1)).withArguments(sessionStorageMock, REPORT_UNIT_URI);
    }


    @Test
    public void should_return_proper_ReportOptionsAdapter_object_by_optionsId() throws Exception {

        /* When */
        PowerMockito.whenNew(ReportOptionsAdapter .class).withArguments(sessionStorageMock, REPORT_UNIT_URI, OPTIONS_ID).thenReturn(reportOptionsAdapterMock);

        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);
        ReportOptionsAdapter retrieved = adapterSpy.reportOptions(OPTIONS_ID);

        assertSame(retrieved, reportOptionsAdapterMock);
        verifyNew(ReportOptionsAdapter.class, times(1)).withArguments(sessionStorageMock, REPORT_UNIT_URI, OPTIONS_ID);
    }

    @Test
    public void should_return_proper_ReportOptionsAdapter_object_by_options_map() throws Exception {
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        /* When */
        PowerMockito.whenNew(ReportOptionsAdapter .class).withArguments(sessionStorageMock, REPORT_UNIT_URI, map).thenReturn(reportOptionsAdapterMock);

        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);
        ReportOptionsAdapter retrieved = adapterSpy.reportOptions(map);

        assertSame(retrieved, reportOptionsAdapterMock);
        verifyNew(ReportOptionsAdapter.class, times(1)).withArguments(sessionStorageMock, REPORT_UNIT_URI, map);
    }

    @Test
    public void should_return_proper_ReportOptionsAdapter_object_by_options_list() throws Exception {
        List<ReportParameter> parameterList = new LinkedList<>();
        MultivaluedHashMap<String, String> map = spy(new MultivaluedHashMap<String, String>());
        /* When */
        mockStatic(ReportOptionsUtil.class);
        when(ReportOptionsUtil.toMap(parameterList)).thenReturn(map);
        PowerMockito.whenNew(ReportOptionsAdapter.class).withArguments(sessionStorageMock, REPORT_UNIT_URI, map).thenReturn(reportOptionsAdapterMock);

        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);
        ReportOptionsAdapter retrieved = adapterSpy.reportOptions(parameterList);

        assertSame(retrieved, reportOptionsAdapterMock);
        verifyNew(ReportOptionsAdapter.class, times(1)).withArguments(sessionStorageMock, REPORT_UNIT_URI, map);
    }

    @Test
    public void should_return_proper_ReorderingReportParametersAdapter_object() throws Exception {

        /* When */
        PowerMockito.whenNew(ReorderingReportParametersAdapter.class).withArguments(sessionStorageMock, REPORT_UNIT_URI).thenReturn(reportParametersAdapterMock);

        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);
        ReorderingReportParametersAdapter retrieved = adapterSpy.reportParameters();

        assertSame(retrieved, reportParametersAdapterMock);
        verifyNew(ReorderingReportParametersAdapter.class, times(1)).withArguments(sessionStorageMock, REPORT_UNIT_URI);
    }

    @Test
    public void should_return_proper_ReportParametersAdapter_instance() throws Exception {

        /* Given */
        ReportParametersAdapter parametersAdapterMock = mock(ReportParametersAdapter.class);
        whenNew(ReportParametersAdapter.class).withArguments(sessionStorageMock, REPORT_UNIT_URI, "1;2;3;4;5;").thenReturn(parametersAdapterMock);
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        ReportParametersAdapter retrieved = adapterSpy.reportParameters("1", "2", "3", "4", "5");

        /* Then */
        assertSame(retrieved, parametersAdapterMock);
        verifyNew(ReportParametersAdapter.class, times(1)).withArguments(sessionStorageMock, REPORT_UNIT_URI, "1;2;3;4;5;");
    }

    @Test
    public void should_convert_params_into_the_adapter_with_pages() throws Exception {

        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun(ReportOutputFormat.PDF, 1, 2, 3);

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(Whitebox.getInternalState(retrieved, "pages"), new String[]{"1", "2", "3"});
    }
    @Test
    public void should_convert_params_into_the_adapter_with_format() throws Exception {

        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun(ReportOutputFormat.PDF);

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(Whitebox.getInternalState(retrieved, "format"), new String("pdf"));
    }

    @Test
    public void should_convert_params_into_the_adapter_without_pages() throws Exception {

        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat.PDF, new com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.PageRange(1, 10));

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(Whitebox.getInternalState(retrieved, "pages"), new String[]{"1-10"});

    }

    @Test
    public void should_convert_params_into_the_adapter_with_pages_and_format_as_string() throws Exception {

        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun("PDF", 1, 2, 3);

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(Whitebox.getInternalState(retrieved, "pages"), new String[]{"1", "2", "3"});
        assertEquals(Whitebox.getInternalState(retrieved, "format"), "pdf");
    }
    @Test
    public void should_convert_params_into_the_adapter_with_format_as_string_in_uppercase() throws Exception {

        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun("HTML");

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(Whitebox.getInternalState(retrieved, "format"), "html");

    }
    @Test
    public void should_convert_params_into_the_adapter_with_format_as_string() throws Exception {

        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun("pdf");

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(Whitebox.getInternalState(retrieved, "format"), "pdf");

    }

    @Test
    public void should_convert_params_into_the_adapter_without_pages_as_string() throws Exception {

        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun("pdf", new com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.PageRange(1, 10));

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(Whitebox.getInternalState(retrieved, "pages"), new String[]{"1-10"});
        assertEquals(Whitebox.getInternalState(retrieved, "format"), "pdf");
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, /*formatMock, */reportAdapterMock, parametersAdapterMock, reportOptionsAdapterMock, reportParametersAdapterMock);
    }
}