package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceGroupElement;
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
@PrepareForTest({ContextService.class, SessionStorage.class, SingleConnectionsAdapter.class, TopicContextManager.class})
public class TopicContextManagerTest extends PowerMockTestCase {
    private SessionStorage sessionStorageMock;
    private ContextService contextServiceMock;
    private SingleContextAdapter contextAdapterMock;
    private OperationResult<ClientReportUnit> operationResultMock;
    private OperationResult<ResourceGroupElement> resourceGroupElementOperationResult;


    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        contextServiceMock = mock(ContextService.class);
        contextAdapterMock = mock(SingleContextAdapter.class);
        operationResultMock = mock(OperationResult.class);
        resourceGroupElementOperationResult = mock(OperationResult.class);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, contextAdapterMock, contextServiceMock, operationResultMock, resourceGroupElementOperationResult);
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
        PowerMockito.whenNew(ContextService.class).withArguments(sessionStorageMock).thenReturn(contextServiceMock);
        PowerMockito.when(contextServiceMock.context(ClientReportUnit.class,
                ContextMediaTypes.REPORT_UNIT_JSON)).thenReturn(contextAdapterMock);
        PowerMockito.when(contextAdapterMock.create(reportUnit)).thenReturn(operationResultMock);

        OperationResult<ClientReportUnit> retrievedOperationResult = new TopicContextManager(sessionStorageMock).create(reportUnit);
        //Then
        assertSame(operationResultMock, retrievedOperationResult);
    }

    @Test
    public void should_return_proper_domain_when_get_metadata() throws Exception {
        // When

        String someId = "someId";
        PowerMockito.whenNew(ContextService.class).withArguments(sessionStorageMock).thenReturn(contextServiceMock);
        PowerMockito.when(contextServiceMock.context(someId,
                ResourceGroupElement.class,
                ContextMediaTypes.REPORT_UNIT_METADATA_JSON)).
                thenReturn(contextAdapterMock);
        PowerMockito.when(contextAdapterMock.metadata()).thenReturn(resourceGroupElementOperationResult);

        OperationResult<ResourceGroupElement> retrievedOperationResult = new TopicContextManager(sessionStorageMock).
                fetchMetadataById(someId);
        //Then
        assertSame(resourceGroupElementOperationResult, retrievedOperationResult);
    }


    @Test
    public void should_return_proper_domain_when_create_context_and_get_metadata() throws Exception {
        // When

        ClientReportUnit reportUnit = spy(new ClientReportUnit());
        PowerMockito.whenNew(ContextService.class).withArguments(sessionStorageMock).thenReturn(contextServiceMock);
        PowerMockito.when(contextServiceMock.context(ClientReportUnit.class,
                ContextMediaTypes.REPORT_UNIT_JSON,
                ResourceGroupElement.class,
                ContextMediaTypes.REPORT_UNIT_METADATA_JSON
                )).thenReturn(contextAdapterMock);
        PowerMockito.when(contextAdapterMock.createAndGetMetadata(reportUnit)).thenReturn(resourceGroupElementOperationResult);

        OperationResult<ResourceGroupElement> retrievedOperationResult = new TopicContextManager(sessionStorageMock).fetchMetadataByContext(reportUnit);
        //Then
        assertSame(resourceGroupElementOperationResult, retrievedOperationResult);
    }

}