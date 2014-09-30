package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class RequestedRepresentationNotAvailableForResourceExceptionTest {

    @Test
    public void should_create_an_object_of_exception_class() {
        RequestedRepresentationNotAvailableForResourceException instance =
                new RequestedRepresentationNotAvailableForResourceException();

        assertNotNull(instance);
    }

    @Test
    public void should_construct_an_instance_with_proper_param() {
        RequestedRepresentationNotAvailableForResourceException instance =
                new RequestedRepresentationNotAvailableForResourceException("msg");

        assertEquals(instance.getMessage(), "msg");
    }

    @Test
    public void should_pass_message_and_descriptors_to_parent_class() {
        List<ErrorDescriptor> expected = new ArrayList<ErrorDescriptor>();
        RequestedRepresentationNotAvailableForResourceException exception =
                new RequestedRepresentationNotAvailableForResourceException("msg", expected);
        assertEquals(expected, exception.getErrorDescriptors());
        assertEquals("msg", exception.getMessage());
    }
}