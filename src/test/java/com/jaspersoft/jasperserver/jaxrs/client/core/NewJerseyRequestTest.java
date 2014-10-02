package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.dto.resources.ClientFolder;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.WithEntityOperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.providers.CustomRepresentationTypeProvider;
import com.sun.jersey.multipart.impl.MultiPartWriter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * @author Alexander Krasnyanskiy
 */
public class NewJerseyRequestTest extends PowerMockTestCase {

    @Mock
    public SessionStorage sessionStorageMock;

    @Mock
    public ErrorHandler handlerMock;

    @Mock
    public RestClientConfiguration configurationMock;

    @Mock
    public WebTarget targetMock;

    @Mock
    public Invocation.Builder builderMock;

    @Mock
    public Response responseMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_invoke_private_logic_and_sublogic_andsubsub_logic() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));

        Class stateDtoClass = StateDto.class;
        InOrder inOrder = Mockito.inOrder(targetMock);

        /* When */
        JerseyRequest<StateDto> retrieved = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass,
                new String[]{"/uri"}, handlerMock);

        /* Than */
        assertNotNull(retrieved);
        assertSame(retrieved.getUsersWebTarget(), targetMock);
        assertSame(retrieved.getAcceptType(), "application/json");
        assertSame(retrieved.getContentType(), "application/json");
        assertSame(retrieved.getErrorHandler(), handlerMock);

        Object retrievedOperationResultFactory = Whitebox.getInternalState(retrieved, "operationResultFactory");
        assertNotNull(retrievedOperationResultFactory);
        assertTrue(instanceOf(OperationResultFactoryImpl.class).matches(retrievedOperationResultFactory));

        assertSame(retrieved.getResponseClass(), StateDto.class);

        Mockito.verify(sessionStorageMock, times(1)).getConfiguration();
        Mockito.verify(configurationMock, times(1)).getContentMimeType();
        Mockito.verify(configurationMock, times(1)).getAcceptMimeType();

        assertNotNull(retrieved.getHeaders());
        assertTrue(retrieved.getHeaders().size() == 0);

        inOrder.verify(targetMock).path("/rest_v2");
        inOrder.verify(targetMock).register(CustomRepresentationTypeProvider.class);
        inOrder.verify(targetMock).register(JacksonFeature.class);
        inOrder.verify(targetMock).register(MultiPartWriter.class);
        inOrder.verify(targetMock).path("/uri");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_invoke_private_logic_and_sublogic_and_subsublogic_when_content_type_is_xml() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));

        Class stateDtoClass = StateDto.class;
        InOrder inOrder = Mockito.inOrder(targetMock);

        /* When */
        JerseyRequest<StateDto> retrieved = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass,
                new String[]{"/uri"}, handlerMock);

        /* Than */
        assertNotNull(retrieved);
        assertSame(retrieved.getUsersWebTarget(), targetMock);
        assertSame(retrieved.getAcceptType(), "application/xml");
        assertSame(retrieved.getContentType(), "application/xml");
        assertSame(retrieved.getErrorHandler(), handlerMock);

        Object retrievedOperationResultFactory = Whitebox.getInternalState(retrieved, "operationResultFactory");
        assertNotNull(retrievedOperationResultFactory);
        assertTrue(instanceOf(OperationResultFactoryImpl.class).matches(retrievedOperationResultFactory));

        assertSame(retrieved.getResponseClass(), StateDto.class);

        Mockito.verify(sessionStorageMock, times(1)).getConfiguration();
        Mockito.verify(configurationMock, times(1)).getContentMimeType();
        Mockito.verify(configurationMock, times(1)).getAcceptMimeType();

        assertNotNull(retrieved.getHeaders());
        assertTrue(retrieved.getHeaders().size() == 0);

        inOrder.verify(targetMock).path("/rest_v2");
        inOrder.verify(targetMock).register(CustomRepresentationTypeProvider.class);
        inOrder.verify(targetMock).register(JacksonFeature.class);
        inOrder.verify(targetMock).register(MultiPartWriter.class);
        inOrder.verify(targetMock).path("/uri");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_set_field_with_proper_handler() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));

        Class stateDtoClass = StateDto.class;

        /* When */
        JerseyRequest<StateDto> retrieved = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass, new String[]{"/uri"});

        /* Than */
        assertNotNull(retrieved.getHeaders());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_invoke_deep_logic_of_unit() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(builderMock).when(builderMock).header(anyString(), anyString());
        Mockito.doReturn(responseMock).when(builderMock).get();
        Mockito.doReturn(builderMock).when(builderMock).accept("application/xml");
        Mockito.doReturn(true).when(responseMock).hasEntity();

        Class stateDtoClass = ClientFolder.class;

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass, new String[]{"/uri"});
        OperationResult<StateDto> retrieved = jerseyRequest.get();


        /* Than */
        assertNotNull(retrieved);
        assertTrue(instanceOf(WithEntityOperationResult.class).matches(retrieved));

        Mockito.verify(targetMock, times(1)).request();
        Mockito.verify(builderMock, times(1)).accept("application/xml");
        Mockito.verify(builderMock, times(1)).get();
        Mockito.verify(responseMock, times(1)).hasEntity();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_do_post() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(builderMock).when(builderMock).header(anyString(), anyString());
        Mockito.doReturn(responseMock).when(builderMock).post(any(Entity.class));
        Mockito.doReturn(builderMock).when(builderMock).accept("application/xml");
        Mockito.doReturn(true).when(responseMock).hasEntity();

        Class stateDtoClass = ClientFolder.class;
        ClientFolder dummyFolder = new ClientFolder();

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass, new String[]{"/uri"});
        OperationResult<StateDto> retrieved = jerseyRequest.post(dummyFolder);

        /* Than */
        assertNotNull(retrieved);
        assertTrue(instanceOf(WithEntityOperationResult.class).matches(retrieved));

        Mockito.verify(targetMock, times(1)).request();
        Mockito.verify(builderMock, times(1)).accept("application/xml");
        Mockito.verify(builderMock, times(1)).post(any(Entity.class));
        Mockito.verify(responseMock, times(1)).hasEntity();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_do_put() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(builderMock).when(builderMock).header(anyString(), anyString());
        Mockito.doReturn(responseMock).when(builderMock).put(any(Entity.class));
        Mockito.doReturn(builderMock).when(builderMock).accept("application/xml");
        Mockito.doReturn(true).when(responseMock).hasEntity();

        Class stateDtoClass = ClientFolder.class;
        ClientFolder dummyFolder = new ClientFolder();

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass, new String[]{"/uri"});
        OperationResult<StateDto> retrieved = jerseyRequest.put(dummyFolder);

        /* Than */
        assertNotNull(retrieved);
        assertTrue(instanceOf(WithEntityOperationResult.class).matches(retrieved));

        Mockito.verify(targetMock, times(1)).request();
        Mockito.verify(builderMock, times(1)).accept("application/xml");
        Mockito.verify(builderMock, times(1)).put(any(Entity.class));
        Mockito.verify(responseMock, times(1)).hasEntity();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_do_delete() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(builderMock).when(builderMock).header(anyString(), anyString());
        Mockito.doReturn(responseMock).when(builderMock).delete();
        Mockito.doReturn(builderMock).when(builderMock).accept("application/xml");
        Mockito.doReturn(true).when(responseMock).hasEntity();
        Mockito.doReturn(200).when(responseMock).getStatus();

        Class stateDtoClass = ClientFolder.class;

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass, new String[]{"/uri"});
        OperationResult<StateDto> retrieved = jerseyRequest.delete();

        /* Than */
        assertNotNull(retrieved);
        assertTrue(instanceOf(WithEntityOperationResult.class).matches(retrieved));

        Mockito.verify(targetMock, times(1)).request();
        Mockito.verify(builderMock, times(1)).accept("application/xml");
        Mockito.verify(builderMock, times(1)).delete();
        Mockito.verify(responseMock, times(1)).hasEntity();
        Mockito.verify(responseMock, times(1)).getStatus();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_set_headers_with_proper_param() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(builderMock).when(builderMock).header(anyString(), anyString());
        Mockito.doReturn(responseMock).when(builderMock).delete();
        Mockito.doReturn(builderMock).when(builderMock).accept("application/xml");
        Mockito.doReturn(true).when(responseMock).hasEntity();
        Mockito.doReturn(200).when(responseMock).getStatus();

        Class stateDtoClass = ClientFolder.class;

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass, new String[]{"/uri"});
        jerseyRequest.addHeader("Cache-Control", "no-cache");
        OperationResult<StateDto> retrieved = jerseyRequest.delete();

        /* Than */
        assertNotNull(retrieved);
        assertTrue(instanceOf(WithEntityOperationResult.class).matches(retrieved));
        assertTrue(jerseyRequest.getHeaders().size() == 1);
        assertEquals(jerseyRequest.getHeaders().get("Cache-Control").get(0), "no-cache");

        Mockito.verify(targetMock, times(1)).request();
        Mockito.verify(builderMock, times(1)).accept("application/xml");
        Mockito.verify(builderMock, times(1)).delete();
        Mockito.verify(responseMock, times(1)).hasEntity();
        Mockito.verify(responseMock, times(1)).getStatus();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_set_headers_with_proper_params() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(builderMock).when(builderMock).header(anyString(), anyString());
        Mockito.doReturn(responseMock).when(builderMock).delete();
        Mockito.doReturn(builderMock).when(builderMock).accept("application/xml");
        Mockito.doReturn(true).when(responseMock).hasEntity();
        Mockito.doReturn(200).when(responseMock).getStatus();

        Class stateDtoClass = ClientFolder.class;

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass, new String[]{"/uri"});
        jerseyRequest.addHeader("Cache-Control", "no-cache");
        jerseyRequest.setHeaders(new MultivaluedHashMap<String, String>() {{
            put("Cache-Control", Arrays.asList("no-cache"));
            put("Accept-Ranges", Arrays.asList("bytes"));
        }});
        OperationResult<StateDto> retrieved = jerseyRequest.delete();

        /* Than */
        assertNotNull(retrieved);
        assertTrue(instanceOf(WithEntityOperationResult.class).matches(retrieved));
        assertEquals(jerseyRequest.getHeaders().get("Cache-Control").get(0), "no-cache");
        assertTrue(jerseyRequest.getHeaders().size() == 2);
        assertNotNull(jerseyRequest.getOperationResultFactory());

        Mockito.verify(targetMock, times(1)).request();
        Mockito.verify(builderMock, times(1)).accept("application/xml");
        Mockito.verify(builderMock, times(1)).delete();
        Mockito.verify(responseMock, times(1)).hasEntity();
        Mockito.verify(responseMock, times(1)).getStatus();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_invoke_private_logic() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));

        Class stateDtoClass = StateDto.class;

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass,
                new String[]{"/uri"}, handlerMock);
        RequestBuilder<StateDto> retrieved = jerseyRequest.addParam("param", "val");

        /* Than */
        Mockito.verify(targetMock, times(1)).queryParam("param", "val");
        assertSame(jerseyRequest, retrieved);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_invoke_private_logic_() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));
        Mockito.doReturn(targetMock).when(targetMock).queryParam(anyString(), any(Object[].class));


        Class stateDtoClass = StateDto.class;

        MultivaluedHashMap<String, String> params = new MultivaluedHashMap<String, String>() {{
            put("param1", Arrays.asList("val1"));
            put("param2", Arrays.asList("val2"));
        }};

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass,
                new String[]{"/uri"}, handlerMock);
        RequestBuilder<StateDto> retrieved = jerseyRequest.addParams(params);

        /* Than */
        Mockito.verify(targetMock, times(2)).queryParam(anyString(), anyString());
        assertSame(jerseyRequest, retrieved);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_add_proper_amount_of_matrix_param() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));
        Mockito.doReturn(targetMock).when(targetMock).matrixParam(anyString(), anyVararg());

        Class stateDtoClass = StateDto.class;

        MultivaluedHashMap<String, String> params = new MultivaluedHashMap<String, String>() {{
            put("param1", Arrays.asList("val1"));
            put("param2", Arrays.asList("val2"));
        }};

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass,
                new String[]{"/uri"}, handlerMock);
        RequestBuilder<StateDto> retrieved = jerseyRequest.addMatrixParam("name", "val1", "val2");

        /* Than */
        Mockito.verify(targetMock, times(1)).matrixParam(anyString(), anyVararg());
        assertSame(jerseyRequest, retrieved);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_add_proper_matrix_params() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));
        Mockito.doReturn(targetMock).when(targetMock).matrixParam(anyString(), anyVararg());

        Class stateDtoClass = StateDto.class;

        MultivaluedHashMap<String, String> params = new MultivaluedHashMap<String, String>() {{
            put("param1", Arrays.asList("val1"));
            put("param2", Arrays.asList("val2"));
        }};

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass, new String[]{"/uri"}, handlerMock);
        RequestBuilder<StateDto> retrieved = jerseyRequest.addMatrixParams(params);

        /* Than */
        Mockito.verify(targetMock, times(2)).matrixParam(anyString(), anyVararg());
        assertSame(jerseyRequest, retrieved);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_set_proper_content_type() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));

        Class stateDtoClass = StateDto.class;

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass, new String[]{"/uri"}, handlerMock);
        RequestBuilder<StateDto> retrieved = jerseyRequest.setContentType("application/json");

        /* Than */
        assertSame(jerseyRequest, retrieved);
        assertEquals(((JerseyRequest<StateDto>)retrieved).getContentType(), "application/json");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_set_accept_type() {

        /* Given */
        Mockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        Mockito.doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).register(any(Class.class));

        Class stateDtoClass = StateDto.class;

        /* When */
        JerseyRequest<StateDto> jerseyRequest = JerseyRequest.buildRequest(sessionStorageMock, stateDtoClass, new String[]{"/uri"}, handlerMock);
        RequestBuilder<StateDto> retrieved = jerseyRequest.setAccept("application/json");

        /* Than */
        assertSame(jerseyRequest, retrieved);
        assertEquals(((JerseyRequest<StateDto>)retrieved).getAcceptType(), "application/json");
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, configurationMock, handlerMock, targetMock, builderMock, responseMock);
    }
}