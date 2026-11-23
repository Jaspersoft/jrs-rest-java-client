package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceGroupElement;
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

import java.lang.reflect.Field;

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
public class TopicContextManagerTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<ClientReportUnit> reportUnitRequestMock;
    @Mock
    private JerseyRequest<ResourceGroupElement> metadataRequestMock;
    @Mock
    private OperationResult<ClientReportUnit> operationResultMock;
    @Mock
    private OperationResult<ResourceGroupElement> resourceGroupElementOperationResult;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, reportUnitRequestMock, metadataRequestMock,
                operationResultMock, resourceGroupElementOperationResult);
    }

    private Object getInternalState(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Test
    public void should_set_proper_session_storage() throws Exception {
        TopicContextManager manager = new TopicContextManager(sessionStorageMock);
        assertSame(getInternalState(manager, "sessionStorage"), sessionStorageMock);
    }

    @Test
    public void should_return_proper_domain_when_create_context() {
        ClientReportUnit reportUnit = new ClientReportUnit();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientReportUnit.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class))).thenReturn(reportUnitRequestMock);
            doReturn(operationResultMock).when(reportUnitRequestMock).post(reportUnit);

            OperationResult<ClientReportUnit> retrieved =
                    new TopicContextManager(sessionStorageMock).create(reportUnit);

            assertSame(operationResultMock, retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientReportUnit.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class)));
            verify(reportUnitRequestMock).post(reportUnit);
        }
    }

    @Test
    public void should_return_proper_domain_when_get_metadata() {
        String someId = "someId";

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ResourceGroupElement.class),
                    eq(new String[]{"contexts", someId, "metadata"}),
                    any(DefaultErrorHandler.class))).thenReturn(metadataRequestMock);
            doReturn(resourceGroupElementOperationResult).when(metadataRequestMock).get();

            OperationResult<ResourceGroupElement> retrieved =
                    new TopicContextManager(sessionStorageMock).fetchMetadataById(someId);

            assertSame(resourceGroupElementOperationResult, retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ResourceGroupElement.class),
                    eq(new String[]{"contexts", someId, "metadata"}), any(DefaultErrorHandler.class)));
            verify(metadataRequestMock).get();
        }
    }

    @Test
    public void should_return_proper_domain_when_create_context_and_get_metadata() {
        ClientReportUnit reportUnit = new ClientReportUnit();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ResourceGroupElement.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class))).thenReturn(metadataRequestMock);
            doReturn(resourceGroupElementOperationResult).when(metadataRequestMock).post(reportUnit);

            OperationResult<ResourceGroupElement> retrieved =
                    new TopicContextManager(sessionStorageMock).fetchMetadataByContext(reportUnit);

            assertSame(resourceGroupElementOperationResult, retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ResourceGroupElement.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class)));
            verify(metadataRequestMock).post(reportUnit);
        }
    }
}
