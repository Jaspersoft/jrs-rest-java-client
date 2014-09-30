package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.NotAFileException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import org.apache.commons.logging.Log;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler}
 */
@PrepareForTest({DefaultErrorHandler.class, ErrorDescriptor.class})
public class DefaultErrorHandlerTest extends PowerMockTestCase {

    @Mock
    private Log logMock;

    @Mock
    private Response responseMock;

    @Mock
    private ErrorDescriptor descriptorMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_handle_error() {
        Mockito.when(responseMock.hasEntity()).thenReturn(true);
        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());

        Mockito.doNothing().when(handlerSpy).handleBodyError(responseMock);
        Mockito.doNothing().when(handlerSpy).handleStatusCodeError(responseMock, null);

        handlerSpy.handleError(responseMock);

        Mockito.verify(responseMock, Mockito.times(1)).hasEntity();
        Mockito.verify(responseMock, Mockito.times(1)).bufferEntity();

        Mockito.verify(handlerSpy, Mockito.times(1)).handleBodyError(responseMock);
        Mockito.verify(handlerSpy, Mockito.times(1)).handleBodyError(responseMock);
    }

    @Test(expectedExceptions = JSClientWebException.class, enabled = false)
    public void should_throw_an_exception_when_() {

        Response.StatusType fakeStatusSpy = Mockito.spy(new Response.StatusType() {
            @Override
            public int getStatusCode() {
                return 403;
            }

            @Override
            public Response.Status.Family getFamily() {
                return null;
            }

            @Override
            public String getReasonPhrase() {
                return "Forbidden";
            }
        });

        Mockito.when(responseMock.getStatus()).thenReturn(403);
        Mockito.when(responseMock.getStatusInfo()).thenReturn(fakeStatusSpy);

        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());
        handlerSpy.handleStatusCodeError(responseMock, "msg");
    }


    @Test
    public void should_return_entity_of_proper_class() {

        /* Given */
        OperationResult resultMock = Mockito.mock(OperationResult.class);
        Mockito.when(responseMock.readEntity(OperationResult.class)).thenReturn(resultMock);
        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());

        /* When */
        OperationResult retrieved = handlerSpy.readBody(responseMock, OperationResult.class);

        /* Than */
        assertNotNull(retrieved);
        assertSame(retrieved, resultMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_throw_an_exception_while_reading_entity_from_response() {

        /* Given */
        Mockito.when(responseMock.readEntity(OperationResult.class)).thenThrow(MessageBodyProviderNotFoundException.class);
        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());

        /* When */
        OperationResult retrieved = handlerSpy.readBody(responseMock, OperationResult.class);

        /* Than */
        assertNull(retrieved);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_throw_ProcessingException_while_reading_entity_from_response() {

        /* Given */
        Mockito.when(responseMock.readEntity(OperationResult.class)).thenThrow(ProcessingException.class);
        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());

        /* When */
        OperationResult retrieved = handlerSpy.readBody(responseMock, OperationResult.class);

        /* Than */
        assertNull(retrieved);
    }

    @Test
    public void should_handle_body_error() {

        /* Given */
        Mockito.when(responseMock.getHeaderString("Content-Type")).thenReturn("text/html");
        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());

        /* When */
        handlerSpy.handleBodyError(responseMock);

        /* Than */
        verify(responseMock).getHeaderString("Content-Type");
    }

    @Test(expectedExceptions = ResourceNotFoundException.class, enabled = false)
    public void should_throw_an_exception() throws Exception {

        /* Given */
        ErrorDescriptor descriptorMock = PowerMockito.mock(ErrorDescriptor.class);
        Mockito.doReturn("non-text-html").when(responseMock).getHeaderString("Content-Type");
        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());
        Mockito.doReturn(descriptorMock).when(responseMock).readEntity(ErrorDescriptor.class);
        Mockito.doReturn("resource.not.found").when(descriptorMock).getErrorCode();

        /* When */
        handlerSpy.handleBodyError(responseMock);

        /* Than throw an exception */
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler#handleStatusCodeError(javax.ws.rs.core.Response, String)}
     */
    public void should_throw_proper_exception() {

        /* Given */
        Mockito.doReturn(404).when(responseMock).getStatus();
        Mockito.doReturn(Response.Status.NOT_FOUND).when(responseMock).getStatusInfo();

        /* When */
        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());

        /* Than */
        try {
            handlerSpy.handleError(responseMock);
        } catch (Exception e) {
            //assertTrue(instanceOf(ResourceNotFoundException.class).matches(e));
            Mockito.verify(responseMock, times(1)).getStatusInfo();
            Mockito.verify(responseMock, times(1)).getStatus();
        }
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler#handleStatusCodeError(javax.ws.rs.core.Response, String)}
     */
    public void should_throw_proper_exception_type_instance_and_log_error() {

        /* Given */
        Mockito.doReturn(1000).when(responseMock).getStatus();
        Mockito.doReturn(Response.Status.NOT_FOUND).when(responseMock).getStatusInfo();
        Mockito.doNothing().when(logMock).error(anyString(), any(NullPointerException.class));

        /* When */
        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());
        Whitebox.setInternalState(handlerSpy, "log", logMock);

        /* Than */
        try {
            handlerSpy.handleError(responseMock);
        } catch (Exception e) {
            Mockito.verify(responseMock, times(1)).getStatusInfo();
            Mockito.verify(responseMock, times(1)).getStatus();
            Mockito.verify(logMock, times(1)).error(anyString(), any(NullPointerException.class));
        }
    }

    @Test
    public void should_invoke_handleBodyError_method_and_throw_proper_exception() throws Exception {

        /* Given */
        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());
        Mockito.doReturn(descriptorMock).when(responseMock).readEntity(ErrorDescriptor.class);
        Mockito.doReturn("application/json").when(responseMock).getHeaderString("Content-Type");
        Mockito.doReturn("not.a.file").when(descriptorMock).getErrorCode();
        Mockito.doReturn("someErrorMessage").when(descriptorMock).getMessage();

        /* When */
        try {
            handlerSpy.handleError(responseMock);
        } catch (Exception e) {

            /* Than */
            assertTrue(instanceOf(NotAFileException.class).matches(e));

            Mockito.verify(responseMock, times(1)).readEntity(ErrorDescriptor.class);
            Mockito.verify(responseMock, times(1)).getHeaderString("Content-Type");
            Mockito.verify(descriptorMock, times(1)).getErrorCode();
            Mockito.verify(descriptorMock, times(1)).getMessage();
        }
    }

    @Test
    public void should_invoke_handleBodyError_method_and_log_error_when_cannot_instantiate_exception_class() {

        /* Given */
        class CustomJSClientWebException extends JSClientWebException{
            CustomJSClientWebException (Integer parameter){

            }
        }
        JRSExceptionsMapping.ERROR_CODE_TO_TYPE_MAP.put("key", CustomJSClientWebException.class);

        DefaultErrorHandler handlerSpy = Mockito.spy(new DefaultErrorHandler());
        Whitebox.setInternalState(handlerSpy, "log", logMock);

        Mockito.doNothing().when(logMock).warn(anyString(), any(NoSuchMethodException.class));
        Mockito.doReturn(descriptorMock).when(responseMock).readEntity(ErrorDescriptor.class);
        Mockito.doReturn("application/json").when(responseMock).getHeaderString("Content-Type");
        Mockito.doReturn("key").when(descriptorMock).getErrorCode();
        Mockito.doReturn("someErrorMessage").when(descriptorMock).getMessage();

        /* When */
        try {
            handlerSpy.handleError(responseMock);
        } catch (Exception e){

            /* Than */
            Mockito.verify(logMock, times(1)).warn(anyString(), any(NoSuchMethodException.class));
        }

    }

    @AfterMethod
    public void after() {
        reset(responseMock, descriptorMock, logMock);
    }
}