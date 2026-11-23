package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.newDomain;

import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
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
public class DomainAdapterTest {

    public static final String URI = "uri";
    public static final List<String> PATH = asList("resources", URI);
    public static final String CONTENT_TYPE = "application/repository.domain+json";

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private JerseyRequest<ClientDomain> requestMock;
    @Mock
    private OperationResult<ClientDomain> resultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, configurationMock, requestMock, resultMock);
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
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() throws Exception {
        DomainAdapter adapter = new DomainAdapter(sessionStorageMock, URI);
        List<String> path = (List<String>) getInternalState(adapter, "path");

        assertNotNull(adapter);
        assertEquals(path, PATH);
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_get() {
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).get();

            new DomainAdapter(sessionStorageMock, URI).get();

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class)));
            verify(requestMock).setAccept(CONTENT_TYPE);
            verify(requestMock).get();
            verifyNoMoreInteractions(requestMock);
        }
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_create() {
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        ClientDomain domain = new ClientDomain();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).post(domain);

            new DomainAdapter(sessionStorageMock, URI).create(domain);

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class)));
            verify(requestMock).setContentType(CONTENT_TYPE);
            verify(requestMock).post(domain);
            verifyNoMoreInteractions(requestMock);
        }
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_update() {
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        ClientDomain domain = new ClientDomain();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).put(domain);

            new DomainAdapter(sessionStorageMock, URI).update(domain);

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class)));
            verify(requestMock).setContentType(CONTENT_TYPE);
            verify(requestMock).put(domain);
            verifyNoMoreInteractions(requestMock);
        }
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_delete() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).delete();

            new DomainAdapter(sessionStorageMock, URI).delete();

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class)));
            verify(requestMock).delete();
            verifyNoMoreInteractions(requestMock);
        }
    }

    @Test
    public void should_invoke_private_static_method() {
        // Verifies the URI is correctly split into path segments for the static buildRequest call
        String multiSegmentUri = "a/b";
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"resources", "a", "b"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).get();

            OperationResult<ClientDomain> retrieved = new DomainAdapter(sessionStorageMock, multiSegmentUri).get();

            assertNotNull(retrieved);
            assertSame(retrieved, resultMock);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientDomain.class),
                    eq(new String[]{"resources", "a", "b"}), any(DefaultErrorHandler.class)));
            verify(requestMock).setAccept(CONTENT_TYPE);
            verify(requestMock).get();
        }
    }
}
