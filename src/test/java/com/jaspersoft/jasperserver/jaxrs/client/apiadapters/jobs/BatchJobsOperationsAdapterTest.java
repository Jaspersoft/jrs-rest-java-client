package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.JobSummaryListWrapper;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.lang.reflect.Field;
import java.util.List;

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
 * Unit tests for {@link BatchJobsOperationsAdapter}
 */
@PrepareForTest({JerseyRequest.class, BatchJobsOperationsAdapter.class})
public class BatchJobsOperationsAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private OperationResult<JobSummaryListWrapper> wrapperOperationResultMock;

    @Mock
    private Job searchCriteriaMock;

    @Mock
    private JerseyRequest<JobSummaryListWrapper> jobSummaryListWrapperJerseyRequestMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
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
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JobSummaryListWrapper.class),
                eq(new String[]{"/jobs"}))).thenReturn(jobSummaryListWrapperJerseyRequestMock);
        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        adapter.search(null);

        // When
        Field field = field(BatchJobsOperationsAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) field.get(adapter);

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JobSummaryListWrapper.class),
                eq(new String[]{"/jobs"}));
        verifyPrivate(adapter, times(1)).invoke("prepareSearchRequest", null);
        verify(jobSummaryListWrapperJerseyRequestMock, times(1)).addParams(retrievedParams);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_invoke_private_prepareSearchRequest_method_with_not_null_searchCriteria() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JobSummaryListWrapper.class),
                eq(new String[]{"/jobs"}))).thenReturn(jobSummaryListWrapperJerseyRequestMock);
        BatchJobsOperationsAdapter adapter = PowerMockito.spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        PowerMockito.doReturn("criteriaJson").when(adapter, "buildJson", searchCriteriaMock);
        adapter.search(searchCriteriaMock);

        // When
        Field field = field(BatchJobsOperationsAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) field.get(adapter);

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JobSummaryListWrapper.class),
                eq(new String[]{"/jobs"}));
        verifyPrivate(adapter, times(1)).invoke("prepareSearchRequest", searchCriteriaMock);
        verify(jobSummaryListWrapperJerseyRequestMock, times(1)).addParams(retrievedParams);
        verify(jobSummaryListWrapperJerseyRequestMock, times(1))
                .addParam("example", "criteriaJson", "UTF-8");
    }

    @Test
    public void buildJson() {
        // ...
    }

    @Test
    public void buildXml() {
        // ...
    }

    @Test
    public void updateWithProcessedParameters() {
        // ...
    }

    @Test
    public void update() {
        // ...
    }

    @Test
    public void getIds() {
        // ...
    }

    @Test
    public void pause() {
        // ...
    }

    @Test
    public void resume() {
        // ...
    }

    @Test
    public void restart() {
        // ...
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, wrapperOperationResultMock, searchCriteriaMock,
                jobSummaryListWrapperJerseyRequestMock);
    }
}