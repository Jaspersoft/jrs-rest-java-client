package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import org.testng.annotations.Test;

import static org.testng.Assert.assertSame;

public class AttachmentTest {

    @Test
    /**
     * for whole bunch of setters/getters
     */
    public void should_set_fields_and_get_proper_values() {

        /* Given */

        byte[] dummyContent = new byte[]{1, 2, 3};
        Attachment attachment = new Attachment();

        /* When */
        attachment.setContent(dummyContent);
        attachment.setMimeType("someType");
        attachment.setName("name");

        /* Than */
        assertSame(attachment.getContent(), dummyContent);
        assertSame(attachment.getMimeType(), "someType");
        assertSame(attachment.getName(), "name");
    }
}