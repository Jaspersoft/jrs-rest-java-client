package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.dto.serverinfo.ServerInfo;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertSame;

@PrepareForTest({JerseyRequest.class, ServerInfoService.class})
public class ServerInfoServiceTest extends PowerMockTestCase {

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

    private ServerInfoService service;

    @BeforeMethod
    public void before() {
        initMocks(this);
        service = PowerMockito.spy(new ServerInfoService(sessionStorageMock));
    }

    @Test
    public void should_return_date_time_format_pattern() throws Exception {
        PowerMockito.doReturn(requestMock).when(service, "buildServerInfoRequest", "datetimeFormatPattern");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();

        OperationResult<String> retrieved = service.dateTimeFormatPattern();

        PowerMockito.verifyPrivate(service, times(1)).invoke("buildServerInfoRequest", "datetimeFormatPattern");
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_proper_date_format_pattern() throws Exception {

        ServerInfoService service = PowerMockito.spy(new ServerInfoService(sessionStorageMock));
        PowerMockito.doReturn(requestMock).when(service, "buildServerInfoRequest", "dateFormatPattern");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();

        OperationResult<String> retrieved = service.dateFormatPattern();

        PowerMockito.verifyPrivate(service, times(1)).invoke("buildServerInfoRequest", "dateFormatPattern");
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_proper_expiration() throws Exception {

        ServerInfoService service = PowerMockito.spy(new ServerInfoService(sessionStorageMock));
        PowerMockito.doReturn(requestMock).when(service, "buildServerInfoRequest", "expiration");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();

        OperationResult<String> retrieved = service.expiration();

        PowerMockito.verifyPrivate(service, times(1)).invoke("buildServerInfoRequest", "expiration");
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_proper_licence_type() throws Exception {

        ServerInfoService service = PowerMockito.spy(new ServerInfoService(sessionStorageMock));
        PowerMockito.doReturn(requestMock).when(service, "buildServerInfoRequest", "licenseType");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();

        OperationResult<String> retrieved = service.licenseType();

        PowerMockito.verifyPrivate(service, times(1)).invoke("buildServerInfoRequest", "licenseType");
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_proper_edition_name() throws Exception {

        ServerInfoService service = PowerMockito.spy(new ServerInfoService(sessionStorageMock));
        PowerMockito.doReturn(requestMock).when(service, "buildServerInfoRequest", "editionName");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();

        OperationResult<String> retrieved = service.editionName();

        PowerMockito.verifyPrivate(service, times(1)).invoke("buildServerInfoRequest", "editionName");
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_proper_features() throws Exception {

        ServerInfoService service = PowerMockito.spy(new ServerInfoService(sessionStorageMock));
        PowerMockito.doReturn(requestMock).when(service, "buildServerInfoRequest", "features");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();

        OperationResult<String> retrieved = service.features();

        PowerMockito.verifyPrivate(service, times(1)).invoke("buildServerInfoRequest", "features");
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_proper_build() throws Exception {

        ServerInfoService service = PowerMockito.spy(new ServerInfoService(sessionStorageMock));
        PowerMockito.doReturn(requestMock).when(service, "buildServerInfoRequest", "build");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();

        OperationResult<String> retrieved = service.build();

        PowerMockito.verifyPrivate(service, times(1)).invoke("buildServerInfoRequest", "build");
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_proper_server_version() throws Exception {

        ServerInfoService service = PowerMockito.spy(new ServerInfoService(sessionStorageMock));
        PowerMockito.doReturn(requestMock).when(service, "buildServerInfoRequest", "version");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();

        OperationResult<String> retrieved = service.version();

        PowerMockito.verifyPrivate(service, times(1)).invoke("buildServerInfoRequest", "version");
        assertSame(retrieved, operationResultMock);
    }
    @Test
    public void should_return_proper_server_details() {

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ServerInfo.class),
                eq(new String[]{"serverInfo"}))).thenReturn(serverInfoJerseyRequest);
        PowerMockito.doReturn(serverInfoOperationResult).when(serverInfoJerseyRequest).get();

        ServerInfoService service = new ServerInfoService(sessionStorageMock);
        OperationResult<ServerInfo> details = service.details();

        assertSame(details, serverInfoOperationResult);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ServerInfo.class), eq(new String[]{"serverInfo"}));
    }

    @Test
    public void should_return_proper_edition() {

        // Given
        final String path = "edition";

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class), eq(new String[]{"serverInfo", path}))).thenReturn(requestMock);

        PowerMockito.doReturn(builderMock).when(requestMock).setAccept("text/plain");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();

        // When
        ServerInfoService service = new ServerInfoService(sessionStorageMock);
        OperationResult<String> edition = service.edition();

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(String.class), eq(new String[]{"serverInfo", path}));

        assertSame(edition, operationResultMock);
        Mockito.verify(requestMock).setAccept("text/plain");
        Mockito.verify(requestMock).get();
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, builderMock, operationResultMock, serverInfoJerseyRequest, serverInfoOperationResult);
    }

}