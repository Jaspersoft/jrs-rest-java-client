package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportoptions;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.dto.reports.options.ReportOptionsSummary;
import com.jaspersoft.jasperserver.dto.reports.options.ReportOptionsSummaryList;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.MultivaluedHashMap;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
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
@PrepareForTest({JerseyRequest.class})
public class ReportOptionsAdapterTest extends PowerMockTestCase {

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

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, resultMock);
    }

    @Test
    public void should_pass_session_to_parent_class() {

        /* When */
        ReportOptionsAdapter adapterSpy = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI);

        /* Then */
        assertSame(adapterSpy.getSessionStorage(), sessionStorageMock);
        String reportUnitUri = Whitebox.getInternalState(adapterSpy, REPORT_UNIT_URI);
        assertEquals(reportUnitUri, REPORT_UNIT_URI);
    }

    @Test
    public void should_return_proper_operationResult_when_get() throws Exception {
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI);
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ReportOptionsSummaryList.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI}))).thenReturn(requestMock);
        PowerMockito.doReturn(resultMock).when(requestMock).get();

        OperationResult<ReportOptionsSummaryList> result = adapter.get();
        //then
        assertSame(result, resultMock);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(sessionStorageMock,
                ReportOptionsSummaryList.class,
                new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI});
        Mockito.verify(requestMock).get();
    }

    @Test
    public void should_return_proper_operationResult_when_create() throws Exception {
        MultivaluedHashMap<String, String> options = new MultivaluedHashMap<>();
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI, options);
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ReportOptionsSummary.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI}))).thenReturn(requestMock);
        PowerMockito.doReturn(resultMock).when(requestMock).post(options);

        OperationResult<ReportOptionsSummary> result = adapter.create();
        //then
        assertSame(result, resultMock);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(sessionStorageMock,
                ReportOptionsSummary.class,
                new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI});
        Mockito.verify(requestMock).post(options);
    }

    @Test
    public void should_return_proper_operationResult_when_create_with_params() throws Exception {
        MultivaluedHashMap<String, String> options = new MultivaluedHashMap<>();
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI, options);
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ReportOptionsSummary.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI}))).thenReturn(requestMock);
        PowerMockito.doReturn(requestMock).when(requestMock).addParam("label", "label");
        PowerMockito.doReturn(requestMock).when(requestMock).addParam("overwrite", "true");
        PowerMockito.doReturn(resultMock).when(requestMock).post(options);

        OperationResult<ReportOptionsSummary> result = adapter.label("label").overwrite(Boolean.TRUE).create();
        //then
        assertSame(result, resultMock);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(sessionStorageMock,
                ReportOptionsSummary.class,
                new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI});
        Mockito.verify(requestMock).addParam("label", "label");
        Mockito.verify(requestMock).addParam("overwrite", "true");
        Mockito.verify(requestMock).post(options);
    }


    @Test
    public void should_return_proper_operationResult_when_update_map() throws Exception {
        MultivaluedHashMap<String, String> options = new MultivaluedHashMap<>();
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI, OPTIONS_ID);
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ReportOptionsSummary.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID}))).thenReturn(requestMock);
        PowerMockito.doReturn(resultMock).when(requestMock).put(options);

        OperationResult<ReportOptionsSummary> result = adapter.update(options);
        //then
        assertSame(result, resultMock);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(sessionStorageMock,
                ReportOptionsSummary.class,
                new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID});
        Mockito.verify(requestMock).put(options);
    }

    @Test
    public void should_return_proper_operationResult_when_update_list() throws Exception {
        List<ReportParameter> parameterList = new LinkedList<>();
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI, OPTIONS_ID);
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ReportOptionsSummary.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID}))).thenReturn(requestMock);
        PowerMockito.doReturn(resultMock).when(requestMock).put(anyMap());

        OperationResult<ReportOptionsSummary> result = adapter.update(parameterList);
        //then
        assertSame(result, resultMock);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(sessionStorageMock,
                ReportOptionsSummary.class,
                new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID});
        Mockito.verify(requestMock).put(anyMap());
    }

    @Test
    public void should_return_proper_operationResult_when_delete() throws Exception {
        ReportOptionsAdapter adapter = new ReportOptionsAdapter(sessionStorageMock, REPORT_UNIT_URI, OPTIONS_ID);
        //when
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(Object.class), eq(new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID}))).thenReturn(requestMock);
        PowerMockito.doReturn(resultMock).when(requestMock).delete();

        OperationResult<ReportOptionsSummary> result = adapter.delete();
        //then
        assertSame(result, resultMock);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(sessionStorageMock,
                Object.class,
                new String[]{REPORT_SERVICE_URI, REPORT_UNIT_URI, OPTIONS_SERVICE_URI, OPTIONS_ID});
        Mockito.verify(requestMock).delete();
    }

}