package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.Attachment;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.HtmlReport;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.dto.reports.HtmlReport}
 */
public class HtmlReportTest {

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.dto.reports.HtmlReport#HtmlReport(String)}
     */
    public void should_create_object_with_proper_params() {

        /* When */
        HtmlReport report = new HtmlReport("_id");

        /* Then */
        assertNotNull(report.getAttachments());
        assertEquals(report.getId(), "_id");

    }

    @Test
    /**
     * for getters and setters of {@link com.jaspersoft.jasperserver.jaxrs.client.dto.reports.HtmlReport} class
     */
    public void should_set_fields_and_get_proper_values() {

        /* Given */
        HtmlReport report = new HtmlReport("id");

        /* When */
        report.setAttachments(new ArrayList<Attachment>() {{
            add(new Attachment());
            add(new Attachment());
            add(new Attachment());
        }});
        report.setAttachmentsPrefix("fakePrefix");
        report.setHtml("fakeHtml");
        report.setId("newId");

        /* Then */
        assertEquals(report.getHtml(), "fakeHtml");
        assertNotEquals(report.getId(), "id");
        assertEquals(report.getId(), "newId");
        assertEquals(report.getAttachmentsPrefix(), "fakePrefix");
        assertTrue(report.getAttachments().size() == 3);
    }

    @Test
    public void should_add_and_remove_only_one_element() {

        /* Given */
        Attachment attachment = new Attachment();
        HtmlReport report = new HtmlReport("id");
        report.setAttachments(new ArrayList<Attachment>());

        // {Check 1}
        assertTrue(report.getAttachments().isEmpty());

        // {Check 2}
        report.addAttachment(attachment);
        assertTrue(report.getAttachments().size() == 1);

        // {Check 3}
        report.removeAttachment(attachment);
        assertTrue(report.getAttachments().isEmpty());
    }
}