package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.resources.ClientAdhocDataView;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.dto.resources.ClientVirtualDataSource;
import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * Unit Tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter}
 */
public class SingleResourceAdapterTest {

    @Captor
    private ArgumentCaptor<FormDataMultiPart> captor;
    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private PatchDescriptor descriptorMock;
    @Mock
    private JerseyRequest<ClientResource> jerseyRequestMock;
    @Mock
    private OperationResult<ClientResource> operationResultMock;
    @Mock
    private JerseyRequest<Object> objectJerseyRequestMock;
    @Mock
    private OperationResult<Object> objectOperationResultMock;
    @Mock
    private JerseyRequest<ClientFile> clientFileJerseyRequestMock;
    @Mock
    private OperationResult<ClientFile> clientFileOperationResultMock;
    @Mock
    private JerseyRequest<ClientAdhocDataView> adhocDataViewJerseyRequestMock;
    @Mock
    private OperationResult<ClientAdhocDataView> adhocDataViewOperationResultMock;
    @Mock
    private JerseyRequest<InputStream> inputStreamJerseyRequestMock;
    @Mock
    private OperationResult<InputStream> inputStreamOperationResultMock;
    @Mock
    private JerseyRequest<ClientVirtualDataSource> virtualDataSourceJerseyRequestMock;
    @Mock
    private OperationResult<ClientVirtualDataSource> virtualDataSourceOperationResultMock;
    @Mock
    private File fileMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, configurationMock, jerseyRequestMock, operationResultMock, objectJerseyRequestMock,
                objectOperationResultMock, clientFileJerseyRequestMock,
                clientFileOperationResultMock, fileMock, adhocDataViewJerseyRequestMock,
                adhocDataViewOperationResultMock, configurationMock, descriptorMock,
                inputStreamJerseyRequestMock, inputStreamOperationResultMock,
                virtualDataSourceJerseyRequestMock, virtualDataSourceOperationResultMock);
        mockitoSession.close();
    }

    @Test
    public void should_return_specified_resource_asynchronously_as_json() throws InterruptedException {

        String resourceUri = "requestId";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleResourceAdapter adapterSpy = spy(new SingleResourceAdapter(sessionStorageMock, resourceUri));
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientResource.class),
                    eq(new String[]{"resources", resourceUri}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).get();

            final Callback<OperationResult<ClientResource>, Void> callback = spy(new Callback<OperationResult<ClientResource>, Void>() {
                @Override
                public Void execute(OperationResult<ClientResource> data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });
            doNothing().when(callback).execute(operationResultMock);

            RequestExecution retrieved = adapterSpy.asyncDetails(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            verify(jerseyRequestMock, times(1)).get();
            verify(callback).execute(operationResultMock);
        }
    }

    @Test
    public void should_return_specified_resource_asynchronously_as_xml() throws InterruptedException {

        String resourceUri = "requestId";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleResourceAdapter adapterSpy = spy(new SingleResourceAdapter(sessionStorageMock, resourceUri));
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.XML);

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientResource.class),
                    eq(new String[]{"resources", resourceUri}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).get();

            final Callback<OperationResult<ClientResource>, Void> callback = spy(new Callback<OperationResult<ClientResource>, Void>() {
                @Override
                public Void execute(OperationResult<ClientResource> data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });
            doNothing().when(callback).execute(operationResultMock);

            RequestExecution retrieved = adapterSpy.asyncDetails(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            verify(jerseyRequestMock, times(1)).get();
            verify(callback).execute(operationResultMock);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_set_parameter() {

        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, "resourceUri");

        SingleResourceAdapter retrieved = adapter.parameter(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceServiceParameter.CREATE_FOLDERS, "true");

        assertSame(adapter, retrieved);
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) getInternalState(retrieved, "params");
        String param = retrievedParams.get(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceServiceParameter.CREATE_FOLDERS.getName()).get(0);
        assertEquals(param, "true");
    }

    @Test
    public void should_delete_resource_asynchronously() throws InterruptedException {

        String resourceUri = "requestId";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(Object.class),
                    eq(new String[]{"resources", resourceUri}), any(DefaultErrorHandler.class))).thenReturn(objectJerseyRequestMock);
            doReturn(objectOperationResultMock).when(objectJerseyRequestMock).delete();

            final Callback<OperationResult, Void> callback = spy(new Callback<OperationResult, Void>() {
                @Override
                public Void execute(OperationResult data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });
            doNothing().when(callback).execute(objectOperationResultMock);

            RequestExecution retrieved = adapter.asyncDelete(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            verify(objectJerseyRequestMock, times(1)).delete();
            verify(callback).execute(objectOperationResultMock);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_return_operation_result_with_client_resource_folder() {

        String resourceUri = "/";
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientResource.class),
                    eq(new String[]{"resources"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).get();

            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            OperationResult<ClientResource> retrieved = adapter.details();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);

            Mockito.verify(jerseyRequestMock, times(1)).addParams(any(MultivaluedHashMap.class));
            Mockito.verify(jerseyRequestMock).setAccept(ResourceMediaType.FOLDER_JSON);
            Mockito.verify(jerseyRequestMock).get();
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_return_operation_result_with_client_resource_folder_as_xml() {

        String resourceUri = "/";
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.XML);

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientResource.class),
                    eq(new String[]{"resources"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).get();

            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            OperationResult<ClientResource> retrieved = adapter.details();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);

            Mockito.verify(jerseyRequestMock, times(1)).addParams(any(MultivaluedHashMap.class));
            Mockito.verify(jerseyRequestMock).setAccept(ResourceMediaType.FOLDER_XML);
            Mockito.verify(jerseyRequestMock).get();
        }
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void should_throw_UnsupportedOperationException() {
        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, "uri");
        adapter.patchResource(null);
    }

    @Test
    public void should_send_descriptor_into_server_asynchronously() throws InterruptedException {

        String resourceUri = "/";

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientAdhocDataView.class),
                    eq(new String[]{"resources"}),
                    any(DefaultErrorHandler.class))).thenReturn(adhocDataViewJerseyRequestMock);
            doReturn(adhocDataViewOperationResultMock).when(adhocDataViewJerseyRequestMock).post(descriptorMock);
            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
            doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();

            final AtomicInteger newThreadId = new AtomicInteger();
            final int currentThreadId = (int) Thread.currentThread().getId();

            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            final Callback<OperationResult<ClientAdhocDataView>, Void> callback =
                    spy(new Callback<OperationResult<ClientAdhocDataView>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientAdhocDataView> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            doNothing().when(callback).execute(adhocDataViewOperationResultMock);

            RequestExecution retrieved = adapter.asyncPatchResource(ClientAdhocDataView.class, descriptorMock, callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());

            Mockito.verify(adhocDataViewJerseyRequestMock).post(descriptorMock);
            Mockito.verify(adhocDataViewJerseyRequestMock).setAccept("application/repository.adhocDataView+json");
            Mockito.verify(adhocDataViewJerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
            Mockito.verify(callback).execute(adhocDataViewOperationResultMock);
        }
    }

    @Test
    public void should_send_descriptor_into_server() {

        String resourceUri = "/";

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientAdhocDataView.class),
                    eq(new String[]{"resources"}),
                    any(DefaultErrorHandler.class))).thenReturn(adhocDataViewJerseyRequestMock);
            doReturn(adhocDataViewOperationResultMock).when(adhocDataViewJerseyRequestMock).post(descriptorMock);
            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
            doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();

            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            OperationResult<ClientAdhocDataView> retrieved = adapter.patchResource(ClientAdhocDataView.class, descriptorMock);

            assertNotNull(retrieved);
            assertSame(retrieved, adhocDataViewOperationResultMock);

            Mockito.verify(adhocDataViewJerseyRequestMock).post(descriptorMock);
            Mockito.verify(adhocDataViewJerseyRequestMock).setAccept("application/repository.adhocDataView+json");
            Mockito.verify(adhocDataViewJerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
            Mockito.verify(adhocDataViewJerseyRequestMock).addParams(any(MultivaluedHashMap.class));
        }
    }

    @Test
    public void should_delete_resource_on_server() {

        String resourceUri = "uri";

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(Object.class),
                    eq(new String[]{"resources", resourceUri}), any(DefaultErrorHandler.class))).thenReturn(objectJerseyRequestMock);
            doReturn(objectOperationResultMock).when(objectJerseyRequestMock).delete();

            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            OperationResult retrieved = adapter.delete();

            assertSame(retrieved, objectOperationResultMock);
            Mockito.verify(objectJerseyRequestMock).delete();
            Mockito.verifyNoMoreInteractions(objectJerseyRequestMock);
        }
    }

    @Test
    public void should_return_resource_as_IS_in_binary_format() {

        String resourceUri = "uri";

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(InputStream.class),
                    eq(new String[]{"resources", resourceUri}), any(DefaultErrorHandler.class))).thenReturn(inputStreamJerseyRequestMock);
            doReturn(inputStreamOperationResultMock).when(inputStreamJerseyRequestMock).get();
            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            OperationResult<InputStream> retrieved = adapter.downloadBinary();

            assertNotNull(retrieved);
            assertSame(retrieved, inputStreamOperationResultMock);
            Mockito.verify(inputStreamJerseyRequestMock).get();
            Mockito.verifyNoMoreInteractions(inputStreamJerseyRequestMock);
        }
    }

    @Test
    public void should_return_resource_as_IS_in_binary_format_asynchronously() throws InterruptedException {

        String resourceUri = "uri";

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(InputStream.class),
                    eq(new String[]{"resources", resourceUri}), any(DefaultErrorHandler.class))).thenReturn(inputStreamJerseyRequestMock);
            doReturn(inputStreamOperationResultMock).when(inputStreamJerseyRequestMock).get();
            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            final AtomicInteger newThreadId = new AtomicInteger();
            final int currentThreadId = (int) Thread.currentThread().getId();

            final Callback<OperationResult<InputStream>, Void> callback =
                    spy(new Callback<OperationResult<InputStream>, Void>() {
                        @Override
                        public Void execute(OperationResult<InputStream> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            doNothing().when(callback).execute(inputStreamOperationResultMock);

            RequestExecution retrieved = adapter.asyncDownloadBinary(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());

            Mockito.verify(inputStreamJerseyRequestMock).get();
            Mockito.verify(callback).execute(inputStreamOperationResultMock);
            Mockito.verifyNoMoreInteractions(inputStreamJerseyRequestMock);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_update_resource_asynchronously() throws InterruptedException {

        String resourceUri = "uri";
        ClientVirtualDataSource source = new ClientVirtualDataSource();

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientVirtualDataSource.class),
                    eq(new String[]{"resources", resourceUri}),
                    any(DefaultErrorHandler.class))).thenReturn(virtualDataSourceJerseyRequestMock);

            doReturn(operationResultMock).when(virtualDataSourceJerseyRequestMock).put(source);
            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
            doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
            doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            final AtomicInteger newThreadId = new AtomicInteger();
            final int currentThreadId = (int) Thread.currentThread().getId();

            final Callback<OperationResult<ClientResource>, Void> callback =
                    spy(new Callback<OperationResult<ClientResource>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientResource> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            doNothing().when(callback).execute(operationResultMock);

            RequestExecution retrieved = adapter.asyncCreateOrUpdate(source, callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());

            Mockito.verify(virtualDataSourceJerseyRequestMock).put(source);
            Mockito.verify(callback).execute(operationResultMock);
            Mockito.verify(virtualDataSourceJerseyRequestMock).addParams(any(MultivaluedHashMap.class));
            Mockito.verify(virtualDataSourceJerseyRequestMock).setContentType("application/repository.virtualDataSource+json");
            Mockito.verify(virtualDataSourceJerseyRequestMock).setAccept("application/repository.virtualDataSource+json");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_copy_resource_asynchronously() throws InterruptedException {

        String resourceUri = "uri";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientResource.class),
                    eq(new String[]{"resources", resourceUri}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).put("");

            final Callback<OperationResult<ClientResource>, Void> callback = Mockito.spy(new Callback<OperationResult<ClientResource>, Void>() {
                @Override
                public Void execute(OperationResult<ClientResource> data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });
            doNothing().when(callback).execute(operationResultMock);
            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            RequestExecution retrieved = adapter.asyncCopyOrMove(true, "fromUri", callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());

            Mockito.verify(jerseyRequestMock).put("");
            Mockito.verify(jerseyRequestMock).addParams(any(MultivaluedHashMap.class));
            Mockito.verify(jerseyRequestMock).addHeader("Content-Location", "fromUri");
            Mockito.verify(callback).execute(operationResultMock);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_move_resource_asynchronously() throws InterruptedException {

        String resourceUri = "uri";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientResource.class),
                    eq(new String[]{"resources", resourceUri}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).post(null);

            final Callback<OperationResult<ClientResource>, Void> callback = Mockito.spy(new Callback<OperationResult<ClientResource>, Void>() {
                @Override
                public Void execute(OperationResult<ClientResource> data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });
            doNothing().when(callback).execute(operationResultMock);
            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            RequestExecution retrieved = adapter.asyncCopyOrMove(false, "fromUri", callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());

            Mockito.verify(jerseyRequestMock).post(null);
            Mockito.verify(jerseyRequestMock).addParams(any(MultivaluedHashMap.class));
            Mockito.verify(jerseyRequestMock).addHeader("Content-Location", "fromUri");
            Mockito.verify(callback).execute(operationResultMock);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_update_resource() throws InterruptedException {

        String resourceUri = "uri";
        ClientVirtualDataSource source = new ClientVirtualDataSource();

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientVirtualDataSource.class),
                    eq(new String[]{"resources", resourceUri}),
                    any(DefaultErrorHandler.class))).thenReturn(virtualDataSourceJerseyRequestMock);

            doReturn(operationResultMock).when(virtualDataSourceJerseyRequestMock).put(source);
            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
            doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
            doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
            SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

            OperationResult<ClientVirtualDataSource> retrieved = adapter.createOrUpdate(source);

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            Mockito.verify(virtualDataSourceJerseyRequestMock).put(source);
            Mockito.verify(virtualDataSourceJerseyRequestMock).addParams(any(MultivaluedHashMap.class));
            Mockito.verify(virtualDataSourceJerseyRequestMock).setContentType("application/repository.virtualDataSource+json");
            Mockito.verify(virtualDataSourceJerseyRequestMock).setAccept("application/repository.virtualDataSource+json");
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T getInternalState(Object target, String fieldName) {
        try {
            Class<?> clazz = target.getClass();
            while (clazz != null) {
                try {
                    java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return (T) field.get(target);
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            throw new NoSuchFieldException(fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
