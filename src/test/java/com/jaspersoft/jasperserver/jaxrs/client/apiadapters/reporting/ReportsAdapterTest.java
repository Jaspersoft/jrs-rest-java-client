package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportoptions.ReportOptionsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportoptions.ReportOptionsUtil;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jakarta.ws.rs.core.MultivaluedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class ReportsAdapterTest {

    public static final String REPORT_UNIT_URI = "reportUnitUri";
    public static final String OPTIONS_ID = "optionsId";

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private ReportOptionsAdapter reportOptionsAdapterMock;

    @Mock
    private RunReportAdapter reportAdapterMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_pass_session_to_parent_class() {
        /* When */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* Then */
        assertSame(adapterSpy.getSessionStorage(), sessionStorageMock);
        String reportUnitUri = getInternalState(adapterSpy, REPORT_UNIT_URI);
        assertEquals(reportUnitUri, REPORT_UNIT_URI);
    }

    @Test
    public void should_return_proper_ReportOptionsAdapter_object() throws Exception {
        /* When */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        try (MockedConstruction<ReportOptionsAdapter> mockedAdapter = Mockito.mockConstruction(ReportOptionsAdapter.class)) {
            ReportOptionsAdapter retrieved = adapterSpy.reportOptions();

            assertNotNull(retrieved);
        }
    }

    @Test
    public void should_return_proper_ReportOptionsAdapter_object_by_optionsId() throws Exception {
        /* When */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        try (MockedConstruction<ReportOptionsAdapter> mockedAdapter = Mockito.mockConstruction(ReportOptionsAdapter.class)) {
            ReportOptionsAdapter retrieved = adapterSpy.reportOptions(OPTIONS_ID);

            assertNotNull(retrieved);
        }
    }

    @Test
    public void should_return_proper_ReportOptionsAdapter_object_by_options_map() throws Exception {
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        /* When */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        try (MockedConstruction<ReportOptionsAdapter> mockedAdapter = Mockito.mockConstruction(ReportOptionsAdapter.class)) {
            ReportOptionsAdapter retrieved = adapterSpy.reportOptions(map);

            assertNotNull(retrieved);
        }
    }

    @Test
    public void should_return_proper_ReportOptionsAdapter_object_by_options_list() throws Exception {
        List<ReportParameter> parameterList = new LinkedList<>();
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<String, String>();

        try (MockedStatic<ReportOptionsUtil> mockedUtil = Mockito.mockStatic(ReportOptionsUtil.class);
             MockedConstruction<ReportOptionsAdapter> mockedAdapter = Mockito.mockConstruction(ReportOptionsAdapter.class)) {

            mockedUtil.when(() -> ReportOptionsUtil.toMap(parameterList)).thenReturn(map);

            ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);
            ReportOptionsAdapter retrieved = adapterSpy.reportOptions(parameterList);

            assertNotNull(retrieved);
        }
    }

    @Test
    public void should_convert_params_into_the_adapter_with_pages() throws Exception {
        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun(ReportOutputFormat.PDF, 1, 2, 3);

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(getInternalState(retrieved, "pages"), new String[]{"1", "2", "3"});
    }

    @Test
    public void should_convert_params_into_the_adapter_with_format() throws Exception {
        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun(ReportOutputFormat.PDF);

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(getInternalState(retrieved, "format"), new String("pdf"));
    }

    @Test
    public void should_convert_params_into_the_adapter_without_pages() throws Exception {
        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun(ReportOutputFormat.PDF, new com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.PageRange(1, 10));

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(getInternalState(retrieved, "pages"), new String[]{"1-10"});
    }

    @Test
    public void should_convert_params_into_the_adapter_with_pages_and_format_as_string() throws Exception {
        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun("PDF", 1, 2, 3);

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(getInternalState(retrieved, "pages"), new String[]{"1", "2", "3"});
        assertEquals(getInternalState(retrieved, "format"), "pdf");
    }

    @Test
    public void should_convert_params_into_the_adapter_with_format_as_string_in_uppercase() throws Exception {
        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun("HTML");

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(getInternalState(retrieved, "format"), "html");
    }

    @Test
    public void should_convert_params_into_the_adapter_with_format_as_string() throws Exception {
        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun("pdf");

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(getInternalState(retrieved, "format"), "pdf");
    }

    @Test
    public void should_convert_params_into_the_adapter_without_pages_as_string() throws Exception {
        /* Given */
        ReportsAdapter adapterSpy = new ReportsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* When */
        RunReportAdapter retrieved = adapterSpy.prepareForRun("pdf", new com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.PageRange(1, 10));

        /* Then */
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, REPORT_UNIT_URI), REPORT_UNIT_URI);
        assertEquals(getInternalState(retrieved, "pages"), new String[]{"1-10"});
        assertEquals(getInternalState(retrieved, "format"), "pdf");
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, reportAdapterMock, reportOptionsAdapterMock);
        mockitoSession.close();
    }

    @SuppressWarnings("unchecked")
    private static <T> T getInternalState(Object target, String fieldName) {
        try {
            Class<?> clazz = target.getClass();
            while (clazz != null) {
                try {
                    java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return (T) field.get(target);
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            throw new NoSuchFieldException(fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
