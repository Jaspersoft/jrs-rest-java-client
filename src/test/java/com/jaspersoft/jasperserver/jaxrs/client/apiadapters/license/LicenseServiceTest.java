package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.license;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link LicenseService}
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class LicenseServiceTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<Map> requestMock;

    @Mock
    private OperationResult<Map> operationResultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
    }

    @Test
    public void should_return_all_features() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Map.class),
                    eq(new String[]{"licenseFeatures"}))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();

            assertSame(new LicenseService(sessionStorageMock).allFeatures(), operationResultMock);
        }
    }

    @Test
    public void should_return_single_feature() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Map.class),
                    eq(new String[]{"licenseFeatures", "ef"}))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();

            assertSame(new LicenseService(sessionStorageMock).feature("ef"), operationResultMock);
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_fail_on_empty_feature_name() {
        new LicenseService(sessionStorageMock).feature("");
    }
}
