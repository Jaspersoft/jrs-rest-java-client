//package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder;
//
//import com.jaspersoft.jasperserver.dto.resources.ClientBundle;
//import com.jaspersoft.jasperserver.dto.resources.ClientFile;
//import com.jaspersoft.jasperserver.dto.resources.ClientReference;
//import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableFile;
//import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
//import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
//import com.sun.jersey.multipart.FormDataBodyPart;
//import org.mockito.Mock;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
//import static org.mockito.Mockito.reset;
//import static org.mockito.MockitoAnnotations.initMocks;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertSame;
//import static org.testng.Assert.assertTrue;
//
//public class DomainResourceBuilderTest {
//
//    @Mock
//    private SessionStorage storageMock;
//
//    private ClientSemanticLayerDataSource dummyDomain;
//    private ClientFile schemaDescriptor;
//    private ClientFile securityFileDescriptor;
//    private ClientBundle enUsBundleDescriptor;
//    private ClientBundle defaultBundle;
//    private ClientReference dataSourceDescriptor;
//
//
//    @BeforeMethod
//    public void before() {
//        initMocks(this);
//        dummyDomain = new ClientSemanticLayerDataSource();
//
//        schemaDescriptor = new ClientFile()
//                .setLabel("supermartDomain_schema")
//                .setDescription("supermartDomain_schema")
//                .setType(ClientFile.FileType.xml);
//
//        securityFileDescriptor = new ClientFile()
//                .setLabel("supermartDomain_domain_security")
//                .setDescription("supermartDomain_domain_security")
//                .setType(ClientFile.FileType.xml);
//
//        enUsBundleDescriptor = new ClientBundle()
//                .setLocale("en_US")
//                .setFile(new ClientFile()
//                        .setLabel("supermart_domain_en_US.properties")
//                        .setType(ClientFile.FileType.prop));
//
//        defaultBundle = new ClientBundle()
//                .setLocale("")
//                .setFile(new ClientFile()
//                        .setLabel("supermart_domain.properties")
//                        .setType(ClientFile.FileType.prop));
//
//        dataSourceDescriptor = new ClientReference()
//                .setUri("/public/Samples/Data_Sources/FoodmartDataSourceJNDI");
//    }
//
//    @Test
//    public void should_pass_params_to_parent_class() {
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        SessionStorage retrievedStorage = created.getProcessor().getSessionStorage();
//        ClientSemanticLayerDataSource retrievedDomain = created.getDomain();
//
//        assertSame(retrievedStorage, storageMock);
//        assertSame(retrievedDomain, dummyDomain);
//    }
//
//    @Test
//    public void should_pass_string_schema_to_parent_class_and_return_this() {
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        DomainResourceBuilder afterSetup = created.withSchema("file", schemaDescriptor);
//
//        FormDataBodyPart form = afterSetup.getMultipart().getField("schema");
//        String entity = (String) form.getEntity();
//        ClientReferenceableFile descriptor = afterSetup.getDomain().getSchema();
//
//        assertSame(created, afterSetup);
//        assertEquals(form.getMediaType(), APPLICATION_XML_TYPE);
//        assertEquals(entity, "file");
//        assertSame(descriptor, schemaDescriptor);
//    }
//
//    @Test
//    public void should_pass_input_stream_schema_to_parent_class_and_return_this() throws FileNotFoundException {
//        InputStream dummyStream = new ByteArrayInputStream(new byte[]{});
//
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        DomainResourceBuilder afterSetup = created.withSchema(dummyStream, schemaDescriptor);
//
//        FormDataBodyPart form = afterSetup.getMultipart().getField("schema");
//        InputStream entity = (InputStream) form.getEntity();
//        ClientReferenceableFile descriptor = afterSetup.getDomain().getSchema();
//
//        assertSame(created, afterSetup);
//        assertEquals(form.getMediaType(), APPLICATION_XML_TYPE);
//        assertSame(entity, dummyStream);
//        assertSame(descriptor, schemaDescriptor);
//    }
//
//    @Test
//    public void should_pass_input_stream_security_file_to_parent_class_and_return_this() throws FileNotFoundException {
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        DomainResourceBuilder afterSetup = created.withSecurityFile("file", securityFileDescriptor);
//
//        FormDataBodyPart form = afterSetup.getMultipart().getField("securityFile");
//        String entity = (String) form.getEntity();
//        ClientReferenceableFile descriptor = afterSetup.getDomain().getSecurityFile();
//
//        assertSame(created, afterSetup);
//        assertEquals(form.getMediaType(), APPLICATION_XML_TYPE);
//        assertSame(entity, "file");
//        assertSame(descriptor, securityFileDescriptor);
//    }
//
//    @Test
//    public void should_pass_string_security_file_to_parent_class_and_return_this() throws FileNotFoundException {
//        InputStream dummyStream = new ByteArrayInputStream(new byte[]{});
//
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        DomainResourceBuilder afterSetup = created.withSecurityFile(dummyStream, securityFileDescriptor);
//
//        FormDataBodyPart form = afterSetup.getMultipart().getField("securityFile");
//        InputStream entity = (InputStream) form.getEntity();
//        ClientReferenceableFile descriptor = afterSetup.getDomain().getSecurityFile();
//
//        assertSame(created, afterSetup);
//        assertEquals(form.getMediaType(), APPLICATION_XML_TYPE);
//        assertSame(entity, dummyStream);
//        assertSame(descriptor, securityFileDescriptor);
//    }
//
//    @Test
//    public void should_add_bundle_file_as_string_to_multipart_form() {
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        DomainResourceBuilder afterSetup = created.withBundle("bundle_file", enUsBundleDescriptor);
//
//        FormDataBodyPart form = afterSetup.getMultipart().getField("bundles.bundle[0]");
//        String bundle = (String) form.getEntity();
//        List<ClientBundle> bundleDescriptors = afterSetup.getDomain().getBundles();
//
//        assertSame(created, afterSetup);
//        assertTrue(bundleDescriptors.size() == 1);
//        assertTrue(bundleDescriptors.contains(enUsBundleDescriptor));
//        assertEquals(bundle, "bundle_file");
//    }
//
//    @Test
//    public void should_add_bundle_file_as_stream_to_multipart_form() {
//        InputStream dummyStream = new ByteArrayInputStream(new byte[]{});
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        DomainResourceBuilder afterSetup = created.withBundle(dummyStream, enUsBundleDescriptor);
//
//        FormDataBodyPart form = afterSetup.getMultipart().getField("bundles.bundle[0]");
//        InputStream bundle = (InputStream) form.getEntity();
//        List<ClientBundle> bundleDescriptors = afterSetup.getDomain().getBundles();
//
//        assertSame(created, afterSetup);
//        assertTrue(bundleDescriptors.size() == 1);
//        assertTrue(bundleDescriptors.contains(enUsBundleDescriptor));
//        assertSame(bundle, dummyStream);
//    }
//
//    @Test
//    public void should_add_second_bundle_to_the_list() {
//        InputStream dummyStream = new ByteArrayInputStream(new byte[]{});
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        created = created.withBundle(dummyStream, enUsBundleDescriptor);
//        created = created.withBundle(dummyStream, enUsBundleDescriptor);
//        assertTrue(created.getMultipart().getFields().size() == 2);
//    }
//
//    @Test
//    public void should_add_second_string_bundle_to_the_list() {
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        created = created.withBundle("a", enUsBundleDescriptor);
//        created = created.withBundle("a", enUsBundleDescriptor);
//        assertTrue(created.getMultipart().getFields().size() == 2);
//    }
//
//    @Test
//    public void should_add_second_string_bundle_to_the_list_when_second_bundle_different_Then_first() {
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        ClientBundle defaultBundle = new ClientBundle()
//                .setLocale("")
//                .setFile(new ClientFile()
//                        .setLabel("supermart_domain.properties")
//                        .setType(ClientFile.FileType.prop));
//
//        created = created.withBundle("a", enUsBundleDescriptor);
//        created = created.withBundle("c", defaultBundle);
//        assertTrue(created.getMultipart().getFields().size() == 2);
//    }
//
//    @Test
//    public void should_add_second_stream_bundle_to_the_list_when_second_bundle_different_Then_first() {
//        InputStream firstBundle = new ByteArrayInputStream(new byte[]{});
//        InputStream secondBundle = new ByteArrayInputStream(new byte[]{});
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        created = created.withBundle(firstBundle, enUsBundleDescriptor);
//        created = created.withBundle(secondBundle, defaultBundle);
//        assertTrue(created.getMultipart().getFields().size() == 2);
//    }
//
//    @Test
//    public void should_add_bundles_as_a_batch() {
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        InputStream firstBundle = new ByteArrayInputStream(new byte[]{});
//        InputStream secondBundle = new ByteArrayInputStream(new byte[]{});
//
//        ArrayList<ClientBundle> list = new ArrayList<ClientBundle>();
//
//        list.add(defaultBundle);
//        list.add(enUsBundleDescriptor);
//
//        DomainResourceBuilder retrieved = created.withBundles(Arrays.asList(firstBundle, secondBundle), list);
//
//        assertTrue(retrieved.getDomain().getBundles().size() == 2);
//        assertTrue(retrieved.getMultipart().getFields().size() == 2);
//    }
//
//    @Test
//    public void should_set_data_source_reference_file() {
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        DomainResourceBuilder retrieved = created.withDataSource(dataSourceDescriptor);
//        assertSame(retrieved.getDomain().getDataSource(), dataSourceDescriptor);
//        assertSame(retrieved, created);
//    }
//
//    @Test
//    public void should_set_uri() {
//        DomainResourceBuilder created = new DomainResourceBuilder(dummyDomain, storageMock);
//        DomainResourceBuilder retrieved = created.withUri("uri");
//        assertSame(retrieved.getDomain().getUri(), "uri");
//        assertSame(retrieved, created);
//    }
//
//    @AfterMethod
//    public void after() {
//        reset(storageMock);
//
//        dummyDomain = null;
//        schemaDescriptor = null;
//        securityFileDescriptor = null;
//        enUsBundleDescriptor = null;
//    }
//}