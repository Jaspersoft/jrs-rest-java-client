package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections.ConnectionsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections.SingleConnectionAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ConnectionMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.testng.Assert.assertSame;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@PrepareForTest({ConnectionsService.class, SessionStorage.class, SingleConnectionAdapter.class, TopicContextManager.class})
public class TopicContextManagerTest extends PowerMockTestCase {
    private SessionStorage sessionStorageMock;
    private ConnectionsService connectionsServiceMock;
    private SingleConnectionAdapter connectionAdapterMock;
    private OperationResult<ClientReportUnit> operationResultMock;
    private OperationResult<ResourceGroupElement> resourceGroupElementOperationResult;


    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        connectionsServiceMock = mock(ConnectionsService.class);
        connectionAdapterMock = mock(SingleConnectionAdapter.class);
        operationResultMock = mock(OperationResult.class);
        resourceGroupElementOperationResult = mock(OperationResult.class);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, connectionAdapterMock, connectionsServiceMock, operationResultMock, resourceGroupElementOperationResult);
    }


    @Test
    public void should_set_proper_session_storage() {
        // When
        TopicContextManager domainContextManager = new TopicContextManager(sessionStorageMock);
        //Then
        assertSame(Whitebox.getInternalState(domainContextManager, "sessionStorage"), sessionStorageMock);
    }

    @Test
    public void should_return_proper_domain_when_create_context() throws Exception {
        // When

        ClientReportUnit reportUnit = spy(new ClientReportUnit());
        PowerMockito.whenNew(ConnectionsService.class).withArguments(sessionStorageMock).thenReturn(connectionsServiceMock);
        PowerMockito.when(connectionsServiceMock.connection(ClientReportUnit.class,
                ConnectionMediaType.REPORT_UNIT_TYPE)).thenReturn(connectionAdapterMock);
        PowerMockito.when(connectionAdapterMock.create(reportUnit)).thenReturn(operationResultMock);

        OperationResult<ClientReportUnit> retrievedOperationResult = new TopicContextManager(sessionStorageMock).create(reportUnit);
        //Then
        assertSame(operationResultMock, retrievedOperationResult);
    }

    @Test
    public void should_return_proper_domain_when_get_metadata() throws Exception {
        // When

        String someId = "someId";
        PowerMockito.whenNew(ConnectionsService.class).withArguments(sessionStorageMock).thenReturn(connectionsServiceMock);
        PowerMockito.when(connectionsServiceMock.connection(someId,
                ResourceGroupElement.class,
                ConnectionMediaType.REPORT_UNIT_METADATA_TYPE)).
                thenReturn(connectionAdapterMock);
        PowerMockito.when(connectionAdapterMock.metadata()).thenReturn(resourceGroupElementOperationResult);

        OperationResult<ResourceGroupElement> retrievedOperationResult = new TopicContextManager(sessionStorageMock).
                fetchMetadataById(someId);
        //Then
        assertSame(resourceGroupElementOperationResult, retrievedOperationResult);
    }


    @Test
    public void should_return_proper_domain_when_create_context_and_get_metadata() throws Exception {
        // When

        ClientReportUnit reportUnit = spy(new ClientReportUnit());
        PowerMockito.whenNew(ConnectionsService.class).withArguments(sessionStorageMock).thenReturn(connectionsServiceMock);
        PowerMockito.when(connectionsServiceMock.connection(ClientReportUnit.class,
                ConnectionMediaType.REPORT_UNIT_TYPE,
                ResourceGroupElement.class,
                ConnectionMediaType.REPORT_UNIT_METADATA_TYPE
                )).thenReturn(connectionAdapterMock);
        PowerMockito.when(connectionAdapterMock.createAndGetMetadata(reportUnit)).thenReturn(resourceGroupElementOperationResult);

        OperationResult<ResourceGroupElement> retrievedOperationResult = new TopicContextManager(sessionStorageMock).fetchMetadataByContext(reportUnit);
        //Then
        assertSame(resourceGroupElementOperationResult, retrievedOperationResult);
    }

}