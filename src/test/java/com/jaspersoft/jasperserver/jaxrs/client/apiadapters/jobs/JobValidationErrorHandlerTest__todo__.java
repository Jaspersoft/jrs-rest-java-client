package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ValidationErrorsListWrapper;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.lang.reflect.Method;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobValidationErrorHandler}
 */
@PrepareForTest({JobValidationErrorHandler.class, DefaultErrorHandler.class})
public class JobValidationErrorHandlerTest__todo__ extends PowerMockTestCase {

    @Mock
    private Response responseMock;

    @Mock
    private ValidationErrorsListWrapper validationErrorsListWrapperMock;

    @Mock
    private List<ErrorDescriptor> descriptorsMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void test() throws Exception {

        // FIXME: !!!

        // Given
        JobValidationErrorHandler handler = spy(new JobValidationErrorHandler());
        Method readBody = method(DefaultErrorHandler.class, "readBody");
        Method childReadBody = method(JobValidationErrorHandler.class, "readBody");
        Method handleBodyError = method(DefaultErrorHandler.class, "handleBodyError", Response.class);

        PowerMockito.doReturn("application/xml").when(responseMock).getHeaderString("Content-Type");
        PowerMockito.doReturn(validationErrorsListWrapperMock).when(handler, readBody).withArguments(responseMock, ValidationErrorsListWrapper.class);
        PowerMockito.doNothing().when(handler, handleBodyError).withArguments(responseMock);

        PowerMockito.doReturn(descriptorsMock).when(handler).toErrorDescriptorList(validationErrorsListWrapperMock);

        // When
        handler.handleBodyError(responseMock);

        // Than
        Mockito.verify(handler, times(1)).handleBodyError(responseMock);
    }

    @AfterMethod
    public void after() {
        reset(responseMock, validationErrorsListWrapperMock, descriptorsMock);
    }
}