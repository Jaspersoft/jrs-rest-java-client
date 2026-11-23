package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.BatchAttributeAdapter}
 */
public class BatchAttributeAdapterTest {

    private SessionStorage sessionStorageMock;
    private JerseyRequest jerseyRequestMock;
    private OperationResult operationResultMock;

    @InjectMocks
    private BatchAttributeAdapter adapter;

    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        jerseyRequestMock = mock(JerseyRequest.class);
        operationResultMock = mock(OperationResult.class);
    }

    @Test
    public void should_return_proper_operation_result() {
        // Given
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        HypermediaAttributesListWrapper attributes = new HypermediaAttributesListWrapper();
        attributes.setProfileAttributes(asList(new HypermediaAttribute(new ClientAttribute().setName("max_threads").setValue("512")), new HypermediaAttribute(new ClientAttribute().setName("admin_cell_phone").setValue("03"))));

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
            doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
            doReturn(jerseyRequestMock).when(jerseyRequestMock).setContentType(anyString());
            when(jerseyRequestMock.put(attributes)).thenReturn(operationResultMock);

            // When
            BatchAttributeAdapter adapter = new BatchAttributeAdapter("/", null, sessionStorageMock, attributes.getProfileAttributes().get(0).getName(), attributes.getProfileAttributes().get(1).getName());
            OperationResult<HypermediaAttributesListWrapper> retrieved = adapter.createOrUpdate(attributes);

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_throw_an_exception_when_query_params_were_not_set() {
        // Given
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        HypermediaAttributesListWrapper attributes = new HypermediaAttributesListWrapper();
        attributes.setProfileAttributes(asList(new HypermediaAttribute(new ClientAttribute().setName("max_threads").setValue("512")), new HypermediaAttribute(new ClientAttribute().setName("admin_cell_phone").setValue("03"))));

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
            doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
            doReturn(jerseyRequestMock).when(jerseyRequestMock).setContentType(anyString());
            when(jerseyRequestMock.put(attributes)).thenReturn(operationResultMock);

            // When & Then
            BatchAttributeAdapter adapter = new BatchAttributeAdapter("/", null, sessionStorageMock);
            try {
                adapter.createOrUpdate(null);
                fail("Expected MandatoryParameterNotFoundException");
            } catch (MandatoryParameterNotFoundException e) {
                // expected
            }
        }
    }

    @Test
    void shouldReturnProperOperationResult() {
        // Given
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        HypermediaAttributesListWrapper attributes = new HypermediaAttributesListWrapper();
        attributes.setProfileAttributes(asList(new HypermediaAttribute(new ClientAttribute().setName("max_threads").setValue("512")), new HypermediaAttribute(new ClientAttribute().setName("admin_cell_phone").setValue("03"))));

        // Mock static methods
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            // Stubbing static method
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Stubbing instance methods
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
            when(jerseyRequestMock.setContentType(anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.put(attributes)).thenReturn(operationResultMock);

            // When
            BatchAttributeAdapter adapter = new BatchAttributeAdapter("/", null, sessionStorageMock, attributes.getProfileAttributes().get(0).getName(), attributes.getProfileAttributes().get(1).getName());
            OperationResult<HypermediaAttributesListWrapper> retrieved = adapter.createOrUpdate(attributes);

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);

            // Verify static method interaction
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    void should_set_params_as_vararg() throws Exception {
        // Given
        // Create adapter passing varargs
        BatchAttributeAdapter adapter = new BatchAttributeAdapter("/", null, sessionStorageMock, "x", "y", "z");
        // When
        MultivaluedMap<String, String> params = getField(adapter, "params");
        // Then
        assertNotNull(params);
        List<String> list = params.get("name");
        assertEquals(3, list.size());
        assertTrue(list.containsAll(Arrays.asList("x", "y", "z")));
    }

    @Test
    void should_discard_extra_parameters() {
        // Given
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        HypermediaAttributesListWrapper attributes = new HypermediaAttributesListWrapper();
        attributes.setProfileAttributes(asList(new HypermediaAttribute(new ClientAttribute().setName("max_threads").setValue("512")), new HypermediaAttribute(new ClientAttribute().setName("admin_cell_phone").setValue("03"))));

        HypermediaAttributesListWrapper newServerAttributes = new HypermediaAttributesListWrapper(attributes);
        newServerAttributes.getProfileAttributes().add(new HypermediaAttribute().setName("extra_attr_1").setValue("some_value_1"));
        newServerAttributes.getProfileAttributes().add(new HypermediaAttribute().setName("extra_attr_2").setValue("some_value_2"));
        newServerAttributes.getProfileAttributes().add(new HypermediaAttribute().setName("extra_attr_3").setValue("some_value_3"));

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            // Mock static method
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock instance methods
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
            when(jerseyRequestMock.setContentType(anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.put(attributes)).thenReturn(operationResultMock);

            // When
            BatchAttributeAdapter adapter = new BatchAttributeAdapter("/", null, sessionStorageMock, attributes.getProfileAttributes().get(0).getName(), attributes.getProfileAttributes().get(1).getName());
            OperationResult<HypermediaAttributesListWrapper> retrieved = adapter.createOrUpdate(newServerAttributes);

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    void should_delete_server_attributes() {
        // Given
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        HypermediaAttributesListWrapper attributes = new HypermediaAttributesListWrapper();
        attributes.setProfileAttributes(asList(new HypermediaAttribute(new ClientAttribute().setName("max_threads").setValue("512")), new HypermediaAttribute(new ClientAttribute().setName("admin_cell_phone").setValue("03"))));

        HypermediaAttributesListWrapper newServerAttributes = new HypermediaAttributesListWrapper(attributes);
        newServerAttributes.getProfileAttributes().add(new HypermediaAttribute().setName("extra_attr_1").setValue("some_value_1"));
        newServerAttributes.getProfileAttributes().add(new HypermediaAttribute().setName("extra_attr_2").setValue("some_value_2"));
        newServerAttributes.getProfileAttributes().add(new HypermediaAttribute().setName("extra_attr_3").setValue("some_value_3"));

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            // Mock static method
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock instance methods
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
            when(jerseyRequestMock.setContentType(anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.delete()).thenReturn(operationResultMock);

            // When
            BatchAttributeAdapter adapter = new BatchAttributeAdapter("/", null, sessionStorageMock);
            OperationResult<HypermediaAttributesListWrapper> retrieved = adapter.delete();

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class)), times(1));
            verify(jerseyRequestMock, times(1)).delete();
        }
    }

    @Test
    void should_retrieve_server_attributes() {
        // Given
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        HypermediaAttributesListWrapper attributes = new HypermediaAttributesListWrapper();
        attributes.setProfileAttributes(asList(new HypermediaAttribute(new ClientAttribute().setName("max_threads").setValue("512")), new HypermediaAttribute(new ClientAttribute().setName("admin_cell_phone").setValue("03"))));

        HypermediaAttributesListWrapper newServerAttributes = new HypermediaAttributesListWrapper(attributes);
        newServerAttributes.getProfileAttributes().add(new HypermediaAttribute().setName("extra_attr_1").setValue("some_value_1"));
        newServerAttributes.getProfileAttributes().add(new HypermediaAttribute().setName("extra_attr_2").setValue("some_value_2"));
        newServerAttributes.getProfileAttributes().add(new HypermediaAttribute().setName("extra_attr_3").setValue("some_value_3"));

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            // Mock static method
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock instance methods
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
            when(jerseyRequestMock.setContentType(anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.get()).thenReturn(operationResultMock);
            when(jerseyRequestMock.addParams(any(MultivaluedHashMap.class))).thenReturn(jerseyRequestMock);

            // When
            BatchAttributeAdapter adapter = new BatchAttributeAdapter("/", null, sessionStorageMock);
            OperationResult<HypermediaAttributesListWrapper> retrieved = adapter.get();

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class)), times(1));
            verify(jerseyRequestMock, times(1)).get();
            verify(jerseyRequestMock, times(1)).addParams(any(MultivaluedHashMap.class));
            verifyNoMoreInteractions(jerseyRequestMock);
        }
    }

    @Test
    void should_createOrUpdate_attributes_asynchronously() throws Exception {
        // Given
        HypermediaAttributesListWrapper listWrapperMock = mock(HypermediaAttributesListWrapper.class);
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        BatchAttributeAdapter adapterSpy = spy(new BatchAttributeAdapter("my/cool/uri", null, sessionStorageMock));

        final Callback<OperationResult<HypermediaAttributesListWrapper>, Void> callback = spy(new Callback<OperationResult<HypermediaAttributesListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<HypermediaAttributesListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            // Mock static method
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock instance methods
            doReturn(jerseyRequestMock).when(adapterSpy).buildRequest();
            doReturn(jerseyRequestMock).when(adapterSpy).buildRequest();
            doReturn(operationResultMock).when(jerseyRequestMock).put(listWrapperMock);
            doNothing().when(callback).execute(operationResultMock);

            // When
            RequestExecution retrieved = adapterSpy.asyncCreateOrUpdate(listWrapperMock, callback);

            // Wait
            synchronized (callback) {
                callback.wait(1000);
            }

            // Then
            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            verify(callback, times(1)).execute(operationResultMock);
            verify(jerseyRequestMock, times(1)).put(listWrapperMock);
        }
    }

    @Test
    void should_delete_attributes_asynchronously() throws Exception {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        BatchAttributeAdapter adapterSpy = spy(new BatchAttributeAdapter("my/cool/uri", null, sessionStorageMock));

        final Callback<OperationResult<HypermediaAttributesListWrapper>, Void> callback = spy(new Callback<OperationResult<HypermediaAttributesListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<HypermediaAttributesListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            // Mock static method
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock instance methods
            doReturn(jerseyRequestMock).when(adapterSpy).buildRequest();
            doReturn(operationResultMock).when(jerseyRequestMock).delete();
            doNothing().when(callback).execute(operationResultMock);

            // When
            RequestExecution retrieved = adapterSpy.asyncDelete(callback);

            // Wait
            synchronized (callback) {
                callback.wait(1000);
            }

            // Then
            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            verify(callback, times(1)).execute(operationResultMock);
            verify(jerseyRequestMock, times(1)).delete();
        }
    }

    @Test
    void should_retrieve_server_attributes_asynchronously() throws Exception {
        // Given
        BatchAttributeAdapter adapter = spy(new BatchAttributeAdapter("/", null, sessionStorageMock));
        doReturn(jerseyRequestMock).when(adapter).buildRequest();
        doReturn(operationResultMock).when(jerseyRequestMock).get();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<HypermediaAttributesListWrapper>, Void> callback = spy(new Callback<OperationResult<HypermediaAttributesListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<HypermediaAttributesListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doNothing().when(callback).execute(operationResultMock);

        // When
        RequestExecution retrieved = adapter.asyncGet(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        verify(jerseyRequestMock).get();
        verify(callback).execute(operationResultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    void should_search_server_attributes_with_proper_user_in_root_holder() {
        // Given
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("group", "custom");
        map.add("recursive", "true");
        map.add("includeInherited", "true");
        map.add("offset", "10");
        map.add("limit", "20");

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock other behaviors
            RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);
            when(jerseyRequestMock.addParam(anyString(), anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.addParams(map)).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.get()).thenReturn(operationResultMock);

            // When
            AttributesService service = new AttributesService(sessionStorageMock);
            OperationResult<HypermediaAttributesListWrapper> retrieved = service.forUser("myUser").allAttributes().parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.CUSTOM).parameter(AttributesSearchParameter.RECURSIVE, Boolean.TRUE).parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE).parameter(AttributesSearchParameter.OFFSET, 10).parameter(AttributesSearchParameter.LIMIT, 20).setIncludePermissions(true).search();

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class)), times(1));
            verify(jerseyRequestMock, times(1)).addParams(map);
            verify(jerseyRequestMock, times(1)).get();
            verify(jerseyRequestMock, times(1)).addParam("_embedded", "permission");
            verify(jerseyRequestMock, times(1)).addParam("holder", "user:/myUser");
            verify(jerseyRequestMock, times(1)).setAccept("application/attributes.collection.hal+json");
            verifyNoMoreInteractions(jerseyRequestMock);
        }
    }

    @Test
    public void should_search_server_attributes_with_proper_user_in_tenant_holder() {
        // Given
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("group", "custom");
        map.add("recursive", "true");
        map.add("includeInherited", "true");
        map.add("offset", "10");
        map.add("limit", "20");

        // Mock static method
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock instance methods
            RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);
            when(jerseyRequestMock.addParam(anyString(), anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.addParams(map)).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.get()).thenReturn(operationResultMock);

            // When
            AttributesService service = new AttributesService(sessionStorageMock);
            OperationResult<HypermediaAttributesListWrapper> retrieved = service.forOrganization("myOrg").forUser("myUser").allAttributes().parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.CUSTOM).parameter(AttributesSearchParameter.RECURSIVE, Boolean.TRUE).parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE).parameter(AttributesSearchParameter.OFFSET, 10).parameter(AttributesSearchParameter.LIMIT, 20).setIncludePermissions(true).search();

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            verify(jerseyRequestMock).addParams(map);
            verify(jerseyRequestMock).get();
            verify(jerseyRequestMock).addParam("_embedded", "permission");
            verify(jerseyRequestMock).addParam("holder", "user:/myOrg/myUser");
            verify(jerseyRequestMock).setAccept("application/attributes.collection.hal+json");
            verifyNoMoreInteractions(jerseyRequestMock);
        }
    }

    @Test
    void shouldSearchServerAttributesWithProperTenantHolder() {
        // Given
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("group", "custom");
        map.add("recursive", "true");
        map.add("includeInherited", "true");
        map.add("offset", "10");
        map.add("limit", "20");

        // Mock static methods
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            // Mock behavior of buildRequest
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock other dependencies
            RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

            when(jerseyRequestMock.addParam(anyString(), anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.addParams(map)).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.get()).thenReturn(operationResultMock);

            // When
            AttributesService service = new AttributesService(sessionStorageMock);

            OperationResult<HypermediaAttributesListWrapper> retrieved = service.forOrganization("myOrg").allAttributes().parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.CUSTOM).parameter(AttributesSearchParameter.RECURSIVE, Boolean.TRUE).parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE).parameter(AttributesSearchParameter.OFFSET, 10).parameter(AttributesSearchParameter.LIMIT, 20).setIncludePermissions(true).search();

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            verify(jerseyRequestMock, times(1)).addParams(map);
            verify(jerseyRequestMock, times(1)).get();
            verify(jerseyRequestMock, times(1)).addParam("_embedded", "permission");
            verify(jerseyRequestMock, times(1)).addParam("holder", "tenant:/myOrg");
            verify(jerseyRequestMock, times(1)).setAccept("application/attributes.collection.hal+json");
            verifyNoMoreInteractions(jerseyRequestMock);
        }
    }

    @Test
    void shouldSearchServerAttributesWithProperRootHolder() {
        // Given
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("group", "custom");
        map.add("recursive", "true");
        map.add("includeInherited", "true");
        map.add("offset", "10");
        map.add("limit", "20");

        // Mock static methods
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            // Mock behavior of buildRequest
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock other dependencies
            RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

            when(jerseyRequestMock.addParam(anyString(), anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.addParams(map)).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.get()).thenReturn(operationResultMock);

            // When
            AttributesService service = new AttributesService(sessionStorageMock);

            OperationResult<HypermediaAttributesListWrapper> retrieved = service.allAttributes().parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.CUSTOM).parameter(AttributesSearchParameter.RECURSIVE, Boolean.TRUE).parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE).parameter(AttributesSearchParameter.OFFSET, 10).parameter(AttributesSearchParameter.LIMIT, 20).setIncludePermissions(true).search();

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            verify(jerseyRequestMock, times(1)).addParams(map);
            verify(jerseyRequestMock, times(1)).get();
            verify(jerseyRequestMock, times(1)).addParam("_embedded", "permission");
            verify(jerseyRequestMock, times(1)).setAccept("application/attributes.collection.hal+json");
            verifyNoMoreInteractions(jerseyRequestMock);
        }
    }

    @Test
    void shouldSearchServerAttributesWithParameters() {
        // Given
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("name", "attrName1");
        map.add("name", "attrName2");
        map.add("name", "attrName3");
        map.add("holder", "tenant:/");
        map.add("group", "custom");
        map.add("recursive", "true");
        map.add("includeInherited", "true");
        map.add("offset", "10");
        map.add("limit", "20");

        // Mock static methods
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            // Mock behavior of buildRequest
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock other dependencies
            RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

            when(jerseyRequestMock.addParam(anyString(), anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.addParams(map)).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.get()).thenReturn(operationResultMock);

            // When
            BatchAttributeAdapter adapter = new BatchAttributeAdapter("/", null, sessionStorageMock, "attrName1", "attrName2", "attrName3");

            OperationResult<HypermediaAttributesListWrapper> retrieved = adapter.parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.CUSTOM).parameter(AttributesSearchParameter.HOLDER, "/").parameter(AttributesSearchParameter.RECURSIVE, Boolean.TRUE).parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE).parameter(AttributesSearchParameter.OFFSET, 10).parameter(AttributesSearchParameter.LIMIT, 20).setIncludePermissions(true).search();

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            verify(jerseyRequestMock, times(1)).addParams(map);
            verify(jerseyRequestMock, times(1)).get();
            verify(jerseyRequestMock, times(1)).addParam("_embedded", "permission");
            verify(jerseyRequestMock, times(1)).setAccept("application/attributes.collection.hal+json");
            verifyNoMoreInteractions(jerseyRequestMock);
        }
    }

    @Test
    void shouldSearchServerAttributesWithProperUserInTenantHolderViaParameter() {
        // Given
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("group", "custom");
        map.add("holder", "user:/myOrg/user");
        map.add("recursive", "true");
        map.add("includeInherited", "true");
        map.add("offset", "10");
        map.add("limit", "20");

        // Mock static methods
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            // Mock behavior of buildRequest
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Mock other dependencies
            RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

            when(jerseyRequestMock.addParam(anyString(), anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.addParams(map)).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.get()).thenReturn(operationResultMock);

            // When
            AttributesService service = new AttributesService(sessionStorageMock);

            OperationResult<HypermediaAttributesListWrapper> retrieved = service.allAttributes().parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.CUSTOM).parameter(AttributesSearchParameter.HOLDER, "myOrg/user").parameter(AttributesSearchParameter.RECURSIVE, Boolean.TRUE).parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE).parameter(AttributesSearchParameter.OFFSET, 10).parameter(AttributesSearchParameter.LIMIT, 20).setIncludePermissions(true).search();

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            verify(jerseyRequestMock, times(1)).addParams(map);
            verify(jerseyRequestMock, times(1)).get();
            verify(jerseyRequestMock, times(1)).addParam("_embedded", "permission");
            verify(jerseyRequestMock, times(1)).setAccept("application/attributes.collection.hal+json");
            verifyNoMoreInteractions(jerseyRequestMock);
        }
    }

    @Test
    void shouldSetInternalStateWithParametersWithHolder() throws NoSuchFieldException, IllegalAccessException {
        // Given
        adapter = new BatchAttributeAdapter("/", null, sessionStorageMock, "attrName1", "attrName2", "attrName3");

        // When
        BatchAttributeAdapter retrieved = adapter.parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.CUSTOM).parameter(AttributesSearchParameter.HOLDER, "myOrg").parameter(AttributesSearchParameter.RECURSIVE, Boolean.TRUE).parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE).parameter(AttributesSearchParameter.OFFSET, 10).parameter(AttributesSearchParameter.LIMIT, 20);

        // Access private 'params' field using reflection
        MultivaluedHashMap<String, String> params = getField(adapter, "params");

        // Then
        assertSame(retrieved, adapter);
        assertEquals(7, params.size());
        assertEquals(3, params.get("name").size());
        assertEquals("tenant:/myOrg", params.get("holder").get(0));
        assertEquals("custom", params.get("group").get(0));
        assertEquals("true", params.get("recursive").get(0));
        assertEquals("true", params.get("includeInherited").get(0));
        assertEquals("10", params.get("offset").get(0));
        assertEquals("20", params.get("limit").get(0));
    }

    @Test
    void shouldSetInternalStateWithParametersWithoutHolder() {
        // Given
        adapter = new BatchAttributeAdapter("/", null, sessionStorageMock, "attrName1", "attrName2", "attrName3");

        // When
        BatchAttributeAdapter retrieved = adapter.parameter(AttributesSearchParameter.GROUP, AttributesGroupParameter.CUSTOM).parameter(AttributesSearchParameter.HOLDER, "/").parameter(AttributesSearchParameter.RECURSIVE, Boolean.TRUE).parameter(AttributesSearchParameter.INCLUDE_INHERITED, Boolean.TRUE).parameter(AttributesSearchParameter.OFFSET, 10).parameter(AttributesSearchParameter.LIMIT, 20);

        // Access private 'params' field using reflection
        MultivaluedHashMap<String, String> params = getField(adapter, "params");

        // Then
        assertSame(retrieved, adapter);
        assertEquals(7, params.size());
        assertEquals(3, params.get("name").size());
        assertEquals("tenant:/", params.get("holder").get(0));
        assertEquals("custom", params.get("group").get(0));
        assertEquals("true", params.get("recursive").get(0));
        assertEquals("true", params.get("includeInherited").get(0));
        assertEquals("10", params.get("offset").get(0));
        assertEquals("20", params.get("limit").get(0));
    }

    @Test
    void should_retrieve_server_attributes_with_permissions() {
        // Given: mock static JerseyRequest
        try (MockedStatic<JerseyRequest> jerseyMock = mockStatic(JerseyRequest.class)) {
            jerseyMock.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

            // Also mock sessionStorage to return a valid configuration
            RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

            // Stub behavior on the request mock
            when(jerseyRequestMock.addParam(anyString(), anyString())).thenReturn(jerseyRequestMock);
            when(jerseyRequestMock.get()).thenReturn(operationResultMock);

            // When: run the adapter
            BatchAttributeAdapter adapter = new BatchAttributeAdapter("/", null, sessionStorageMock);
            OperationResult<HypermediaAttributesListWrapper> result = adapter.setIncludePermissions(true).get();

            // Then: verify behavior
            assertNotNull(result);
            assertSame(operationResultMock, result);

            jerseyMock.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(HypermediaAttributesListWrapper.class), eq(new String[]{"attributes"}), any(DefaultErrorHandler.class)), times(1));
            verify(jerseyRequestMock, times(1)).addParams(any(MultivaluedHashMap.class));
            verify(jerseyRequestMock, times(1)).get();
            verify(jerseyRequestMock, times(1)).setAccept("application/hal+json");
            verify(jerseyRequestMock, times(1)).addParam("_embedded", "permission");
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T getField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(target);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to access field '" + fieldName + "' on " + target, e);
        }
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        jerseyRequestMock = null;
        operationResultMock = null;
    }
}
