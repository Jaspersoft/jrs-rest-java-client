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
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import jakarta.ws.rs.core.MediaType;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
@SuppressWarnings("unchecked")
public class DomainSchemaAdapterTest {

    public static final String URI = "domainSchemaUri";
    public static final List<String> PATH = asList("resources", URI);
    public static final String CONTENT_TYPE = "application/repository.domainSchema+json";

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private JerseyRequest<Schema> requestMock;
    @Mock
    private OperationResult<Schema> resultMock;
    @Mock
    private JerseyRequest<ClientFile> fileJerseyRequestMock;
    @Mock
    private OperationResult<ClientFile> fileOperationResultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, configurationMock, requestMock, resultMock,
                fileJerseyRequestMock, fileOperationResultMock);
    }

    private Object getInternalState(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() throws Exception {
        DomainSchemaAdapter adapter = new DomainSchemaAdapter(sessionStorageMock, URI);
        List<String> path = (List<String>) getInternalState(adapter, "path");

        assertNotNull(adapter);
        assertEquals(path, PATH);
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_get() {
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(Schema.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).get();

            new DomainSchemaAdapter(sessionStorageMock, URI).get();

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(Schema.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class)));
            verify(requestMock).setAccept(CONTENT_TYPE);
            verify(requestMock).get();
            verifyNoMoreInteractions(requestMock);
        }
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_update() {
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        Schema schema = new Schema();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(Schema.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).put(schema);

            new DomainSchemaAdapter(sessionStorageMock, URI).update(schema);

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(Schema.class),
                    eq(new String[]{"resources", URI}), any(DefaultErrorHandler.class)));
            verify(requestMock).setContentType(CONTENT_TYPE);
            verify(requestMock).put(schema);
            verifyNoMoreInteractions(requestMock);
        }
    }

    @Test(enabled = true)
    public void should_involve_protected_methods() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientFile.class),
                    eq(new String[]{"resources", URI}))).thenReturn(fileJerseyRequestMock);
            doReturn(fileOperationResultMock).when(fileJerseyRequestMock).post(any(FormDataMultiPart.class));

            OperationResult<ClientFile> result = new DomainSchemaAdapter(sessionStorageMock, URI)
                    .upload("path", "name", "description");

            assertEquals(fileOperationResultMock, result);
            verify(fileJerseyRequestMock).setContentType(MediaType.MULTIPART_FORM_DATA);
            verify(fileJerseyRequestMock).post(any(FormDataMultiPart.class));
        }
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock_when_upload() {
        File file = new File("path");
        DomainSchemaAdapter spyAdapter = spy(new DomainSchemaAdapter(sessionStorageMock, URI));
        doReturn(fileOperationResultMock).when(spyAdapter)
                .uploadFile(file, ClientFile.FileType.xml, "name", "description");

        OperationResult<ClientFile> result = spyAdapter.upload("path", "name", "description");

        verify(spyAdapter).uploadFile(file, ClientFile.FileType.xml, "name", "description");
        assertEquals(fileOperationResultMock, result);
    }

    @Test
    public void should_involve_static_method() {
        // Verifies the URI is correctly split into path segments for the static buildRequest call
        String multiSegmentUri = "a/b";
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(Schema.class),
                    eq(new String[]{"resources", "a", "b"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).get();

            OperationResult<Schema> retrieved = new DomainSchemaAdapter(sessionStorageMock, multiSegmentUri).get();

            assertNotNull(retrieved);
            assertSame(retrieved, resultMock);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(Schema.class),
                    eq(new String[]{"resources", "a", "b"}), any(DefaultErrorHandler.class)));
            verify(requestMock).setAccept(CONTENT_TYPE);
            verify(requestMock).get();
        }
    }
}
