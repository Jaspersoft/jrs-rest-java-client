package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.resources.ClientAdhocDataView;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.dto.resources.ClientVirtualDataSource;
import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceValidationErrorHandler;
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
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
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
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * Unit Tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter}
 */
@PrepareForTest(JerseyRequest.class)
public class SingleResourceAdapterTest extends PowerMockTestCase {

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


    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, configurationMock, jerseyRequestMock, operationResultMock, objectJerseyRequestMock,
                objectOperationResultMock, clientFileJerseyRequestMock,
                clientFileOperationResultMock, fileMock, adhocDataViewJerseyRequestMock,
                adhocDataViewOperationResultMock, configurationMock, descriptorMock,
                inputStreamJerseyRequestMock, inputStreamOperationResultMock,
                virtualDataSourceJerseyRequestMock, virtualDataSourceOperationResultMock);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter#asyncDetails(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_return_specified_resource_asynchronously_as_json() throws InterruptedException {

        /** Given **/
        String resourceUri = "requestId";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleResourceAdapter adapterSpy = PowerMockito.spy(new SingleResourceAdapter(sessionStorageMock, resourceUri));
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);
        mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(ClientResource.class),
                eq(new String[]{"resources", resourceUri}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        PowerMockito.doReturn(operationResultMock).when(jerseyRequestMock).get();

        final Callback<OperationResult<ClientResource>, Void> callback = PowerMockito.spy(new Callback<OperationResult<ClientResource>, Void>() {
            @Override
            public Void execute(OperationResult<ClientResource> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });
        PowerMockito.doNothing().when(callback).execute(operationResultMock);

        /** When **/
        RequestExecution retrieved = adapterSpy.asyncDetails(callback);

        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }

        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(jerseyRequestMock, times(1)).get();
        verify(callback).execute(operationResultMock);
    }


    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter#asyncDetails(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_return_specified_resource_asynchronously_as_xml() throws InterruptedException {

        /** Given **/
        String resourceUri = "requestId";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleResourceAdapter adapterSpy = PowerMockito.spy(new SingleResourceAdapter(sessionStorageMock, resourceUri));
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.XML);
        mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(ClientResource.class),
                eq(new String[]{"resources", resourceUri}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        PowerMockito.doReturn(operationResultMock).when(jerseyRequestMock).get();

        final Callback<OperationResult<ClientResource>, Void> callback = PowerMockito.spy(new Callback<OperationResult<ClientResource>, Void>() {
            @Override
            public Void execute(OperationResult<ClientResource> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });
        PowerMockito.doNothing().when(callback).execute(operationResultMock);

        /** When **/
        RequestExecution retrieved = adapterSpy.asyncDetails(callback);

        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }

        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(jerseyRequestMock, times(1)).get();
        verify(callback).execute(operationResultMock);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter#parameter(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceServiceParameter, String)}
     */
    @SuppressWarnings("unchecked")
    public void should_set_parameter() {

        /** Given **/
        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, "resourceUri");

        /** When **/
        SingleResourceAdapter retrieved = adapter.parameter(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceServiceParameter.CREATE_FOLDERS, "true");

        /** Then **/
        assertSame(adapter, retrieved);
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) Whitebox.getInternalState(retrieved, "params");
        String param = retrievedParams.get(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceServiceParameter.CREATE_FOLDERS.getName()).get(0);
        assertEquals(param, "true");
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter#asyncDelete(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_delete_resource_asynchronously() throws InterruptedException {

        /** Given **/
        String resourceUri = "requestId";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
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

        /** When **/
        RequestExecution retrieved = adapter.asyncDelete(callback);

        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }

        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(objectJerseyRequestMock, times(1)).delete();
        verify(callback).execute(objectOperationResultMock);
    }


    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter#details()}
     */
    @SuppressWarnings("unchecked")
    public void should_return_operation_result_with_client_resource_folder() {

        /** Given **/
        String resourceUri = "/";
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientResource.class),
                eq(new String[]{"resources"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).get();

        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

        /** When **/
        OperationResult<ClientResource> retrieved = adapter.details();

        /** Then **/
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);

        Mockito.verify(jerseyRequestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        Mockito.verify(jerseyRequestMock).setAccept(ResourceMediaType.FOLDER_JSON);
        Mockito.verify(jerseyRequestMock).get();
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter#details()}
     */
    @SuppressWarnings("unchecked")
    public void should_return_operation_result_with_client_resource_folder_as_xml() {

        /** Given **/
        String resourceUri = "/";
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.XML);
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientResource.class),
                eq(new String[]{"resources"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).get();

        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

        /** When **/
        OperationResult<ClientResource> retrieved = adapter.details();

        /** Then **/
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);

        Mockito.verify(jerseyRequestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        Mockito.verify(jerseyRequestMock).setAccept(ResourceMediaType.FOLDER_XML);
        Mockito.verify(jerseyRequestMock).get();
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void should_throw_UnsupportedOperationException() {
        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, "uri");
        adapter.patchResource(null);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter#asyncPatchResource(Class, com.jaspersoft.jasperserver.dto.common.PatchDescriptor, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)} )}
     */
    public void should_send_descriptor_into_server_asynchronously() throws InterruptedException {

        /** Given **/
        String resourceUri = "/";

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientAdhocDataView.class),
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


        /** When **/
        RequestExecution retrieved = adapter.asyncPatchResource(ClientAdhocDataView.class, descriptorMock, callback);


        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }


        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(adhocDataViewJerseyRequestMock).post(descriptorMock);
        Mockito.verify(adhocDataViewJerseyRequestMock).setAccept("application/repository.adhocDataView+json");
        Mockito.verify(adhocDataViewJerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
        Mockito.verify(callback).execute(adhocDataViewOperationResultMock);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter#patchResource(Class, com.jaspersoft.jasperserver.dto.common.PatchDescriptor)}
     */
    public void should_send_descriptor_into_server() {

        /** Given **/
        String resourceUri = "/";

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientAdhocDataView.class),
                eq(new String[]{"resources"}),
                any(DefaultErrorHandler.class))).thenReturn(adhocDataViewJerseyRequestMock);
        doReturn(adhocDataViewOperationResultMock).when(adhocDataViewJerseyRequestMock).post(descriptorMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();

        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

        /** When **/
        OperationResult<ClientAdhocDataView> retrieved = adapter.patchResource(ClientAdhocDataView.class, descriptorMock);

        /** Then **/
        verifyStatic(JerseyRequest.class);
        buildRequest(eq(sessionStorageMock),
                eq(ClientAdhocDataView.class),
                eq(new String[]{"resources"}),
                any(DefaultErrorHandler.class));

        assertNotNull(retrieved);
        assertSame(retrieved, adhocDataViewOperationResultMock);

        Mockito.verify(adhocDataViewJerseyRequestMock).post(descriptorMock);
        Mockito.verify(adhocDataViewJerseyRequestMock).setAccept("application/repository.adhocDataView+json");
        Mockito.verify(adhocDataViewJerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
        Mockito.verify(adhocDataViewJerseyRequestMock).addParams(any(MultivaluedHashMap.class));
    }

    @Test
    public void should_delete_resource_on_server() {

        /** Given **/
        String resourceUri = "uri";

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(Object.class),
                eq(new String[]{"resources", resourceUri}), any(DefaultErrorHandler.class))).thenReturn(objectJerseyRequestMock);
        doReturn(objectOperationResultMock).when(objectJerseyRequestMock).delete();

        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

        /** When **/
        OperationResult retrieved = adapter.delete();

        /** Then **/
        assertSame(retrieved, objectOperationResultMock);
        Mockito.verify(objectJerseyRequestMock).delete();
        Mockito.verifyNoMoreInteractions(objectJerseyRequestMock);
    }

    @Test
    public void should_return_resource_as_IS_in_binary_format() {

        /** Given **/
        String resourceUri = "uri";

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(InputStream.class),
                eq(new String[]{"resources", resourceUri}), any(DefaultErrorHandler.class))).thenReturn(inputStreamJerseyRequestMock);
        doReturn(inputStreamOperationResultMock).when(inputStreamJerseyRequestMock).get();
        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);

        /** When **/
        OperationResult<InputStream> retrieved = adapter.downloadBinary();

        /** Then **/
        assertNotNull(retrieved);
        assertSame(retrieved, inputStreamOperationResultMock);
        Mockito.verify(inputStreamJerseyRequestMock).get();
        Mockito.verifyNoMoreInteractions(inputStreamJerseyRequestMock);
    }

    @Test
    public void should_return_resource_as_IS_in_binary_format_asynchronously() throws InterruptedException {

        /** Given **/
        String resourceUri = "uri";

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(InputStream.class),
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


        /** When **/
        RequestExecution retrieved = adapter.asyncDownloadBinary(callback);


        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }


        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(inputStreamJerseyRequestMock).get();
        Mockito.verify(callback).execute(inputStreamOperationResultMock);
        Mockito.verifyNoMoreInteractions(inputStreamJerseyRequestMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_update_resource_asynchronously() throws InterruptedException {

        /** Given **/
        String resourceUri = "uri";
        ClientVirtualDataSource source = new ClientVirtualDataSource();

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
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


        /** When **/
        RequestExecution retrieved = adapter.asyncCreateOrUpdate(source, callback);

        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }

        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(virtualDataSourceJerseyRequestMock).put(source);
        Mockito.verify(callback).execute(operationResultMock);
        Mockito.verify(virtualDataSourceJerseyRequestMock).addParams(any(MultivaluedHashMap.class));
        Mockito.verify(virtualDataSourceJerseyRequestMock).setContentType("application/repository.virtualDataSource+json");
        Mockito.verify(virtualDataSourceJerseyRequestMock).setAccept("application/repository.virtualDataSource+json");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_copy_resource_asynchronously() throws InterruptedException {

        /** Given **/
        String resourceUri = "uri";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
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


        /** When **/
        RequestExecution retrieved = adapter.asyncCopyOrMove(true, "fromUri", callback);


        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }


        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(jerseyRequestMock).put("");
        Mockito.verify(jerseyRequestMock).addParams(any(MultivaluedHashMap.class));
        Mockito.verify(jerseyRequestMock).addHeader("Content-Location", "fromUri");
        Mockito.verify(callback).execute(operationResultMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_move_resource_asynchronously() throws InterruptedException {

        /** Given **/
        String resourceUri = "uri";

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
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


        /** When **/
        RequestExecution retrieved = adapter.asyncCopyOrMove(false, "fromUri", callback);


        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }


        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(jerseyRequestMock).post(null);
        Mockito.verify(jerseyRequestMock).addParams(any(MultivaluedHashMap.class));
        Mockito.verify(jerseyRequestMock).addHeader("Content-Location", "fromUri");
        Mockito.verify(callback).execute(operationResultMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_update_resource() throws InterruptedException {

        /** Given **/
        String resourceUri = "uri";
        ClientVirtualDataSource source = new ClientVirtualDataSource();

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(ClientVirtualDataSource.class),
                eq(new String[]{"resources", resourceUri}),
                any(DefaultErrorHandler.class))).thenReturn(virtualDataSourceJerseyRequestMock);

        doReturn(operationResultMock).when(virtualDataSourceJerseyRequestMock).put(source);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();

        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        SingleResourceAdapter adapter = new SingleResourceAdapter(sessionStorageMock, resourceUri);


        /** When **/
        OperationResult<ClientVirtualDataSource> retrieved = adapter.createOrUpdate(source);


        /** Then **/
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        Mockito.verify(virtualDataSourceJerseyRequestMock).put(source);
        Mockito.verify(virtualDataSourceJerseyRequestMock).addParams(any(MultivaluedHashMap.class));
        Mockito.verify(virtualDataSourceJerseyRequestMock).setContentType("application/repository.virtualDataSource+json");
        Mockito.verify(virtualDataSourceJerseyRequestMock).setAccept("application/repository.virtualDataSource+json");
    }

}
