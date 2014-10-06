package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableFile;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link SecureMondrianConnectionResourceBuilder}
 */
public class SecureMondrianConnectionResourceBuilderTest {

    @Mock
    private SessionStorage sessionStorageMock;
    private ClientSecureMondrianConnection dummyMondrianConnection;
    private SecureMondrianConnectionResourceBuilder created;

    @BeforeMethod
    public void before() {
        initMocks(this);
        dummyMondrianConnection = new ClientSecureMondrianConnection();
        created = new SecureMondrianConnectionResourceBuilder(dummyMondrianConnection, sessionStorageMock);
    }

    @Test
    /** - for {@link SecureMondrianConnectionResourceBuilder#SecureMondrianConnectionResourceBuilder(
     * ClientSecureMondrianConnection, SessionStorage)} **/
    public void should_pass_params_to_parent_class() {
        SessionStorage retrievedStorage = created.getProcessor().getSessionStorage();
        ClientSecureMondrianConnection retrievedConnection = created.getConnection();
        assertSame(retrievedStorage, sessionStorageMock);
        assertSame(retrievedConnection, dummyMondrianConnection);
    }

    @Test
    /** - for {@link SecureMondrianConnectionResourceBuilder#withMondrianSchema(String, ClientFile)} **/
    public void should_set_mondrian_schema_as_stream_and_descriptor() {

        /** Given **/
        ClientFile schemaDescriptor = new ClientFile()
                .setLabel("FoodmartSchemaPostgres2013")
                .setDescription("Some description...")
                .setType(ClientFile.FileType.olapMondrianSchema);

        InputStream fakeSchema = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        /** When **/
        SecureMondrianConnectionResourceBuilder same = created.withMondrianSchema(fakeSchema, schemaDescriptor);


        /** Than **/
        assertSame(same, created);
        assertEquals(same.getConnection().getSchema(), schemaDescriptor);
        assertEquals(same.getMultipart().getField("schema").getEntity(), fakeSchema);
        assertEquals(
                same.getMultipart().getField("schema").getMediaType(),
                new MediaType("application", "olapMondrianSchema+xml"));
        assertEquals(same.getConnection().getSchema(), schemaDescriptor);
    }

    @Test
    /** - for {@link SecureMondrianConnectionResourceBuilder#withMondrianSchema(InputStream)} **/
    public void should_set_mondrian_schema_as_stream() {
        InputStream fakeSchema = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        /** When **/
        SecureMondrianConnectionResourceBuilder same = created.withMondrianSchema(fakeSchema);


        /** Than **/
        assertSame(same, created);
        assertEquals(same.getMultipart().getField("schema").getEntity(), fakeSchema);
        assertEquals(
                same.getMultipart().getField("schema").getMediaType(),
                new MediaType("application", "olapMondrianSchema+xml"));
    }

    @Test
    /** - for {@link SecureMondrianConnectionResourceBuilder#withMondrianSchema(String)} **/
    public void should_set_mondrian_schema_as_string() {
        String fakeSchema = "fakeSchema";

        /** When **/
        SecureMondrianConnectionResourceBuilder same = created.withMondrianSchema(fakeSchema);

        /** Than **/
        assertSame(same, created);
        assertEquals(same.getMultipart().getField("schema").getEntity(), fakeSchema);
        assertEquals(
                same.getMultipart().getField("schema").getMediaType(),
                new MediaType("application", "olapMondrianSchema+xml"));
    }

    @Test
    /** - for {@link SecureMondrianConnectionResourceBuilder#withMondrianSchema(InputStream, ClientFile)} **/
    public void should_set_mondrian_schema_as_string_and_descriptor() {

        /** Given **/
        ClientFile schemaDescriptor = new ClientFile()
                .setLabel("FoodmartSchemaPostgres2013")
                .setDescription("Some description...")
                .setType(ClientFile.FileType.olapMondrianSchema);
        String fakeSchema = "fakeSchema";

        /** When **/
        SecureMondrianConnectionResourceBuilder same = created.withMondrianSchema(fakeSchema, schemaDescriptor);


        /** Than **/
        assertSame(same, created);
        assertEquals(same.getConnection().getSchema(), schemaDescriptor);
        assertEquals(same.getMultipart().getField("schema").getEntity(), fakeSchema);
        assertEquals(
                same.getMultipart().getField("schema").getMediaType(),
                new MediaType("application", "olapMondrianSchema+xml"));
        assertEquals(same.getConnection().getSchema(), schemaDescriptor);
    }

    @Test
    /** - for {@link SecureMondrianConnectionResourceBuilder#withAccessGrantSchemasAsStream(List, List)} **/
    public void should_set_access_grant_schemas_as_stream_and_list_of_descriptors_an_return_this() {

        /** Given **/
        ClientReferenceableFile firstAccessGrantSchema = new ClientFile()
                .setLabel("1_schema")
                .setDescription("1_schema")
                .setType(ClientFile.FileType.accessGrantSchema);
        ClientReferenceableFile secondAccessGrantSchema = new ClientFile()
                .setLabel("2_schema")
                .setDescription("2_schema")
                .setType(ClientFile.FileType.accessGrantSchema);


        InputStream firstXmlAccessGrantSchema = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        InputStream secondXmlAccessGrantSchema = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        List<InputStream> schemas = asList(firstXmlAccessGrantSchema, secondXmlAccessGrantSchema);
        List<ClientReferenceableFile> schemaDescriptors = asList(firstAccessGrantSchema, secondAccessGrantSchema);


        /** When **/
        SecureMondrianConnectionResourceBuilder same =
                created.withAccessGrantSchemasAsStream(schemas, schemaDescriptors);


        /** Than **/
        assertSame(same, created);
        assertEquals(same.getConnection().getAccessGrants(), schemaDescriptors);

        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[0]").getEntity(),
                firstXmlAccessGrantSchema);
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[1]").getEntity(),
                secondXmlAccessGrantSchema);
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[0]").getMediaType(),
                new MediaType("application", "accessGrantSchema+xml"));
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[1]").getMediaType(),
                new MediaType("application", "accessGrantSchema+xml"));

        assertNull(same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[2]"));
    }

    @Test
    /** - for {@link SecureMondrianConnectionResourceBuilder#withAccessGrantSchemasAsString(List, List)} **/
    public void should_set_access_grant_schemas_as_string_and_list_of_descriptors_an_return_this() {

        /** Given **/
        ClientReferenceableFile firstAccessGrantSchema = new ClientFile()
                .setLabel("1_schema")
                .setDescription("1_schema")
                .setType(ClientFile.FileType.accessGrantSchema);
        ClientReferenceableFile secondAccessGrantSchema = new ClientFile()
                .setLabel("2_schema")
                .setDescription("2_schema")
                .setType(ClientFile.FileType.accessGrantSchema);


        String firstXmlAccessGrantSchemaAsString = "_1";
        String secondXmlAccessGrantSchemaAsString = "_2";
        List<String> schemas = asList(firstXmlAccessGrantSchemaAsString, secondXmlAccessGrantSchemaAsString);
        List<ClientReferenceableFile> schemaDescriptors = asList(firstAccessGrantSchema, secondAccessGrantSchema);


        /** When **/
        SecureMondrianConnectionResourceBuilder same =
                created.withAccessGrantSchemasAsString(schemas, schemaDescriptors);


        /** Than **/
        assertSame(same, created);
        assertEquals(same.getConnection().getAccessGrants(), schemaDescriptors);

        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[0]").getEntity(),
                firstXmlAccessGrantSchemaAsString);
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[1]").getEntity(),
                secondXmlAccessGrantSchemaAsString);
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[0]").getMediaType(),
                new MediaType("application", "accessGrantSchema+xml"));
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[1]").getMediaType(),
                new MediaType("application", "accessGrantSchema+xml"));

        assertNull(same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[2]"));
    }

    @Test
    /** - for {@link SecureMondrianConnectionResourceBuilder#withAccessGrantSchemasAsString(List)} **/
    public void should_set_access_grant_schemas_as_string_an_return_this() {

        /** Given **/
        String firstXmlAccessGrantSchemaAsString = "_1";
        String secondXmlAccessGrantSchemaAsString = "_2";
        List<String> schemas = asList(firstXmlAccessGrantSchemaAsString, secondXmlAccessGrantSchemaAsString);


        /** When **/
        SecureMondrianConnectionResourceBuilder same =
                created.withAccessGrantSchemasAsString(schemas);


        /** Than **/
        assertSame(same, created);
        assertTrue(same.getMultipart().getFields().size() == 2);

        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[0]").getEntity(),
                firstXmlAccessGrantSchemaAsString);
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[1]").getEntity(),
                secondXmlAccessGrantSchemaAsString);
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[0]").getMediaType(),
                new MediaType("application", "accessGrantSchema+xml"));
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[1]").getMediaType(),
                new MediaType("application", "accessGrantSchema+xml"));

        assertNull(same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[2]"));
    }

    @Test
    /** - for {@link SecureMondrianConnectionResourceBuilder#withAccessGrantSchemas(List)} **/
    public void should_set_access_grant_schemas_as_stream_an_return_this() {

        /** Given **/
        InputStream firstXmlAccessGrantSchema = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        InputStream secondXmlAccessGrantSchema = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        List<InputStream> schemas = asList(firstXmlAccessGrantSchema, secondXmlAccessGrantSchema);


        /** When **/
        SecureMondrianConnectionResourceBuilder same =
                created.withAccessGrantSchemas(schemas);


        /** Than **/
        assertSame(same, created);
        assertTrue(same.getMultipart().getFields().size() == 2);

        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[0]").getEntity(),
                firstXmlAccessGrantSchema);
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[1]").getEntity(),
                secondXmlAccessGrantSchema);
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[0]").getMediaType(),
                new MediaType("application", "accessGrantSchema+xml"));
        assertEquals(
                same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[1]").getMediaType(),
                new MediaType("application", "accessGrantSchema+xml"));

        assertNull(same.getMultipart().getField("accessGrantSchemas.accessGrantSchema[2]"));
    }

    @Test
    /** - for {@link SecureMondrianConnectionResourceBuilder#withUri(String)} **/
    public void should_set_uri() {
        SecureMondrianConnectionResourceBuilder same = created.withUri("/path/to/my/folder");
        assertSame(same, created);
        assertEquals("/path/to/my/folder", created.getConnection().getUri());
        assertNotEquals("/wrong/path/to/my/folder", created.getConnection().getUri());
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock);
        dummyMondrianConnection = null;
    }
}