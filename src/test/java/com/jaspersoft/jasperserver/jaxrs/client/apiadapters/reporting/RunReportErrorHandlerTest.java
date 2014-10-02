package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Unit test for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.RunReportErrorHandler}
 */
@PrepareForTest({DefaultErrorHandler.class, RunReportErrorHandler.class})
public class RunReportErrorHandlerTest extends PowerMockTestCase {

    @Mock
    private Response responseMock;

    @Mock
    private Response.StatusType statusTypeMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(enabled = false)
    public void should_handle_body_error_and_rethrow_exception() {

        Mockito.when(responseMock.getHeaderString("JasperServerError")).thenReturn("true");
        Mockito.when(responseMock.readEntity(String.class)).thenReturn("data");
        Mockito.when(responseMock.getStatus()).thenReturn(404);
        Mockito.when(responseMock.getStatusInfo()).thenReturn(Response.Status.NOT_FOUND);
        Mockito.when(responseMock.getHeaderString("Content-Type")).thenReturn("text/html");

        RunReportErrorHandler handler = new RunReportErrorHandler();

        try {
            handler.handleBodyError(responseMock);
        } catch (Exception e) {
            assertTrue(instanceOf(ResourceNotFoundException.class).matches(e));
        }
    }

    @Test
    public void should_rethrow_exception() {

        Mockito.doReturn("_msg").when(responseMock).readEntity(String.class);
        Mockito.doReturn("true").when(responseMock).getHeaderString("JasperServerError");
        Mockito.doReturn(statusTypeMock).when(responseMock).getStatusInfo();
        Mockito.doReturn("Phrase").when(statusTypeMock).getReasonPhrase();
        Mockito.doReturn(404).when(responseMock).getStatus();

        RunReportErrorHandler handler = new RunReportErrorHandler();

        try {
            handler.handleBodyError(responseMock);
        } catch (Exception e) {
            //assertTrue(instanceOf(ResourceNotFoundException.class).matches(e));
            assertEquals(e.getMessage(), "_msg");
        }
    }

    @Test
    public void should_handle_body_error_and_rethrow_exception_if_HeaderString_is_not_JasperServerError() {

        Mockito.doReturn("_msg").when(responseMock).readEntity(String.class);
        Mockito.doReturn("false").when(responseMock).getHeaderString("JasperServerError");
        Mockito.doReturn(statusTypeMock).when(responseMock).getStatusInfo();
        Mockito.doReturn("Phrase").when(statusTypeMock).getReasonPhrase();
        Mockito.doReturn(404).when(responseMock).getStatus();

        RunReportErrorHandler handler = new RunReportErrorHandler();

        try {
            handler.handleBodyError(responseMock);
        } catch (Exception e) {
            //assertTrue(instanceOf(ResourceNotFoundException.class).matches(e));
            assertEquals(e.getMessage(), "_msg");
        }
    }

    @AfterMethod
    public void after() {
        reset(responseMock, statusTypeMock);
    }
}