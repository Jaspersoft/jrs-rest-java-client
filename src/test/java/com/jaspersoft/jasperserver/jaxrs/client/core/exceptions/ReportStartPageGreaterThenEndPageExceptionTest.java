package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ReportStartPageGreaterThenEndPageException}
 */
public class ReportStartPageGreaterThenEndPageExceptionTest {

    @Test
    public void should_invoke_parent_constructor() {
        ReportStartPageGreaterThenEndPageException exception =
                new ReportStartPageGreaterThenEndPageException();
        assertNotNull(exception.getErrorDescriptors());
    }

    @Test
    public void should_pass_message_to_parent_class() {
        ReportStartPageGreaterThenEndPageException exception =
                new ReportStartPageGreaterThenEndPageException("msg");
        assertEquals("msg", exception.getMessage());
    }

    @Test
    public void should_pass_message_and_descriptors_to_parent_class() {
        List<ErrorDescriptor> expected = new ArrayList<ErrorDescriptor>();
        ReportStartPageGreaterThenEndPageException exception =
                new ReportStartPageGreaterThenEndPageException("msg", expected);
        assertEquals(expected, exception.getErrorDescriptors());
        assertEquals("msg", exception.getMessage());
    }
}