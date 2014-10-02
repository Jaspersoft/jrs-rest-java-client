package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link IllegalParameterValueException}
 */
public class IllegalParameterValueExceptionTest {

    @Test
    public void should_invoke_parent_constructor() {
        IllegalParameterValueException exception = new IllegalParameterValueException();
        assertNotNull(exception.getErrorDescriptors());
    }

    @Test
    public void should_pass_message_to_parent_class() {
        IllegalParameterValueException exception = new IllegalParameterValueException("msg");
        assertEquals("msg", exception.getMessage());
    }

    @Test
    public void should_pass_message_and_descriptors_to_parent_class() {
        List<ErrorDescriptor> expected = new ArrayList<ErrorDescriptor>();
        IllegalParameterValueException exception = new IllegalParameterValueException("msg", expected);
        assertEquals(expected, exception.getErrorDescriptors());
        assertEquals("msg", exception.getMessage());
    }
}