package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections.SingleConnectionsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.SingleContextAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
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
@PrepareForTest({ContextService.class, SessionStorage.class, SingleConnectionsAdapter.class, DomainContextManager.class})
public class DomainContextManagerTest extends PowerMockTestCase {
    private SessionStorage sessionStorageMock;
    private ContextService contextServiceMock;
    private SingleContextAdapter contextAdapterMock;
    private OperationResult<ClientDomain> operationResultMock;
    private OperationResult<PresentationGroupElement> dataIslandsContainerOperationResult;


    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        contextServiceMock = mock(ContextService.class);
        contextAdapterMock = mock(SingleContextAdapter.class);
        operationResultMock = mock(OperationResult.class);
        dataIslandsContainerOperationResult = mock(OperationResult.class);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, contextAdapterMock, contextServiceMock, operationResultMock, dataIslandsContainerOperationResult);
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

        ClientDomain domain = spy(new ClientDomain());
        PowerMockito.whenNew(ContextService.class).withArguments(sessionStorageMock).thenReturn(contextServiceMock);
        PowerMockito.when(contextServiceMock.context(ClientDomain.class, ContextMediaTypes.DOMAIN_JSON)).thenReturn(contextAdapterMock);
        PowerMockito.when(contextAdapterMock.create(domain)).thenReturn(operationResultMock);

        OperationResult<ClientDomain> retrievedOperationResult = new DomainContextManager(sessionStorageMock).create(domain);
        //Then
        assertSame(operationResultMock, retrievedOperationResult);
    }

    @Test
    public void should_return_proper_domain_when_get_metadata() throws Exception {
        // When

        String someId = "someId";
        PowerMockito.whenNew(ContextService.class).withArguments(sessionStorageMock).thenReturn(contextServiceMock);
        PowerMockito.when(contextServiceMock.context(someId,
                PresentationGroupElement.class,
                ContextMediaTypes.DOMAIN_METADATA_JSON)).
                thenReturn(contextAdapterMock);
        PowerMockito.when(contextAdapterMock.metadata()).thenReturn(dataIslandsContainerOperationResult);

        OperationResult<PresentationGroupElement> retrievedOperationResult = new DomainContextManager(sessionStorageMock).
                fetchMetadataById(someId);
        //Then
        assertSame(dataIslandsContainerOperationResult, retrievedOperationResult);
    }


    @Test
    public void should_return_proper_domain_when_create_context_and_get_metadata() throws Exception {
        // When

        ClientDomain domain = spy(new ClientDomain());
        PowerMockito.whenNew(ContextService.class).withArguments(sessionStorageMock).thenReturn(contextServiceMock);
        PowerMockito.when(contextServiceMock.context(ClientDomain.class,
                ContextMediaTypes.DOMAIN_JSON,
                PresentationGroupElement.class,
                ContextMediaTypes.DOMAIN_METADATA_JSON
                )).thenReturn(contextAdapterMock);
        PowerMockito.when(contextAdapterMock.createAndGetMetadata(domain)).thenReturn(dataIslandsContainerOperationResult);

        OperationResult<PresentationGroupElement> retrievedOperationResult = new DomainContextManager(sessionStorageMock).fetchMetadataByContext(domain);
        //Then
        assertSame(dataIslandsContainerOperationResult, retrievedOperationResult);
    }

}