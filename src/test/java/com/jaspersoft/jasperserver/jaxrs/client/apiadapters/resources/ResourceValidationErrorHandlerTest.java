package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ValidationException;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertTrue;

public class ResourceValidationErrorHandlerTest {

    @Mock
    private Response responseMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_throw_exception() {
        Mockito.doReturn("application/json").when(responseMock).getHeaderString("Content-Type");
        Mockito.doReturn(new ErrorDescriptor[]{new ErrorDescriptor()}).when(responseMock).readEntity(ErrorDescriptor[].class);
        ResourceValidationErrorHandler handler = new ResourceValidationErrorHandler();

        try {
            handler.handleBodyError(responseMock);
        } catch (Exception e) {
            assertTrue(instanceOf(ValidationException.class).matches(e));
        }
    }

    @Test
    public void should_do_not_throw_exception_if_ContentType_is_TextHtml() {

        Mockito.doReturn("text/html").when(responseMock).getHeaderString("Content-Type");
        Mockito.doReturn(new ErrorDescriptor[]{}).when(responseMock).readEntity(ErrorDescriptor[].class);
        ResourceValidationErrorHandler handler = new ResourceValidationErrorHandler();
        handler.handleBodyError(responseMock);

        Mockito.verify(responseMock, times(3)).getHeaderString("Content-Type");
        Mockito.verifyNoMoreInteractions(responseMock);
    }

    @Test
    public void should_3() {

        Mockito.doReturn("application/json").when(responseMock).getHeaderString("Content-Type");
        Mockito.doReturn(null).when(responseMock).readEntity(ErrorDescriptor[].class);
        ResourceValidationErrorHandler handler = new ResourceValidationErrorHandler();

        try {
            handler.handleBodyError(responseMock);
        } catch (Exception e) {
            assertTrue(instanceOf(ValidationException.class).matches(e));
        }

    }

    @AfterMethod
    public void after() {
        reset(responseMock);
    }
}