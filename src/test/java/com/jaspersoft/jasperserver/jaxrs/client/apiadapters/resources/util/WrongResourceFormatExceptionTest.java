package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class WrongResourceFormatExceptionTest {

    @Test
    public void should_pass_message_to_parent_class() {
        WrongResourceFormatException exception = new WrongResourceFormatException("message");
        assertEquals(exception.getMessage(), "message");
    }
}