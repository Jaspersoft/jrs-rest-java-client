package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.DataIslandsContainer;
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
@PrepareForTest({ConnectionsService.class, SessionStorage.class, SingleConnectionAdapter.class, DomainContextManager.class})
public class DomainContextManagerTest extends PowerMockTestCase {
    private SessionStorage sessionStorageMock;
    private ConnectionsService connectionsServiceMock;
    private SingleConnectionAdapter connectionAdapterMock;
    private OperationResult<ClientSemanticLayerDataSource> operationResultMock;
    private OperationResult<DataIslandsContainer> dataIslandsContainerOperationResult;


    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        connectionsServiceMock = mock(ConnectionsService.class);
        connectionAdapterMock = mock(SingleConnectionAdapter.class);
        operationResultMock = mock(OperationResult.class);
        dataIslandsContainerOperationResult = mock(OperationResult.class);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, connectionAdapterMock, connectionsServiceMock, operationResultMock, dataIslandsContainerOperationResult);
    }


    @Test
    public void should_set_proper_session_storage() {
        // When
        DomainContextManager domainContextManager = new DomainContextManager(sessionStorageMock);
        //Then
        assertSame(Whitebox.getInternalState(domainContextManager, "sessionStorage"), sessionStorageMock);
    }

    @Test
    public void should_return_proper_domain_when_create_context() throws Exception {
        // When

        ClientSemanticLayerDataSource domain = spy(new ClientSemanticLayerDataSource());
        PowerMockito.whenNew(ConnectionsService.class).withArguments(sessionStorageMock).thenReturn(connectionsServiceMock);
        PowerMockito.when(connectionsServiceMock.connection(ClientSemanticLayerDataSource.class, ConnectionMediaType.DOMAIN_DATA_SOURCE_TYPE)).thenReturn(connectionAdapterMock);
        PowerMockito.when(connectionAdapterMock.create(domain)).thenReturn(operationResultMock);

        OperationResult<ClientSemanticLayerDataSource> retrievedOperationResult = new DomainContextManager(sessionStorageMock).create(domain);
        //Then
        assertSame(operationResultMock, retrievedOperationResult);
    }

    @Test
    public void should_return_proper_domain_when_get_metadata() throws Exception {
        // When

        String someId = "someId";
        PowerMockito.whenNew(ConnectionsService.class).withArguments(sessionStorageMock).thenReturn(connectionsServiceMock);
        PowerMockito.when(connectionsServiceMock.connection(someId,
                DataIslandsContainer.class,
                ConnectionMediaType.DOMAIN_METADATA_TYPE)).
                thenReturn(connectionAdapterMock);
        PowerMockito.when(connectionAdapterMock.metadata()).thenReturn(dataIslandsContainerOperationResult);

        OperationResult<DataIslandsContainer> retrievedOperationResult = new DomainContextManager(sessionStorageMock).
                fetchMetadataById(someId);
        //Then
        assertSame(dataIslandsContainerOperationResult, retrievedOperationResult);
    }


    @Test
    public void should_return_proper_domain_when_create_context_and_get_metadata() throws Exception {
        // When

        ClientSemanticLayerDataSource domain = spy(new ClientSemanticLayerDataSource());
        PowerMockito.whenNew(ConnectionsService.class).withArguments(sessionStorageMock).thenReturn(connectionsServiceMock);
        PowerMockito.when(connectionsServiceMock.connection(ClientSemanticLayerDataSource.class,
                ConnectionMediaType.DOMAIN_DATA_SOURCE_TYPE,
                DataIslandsContainer.class,
                ConnectionMediaType.DOMAIN_METADATA_TYPE
                )).thenReturn(connectionAdapterMock);
        PowerMockito.when(connectionAdapterMock.createAndGetMetadata(domain)).thenReturn(dataIslandsContainerOperationResult);

        OperationResult<DataIslandsContainer> retrievedOperationResult = new DomainContextManager(sessionStorageMock).fetchMetadataByContext(domain);
        //Then
        assertSame(dataIslandsContainerOperationResult, retrievedOperationResult);
    }

}