package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportoptions;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.dto.reports.options.ReportOptionsSummary;
import com.jaspersoft.jasperserver.dto.reports.options.ReportOptionsSummaryList;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.LinkedList;
import java.util.List;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class ReportOptionsAdapterTest {

    private static final String REPORT_UNIT_URI = "reportUnitUri";
    private static final String OPTIONS_ID = "optionsId";
    private final String REPORT_SERVICE_URI = "reports";
    private final String OPTIONS_SERVICE_URI = "options";

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest requestMock;

    @Mock
    private OperationResult resultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, requestMock, resultMock);
        mockitoSession.close();
    }

    @Test
    public void should_pass_session_to_parent_class() {
        /* When */
        ReportOptionsAdapter adapterSpy = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* Then */
        assertSame(adapterSpy.getSessionStorage(), sessionStorageMock);
        String reportUnitUri = getInternalState(adapterSpy, REPORT_UNIT_URI);
        assertEquals(reportUnitUri, REPORT_UNIT_URI);
    }

    @Test
    public void should_return_proper_operationResult_when_get() throws Exception {
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI);
        // when
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock),
                    eq(ReportOptionsSummaryList.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI}))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).get();

            OperationResult<ReportOptionsSummaryList> result = adapter.get();
            // then
            assertSame(result, resultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(sessionStorageMock,
                    ReportOptionsSummaryList.class,
                    new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI}), times(1));
            verify(requestMock).get();
        }
    }

    @Test
    public void should_return_proper_operationResult_when_create() throws Exception {
        MultivaluedHashMap<String, String> options = new MultivaluedHashMap<>();
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI, options);
        // when
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock),
                    eq(ReportOptionsSummary.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI}))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).post(options);

            OperationResult<ReportOptionsSummary> result = adapter.create();
            // then
            assertSame(result, resultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(sessionStorageMock,
                    ReportOptionsSummary.class,
                    new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI}), times(1));
            verify(requestMock).post(options);
        }
    }

    @Test
    public void should_return_proper_operationResult_when_create_with_params() throws Exception {
        MultivaluedHashMap<String, String> options = new MultivaluedHashMap<>();
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI, options);
        // when
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock),
                    eq(ReportOptionsSummary.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI}))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).addParam("label", "label");
            doReturn(requestMock).when(requestMock).addParam("overwrite", "true");
            doReturn(resultMock).when(requestMock).post(options);

            OperationResult<ReportOptionsSummary> result = adapter.label("label").overwrite(Boolean.TRUE).create();
            // then
            assertSame(result, resultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(sessionStorageMock,
                    ReportOptionsSummary.class,
                    new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI}), times(1));
            verify(requestMock).addParam("label", "label");
            verify(requestMock).addParam("overwrite", "true");
            verify(requestMock).post(options);
        }
    }

    @Test
    public void should_return_proper_operationResult_when_update_map() throws Exception {
        MultivaluedHashMap<String, String> options = new MultivaluedHashMap<>();
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI, OPTIONS_ID);
        // when
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock),
                    eq(ReportOptionsSummary.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID}))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).put(options);

            OperationResult<ReportOptionsSummary> result = adapter.update(options);
            // then
            assertSame(result, resultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(sessionStorageMock,
                    ReportOptionsSummary.class,
                    new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID}), times(1));
            verify(requestMock).put(options);
        }
    }

    @Test
    public void should_return_proper_operationResult_when_update_list() throws Exception {
        List<ReportParameter> parameterList = new LinkedList<>();
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI, OPTIONS_ID);
        // when
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock),
                    eq(ReportOptionsSummary.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID}))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).put(anyMap());

            OperationResult<ReportOptionsSummary> result = adapter.update(parameterList);
            // then
            assertSame(result, resultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(sessionStorageMock,
                    ReportOptionsSummary.class,
                    new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID}), times(1));
            verify(requestMock).put(anyMap());
        }
    }

    @Test
    public void should_return_proper_operationResult_when_delete() throws Exception {
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI, OPTIONS_ID);
        // when
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock),
                    eq(Object.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID}))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).delete();

            OperationResult<ReportOptionsSummary> result = adapter.delete();
            // then
            assertSame(result, resultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(sessionStorageMock,
                    Object.class,
                    new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID}), times(1));
            verify(requestMock).delete();
        }
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
