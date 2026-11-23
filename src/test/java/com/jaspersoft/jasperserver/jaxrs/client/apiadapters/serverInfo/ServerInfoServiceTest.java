package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.dto.serverinfo.ServerInfo;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertSame;

public class ServerInfoServiceTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<String> requestMock;

    @Mock
    private RequestBuilder<String> builderMock;

    @Mock
    private OperationResult<String> operationResultMock;

    @Mock
    private JerseyRequest<ServerInfo> serverInfoJerseyRequest;

    @Mock
    private OperationResult<ServerInfo> serverInfoOperationResult;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_return_date_time_format_pattern() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", "datetimeFormatPattern"}))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).setAccept("text/plain");
            doReturn(operationResultMock).when(requestMock).get();

            ServerInfoService service = new ServerInfoService(sessionStorageMock);
            OperationResult<String> retrieved = service.dateTimeFormatPattern();

            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", "datetimeFormatPattern"})), times(1));
        }
    }

    @Test
    public void should_return_proper_date_format_pattern() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", "dateFormatPattern"}))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).setAccept("text/plain");
            doReturn(operationResultMock).when(requestMock).get();

            ServerInfoService service = new ServerInfoService(sessionStorageMock);
            OperationResult<String> retrieved = service.dateFormatPattern();

            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_return_proper_expiration() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", "expiration"}))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).setAccept("text/plain");
            doReturn(operationResultMock).when(requestMock).get();

            ServerInfoService service = new ServerInfoService(sessionStorageMock);
            OperationResult<String> retrieved = service.expiration();

            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_return_proper_licence_type() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", "licenseType"}))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).setAccept("text/plain");
            doReturn(operationResultMock).when(requestMock).get();

            ServerInfoService service = new ServerInfoService(sessionStorageMock);
            OperationResult<String> retrieved = service.licenseType();

            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_return_proper_edition_name() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", "editionName"}))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).setAccept("text/plain");
            doReturn(operationResultMock).when(requestMock).get();

            ServerInfoService service = new ServerInfoService(sessionStorageMock);
            OperationResult<String> retrieved = service.editionName();

            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_return_proper_features() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", "features"}))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).setAccept("text/plain");
            doReturn(operationResultMock).when(requestMock).get();

            ServerInfoService service = new ServerInfoService(sessionStorageMock);
            OperationResult<String> retrieved = service.features();

            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_return_proper_build() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", "build"}))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).setAccept("text/plain");
            doReturn(operationResultMock).when(requestMock).get();

            ServerInfoService service = new ServerInfoService(sessionStorageMock);
            OperationResult<String> retrieved = service.build();

            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_return_proper_server_version() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", "version"}))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).setAccept("text/plain");
            doReturn(operationResultMock).when(requestMock).get();

            ServerInfoService service = new ServerInfoService(sessionStorageMock);
            OperationResult<String> retrieved = service.version();

            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_return_proper_server_details() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock),
                    eq(ServerInfo.class),
                    eq(new String[]{"serverInfo"}))).thenReturn(serverInfoJerseyRequest);
            doReturn(serverInfoOperationResult).when(serverInfoJerseyRequest).get();

            ServerInfoService service = new ServerInfoService(sessionStorageMock);
            OperationResult<ServerInfo> details = service.details();

            assertSame(details, serverInfoOperationResult);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ServerInfo.class),
                    eq(new String[]{"serverInfo"})), times(1));
        }
    }

    @Test
    public void should_return_proper_edition() {

        // Given
        final String path = "edition";

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", path}))).thenReturn(requestMock);

            doReturn(requestMock).when(requestMock).setAccept("text/plain");
            doReturn(operationResultMock).when(requestMock).get();

            // When
            ServerInfoService service = new ServerInfoService(sessionStorageMock);
            OperationResult<String> edition = service.edition();

            // Then
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class),
                    eq(new String[]{"serverInfo", path})), times(1));

            assertSame(edition, operationResultMock);
            verify(requestMock).setAccept("text/plain");
            verify(requestMock).get();
        }
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, requestMock, builderMock, operationResultMock, serverInfoJerseyRequest, serverInfoOperationResult);
        mockitoSession.close();
    }
}
