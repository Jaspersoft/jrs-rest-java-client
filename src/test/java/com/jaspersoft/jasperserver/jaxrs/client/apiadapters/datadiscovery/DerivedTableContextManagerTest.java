package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.SqlExecutionRequest;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
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
@PrepareForTest({ContextService.class, SessionStorage.class, SingleContextAdapter.class, DerivedTableContextManager.class})
public class DerivedTableContextManagerTest extends PowerMockTestCase {
    private SessionStorage sessionStorageMock;
    private ContextService contextServiceMock;
    private SingleContextAdapter contextAdapterMock;
    private OperationResult<PresentationGroupElement> operationResultMock;


    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        contextServiceMock = mock(ContextService.class);
        contextAdapterMock = mock(SingleContextAdapter.class);
        operationResultMock = mock(OperationResult.class);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, contextAdapterMock, contextServiceMock, operationResultMock);
    }


    @Test
    public void should_set_proper_session_storage() {
        // When
        DomElContextManager domainContextManager = new DomElContextManager(sessionStorageMock);
        //Then
        assertSame(Whitebox.getInternalState(domainContextManager, "sessionStorage"), sessionStorageMock);
    }

    @Test
    public void should_return_proper_operation_result_when_create_context() throws Exception {
        // When

        SqlExecutionRequest request = spy(new SqlExecutionRequest());

        PowerMockito.whenNew(ContextService.class).withArguments(sessionStorageMock).thenReturn(contextServiceMock);
        PowerMockito.when(contextServiceMock.context(SqlExecutionRequest.class,
                ContextMediaTypes.SQL_EXECUTION_JSON,
                PresentationGroupElement.class,
                ContextMediaTypes.DATASET_METADATA_JSON)).
                thenReturn(contextAdapterMock);
        PowerMockito.when(contextAdapterMock.createAndGetMetadata(request)).thenReturn(operationResultMock);

        OperationResult<PresentationGroupElement> retrievedOperationResult = new DerivedTableContextManager(sessionStorageMock).
                execute(request);
        //Then
        assertSame(operationResultMock, retrievedOperationResult);
    }


}