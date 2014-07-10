package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.glassfish.jersey.client.JerseyWebTarget;
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
 * Unit tests for {@link JerseyRequest}.
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

    private String fakePath = "some/cool/fakePath";

    @BeforeMethod
    public void setUp() throws Exception {
        initMocks(this);



        // General expectations setup

//        whenNew(OperationResultFactoryImpl.class).withNoArguments().thenReturn(operationResultFactory);
//        when(sessionStorage.getConfiguration()).thenReturn(clientConfiguration);
//        when(clientConfiguration.getContentMimeType()).thenReturn(MimeType.JSON);
//        when(sessionStorage.getRootTarget()).thenReturn(webTarget);
//        when(webTarget.path(anyString())).thenReturn(webTarget);
//        when(webTarget.register(CustomRepresentationTypeProvider.class)).thenReturn(webTarget);
//        when(webTarget.register(JacksonFeature.class)).thenReturn(webTarget);
//        when(webTarget.register(MultiPartWriter.class)).thenReturn(webTarget);
    }

    /**
     * The method under test is {@link JerseyRequest#buildRequest(SessionStorage, Class, String[])}
     */
    @Test(testName = "buildRequest")
    public void should_invoke_overloaded_buildRequest_method() {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        Mockito.when(JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath)).thenCallRealMethod();

        // When
        JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath); // invocation ot outer method

        // Then
        PowerMockito.verifyStatic(times(1)); // this verify invocation refers to the line below (4 params method)
        JerseyRequest.buildRequest(sessionStorage, Class.class, fakeArrayPath, null);
    }

    /**
     * The method under test is {@link JerseyRequest#buildRequest(SessionStorage, Class, String[], ErrorHandler)}
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

    @Test(testName = "buildRequest_with_4_params")
    public void should_initialize_errorHandler_field_with_default_ErrorHandler_implementation() throws Exception {

        // setup the expectation
        JerseyRequest<Class> spy = spy(new JerseyRequest<Class>(sessionStorage, Class.class));
        whenNew(JerseyRequest.class).withAnyArguments().thenReturn(spy);

        // run
        JerseyRequest<Class> retrieved = JerseyRequest.buildRequest(sessionStorage, Class.class,
                fakeArrayPath, null);

        // assertions
        assertNotNull(retrieved);
        assertNotNull(retrieved.getErrorHandler());
    }

    @Test(testName = "JerseyRequest_constructor")
    public void should_create_proper_JerseyRequest_object() throws Exception {

        // run
        JerseyRequest<Class> retrieved = new JerseyRequest<Class>(sessionStorage, Class.class);

        // assertions
        assertNotNull(retrieved);
        assertNotNull(retrieved.getUsersWebTarget()); // TODO: is it a proper result (NOT NULL)?
        assertEquals(retrieved.getContentType(), MediaType.APPLICATION_JSON);
        assertEquals(retrieved.getUsersWebTarget(), webTarget);
        assertEquals(retrieved.getOperationResultFactory(), operationResultFactory);
        assertEquals(retrieved.getResponseClass(), Class.class);
    }

    @Test(testName = "JerseyRequest_constructor")
    public void should_create_proper_JerseyRequest_object_with_XML_ContentMimeType() throws Exception {

        // setup the expectation
        when(clientConfiguration.getContentMimeType()).thenReturn(MimeType.XML);

        // run
        JerseyRequest<Class> retrieved = new JerseyRequest<Class>(sessionStorage, Class.class);

        // assertions
        assertNotNull(retrieved);
        assertEquals(retrieved.getContentType(), MediaType.APPLICATION_XML);
        assertEquals(retrieved.getUsersWebTarget(), webTarget);
        assertEquals(retrieved.getOperationResultFactory(), operationResultFactory);
        assertEquals(retrieved.getResponseClass(), Class.class);
    }

    @Test(testName = "JerseyRequest_constructor")
    public void should_create_proper_JerseyRequest_object_with_JSON_AcceptType() throws Exception {

        // setup the expectation
        when(clientConfiguration.getAcceptMimeType()).thenReturn(MimeType.JSON);
        when(clientConfiguration.getContentMimeType()).thenReturn(MimeType.JSON);

        // run
        JerseyRequest<Class> retrieved = new JerseyRequest<Class>(sessionStorage, Class.class);

        // assertions
        assertNotNull(retrieved);
        assertEquals(retrieved.getContentType(), MediaType.APPLICATION_JSON);
        assertEquals(retrieved.getUsersWebTarget(), webTarget);
        assertEquals(retrieved.getAcceptType(), MediaType.APPLICATION_JSON);
        assertEquals(retrieved.getOperationResultFactory(), operationResultFactory);
        assertEquals(retrieved.getResponseClass(), Class.class);
    }

    @Test(testName = "setPath")
    public void should_pass_proper_param_to_setPath_method_of_WebTarget_and_return_JerseyRequest_object() {

        // run
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        JerseyRequest<Class> retrieved = expected.setPath(fakePath);

        // assertion and verification
        assertSame(expected, retrieved);
        verify(webTarget, times(1)).path(fakePath);
    }

    @Test(testName = "get")
    public void should_invoke_buildRequest_and_executeRequest_method_and_return_OperationResult() throws Exception {

        // setup the expectation
        int GET = 0;
        JerseyRequest<Class> jerseyRequestSpy = spy(new JerseyRequest<Class>(sessionStorage, Class.class));

        doReturn(builder).when(jerseyRequestSpy, "buildRequest");
        doReturn(operationResult).when(jerseyRequestSpy, "executeRequest", GET, builder);

        // run
        OperationResult<Class> retrieved = jerseyRequestSpy.get();

        // assertion and verification
        assertEquals(retrieved, operationResult);
        verifyPrivate(jerseyRequestSpy).invoke("buildRequest");
        verifyPrivate(jerseyRequestSpy).invoke("executeRequest", GET, builder);
    }

    @Test(testName = "delete")
    public void should_send_Jersey_request_with_proper_HTTP_DELETE_method() throws Exception {

        // setup the expectation
        int DELETE = 1;
        JerseyRequest<Class> jerseyRequestSpy = spy(new JerseyRequest<Class>(sessionStorage, Class.class));

        doReturn(builder).when(jerseyRequestSpy, "buildRequest");
        doReturn(operationResult).when(jerseyRequestSpy, "executeRequest", DELETE, builder);

        // run
        OperationResult<Class> retrieved = jerseyRequestSpy.delete();

        // assertion and verification
        assertEquals(retrieved, operationResult);
        verifyPrivate(jerseyRequestSpy).invoke("buildRequest");
        verifyPrivate(jerseyRequestSpy).invoke("executeRequest", DELETE, builder);
    }

    @Test(testName = "put")
    public void should_send_Jersey_request_with_proper_HTTP_PUT_method() throws Exception {

        // setup the expectation
        int PUT = 3;
        JerseyRequest<Class> jerseyRequestSpy = spy(new JerseyRequest<Class>(sessionStorage, Class.class));

        doReturn(builder).when(jerseyRequestSpy, "buildRequest");
        doReturn(operationResult).when(jerseyRequestSpy, "executeRequest", PUT, builder, dummyEntity);

        // run
        OperationResult<Class> retrieved = jerseyRequestSpy.put(dummyEntity);

        // assertion and verification
        assertEquals(retrieved, operationResult);
        verifyPrivate(jerseyRequestSpy).invoke("buildRequest");
        verifyPrivate(jerseyRequestSpy).invoke("executeRequest", PUT, builder, dummyEntity);
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

        MultivaluedMap<String, String> fakeParams = new MultivaluedMapImpl() {{
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

        MultivaluedMap<String, String> fakeParams = new MultivaluedMapImpl() {{
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

    @Test(testName = "addHeader")
    public void should_add_Header_and_return_RequestBuilder_object() {

        // run
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.addHeader("name", "a");
        MultivaluedMap<String, String> headers = ((JerseyRequest<Class>) retrieved).getHeaders();

        // assertion and verification
        assertSame(expected, retrieved);
        assertEquals(headers.getFirst("name"), "a");
    }

    @Test(testName = "setHeaders")
    public void should_set_headers_and_return_RequestBuilder_object() {

        MultivaluedMap<String, String> fakeParams = new MultivaluedMapImpl() {{
            add("key", "value");
        }};

        // run
        JerseyRequest<Class> expected = new JerseyRequest<Class>(sessionStorage, Class.class);
        RequestBuilder<Class> retrieved = expected.setHeaders(fakeParams);
        MultivaluedMap<String, String> headersParam = ((JerseyRequest<Class>) retrieved).getHeaders();

        // assertion and verification
        assertSame(expected, retrieved);
        assertEquals(headersParam, fakeParams);
    }

    @AfterMethod
    public void tearDown() {
        reset(sessionStorage, dummyErrorHandler, expected, webTarget, clientConfiguration, operationResultFactory, dummyEntity);
    }
}