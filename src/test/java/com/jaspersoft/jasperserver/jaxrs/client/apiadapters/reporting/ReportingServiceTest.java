package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionRequest;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link ReportingService}
 */
@PrepareForTest({JerseyRequest.class, ReportingService.class})
public class ReportingServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private ReportExecutionRequest executionRequestMock;


    @BeforeMethod
    public void before() {
        initMocks(this);
    }



    @Test
    /**
     * for {@link ReportingService#report(String)}
     */
    public void should_return_proper_ReportsAdapter_instance() throws Exception {

        // Given
        ReportsAdapter adapterMock = mock(ReportsAdapter.class);
        whenNew(ReportsAdapter.class).withArguments(sessionStorageMock, "reportUnitUri").thenReturn(adapterMock);
        ReportingService serviceSpy = PowerMockito.spy(new ReportingService(sessionStorageMock));

        // When
        ReportsAdapter retrieved = serviceSpy.report("reportUnitUri");

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, adapterMock);
        verifyNew(ReportsAdapter.class).withArguments(sessionStorageMock, "reportUnitUri");
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, executionRequestMock);
    }
}