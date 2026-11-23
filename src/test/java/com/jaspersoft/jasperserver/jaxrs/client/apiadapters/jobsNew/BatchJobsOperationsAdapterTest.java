package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobsNew;

import com.jaspersoft.jasperserver.dto.job.ClientReportJob;
import com.jaspersoft.jasperserver.dto.job.wrappers.ClientJobSummariesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.BatchJobsOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
public class BatchJobsOperationsAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientJobSummariesListWrapper> jobSummaryListWrapperJerseyRequest;

    @Mock
    private OperationResult<ClientJobSummariesListWrapper> jobSummaryListWrapperOperationResult;

    @Mock
    private RestClientConfiguration configurationMock;

    @Mock
    private ClientReportJob jobMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, jobSummaryListWrapperJerseyRequest, jobSummaryListWrapperOperationResult,
                configurationMock, jobMock);
        mockitoSession.close();
    }

    @Test(testName = "constructor")
    public void should_create_proper_SingleJobOperationsAdapter_object() throws IllegalAccessException {

        // When
        BatchJobsOperationsAdapter adapter = new BatchJobsOperationsAdapter(sessionStorageMock);

        // Then
        assertEquals(getInternalState(adapter, "sessionStorage"), sessionStorageMock);
        assertEquals(getInternalState(adapter, "params"), new MultivaluedHashMap<String, String>());
    }

    @Test
    public void should_return_all_jobs() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            // Given
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock),
                    eq(ClientJobSummariesListWrapper.class),
                    eq(new String[]{"jobs"}))).thenReturn(jobSummaryListWrapperJerseyRequest);
            doReturn(jobSummaryListWrapperOperationResult).when(jobSummaryListWrapperJerseyRequest).get();
            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();

            // When
            BatchJobsOperationsAdapter adapter = spy(new BatchJobsOperationsAdapter(sessionStorageMock));
            OperationResult<ClientJobSummariesListWrapper> retrieved = adapter.searchJobs();

            // Then
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock),
                    eq(ClientJobSummariesListWrapper.class),
                    eq(new String[]{"jobs"})), times(1));
            verify(jobSummaryListWrapperJerseyRequest, times(1)).get();
            assertNotNull(retrieved);
            assertSame(retrieved, jobSummaryListWrapperOperationResult);
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
