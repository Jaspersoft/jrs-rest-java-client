package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ResourceFilesMimeTypeTest {

    @Test
    public void should_check_enum() {

        assertEquals(ResourceFilesMimeType.CSS.toString(), "CSS");
        assertEquals(ResourceFilesMimeType.HTML.getType(), "text/html");

    }
}