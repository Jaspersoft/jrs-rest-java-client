package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ValidationException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ValidationError;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ValidationErrorsListWrapper;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobValidationErrorHandler}
 * @deprecated
 */
@PrepareForTest({DefaultErrorHandler.class, JobValidationErrorHandler.class})
public class JobValidationErrorHandlerTest extends PowerMockTestCase {

    @Mock
    private Response responseMock;

    @Mock
    private ValidationErrorsListWrapper wrapperMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void should_throw_an_exception_when_retrieved_validation_errors() throws Exception {

        /* Given */

        List<ValidationError> errors = new ArrayList<ValidationError>() {{
            add(new ValidationError("code1", new Object[]{}, "msg_1"));
            add(new ValidationError("code2", new Object[]{}, "msg_2"));
        }};

        Mockito.when(responseMock.getHeaderString("Content-Type")).thenReturn("application/xml");
        Mockito.when(responseMock.readEntity(ValidationErrorsListWrapper.class)).thenReturn(wrapperMock);
        Mockito.when(wrapperMock.getErrors()).thenReturn(errors);

        /* When */
        JobValidationErrorHandler handler = new JobValidationErrorHandler();
        handler.handleBodyError(responseMock);

        /* Then */
        Mockito.verify(responseMock, times(1)).getHeaderString("Content-Type");
        Mockito.verify(responseMock, times(1)).readEntity(ValidationErrorsListWrapper.class);
    }

    @Test
    public void should_handle_body_error() {

        /* Given */
        final ValidationErrorsListWrapper expected = null;
        final int wantedNumberOfInvocations = 2; // invoke getHeaderString length in child and parent class
        Mockito.when(responseMock.getHeaderString("Content-Type")).thenReturn("application/xml");
        Mockito.when(responseMock.readEntity(ValidationErrorsListWrapper.class)).thenReturn(expected);

        /* When */
        JobValidationErrorHandler handler = new JobValidationErrorHandler();
        handler.handleBodyError(responseMock);

        /* Then */
        Mockito.verify(responseMock, times(wantedNumberOfInvocations)).getHeaderString("Content-Type");
    }

    @Test
    public void should_handle_body_error_when_content_type_of_response_in_text_html() {

        /* Given */
        final ValidationErrorsListWrapper expected = null;
        Mockito.when(responseMock.getHeaderString("Content-Type")).thenReturn("text/html");
        Mockito.when(responseMock.readEntity(ValidationErrorsListWrapper.class)).thenReturn(expected);

        /* When */
        JobValidationErrorHandler handler = Mockito.spy(new JobValidationErrorHandler());
        handler.handleBodyError(responseMock);

        /* Then */
        Mockito.verify(responseMock, times(3)).getHeaderString("Content-Type");
        Mockito.verify(handler, times(1)).handleBodyError(responseMock);
    }

    @Test
    public void should_convert_error_descriptor_to_list() {
        List<ValidationError> errors = new ArrayList<ValidationError>() {{
            add(new ValidationError("code1", new Object[]{}, "msg_1"));
            add(new ValidationError("code2", new Object[]{}, "msg_2"));
        }};
        Mockito.doReturn(errors).when(wrapperMock).getErrors();
        JobValidationErrorHandler handler = new JobValidationErrorHandler();
        List<ErrorDescriptor> retrieved = handler.toErrorDescriptorList(wrapperMock);
        Assert.assertTrue(retrieved.size() == 2);
        Assert.assertSame(retrieved.get(0).getErrorCode(), "code1");
    }

    @AfterMethod
    public void after() {
        reset(responseMock, wrapperMock);
    }
}