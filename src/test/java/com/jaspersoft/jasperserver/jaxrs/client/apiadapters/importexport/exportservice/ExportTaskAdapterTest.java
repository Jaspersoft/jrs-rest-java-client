package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.ExportTaskDto;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter}
 */
@PrepareForTest({JerseyRequest.class, ExportTaskAdapter.class, ExportTaskDto.class})
public class ExportTaskAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<StateDto> requestStateDtoMock;

    @Mock
    private OperationResult<StateDto> operationResultStateDtoMock;

    @Mock
    private Callback<OperationResult<StateDto>, Object> callbackMock;

    @Mock
    private RequestBuilder<StateDto> requestBuilderMock;

    @Mock
    private ExportTaskDto taskDtoMock;

    @Mock
    private ExportTaskDto taskDtoCopyMock;

    @BeforeMethod
    public void after() {
        initMocks(this);
    }

    @Test
    public void should_create_an_object_and_prepare_ExportTaskDto_field() throws IllegalAccessException {

        // When
        ExportTaskAdapter adapter = new ExportTaskAdapter(sessionStorageMock);
        Field field = field(ExportTaskAdapter.class, "exportTaskDto");
        Object retrievedField = field.get(adapter);

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertNotNull(retrievedField);
        assertTrue(instanceOf(ExportTaskDto.class).matches(retrievedField));

        assertNotNull(((ExportTaskDto) retrievedField).getParameters());
        assertNotNull(((ExportTaskDto) retrievedField).getRoles());
        assertNotNull(((ExportTaskDto) retrievedField).getUsers());
        assertNotNull(((ExportTaskDto) retrievedField).getUris());
    }

    @Test(testName = "role")
    public void should_add_new_role_to_roles_of_ExportTaskDto() throws IllegalAccessException {
        ExportTaskAdapter adapter = spy(new ExportTaskAdapter(sessionStorageMock));
        ExportTaskAdapter retrievedAdapter = adapter.role("newRole");

        Field field = field(ExportTaskAdapter.class, "exportTaskDto");
        ExportTaskDto retrieved = (ExportTaskDto) field.get(adapter);

        verify(adapter, times(1)).role("newRole");
        assertNotNull(retrievedAdapter);
        assertTrue(retrieved.getRoles().size() == 1);
        assertTrue(retrieved.getRoles().contains("newRole"));
    }

    @Test(testName = "roles")
    public void should_add_new_roles_of_ExportTaskDto_as_list() throws IllegalAccessException {

        // Given
        final List<String> fakeRoles = new ArrayList<String>() {{
            add("SuperManRole");
            add("SlugRole");
        }};

        ExportTaskAdapter adapter = spy(new ExportTaskAdapter(sessionStorageMock));

        // When
        ExportTaskAdapter retrievedAdapter = adapter.roles(fakeRoles);
        Field field = field(ExportTaskAdapter.class, "exportTaskDto");
        ExportTaskDto retrieved = (ExportTaskDto) field.get(adapter);

        // Then
        verify(adapter, times(1)).roles(fakeRoles);
        assertNotNull(retrievedAdapter);
        assertTrue(retrieved.getRoles().size() == 2);
        assertTrue(retrieved.getRoles().contains("SlugRole"));
        assertTrue(retrieved.getRoles().contains("SuperManRole"));
    }

    @Test(testName = "user")
    public void should_add_single_user_to_userList_of_ExportTaskDto() throws IllegalAccessException {
        ExportTaskAdapter adapter = spy(new ExportTaskAdapter(sessionStorageMock));
        ExportTaskAdapter retrievedAdapter = adapter.user("newUser");

        Field field = field(ExportTaskAdapter.class, "exportTaskDto");
        ExportTaskDto retrieved = (ExportTaskDto) field.get(adapter);

        verify(adapter, times(1)).user("newUser");
        assertNotNull(retrievedAdapter);
        assertTrue(retrieved.getUsers().size() == 1);
        assertTrue(retrieved.getUsers().contains("newUser"));
    }

    @Test(testName = "users")
    public void should_add_bunch_of_users_to_userList_of_ExportTaskDto() throws IllegalAccessException {

        // Given
        final List<String> girls = new ArrayList<String>() {{
            add("Catherine");
            add("Angelina");
            add("Katy");
        }};

        ExportTaskAdapter adapter = spy(new ExportTaskAdapter(sessionStorageMock));

        // When
        ExportTaskAdapter retrievedAdapter = adapter.users(girls);
        Field field = field(ExportTaskAdapter.class, "exportTaskDto");
        ExportTaskDto exportTaskDto = (ExportTaskDto) field.get(adapter);

        // Then
        verify(adapter, times(1)).users(girls);
        assertNotNull(retrievedAdapter);
        assertTrue(exportTaskDto.getUsers().size() == 3);
        assertTrue(exportTaskDto.getUsers().contains("Catherine"));
        assertTrue(exportTaskDto.getUsers().contains("Angelina"));
    }

    @Test(testName = "uri")
    public void should_add_a_single_uri_to_uriList_of_ExportTaskDto() throws IllegalAccessException {
        ExportTaskAdapter adapter = spy(new ExportTaskAdapter(sessionStorageMock));
        ExportTaskAdapter retrievedAdapter = adapter.uri("newUri");

        Field field = field(ExportTaskAdapter.class, "exportTaskDto");
        ExportTaskDto retrieved = (ExportTaskDto) field.get(adapter);

        verify(adapter, times(1)).uri("newUri");
        assertNotNull(retrievedAdapter);
        assertTrue(retrieved.getUris().size() == 1);
        assertTrue(retrieved.getUris().contains("newUri"));
    }

    @Test(testName = "uris")
    public void should_add_list_of_uri_to_uriList_of_ExportTaskDto() throws IllegalAccessException {

        final List<String> uris = new ArrayList<String>() {{
            add("firstUri");
            add("secondUri");
        }};

        ExportTaskAdapter adapter = spy(new ExportTaskAdapter(sessionStorageMock));

        ExportTaskAdapter retrievedAdapter = adapter.uris(uris);
        Field field = field(ExportTaskAdapter.class, "exportTaskDto");
        ExportTaskDto exportTaskDto = (ExportTaskDto) field.get(adapter);

        verify(adapter, times(1)).uris(uris);
        assertNotNull(retrievedAdapter);
        assertTrue(exportTaskDto.getUris().size() == 2);
        assertTrue(exportTaskDto.getUris().contains("firstUri"));
        assertFalse(exportTaskDto.getUris().contains("notAUri"));
    }

    @Test(testName = "parameter")
    public void should_add_a_single_parameter_to_parameters_of_ExportTaskDto() throws IllegalAccessException {

        ExportTaskAdapter adapter = spy(new ExportTaskAdapter(sessionStorageMock));
        ExportTaskAdapter retrievedAdapter = adapter.parameter(ExportParameter.INCLUDE_MONITORING_EVENTS);

        Field field = field(ExportTaskAdapter.class, "exportTaskDto");
        ExportTaskDto retrieved = (ExportTaskDto) field.get(adapter);

        verify(adapter, times(1)).parameter(ExportParameter.INCLUDE_MONITORING_EVENTS);
        assertNotNull(retrievedAdapter);
        assertTrue(retrieved.getParameters().size() == 1);
        assertTrue(retrieved.getParameters()
                .contains(ExportParameter.INCLUDE_MONITORING_EVENTS.getParamName()));
    }

    @Test(testName = "parameters")
    public void should_add_parameters_to_params_of_ExportTaskDto_instance() throws IllegalAccessException {

        final List<ExportParameter> params = new ArrayList<ExportParameter>() {{
            add(ExportParameter.REPOSITORY_PERMISSIONS);
            add(ExportParameter.INCLUDE_ACCESS_EVENTS);
        }};

        ExportTaskAdapter adapter = spy(new ExportTaskAdapter(sessionStorageMock));

        ExportTaskAdapter retrievedAdapter = adapter.parameters(params);
        Field field = field(ExportTaskAdapter.class, "exportTaskDto");
        ExportTaskDto exportTaskDto = (ExportTaskDto) field.get(adapter);

        verify(adapter, times(1)).parameters(params);
        assertNotNull(retrievedAdapter);
        assertTrue(exportTaskDto.getParameters().size() == 2);
        assertTrue(exportTaskDto.getParameters()
                .contains(ExportParameter.REPOSITORY_PERMISSIONS.getParamName()));
    }

    @Test(testName = "create")
    public void should_return_not_null_op_result() throws IllegalAccessException {

        // Given
        ExportTaskAdapter adapter = spy(new ExportTaskAdapter(sessionStorageMock));
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(StateDto.class), eq(new String[]{"/export"}),
                any(DefaultErrorHandler.class)))
                .thenReturn(requestStateDtoMock);

        Field field = field(ExportTaskAdapter.class, "exportTaskDto");
        ExportTaskDto exportTaskDto = (ExportTaskDto) field.get(adapter);
        when(requestStateDtoMock.post(exportTaskDto)).thenReturn(operationResultStateDtoMock);

        // When
        OperationResult<StateDto> retrieved = adapter.create();

        // Then
        assertSame(retrieved, operationResultStateDtoMock);
    }

    @Test
    public void should_execute_create_operation_asynchronously() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        ExportTaskAdapter taskAdapterSpy = PowerMockito.spy(new ExportTaskAdapter(sessionStorageMock));
        PowerMockito.whenNew(ExportTaskDto.class).withArguments(any((ExportTaskDto.class))).thenReturn(taskDtoCopyMock);
        PowerMockito.mockStatic(JerseyRequest.class);

        PowerMockito.when(JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(StateDto.class),
                eq(new String[]{"/export"})))
                .thenReturn(requestStateDtoMock);

        final Callback<OperationResult<StateDto>, Void> callback = PowerMockito.spy(new Callback<OperationResult<StateDto>, Void>() {
            @Override
            public Void execute(OperationResult<StateDto> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(operationResultStateDtoMock).when(requestStateDtoMock).post(taskDtoCopyMock);
        doReturn(null).when(callback).execute(operationResultStateDtoMock);

        // When
        RequestExecution retrieved = taskAdapterSpy.asyncCreate(callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultStateDtoMock);
        verify(requestStateDtoMock, times(1)).post(taskDtoCopyMock);
    }

    @AfterMethod
    public void before() {
        reset(sessionStorageMock, requestStateDtoMock, operationResultStateDtoMock, requestBuilderMock,
                taskDtoMock, callbackMock, taskDtoCopyMock);
    }
}