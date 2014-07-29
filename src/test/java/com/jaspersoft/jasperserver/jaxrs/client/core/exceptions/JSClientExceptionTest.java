package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientException}
 */
public class JSClientExceptionTest {

  @Test
    public void should_pass_message_to_parent_class() {
        JSClientException exception = new JSClientException("msg");
        assertEquals("msg", exception.getMessage());
    }

    @Test
    public void should_pass_message_and_cause_to_parent_class() {
        Throwable dummyCause = new Throwable();
        JSClientException exception = new JSClientException("msg", dummyCause);
        assertEquals(dummyCause, exception.getCause());
        assertEquals("msg", exception.getMessage());
    }

    @Test
    public void should_pass_cause_to_parent_class() {
        Throwable dummyCause = new Throwable();
        JSClientException exception = new JSClientException(dummyCause);
        assertSame(dummyCause, exception.getCause());
    }
}