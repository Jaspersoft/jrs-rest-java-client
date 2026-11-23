package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionRequest;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link ReportingService}
 */
public class ReportingServiceTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private ReportExecutionRequest executionRequestMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    /**
     * for {@link ReportingService#report(String)}
     */
    @Test
    public void should_return_proper_ReportsAdapter_instance() {
        // Given
        ReportingService service = new ReportingService(sessionStorageMock);

        try (MockedConstruction<ReportsAdapter> ignored = Mockito.mockConstruction(ReportsAdapter.class)) {
            // When
            ReportsAdapter retrieved = service.report("reportUnitUri");

            // Then
            assertNotNull(retrieved);
        }
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, executionRequestMock);
        mockitoSession.close();
    }
}
