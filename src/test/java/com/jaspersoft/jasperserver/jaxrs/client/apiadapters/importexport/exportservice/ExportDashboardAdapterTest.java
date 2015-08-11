package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.dto.dashboard.DashboardExportExecution;
import com.jaspersoft.jasperserver.dto.dashboard.DashboardExportExecutionStatus;
import com.jaspersoft.jasperserver.dto.dashboard.DashboardParameters;
import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link JasperserverRestClient}
 */
@PrepareForTest({JerseyRequest.class, ExportDashboardAdapter.class, JasperserverRestClient.class, SessionStorage.class})
public class ExportDashboardAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<InputStream> requestInputStreamMock;

    @Mock
    private OperationResult<InputStream> operationResultInputStreamMock;

    @Mock
    private JerseyRequest<DashboardExportExecution> requestDashboardExportExecution;

    @Mock
    private OperationResult<DashboardExportExecution> operationResultDashboardExportExecution;

    @Mock
    private JerseyRequest<DashboardExportExecutionStatus> requestDashboardExportExecutionStatus;

    @Mock
    private OperationResult<DashboardExportExecutionStatus> operationResultDashboardExportExecutionStatus;

    private ExportDashboardAdapter adapterSpy;
    private String jobId = "some1meaningful2ID";
    private String dashboardUri = "dashboardUri";

    @BeforeMethod
    public void before() {
        initMocks(this);
        adapterSpy = Mockito.spy(new ExportDashboardAdapter(sessionStorageMock, dashboardUri, jobId));
    }

    @Test(testName = "JasperserverRestClient_constructor")
    public void should_create_an_instance_of_JasperserverRestClient_with_proper_fields() throws IllegalAccessException {
        // When
        Field field = field(ExportDashboardAdapter.class, "dashboardExportExecution");
        Object retrievedField = field.get(adapterSpy);

        // Then
        assertSame(adapterSpy.getSessionStorage(), sessionStorageMock);
        assertNotNull(retrievedField);
        assertTrue(instanceOf(DashboardExportExecution.class).matches(retrievedField));

        assertNotNull(((DashboardExportExecution) retrievedField).getParameters());

    }

    @Test(testName = "parameters")
    public void should_add_parameters_to_params_of_ExportTaskDto_instance() throws IllegalAccessException {

        List<String> countries = new ArrayList<String>();
        countries.add("Canada");
        countries.add("Mexica");
        ReportParameter testParam1 = new ReportParameter();
        testParam1.setName("countries");
        testParam1.setValues(countries);
        List<String> productFamily = new ArrayList<String>();
        productFamily.add("Food");
        productFamily.add("Drink");
        ReportParameter testParam2 = new ReportParameter();
        testParam1.setName("productFamily");
        testParam1.setValues(productFamily);
        final List<ReportParameter> paramList = new ArrayList<ReportParameter>();
        paramList.add(testParam1);
        paramList.add(testParam2);
        DashboardParameters params = new DashboardParameters(paramList);

        ExportDashboardAdapter adapter = spy(new ExportDashboardAdapter(sessionStorageMock, dashboardUri, jobId));

        ExportDashboardAdapter retrievedAdapter = adapter.parameters(params);
        Field field = field(ExportDashboardAdapter.class, "dashboardExportExecution");
        DashboardExportExecution dashboardExportExecution = (DashboardExportExecution) field.get(adapter);

        verify(adapter, times(1)).parameters(params);
        assertNotNull(retrievedAdapter);
        assertTrue(dashboardExportExecution.getParameters().getDashboardParameters().size() == 2);
        assertTrue(dashboardExportExecution.getParameters().getDashboardParameters().contains(testParam2));
    }

    @Test(testName = "exportReport")
    public void should_retrieve_streamed_OperationResult_object() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(InputStream.class),
                eq(new String[]{"/dashboards", dashboardUri, "/exportFormat"}))).thenReturn(requestInputStreamMock);
        Mockito.doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();

        // When
        OperationResult<InputStream> retrieved = adapterSpy.exportReport();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultInputStreamMock);
    }

    @Test(testName = "getAllJobs")
    public void should_retrieve_list_with_all_jobs() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(DashboardExportExecution.class),
                eq(new String[]{"/dashboardExecutions"}))).thenReturn(requestDashboardExportExecution);
        Mockito.doReturn(operationResultDashboardExportExecution).when(requestDashboardExportExecution).get();

        // When
        OperationResult<DashboardExportExecution> retrieved = adapterSpy.getAllJobs();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultDashboardExportExecution);
    }

    @Test(testName = "getJob")
    public void should_retrieve_dto_with_job() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(DashboardExportExecution.class),
                eq(new String[]{"/dashboardExecutions", jobId}))).thenReturn(requestDashboardExportExecution);
        Mockito.doReturn(operationResultDashboardExportExecution).when(requestDashboardExportExecution).get();

        // When
        OperationResult<DashboardExportExecution> retrieved = adapterSpy.getJob();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultDashboardExportExecution);
    }

    @Test(testName = "create")
    public void should_return_not_null_op_result() throws IllegalAccessException {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(DashboardExportExecution.class),
                eq(new String[]{"/dashboardExecutions"}),
                any(DefaultErrorHandler.class))).thenReturn(requestDashboardExportExecution);

        DashboardExportExecution dto = new DashboardExportExecution();
        Mockito.doReturn(operationResultDashboardExportExecution).when(requestDashboardExportExecution).post(dto);

        // When
        OperationResult<DashboardExportExecution> retrieved = adapterSpy.create(dto);

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultDashboardExportExecution);
    }

    @Test(testName = "getJobStatus")
    public void should_retrieve_dto_with_job_status() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(DashboardExportExecutionStatus.class),
                eq(new String[]{"/dashboardExecutions", jobId, "/status"}))).thenReturn(requestDashboardExportExecutionStatus);
        Mockito.doReturn(operationResultDashboardExportExecutionStatus).when(requestDashboardExportExecutionStatus).get();

        // When
        OperationResult<DashboardExportExecutionStatus> retrieved = adapterSpy.getJobStatus();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultDashboardExportExecutionStatus);
    }

    @Test(testName = "getExportResult")
    public void should_retrieve_streamed_Report_object() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(InputStream.class),
                eq(new String[]{"/dashboardExecutions", jobId, "/outputResource"}))).thenReturn(requestInputStreamMock);
        Mockito.doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();

        // When
        OperationResult<InputStream> retrieved = adapterSpy.getExportResult();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultInputStreamMock);
    }

    @Test(testName = "deleteJob")
    public void should_retrieve_204_result() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(DashboardExportExecution.class),
                eq(new String[]{"/dashboardExecutions", jobId}))).thenReturn(requestDashboardExportExecution);
        Mockito.doReturn(operationResultDashboardExportExecution).when(requestDashboardExportExecution).delete();

        // When
        OperationResult<DashboardExportExecution> retrieved = adapterSpy.deleteJob();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultDashboardExportExecution);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestInputStreamMock, operationResultInputStreamMock);
    }

}