package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.core.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobState;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.SingleJobOperationsAdapter}
 */
@PrepareForTest({JerseyRequest.class})
public class SingleJobOperationsAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<Job> jobRequestMock;

    @Mock
    private JerseyRequest objectJerseyRequestMock;

    @Mock
    private JerseyRequest<JobState> jobStateJerseyRequestMock;

    @Mock
    private OperationResult<JobState> jobStateOperationResultMock;

    @Mock
    private OperationResult<Job> jobOperationResultMock;

    @Mock
    private OperationResult operationResultMock;

    @Mock
    private RestClientConfiguration configurationMock;

    @Mock
    private Job jobMock;

    private final String expectedJobId = "3819";

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(testName = "constructor")
    public void should_create_proper_SingleJobOperationsAdapter_object() throws IllegalAccessException {

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Field field = field(SingleJobOperationsAdapter.class, "jobId");
        String retrievedJobId = (String) field.get(adapter);
        assertEquals(retrievedJobId, expectedJobId);
    }

    @Test(testName = "get")
    public void should_return_proper_job_operation_result_when_JRS_version_is_v5_6_1() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"/jobs", expectedJobId}))).thenReturn(jobRequestMock);
        when(jobRequestMock.get()).thenReturn(jobOperationResultMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v5_6_1);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.XML);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult<Job> retrieved = adapter.get();

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"/jobs", expectedJobId}));
        verify(jobRequestMock, times(1)).get();
        verify(jobRequestMock, times(1)).setAccept("application/job+xml");

        assertNotNull(retrieved);
        assertSame(retrieved, jobOperationResultMock);
    }

    @Test
    public void should_return_proper_job_operation_result_when_JRS_version_is_v4_7_0() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"/jobs", expectedJobId}))).thenReturn(jobRequestMock);
        when(jobRequestMock.get()).thenReturn(jobOperationResultMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v4_7_0);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult<Job> retrieved = adapter.get();

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"/jobs", expectedJobId}));
        verify(jobRequestMock, times(1)).get();
        verify(jobRequestMock, times(1)).setAccept("application/job+json");

        assertNotNull(retrieved);
        assertSame(retrieved, jobOperationResultMock);
    }

    @Test
    public void should_proper_op_result_instance() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JobState.class),
                eq(new String[]{"/jobs", expectedJobId, "/state"}))).thenReturn(jobStateJerseyRequestMock);
        when(jobStateJerseyRequestMock.get()).thenReturn(jobStateOperationResultMock);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult<JobState> retrieved = adapter.state();

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JobState.class),
                eq(new String[]{"/jobs", expectedJobId, "/state"}));
        verify(jobStateJerseyRequestMock, times(1)).get();
        assertSame(retrieved, jobStateOperationResultMock);
    }

    @Test(testName = "update")
    public void should_return_prepared_op_result_when_jrs_version_is_5_6_1() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"/jobs", expectedJobId}), any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);
        when(jobRequestMock.post(jobMock)).thenReturn(jobOperationResultMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v5_6_1);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.XML);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.XML);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult<Job> retrieved = adapter.update(jobMock);

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"/jobs", expectedJobId}), any(JobValidationErrorHandler.class));
        verify(jobRequestMock, times(1)).post(jobMock);
        verify(jobRequestMock, times(1)).setContentType("application/job+xml");
        verify(jobRequestMock, times(1)).setAccept("application/job+xml");

        assertNotNull(retrieved);
        assertSame(retrieved, jobOperationResultMock);
    }

    @Test(testName = "update")
    public void should_return_prepared_op_result_when_jrs_version_is_4_7_0() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"/jobs", expectedJobId}), any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);
        when(jobRequestMock.post(jobMock)).thenReturn(jobOperationResultMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v4_7_0);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult<Job> retrieved = adapter.update(jobMock);

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"/jobs", expectedJobId}), any(JobValidationErrorHandler.class));
        verify(jobRequestMock, times(1)).post(jobMock);
        verify(jobRequestMock, times(1)).setContentType("application/job+json");
        verify(jobRequestMock, times(1)).setAccept("application/job+json");

        assertNotNull(retrieved);
        assertSame(retrieved, jobOperationResultMock);
    }

    @Test(testName = "delete")
    public void should_proper_op_result_instance_for_deleting_operation() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class),
                eq(new String[]{"/jobs", expectedJobId}))).thenReturn(objectJerseyRequestMock);
        when(objectJerseyRequestMock.delete()).thenReturn(operationResultMock);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult retrieved = adapter.delete();

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"/jobs", expectedJobId}));
        verify(objectJerseyRequestMock, times(1)).delete();
        assertSame(retrieved, operationResultMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, jobRequestMock, jobOperationResultMock, configurationMock);
    }
}