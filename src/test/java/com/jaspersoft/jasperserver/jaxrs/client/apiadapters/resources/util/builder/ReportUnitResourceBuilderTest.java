package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReference;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link ReportUnitResourceBuilder}
 */
public class ReportUnitResourceBuilderTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private InputStream jrxmlStreamMock;
    @Mock
    private InputStream fileAsStreamMock;

    private ClientReportUnit reportUnitMock;
    private ReportUnitResourceBuilder builder;

    @BeforeMethod
    public void before() {
        initMocks(this);
        reportUnitMock = spy(new ClientReportUnit());
        builder = spy(new ReportUnitResourceBuilder(reportUnitMock, sessionStorageMock));
    }

    @Test
    public void should_fire_parent_constructor_and_initialize_fields_in_parent_class() {
        assertSame(builder.getProcessor().getSessionStorage(), sessionStorageMock);
        assertSame(builder.getProcessor().getResource(), reportUnitMock);

        assertNotNull(builder.getProcessor());
        assertNotNull(builder.getMultipart());
        assertNotNull(builder.getReportUnit());
    }

    @Test
    public void should_set_jrxml_file_as_stream_and_descriptor_to_parent_fields() {

        /** Given **/
        ClientFile fileDescriptor = new ClientFile().setLabel("mainReport").setType(ClientFile.FileType.jrxml);
        doReturn(fileDescriptor).when(reportUnitMock).getJrxml();

        /** When **/
        ReportUnitResourceBuilder retrieved = builder.withJrxml(jrxmlStreamMock, fileDescriptor);

        /** Than **/
        assertSame(retrieved, builder);
        assertSame(retrieved.getMultipart().getField("jrxml").getEntity(), jrxmlStreamMock);
        assertEquals(retrieved.getMultipart().getField("jrxml").getMediaType(), MediaType.APPLICATION_XML_TYPE);
        assertEquals(retrieved.getReportUnit(), reportUnitMock);
        assertEquals(retrieved.getReportUnit().getJrxml(), fileDescriptor);
    }

    @Test
    /** - for {@link ReportUnitResourceBuilder#withJrxml(String, ClientFile)} **/
    public void should_set_jrxml_file_as_string_and_descriptor_to_parent_fields() {

        /** Given **/
        ClientFile fileDescriptor = new ClientFile().setLabel("mainReport").setType(ClientFile.FileType.jrxml);

        /** When **/
        ReportUnitResourceBuilder retrieved = builder.withJrxml("file_", fileDescriptor);

        /** Than **/
        assertSame(retrieved, builder);
        assertSame(retrieved.getMultipart().getField("jrxml").getEntity(), "file_");
        assertEquals(retrieved.getMultipart().getField("jrxml").getMediaType(), MediaType.APPLICATION_XML_TYPE);
        assertEquals(retrieved.getReportUnit(), reportUnitMock);
        assertEquals(retrieved.getReportUnit().getJrxml(), fileDescriptor);

        verify(reportUnitMock).setJrxml(any(ClientReferenceableFile.class));
        verify(reportUnitMock).getJrxml();
        verifyNoMoreInteractions(reportUnitMock);
    }

    @Test
    /** - for {@link ReportUnitResourceBuilder#withNewFile(InputStream, String, ClientFile)} **/
    public void should_add_new_file_as_stream_to_builder_and_init_report_unit_instance() {

        /** Given **/
        ClientFile fileDescriptor = new ClientFile().setLabel("mainReport").setType(ClientFile.FileType.jrxml);

        /** When **/
        ReportUnitResourceBuilder retrieved = builder.withNewFile(fileAsStreamMock, "coolFile", fileDescriptor);

        /** Than **/
        assertSame(retrieved, builder);
        assertNotNull(retrieved.getMultipart().getField("files.coolFile"));
        assertEquals(retrieved.getMultipart().getField("files.coolFile").getEntity(), fileAsStreamMock);
        assertEquals(retrieved.getMultipart().getField("files.coolFile").getMediaType(), MediaType.TEXT_PLAIN_TYPE);
        assertTrue(retrieved.getReportUnit().getFiles().size() == 1);
        assertEquals(retrieved.getReportUnit().getFiles().get("coolFile"), fileDescriptor);
    }

    @Test
    /** - for {@link ReportUnitResourceBuilder#withNewFile(InputStream, String, ClientFile)} **/
    public void should_add_new_file_as_stream_to_builder_two_times() {

        /** Given **/
        ClientFile firstFileDescriptor = new ClientFile().setLabel("mainReport").setType(ClientFile.FileType.jrxml);
        ClientFile secondFileDescriptor = new ClientFile().setLabel("additionalReport").setType(ClientFile.FileType.jrxml);

        /** When **/
        builder.withNewFile(fileAsStreamMock, "firstCoolFile", firstFileDescriptor);
        ReportUnitResourceBuilder retrieved = builder.withNewFile(fileAsStreamMock, "secondCoolFile", secondFileDescriptor);

        /** Than **/
        assertTrue(retrieved.getReportUnit().getFiles().size() == 2);
        assertEquals(retrieved.getReportUnit().getFiles().get("firstCoolFile"), firstFileDescriptor);
        assertEquals(retrieved.getReportUnit().getFiles().get("secondCoolFile"), secondFileDescriptor);
    }

    @Test
    /** - for {@link ReportUnitResourceBuilder#withNewFile(String, String, ClientFile)} **/
    public void should_add_new_file_as_string_to_builder_and_init_report_unit_instance() {

        /** Given **/
        ClientFile fileDescriptor = new ClientFile().setLabel("mainReport").setType(ClientFile.FileType.jrxml);

        /** When **/
        ReportUnitResourceBuilder retrieved = builder.withNewFile("file_", "coolFile", fileDescriptor);

        /** Than **/
        assertSame(retrieved, builder);
        assertNotNull(retrieved.getMultipart().getField("files.coolFile"));
        assertEquals(retrieved.getMultipart().getField("files.coolFile").getEntity(), "file_");
        assertEquals(retrieved.getMultipart().getField("files.coolFile").getMediaType(), MediaType.TEXT_PLAIN_TYPE);
        assertTrue(retrieved.getReportUnit().getFiles().size() == 1);
        assertEquals(retrieved.getReportUnit().getFiles().get("coolFile"), fileDescriptor);
    }

    @Test
    /** - for {@link ReportUnitResourceBuilder#withNewFile(String, String, ClientFile)} **/
    public void should_add_new_file_as_string_to_builder_two_times() {

        /** Given **/
        ClientFile firstFileDescriptor = new ClientFile().setLabel("mainReport").setType(ClientFile.FileType.jrxml);
        ClientFile secondFileDescriptor = new ClientFile().setLabel("additionalReport").setType(ClientFile.FileType.jrxml);

        /** When **/
        builder.withNewFile("firstFile", "firstCoolFile", firstFileDescriptor);
        ReportUnitResourceBuilder retrieved = builder.withNewFile("secondFile", "secondCoolFile", secondFileDescriptor);

        /** Than **/
        assertTrue(retrieved.getReportUnit().getFiles().size() == 2);
        assertEquals(retrieved.getReportUnit().getFiles().get("firstCoolFile"), firstFileDescriptor);
        assertEquals(retrieved.getReportUnit().getFiles().get("secondCoolFile"), secondFileDescriptor);
    }

    @Test
    public void should_set_entity_descriptor_for_the_first_time() {

        /** Given **/
        ClientReference descriptor = new ClientReference().setUri("/my/cool/uri");
        ReportUnitResourceBuilder retrieved = builder.withNewFileReference("coolFile", descriptor);

        /** When **/
        assertTrue(retrieved.getReportUnit().getFiles().size() == 1);
        assertSame(retrieved.getReportUnit().getFiles().get("coolFile"), descriptor);
    }

    @Test
    public void should_set_entity_descriptor_twice_without_creating_new_file_map() {

        /** Given **/
        ClientReference descriptor = new ClientReference().setUri("/my/cool/uri");
        ClientReference newDescriptor = new ClientReference().setUri("/my/another/cool/uri");

        /** When **/
        builder.withNewFileReference("coolFile", descriptor);
        ReportUnitResourceBuilder retrieved = builder.withNewFileReference("nextCoolFile", newDescriptor);

        /** Than **/
        assertNotNull(retrieved.getReportUnit().getFiles());
        assertTrue(retrieved.getReportUnit().getFiles().size() == 2);
        assertSame(retrieved.getReportUnit().getFiles().get("nextCoolFile"), newDescriptor);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, jrxmlStreamMock, fileAsStreamMock);
        reportUnitMock = null;
        builder = null;
    }
}