package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link JSClientWebException}
 */
public class JSClientWebExceptionTest {

    @Test
    public void should_invoke_parent_constructor() {
        JSClientWebException exception = new JSClientWebException();
        assertNotNull(exception.getErrorDescriptors());
    }

    @Test
    public void should_pass_message_to_parent_class() {
        JSClientWebException exception = new JSClientWebException("msg");
        assertEquals("msg", exception.getMessage());
    }

    @Test
    public void should_pass_message_and_descriptors_to_parent_class() {
        List<ErrorDescriptor> expected = new ArrayList<ErrorDescriptor>();
        JSClientWebException exception = new JSClientWebException("msg", expected);
        assertEquals(expected, exception.getErrorDescriptors());
        assertEquals("msg", exception.getMessage());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_set_the_field_with_proper_param() throws IllegalAccessException {

        // Given
        List<ErrorDescriptor> expected = new ArrayList<ErrorDescriptor>();
        JSClientWebException exception = new JSClientWebException();

        // When
        exception.setErrorDescriptors(expected);

        // Then
        final Field field = field(JSClientWebException.class, "errorDescriptors");
        final List<ErrorDescriptor> retrieved = (List<ErrorDescriptor>) field.get(exception);

        assertSame(retrieved, expected);
    }

    @Test
    public void should_return_not_null_errorDescriptors() {
        JSClientWebException exception = new JSClientWebException();
        assertNotNull(exception.getErrorDescriptors());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_add_descriptor_to_list() throws IllegalAccessException {

        // Given
        ErrorDescriptor descriptor = new ErrorDescriptor();
        JSClientWebException exception = new JSClientWebException();

        // When
        exception.addErrorDescriptor(descriptor);

        // Then
        final Field field = field(JSClientWebException.class, "errorDescriptors");
        final List<ErrorDescriptor> retrieved = (List<ErrorDescriptor>) field.get(exception);

        assertTrue(retrieved.size() == 1);
    }
}