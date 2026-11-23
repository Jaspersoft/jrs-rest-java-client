package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
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
public class DomainContextManagerTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<ClientDomain> domainRequestMock;
    @Mock
    private JerseyRequest<PresentationGroupElement> metadataRequestMock;
    @Mock
    private OperationResult<ClientDomain> operationResultMock;
    @Mock
    private OperationResult<PresentationGroupElement> metadataOperationResultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, domainRequestMock, metadataRequestMock,
                operationResultMock, metadataOperationResultMock);
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
        DomainContextManager manager = new DomainContextManager(sessionStorageMock);
        assertSame(getInternalState(manager, "sessionStorage"), sessionStorageMock);
    }

    @Test
    public void should_return_proper_domain_when_create_context() {
        ClientDomain domain = new ClientDomain();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class))).thenReturn(domainRequestMock);
            doReturn(operationResultMock).when(domainRequestMock).post(domain);

            OperationResult<ClientDomain> retrieved = new DomainContextManager(sessionStorageMock).create(domain);

            assertSame(operationResultMock, retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class)));
            verify(domainRequestMock).post(domain);
        }
    }

    @Test
    public void should_return_proper_domain_when_get_metadata() {
        String someId = "someId";

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(PresentationGroupElement.class),
                    eq(new String[]{"contexts", someId, "metadata"}),
                    any(DefaultErrorHandler.class))).thenReturn(metadataRequestMock);
            doReturn(metadataOperationResultMock).when(metadataRequestMock).get();

            OperationResult<PresentationGroupElement> retrieved =
                    new DomainContextManager(sessionStorageMock).fetchMetadataById(someId);

            assertSame(metadataOperationResultMock, retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(PresentationGroupElement.class),
                    eq(new String[]{"contexts", someId, "metadata"}), any(DefaultErrorHandler.class)));
            verify(metadataRequestMock).get();
        }
    }

    @Test
    public void should_return_proper_domain_when_create_context_and_get_metadata() {
        ClientDomain domain = new ClientDomain();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(PresentationGroupElement.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class))).thenReturn(metadataRequestMock);
            doReturn(metadataOperationResultMock).when(metadataRequestMock).post(domain);

            OperationResult<PresentationGroupElement> retrieved =
                    new DomainContextManager(sessionStorageMock).fetchMetadataByContext(domain);

            assertSame(metadataOperationResultMock, retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(PresentationGroupElement.class),
                    eq(new String[]{"contexts"}), any(DefaultErrorHandler.class)));
            verify(metadataRequestMock).post(domain);
        }
    }
}
