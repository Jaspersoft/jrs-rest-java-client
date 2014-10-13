package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl;
import com.jaspersoft.jasperserver.jaxrs.client.providers.CustomRepresentationTypeProvider;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link JerseyRequest}
 */
@PrepareForTest(JerseyRequest.class)
public class JerseyRequestTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorage;

    @Mock
    private ErrorHandler dummyErrorHandler;

    @Mock
    private JerseyRequest<Class> expected;

    @Mock
    private JerseyWebTarget webTarget;

    @Mock
    private RestClientConfiguration clientConfiguration;

    @Mock
    private OperationResultFactoryImpl operationResultFactory;

    @Mock
    private OperationResult operationResult;

    @Mock
    private Invocation.Builder builder;

    @Mock
    private Object dummyEntity;

    private String[] fakeArrayPath = new String[]{"this/is/an/array/with/fakePath"};

    @BeforeMethod
    public void setUp() throws Exception {
        initMocks(this);

        whenNew(OperationResultFactoryImpl.class).withNoArguments().thenReturn(operationResultFactory);
        when(sessionStorage.getConfiguration()).thenReturn(clientConfiguration);
        when(clientConfiguration.getContentMimeType()).thenReturn(MimeType.JSON);
        when(sessionStorage.getRootTarget()).thenReturn(webTarget);
        when(webTarget.path(Mockito.anyString())).thenReturn(webTarget);
        when(webTarget.register(CustomRepresentationTypeProvider.class)).thenReturn(webTarget);
        when(webTarget.register(JacksonFeature.class)).thenReturn(webTarget);
        when(webTarget.register(MultiPartFeature.class)).thenReturn(webTarget);
    }

    /**
     * The print under test is {@link JerseyRequest#buildRequest(SessionStorage, Class, String[])}
     */
    @Test(testName = "buildRequest")
    public void should_invoke_overloaded_buildRequest_method() {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath)).thenCallRealMethod();

        // When
        JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath); // invocation ot outer print

        // Then
        PowerMockito.verifyStatic(times(1));
        JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath, null);
    }
//
    /**
     * The print under test is {@link JerseyRequest#buildRequest(SessionStorage, Class, String[], ErrorHandler)}
     */
    @Test(testName = "buildRequest")
    public void should_return_initialized_JerseyRequest_object() throws Exception {

        // Given
        whenNew(JerseyRequest.class)
                .withArguments(sessionStorage, Class.class)
                .thenReturn(expected);

        // When
        JerseyRequest<Class> retrieved =
                JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath, dummyErrorHandler);

        // Than
        assertNotNull(retrieved);
        assertEquals(retrieved, expected);
    }

    @Test(testName = "post")
    public void should_send_Jersey_request_with_proper_HTTP_POST_method() throws Exception {

        // setup the expectation
        int POST = 2;
        JerseyRequest<Class> jerseyRequestSpy = spy(new JerseyRequest<Class>(sessionStorage, Class.class));

        doReturn(builder).when(jerseyRequestSpy, "buildRequest");
        doReturn(operationResult).when(jerseyRequestSpy, "executeRequest", POST, builder, dummyEntity);

        // run
        OperationResult<Class> retrieved = jerseyRequestSpy.post(dummyEntity);

        // assertion and verification
        assertEquals(retrieved, operationResult);
        verifyPrivate(jerseyRequestSpy).invoke("buildRequest");
        verifyPrivate(jerseyRequestSpy).invoke("executeRequest", POST, builder, dummyEntity);
    }

    @Test(testName = "addParam")
    public void should_pass_proper_param_to_queryParam_method_and_return_RequestBuilder_object() {

        // run
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.addParam("name", "a", "b", "c");

        // assertion and verification
        assertSame(expected, retrieved);
        verify(webTarget, times(1)).queryParam("name", "a", "b", "c");
    }

    @Test(testName = "addParams")
    public void should_pass_proper_params_map_to_queryParam_method_and_return_RequestBuilder_object() {

        MultivaluedMap<String, String> fakeParams = new MultivaluedHashMap<String, String>() {{
            add("key", "value");
        }};

        // run
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.addParams(fakeParams);

        // assertion and verification
        assertSame(expected, retrieved);
        verify(webTarget, times(1)).queryParam("key", "value");
    }

    @Test(testName = "addMatrixParam")
    public void should_add_proper_param_to_WebTarget_and_return_RequestBuilder_object() {

        // run
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.addMatrixParam("name", "a", "b", "c");

        // assertion and verification
        assertSame(expected, retrieved);
        verify(webTarget, times(1)).matrixParam("name", "a", "b", "c");

    }

    @Test(testName = "addMatrixParams")
    public void should_add_proper_params_map_to_WebTarget_and_return_RequestBuilder_object() {

        MultivaluedMap<String, String> fakeParams = new MultivaluedHashMap<String, String>() {{
            add("key", "value");
        }};

        // run
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.addMatrixParams(fakeParams);

        // assertion and verification
        assertSame(expected, retrieved);
        verify(webTarget, times(1)).matrixParam("key", "value");
    }

    @Test(testName = "setContentType")
    public void should_set_ContentType_and_return_RequestBuilder_object() {

        // run
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.setContentType(MediaType.APPLICATION_JSON);
        String type = ((JerseyRequest<Class>) retrieved).getContentType();

        // assertion and verification
        assertSame(expected, retrieved);
        assertEquals(type, MediaType.APPLICATION_JSON);
        assertNotEquals(type, MediaType.APPLICATION_XML);
    }

    @Test(testName = "setAccept")
    public void should_set_proper_AcceptMime_and_return_RequestBuilder_object() {

        // run
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.setAccept(MediaType.APPLICATION_JSON);
        String type = ((JerseyRequest<Class>) retrieved).getAcceptType();

        // assertion and verification
        assertSame(expected, retrieved);
        assertEquals(type, MediaType.APPLICATION_JSON);
        assertNotEquals(type, MediaType.APPLICATION_XML);
    }

    @AfterMethod
    public void tearDown() {
        reset(sessionStorage, dummyErrorHandler, expected, webTarget, clientConfiguration,
                operationResultFactory, dummyEntity);
    }
}