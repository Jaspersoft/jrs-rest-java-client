package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.decorator.MondrianConnectionResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link MondrianConnectionResourceBuilder}
 */
@PrepareForTest({MondrianConnectionResourceOperationProcessorDecorator.class,
                 MondrianConnectionResourceBuilder.class})
public class MondrianConnectionResourceBuilderTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private MondrianConnectionResourceOperationProcessorDecorator decoratorMock;

    private ClientMondrianConnection dummyConnection;
    private MondrianConnectionResourceBuilder builder;

    @BeforeMethod
    public void before() {
        initMocks(this);
        dummyConnection = new ClientMondrianConnection();
        builder = new MondrianConnectionResourceBuilder(dummyConnection, sessionStorageMock);
    }

    @Test
    public void should_pass_params_to_parent_object() throws Exception {
        assertNotNull(builder.getMultipart());
        assertSame(dummyConnection, builder.getConnection());
        assertSame(sessionStorageMock, builder.getProcessor().getSessionStorage());
    }

    @Test
    public void should_add_schema_to_multipart_and_add_descriptor_to_entity() {
        ClientFile schemaRef = new ClientFile()
                .setLabel("FoodmartSchemaPostgres2013")
                .setDescription("Some description...")
                .setType(ClientFile.FileType.olapMondrianSchema);

        InputStream dummyData = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        MondrianConnectionResourceBuilder same = builder.withMondrianSchema(dummyData, schemaRef);

        assertSame(builder, same);
        assertEquals(same.getConnection().getSchema(), schemaRef);
        assertEquals(same.getMultipart().getField("schema").getEntity(), dummyData);
        assertEquals(same.getMultipart().getField("schema").getMediaType(), new MediaType("application", "olapMondrianSchema+xml"));
        assertEquals(same.getConnection().getSchema(), schemaRef);
    }

    @Test
    public void should_add_schema_to_multipart_without_entity_descriptor_update() {
        InputStream dummyData = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        MondrianConnectionResourceBuilder same = builder.withMondrianSchema(dummyData);

        assertSame(builder, same);
        assertEquals(same.getMultipart().getField("schema").getEntity(), dummyData);
        assertEquals(same.getMultipart().getField("schema").getMediaType(), new MediaType("application", "olapMondrianSchema+xml"));
    }

    @Test
    public void should_set_uri() {
        MondrianConnectionResourceBuilder same = builder.withUri("/path/to/my/folder");
        assertSame(same, builder);
        assertEquals("/path/to/my/folder", builder.getConnection().getUri());
        assertNotEquals("/wrong/path/to/my/folder", builder.getConnection().getUri());
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock);
        dummyConnection = null;
        builder = null;
    }
}