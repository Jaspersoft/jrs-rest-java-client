package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.schema;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.domain.Schema;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import java.io.File;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@PrepareForTest({JerseyRequest.class})
public class DomainSchemaAdapterTest extends PowerMockTestCase {

    public static final String URI = "domainSchemaUri";
    public static final List<String> path = asList("resources", URI);
    public static final String CONTENT_TYPE = "application/repository.domainSchema+json";
    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<Schema> requestMock;

    @Mock
    private OperationResult<Schema> resultMock;

    @Mock
    private JerseyRequest<ClientFile> fileJerseyRequestMock;

    @Mock
    private OperationResult<ClientFile> fileOperationResultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() {

        // When
        DomainSchemaAdapter adapter = new DomainSchemaAdapter(sessionStorageMock, URI);
        List<String>  uri = (List<String> ) getInternalState(adapter, "path");

        // Then
        assertNotNull(adapter);
        assertEquals(uri, path);
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_get() throws Exception {

        // Given
        DomainSchemaAdapter adapter = spy(new DomainSchemaAdapter(sessionStorageMock, URI));
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(requestMock).when(requestMock).setAccept(CONTENT_TYPE);
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).get();

        // When
        adapter.get();

        // Then
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).setAccept(CONTENT_TYPE);
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_update() throws Exception {

        // Given
        DomainSchemaAdapter adapter = spy(new DomainSchemaAdapter(sessionStorageMock, URI));
        Schema schema = new Schema();

        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(requestMock).when(requestMock).setContentType(CONTENT_TYPE);
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).put(schema);

        // When
        adapter.update(schema);

        // Then
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
        verify(requestMock, times(1)).put(schema);
        verify(requestMock, times(1)).setContentType(CONTENT_TYPE);
        verifyNoMoreInteractions(requestMock);
    }

    @Test(enabled = true)
    public void should_involve_protected_methods() throws Exception {

        // Given
        DomainSchemaAdapter adapter = spy(new DomainSchemaAdapter(sessionStorageMock, URI));
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientFile.class),
                eq(new String[]{"resources", URI})))
                .thenReturn(fileJerseyRequestMock);
        doReturn(fileJerseyRequestMock).when(fileJerseyRequestMock).setContentType(MediaType.MULTIPART_FORM_DATA);
        doReturn(fileOperationResultMock).when(fileJerseyRequestMock).post(any(FormDataMultiPart.class));

        // When
        OperationResult<ClientFile> result = adapter.upload("path", "name", "description");

        // Then
        assertEquals(fileOperationResultMock, result);
        verify(fileJerseyRequestMock, times(1)).setContentType(MediaType.MULTIPART_FORM_DATA);
        verify(fileJerseyRequestMock, times(1)).post(any(FormDataMultiPart.class));

    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_upload() throws Exception {

        // Given
        DomainSchemaAdapter adapter = spy(new DomainSchemaAdapter(sessionStorageMock, URI));
        File file = new File("path");
        doReturn(resultMock)
                .when(adapter).uploadFile(file, ClientFile.FileType.xml, "name", "description");

        // When
        OperationResult<ClientFile> result = adapter.upload("path", "name", "description");

        // Then
        verify(adapter, times(1)).uploadFile(file, ClientFile.FileType.xml, "name", "description");
        assertEquals(resultMock, result);
    }


    @Test
    public void should_involve_static_method() {

        // Given
        DomainSchemaAdapter schemaAdapter = new DomainSchemaAdapter(sessionStorageMock, URI);
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(Schema.class),
                eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class)))
                .thenReturn(requestMock);
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(requestMock).when(requestMock).setAccept(CONTENT_TYPE);
        doReturn(resultMock).when(requestMock).get();

        // When
        OperationResult<Schema> retrieved = schemaAdapter.get();

        // Then
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock),
                eq(Schema.class),
                eq(new String[]{"resources", URI}),
                any(DefaultErrorHandler.class));
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).setAccept(CONTENT_TYPE);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, resultMock, fileJerseyRequestMock, fileOperationResultMock);
    }
}