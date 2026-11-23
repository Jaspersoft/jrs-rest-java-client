package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.domain.DomElExpressionCollectionContext;
import com.jaspersoft.jasperserver.dto.domain.DomElExpressionContext;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertSame;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@SuppressWarnings("unchecked")
public class DomElContextManagerTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<DomElExpressionContext> domElRequestMock;
    @Mock
    private JerseyRequest<DomElExpressionCollectionContext> domElCollectionRequestMock;
    @Mock
    private OperationResult<DomElExpressionContext> domElContextOperationResultMock;
    @Mock
    private OperationResult<DomElExpressionCollectionContext> domElCollectionContextOperationResultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, domElRequestMock, domElCollectionRequestMock,
                domElContextOperationResultMock, domElCollectionContextOperationResultMock);
    }

    @Test
    public void should_set_proper_session_storage() {
        DomElContextManager manager = new DomElContextManager(sessionStorageMock);
        assertSame(manager.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_return_proper_operation_result_when_create_context() {
        DomElExpressionContext context = new DomElExpressionContext();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(DomElExpressionContext.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class))).thenReturn(domElRequestMock);
            doReturn(domElContextOperationResultMock).when(domElRequestMock).post(context);

            OperationResult<DomElExpressionContext> retrieved =
                    new DomElContextManager(sessionStorageMock).create(context);

            assertSame(domElContextOperationResultMock, retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(DomElExpressionContext.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class)));
            verify(domElRequestMock).post(context);
        }
    }

    @Test
    public void should_return_proper_operation_result_when_create_collection_context() {
        DomElExpressionCollectionContext context = new DomElExpressionCollectionContext();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(DomElExpressionCollectionContext.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class))).thenReturn(domElCollectionRequestMock);
            doReturn(domElCollectionContextOperationResultMock).when(domElCollectionRequestMock).post(context);

            OperationResult<DomElExpressionCollectionContext> retrieved =
                    new DomElContextManager(sessionStorageMock).create(context);

            assertSame(domElCollectionContextOperationResultMock, retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(DomElExpressionCollectionContext.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class)));
            verify(domElCollectionRequestMock).post(context);
        }
    }
}
