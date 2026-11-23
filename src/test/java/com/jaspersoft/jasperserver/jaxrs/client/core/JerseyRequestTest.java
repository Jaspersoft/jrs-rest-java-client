package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.NullEntityOperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl;
import com.jaspersoft.jasperserver.jaxrs.client.providers.CustomRepresentationTypeProvider;
import jakarta.ws.rs.client.WebTarget;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link JerseyRequest}
 */
public class JerseyRequestTest {

    @Mock
    private SessionStorage sessionStorage;
    @Mock
    private ErrorHandler dummyErrorHandler;
    @Mock
    private JerseyRequest<Class> expected;
    @Mock
    private WebTarget webTarget;
    @Mock
    private RestClientConfiguration clientConfiguration;
    @Mock
    private OperationResultFactoryImpl operationResultFactory;
    @Mock
    private OperationResult operationResult;
    @Mock
    private NullEntityOperationResult nullEntityOperationResultMock;
    @Mock
    private Invocation.Builder builder;
    @Mock
    private Object dummyEntity;

    private AutoCloseable mockitoSession;

    private String[] fakeArrayPath = new String[]{"this/is/an/array/with/fakePath"};

    @BeforeMethod
    public void setUp() throws Exception {
        mockitoSession = MockitoAnnotations.openMocks(this);

        when(sessionStorage.getConfiguration()).thenReturn(clientConfiguration);
        when(clientConfiguration.getContentMimeType()).thenReturn(MimeType.JSON);
        when(sessionStorage.getRootTarget()).thenReturn(webTarget);
        when(webTarget.path(Mockito.anyString())).thenReturn(webTarget);
        when(webTarget.register(CustomRepresentationTypeProvider.class)).thenReturn(webTarget);
        when(webTarget.register(JacksonFeature.class)).thenReturn(webTarget);
    }

    /**
     * The method under test is {@link JerseyRequest#buildRequest(SessionStorage, Class, String[])}
     */
    @Test(testName = "buildRequest")
    public void should_invoke_overloaded_buildRequest_method() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath)).thenCallRealMethod();

            // When
            JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath);

            // Then
            mockedStatic.verify(() -> JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath, null), times(1));
        }
    }

    /**
     * The method under test is {@link JerseyRequest#buildRequest(SessionStorage, Class, String[], ErrorHandler)}
     */
    @Test(testName = "buildRequest")
    public void should_return_initialized_JerseyRequest_object() throws Exception {
        try (MockedConstruction<JerseyRequest> mockedConstruction = Mockito.mockConstruction(JerseyRequest.class)) {
            // When
            JerseyRequest<Class> retrieved =
                    JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath, dummyErrorHandler);

            // Then
            assertNotNull(retrieved);
            assertEquals(retrieved, mockedConstruction.constructed().get(0));
        }
    }

    @Test(testName = "post")
    public void should_send_Jersey_request_with_proper_HTTP_POST_method() throws Exception {
        // Given
        Response responseMock = Mockito.mock(Response.class);
        Mockito.doReturn(200).when(responseMock).getStatus();
        Mockito.doReturn(Boolean.FALSE).when(clientConfiguration).getRestrictedHttpMethods();
        Mockito.doReturn(Boolean.TRUE).when(clientConfiguration).getHandleErrors();
        Mockito.doReturn(builder).when(webTarget).request();
        Mockito.doReturn(builder).when(builder).accept(anyString());
        Mockito.doReturn(responseMock).when(builder).post(any(jakarta.ws.rs.client.Entity.class));

        JerseyRequest<Class> jerseyRequest = new JerseyRequest<>(sessionStorage, Class.class);

        // When
        jerseyRequest.post(dummyEntity);

        // Then
        verify(builder).post(any(jakarta.ws.rs.client.Entity.class));
    }

    @Test
    public void should_send_Jersey_request_with_PUT_using_override_header_when_restricted() throws Exception {
        // Given
        Response responseMock = Mockito.mock(Response.class);
        Mockito.doReturn(200).when(responseMock).getStatus();
        Mockito.doReturn(Boolean.TRUE).when(clientConfiguration).getRestrictedHttpMethods();
        Mockito.doReturn(Boolean.TRUE).when(clientConfiguration).getHandleErrors();
        Mockito.doReturn(builder).when(webTarget).request();
        Mockito.doReturn(builder).when(builder).accept(anyString());
        Mockito.doReturn(builder).when(builder).header(anyString(), any());
        Mockito.doReturn(responseMock).when(builder).post(any());

        JerseyRequest<Class> jerseyRequest = new JerseyRequest<>(sessionStorage, Class.class);

        // When
        jerseyRequest.put(dummyEntity);

        // Then
        verify(builder).header("X-HTTP-Method-Override", "PUT");
        verify(builder).post(any());
    }

    @Test(testName = "addParam")
    public void should_pass_proper_param_to_queryParam_method_and_return_RequestBuilder_object() {
        // When
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.addParam("name", "a", "b", "c");

        // Then
        assertSame(expected, retrieved);
        verify(webTarget, times(1)).queryParam("name", "a", "b", "c");
    }

    @Test(testName = "addParams")
    public void should_pass_proper_params_map_to_queryParam_method_and_return_RequestBuilder_object() {
        // Given
        MultivaluedMap<String, String> fakeParams = new MultivaluedHashMap<String, String>() {{
            add("key", "value");
        }};

        // When
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.addParams(fakeParams);

        // Then
        assertSame(expected, retrieved);
        verify(webTarget, times(1)).queryParam("key", "value");
    }

    @Test(testName = "addMatrixParam")
    public void should_add_proper_param_to_WebTarget_and_return_RequestBuilder_object() {
        // When
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.addMatrixParam("name", "a", "b", "c");

        // Then
        assertSame(expected, retrieved);
        verify(webTarget, times(1)).matrixParam("name", "a", "b", "c");
    }

    @Test(testName = "addMatrixParams")
    public void should_add_proper_params_map_to_WebTarget_and_return_RequestBuilder_object() {
        // Given
        MultivaluedMap<String, String> fakeParams = new MultivaluedHashMap<String, String>() {{
            add("key", "value");
        }};

        // When
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.addMatrixParams(fakeParams);

        // Then
        assertSame(expected, retrieved);
        verify(webTarget, times(1)).matrixParam("key", "value");
    }

    @Test(testName = "setContentType")
    public void should_set_ContentType_and_return_RequestBuilder_object() {
        // When
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.setContentType(MediaType.APPLICATION_JSON);

        // Then
        assertSame(expected, retrieved);
        assertEquals(getInternalState(retrieved, "contentType"), MediaType.APPLICATION_JSON);
        assertNotEquals(getInternalState(retrieved, "contentType"), MediaType.APPLICATION_XML);
    }

    @Test(testName = "setAccept")
    public void should_set_proper_AcceptMime_and_return_RequestBuilder_object() {
        // When
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.setAccept(MediaType.APPLICATION_JSON);

        // Then
        assertSame(expected, retrieved);
        assertEquals(getInternalState(retrieved, "acceptType"), MediaType.APPLICATION_JSON);
        assertNotEquals(getInternalState(retrieved, "acceptType"), MediaType.APPLICATION_XML);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        reset(sessionStorage, dummyErrorHandler, expected, webTarget, clientConfiguration,
                operationResultFactory, dummyEntity);
        mockitoSession.close();
    }

    @SuppressWarnings("unchecked")
    private static <T> T getInternalState(Object target, String fieldName) {
        try {
            Class<?> clazz = target.getClass();
            while (clazz != null) {
                try {
                    java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return (T) field.get(target);
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            throw new NoSuchFieldException(fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
