package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import jakarta.ws.rs.core.GenericType;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link AttributesService}
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class AttributesServiceWhitelistTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<Map<String, List<String>>> requestMock;

    @Mock
    private OperationResult<Map<String, List<String>>> operationResultMock;

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
    public void should_return_dashboard_domain_whitelist() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), any(GenericType.class),
                    eq(new String[]{"attributes", "dashboardDomainWhitelistValues"}), any(ErrorHandler.class)))
                    .thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).setAccept("application/json");
            doReturn(operationResultMock).when(requestMock).get();

            OperationResult<Map<String, List<String>>> retrieved =
                    new AttributesService(sessionStorageMock).dashboardDomainWhitelist();

            assertSame(retrieved, operationResultMock);
            verify(requestMock).setAccept("application/json");
        }
    }
}