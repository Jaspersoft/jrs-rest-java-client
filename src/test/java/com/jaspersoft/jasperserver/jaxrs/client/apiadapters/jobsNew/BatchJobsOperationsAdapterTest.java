package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobsNew;

import com.jaspersoft.jasperserver.dto.job.ClientReportJob;
import com.jaspersoft.jasperserver.dto.job.wrappers.ClientJobSummariesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.BatchJobsOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import javax.ws.rs.core.MultivaluedHashMap;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
@PrepareForTest({JerseyRequest.class})
public class BatchJobsOperationsAdapterTest extends PowerMockTestCase {

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

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(testName = "constructor")
    public void should_create_proper_SingleJobOperationsAdapter_object() throws IllegalAccessException {

        // When
        BatchJobsOperationsAdapter adapter = new BatchJobsOperationsAdapter(sessionStorageMock);

        // Then
        assertEquals(Whitebox.getInternalState(adapter, "sessionStorage"), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(adapter, "params"), new MultivaluedHashMap<String, String>());
    }

    @Test
    public void should_return_all_jobs() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientJobSummariesListWrapper.class),
                eq(new String[]{"jobs"}))).thenReturn(jobSummaryListWrapperJerseyRequest);
        when(jobSummaryListWrapperJerseyRequest.get()).thenReturn(jobSummaryListWrapperOperationResult);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);

        // When
        BatchJobsOperationsAdapter adapter = spy(new BatchJobsOperationsAdapter(sessionStorageMock));
        OperationResult<ClientJobSummariesListWrapper> retrieved = adapter.searchJobs();

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientJobSummariesListWrapper.class), eq(new String[]{"jobs"}));
        verify(jobSummaryListWrapperJerseyRequest, times(1)).get();
        assertNotNull(retrieved);
        assertSame(retrieved, jobSummaryListWrapperOperationResult);

    }

}
