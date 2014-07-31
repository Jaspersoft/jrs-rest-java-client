package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobIdListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.JobSummaryListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel.ReportJobModel;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.BatchJobsOperationsAdapter}
 */
@PrepareForTest({JerseyRequest.class, BatchJobsOperationsAdapter.class})
public class BatchJobsOperationsAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private OperationResult<JobSummaryListWrapper> wrapperOperationResultMock;

    @Mock
    private OperationResult<JobIdListWrapper> jobIdListWrapperOperationResultMock;

    @Mock
    private Job searchCriteriaMock;

    @Mock
    private JerseyRequest<JobSummaryListWrapper> jobSummaryListWrapperJerseyRequestMock;

    @Mock
    private JerseyRequest<JobIdListWrapper> jobIdListWrapperJerseyRequestMock;

    @Mock
    private ReportJobModel reportJobModelMock;

    @Mock
    private RestClientConfiguration configurationMock;

    @Mock
    private JobIdListWrapper jobIdListWrapperMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
        // replace spy here?
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_create_BatchJobsOperationsAdapter_object_with_proper_fields() throws IllegalAccessException {

        // Given
        BatchJobsOperationsAdapter adapter = new BatchJobsOperationsAdapter(sessionStorageMock);

        // When
        Field field = field(BatchJobsOperationsAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) field.get(adapter);

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertNotNull(retrievedParams);
    }

    @Test(testName = "constructor")
    @SuppressWarnings("unchecked")
    public void should_set_params_and_return_this() throws IllegalAccessException {

        // Given
        BatchJobsOperationsAdapter adapterSpy = spy(new BatchJobsOperationsAdapter(sessionStorageMock));

        // When
        BatchJobsOperationsAdapter theSameObject = adapterSpy.parameter(JobsParameter.JOB_ID, "id");
        Field field = field(BatchJobsOperationsAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) field.get(adapterSpy);

        // Than
        List<String> retrievedList = retrievedParams.get(JobsParameter.JOB_ID.getName());
        String retrievedId = retrievedList.get(0);
        List<String> noParamList = retrievedParams.get(JobsParameter.SEARCH_LABEL.getName());

        assertEquals(retrievedId, "id");
        assertNull(noParamList);
        assertSame(theSameObject, adapterSpy);
    }

    @Test(testName = "search")
    public void should_invoke_parametrized_search_method_with_null_param_and_return_op_result() {

        // Given
        BatchJobsOperationsAdapter adapterSpy = spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        doReturn(wrapperOperationResultMock).when(adapterSpy).search(null);

        // When
        OperationResult<JobSummaryListWrapper> retrieved = adapterSpy.search();

        // Than
        verify(adapterSpy, times(1)).search(null);
        assertSame(retrieved, wrapperOperationResultMock);
    }

    @Test(testName = "search")
    public void should_invoke_private_method_and_return_op_result() throws Exception {

        // Given        
        mockStatic(JerseyRequest.class);
        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        PowerMockito
                .doReturn(jobSummaryListWrapperJerseyRequestMock)
                .when(adapter, "prepareSearchRequest", searchCriteriaMock);
        when(jobSummaryListWrapperJerseyRequestMock.get()).thenReturn(wrapperOperationResultMock);

        // When
        OperationResult<JobSummaryListWrapper> retrieved = adapter.search(searchCriteriaMock);

        // Than
        verifyPrivate(adapter, times(1)).invoke("prepareSearchRequest", eq(searchCriteriaMock));
        verify(jobSummaryListWrapperJerseyRequestMock, times(1)).get();
        assertSame(retrieved, wrapperOperationResultMock);
    }

    @Test(testName = "prepareSearchRequest")
    @SuppressWarnings("unchecked")
    public void should_invoke_private_prepareSearchRequest_method_with_null_argument() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(JobSummaryListWrapper.class),
                eq(new String[]{"/jobs"}))).thenReturn(jobSummaryListWrapperJerseyRequestMock);
        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        adapter.search(null);

        // When
        Field field = field(BatchJobsOperationsAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) field.get(adapter);

        // Than
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(JobSummaryListWrapper.class),
                eq(new String[]{"/jobs"}));
        verifyPrivate(adapter, times(1)).invoke("prepareSearchRequest", null);
        verify(jobSummaryListWrapperJerseyRequestMock, times(1)).addParams(retrievedParams);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_invoke_private_prepareSearchRequest_method_with_not_null_searchCriteria() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(JobSummaryListWrapper.class),
                eq(new String[]{"/jobs"}))).thenReturn(jobSummaryListWrapperJerseyRequestMock);
        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        PowerMockito.doReturn("criteriaJson").when(adapter, "buildJson", searchCriteriaMock);
        adapter.search(searchCriteriaMock);

        // When
        Field field = field(BatchJobsOperationsAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) field.get(adapter);

        // Than
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(JobSummaryListWrapper.class),
                eq(new String[]{"/jobs"}));
        verifyPrivate(adapter, times(1)).invoke("prepareSearchRequest", searchCriteriaMock);
        verify(jobSummaryListWrapperJerseyRequestMock, times(1)).addParams(retrievedParams);
        verify(jobSummaryListWrapperJerseyRequestMock, times(1))
                .addParam("example", "criteriaJson", "UTF-8");
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void updateWithProcessedParameters() {
        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        adapter.updateWithProcessedParameters(reportJobModelMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_update_jobs_with_specified_ids_for_json_content() throws Exception {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(sessionStorageMock, JobIdListWrapper.class, new String[]{"/jobs"})).thenReturn(jobIdListWrapperJerseyRequestMock);

        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));

        Mockito.when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        Mockito.when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
        Mockito.when(jobIdListWrapperJerseyRequestMock.post(anyString())).thenReturn(jobIdListWrapperOperationResultMock);
        PowerMockito.doReturn("testJsonContent").when(adapter, "buildJson", reportJobModelMock);

        Field params = field(BatchJobsOperationsAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) params.get(adapter);

        // When
        OperationResult<JobIdListWrapper> retrieved = adapter.update(reportJobModelMock);

        // Than
        PowerMockito.verifyStatic(times(1));
        buildRequest(sessionStorageMock, JobIdListWrapper.class, new String[]{"/jobs"});

        Mockito.verify(jobIdListWrapperJerseyRequestMock, times(1)).addParams(retrievedParams);
        Mockito.verify(jobIdListWrapperJerseyRequestMock, times(1)).post("testJsonContent");
        Mockito.verifyNoMoreInteractions(jobIdListWrapperJerseyRequestMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_update_jobs_with_specified_ids_for_xml_content() throws Exception {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(sessionStorageMock, JobIdListWrapper.class, new String[]{"/jobs"})).thenReturn(jobIdListWrapperJerseyRequestMock);

        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));

        Mockito.when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        Mockito.when(configurationMock.getContentMimeType()).thenReturn(MimeType.XML);
        Mockito.when(jobIdListWrapperJerseyRequestMock.post(anyString())).thenReturn(jobIdListWrapperOperationResultMock);
        PowerMockito.doReturn("testXmlContent").when(adapter, "buildXml", reportJobModelMock);

        Field params = field(BatchJobsOperationsAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) params.get(adapter);

        // When
        OperationResult<JobIdListWrapper> retrieved = adapter.update(reportJobModelMock);

        // Than
        PowerMockito.verifyStatic(times(1));
        buildRequest(sessionStorageMock, JobIdListWrapper.class, new String[]{"/jobs"});

        Mockito.verify(jobIdListWrapperJerseyRequestMock, times(1)).addParams(retrievedParams);
        Mockito.verify(jobIdListWrapperJerseyRequestMock, times(1)).post("testXmlContent");
        Mockito.verifyNoMoreInteractions(jobIdListWrapperJerseyRequestMock);

        assertSame(retrieved, jobIdListWrapperOperationResultMock);
    }

    @Test
    public void getIds() {
        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        // TODO...
    }

    @Test
    public void should_pause_all_jobs_with_corresponding_ids() throws Exception {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        ArrayList<Long> ids = new ArrayList<Long>();
        PowerMockito.whenNew(JobIdListWrapper.class).withArguments(ids).thenReturn(jobIdListWrapperMock);
        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        PowerMockito.when(buildRequest(sessionStorageMock, JobIdListWrapper.class, new String[]{"/jobs", "/pause"})).thenReturn(jobIdListWrapperJerseyRequestMock);
        PowerMockito.when(jobIdListWrapperJerseyRequestMock.post(jobIdListWrapperMock)).thenReturn(jobIdListWrapperOperationResultMock);

        // When
        OperationResult<JobIdListWrapper> retrieved = adapter.pause();

        // Than
        assertNotNull(retrieved);

        PowerMockito.verifyStatic(times(1));
        buildRequest(sessionStorageMock, JobIdListWrapper.class, new String[]{"/jobs", "/pause"});

        Mockito.verify(jobIdListWrapperJerseyRequestMock, times(1)).post(jobIdListWrapperMock);
    }

    @Test
    public void should_resume_all_jobs_with_corresponding_ids() throws Exception {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        ArrayList<Long> ids = new ArrayList<Long>();
        PowerMockito.whenNew(JobIdListWrapper.class).withArguments(ids).thenReturn(jobIdListWrapperMock);
        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        PowerMockito.when(buildRequest(sessionStorageMock, JobIdListWrapper.class, new String[]{"/jobs", "/resume"})).thenReturn(jobIdListWrapperJerseyRequestMock);
        PowerMockito.when(jobIdListWrapperJerseyRequestMock.post(jobIdListWrapperMock)).thenReturn(jobIdListWrapperOperationResultMock);

        // When
        OperationResult<JobIdListWrapper> retrieved = adapter.resume();

        // Than
        assertNotNull(retrieved);

        PowerMockito.verifyStatic(times(1));
        buildRequest(sessionStorageMock, JobIdListWrapper.class, new String[]{"/jobs", "/resume"});
        Mockito.verify(jobIdListWrapperJerseyRequestMock, times(1)).post(jobIdListWrapperMock);
    }

    @Test
    public void should_restart_all_jobs_with_corresponding_ids() throws Exception {
        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        ArrayList<Long> ids = new ArrayList<Long>();
        PowerMockito.whenNew(JobIdListWrapper.class).withArguments(ids).thenReturn(jobIdListWrapperMock);
        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        PowerMockito.when(buildRequest(sessionStorageMock, JobIdListWrapper.class, new String[]{"/jobs", "/restart"})).thenReturn(jobIdListWrapperJerseyRequestMock);
        PowerMockito.when(jobIdListWrapperJerseyRequestMock.post(jobIdListWrapperMock)).thenReturn(jobIdListWrapperOperationResultMock);

        // When
        OperationResult<JobIdListWrapper> retrieved = adapter.restart();

        // Than
        assertNotNull(retrieved);

        PowerMockito.verifyStatic(times(1));
        buildRequest(sessionStorageMock, JobIdListWrapper.class, new String[]{"/jobs", "/restart"});
        Mockito.verify(jobIdListWrapperJerseyRequestMock, times(1)).post(jobIdListWrapperMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, wrapperOperationResultMock, searchCriteriaMock,
                jobSummaryListWrapperJerseyRequestMock, reportJobModelMock, jobIdListWrapperOperationResultMock,
                configurationMock, jobIdListWrapperMock);
    }
}