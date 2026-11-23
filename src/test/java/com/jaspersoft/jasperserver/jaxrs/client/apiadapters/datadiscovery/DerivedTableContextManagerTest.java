package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.SqlExecutionRequest;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
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
public class DerivedTableContextManagerTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<PresentationGroupElement> metadataRequestMock;
    @Mock
    private OperationResult<PresentationGroupElement> operationResultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, metadataRequestMock, operationResultMock);
    }

    @Test
    public void should_set_proper_session_storage() {
        DerivedTableContextManager manager = new DerivedTableContextManager(sessionStorageMock);
        assertSame(manager.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_return_proper_operation_result_when_create_context() {
        SqlExecutionRequest request = new SqlExecutionRequest();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(PresentationGroupElement.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class))).thenReturn(metadataRequestMock);
            doReturn(operationResultMock).when(metadataRequestMock).post(request);

            OperationResult<PresentationGroupElement> retrieved =
                    new DerivedTableContextManager(sessionStorageMock).execute(request);

            assertSame(operationResultMock, retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(PresentationGroupElement.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class)));
            verify(metadataRequestMock).setContentType(ContextMediaTypes.SQL_EXECUTION_JSON);
            verify(metadataRequestMock).setAccept(ContextMediaTypes.DATASET_METADATA_JSON);
            verify(metadataRequestMock).post(request);
        }
    }
}
