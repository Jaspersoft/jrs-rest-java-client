package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.dto.importexport.State;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportTaskRequestAdapter}
 */
@PrepareForTest({JerseyRequest.class, ImportTaskRequestAdapter.class})
public class ImportTaskRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private File fileMock;

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<State> requestStateDtoMock;

    @Mock
    private OperationResult<State> operationResultStateDtoMock;

    @Mock
    private Callback<OperationResult<State>, Object> callbackMock;

    @Mock
    private MultivaluedHashMap<String, String> mapMock;

    @Mock
    private RequestBuilder<State> requestBuilderMock;

    @BeforeMethod
    public void after() {
        initMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_add_boolean_param_to_private_field() throws IllegalAccessException {

        // When
        ImportTaskRequestAdapter expected = spy(new ImportTaskRequestAdapter(sessionStorageMock));
        ImportTaskRequestAdapter retrieved = expected.parameter(ImportParameter.INCLUDE_ACCESS_EVENTS, Boolean.TRUE);

        Field field = field(ImportTaskRequestAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) field.get(retrieved);

        // Then
        verify(expected, times(1)).parameter(ImportParameter.INCLUDE_ACCESS_EVENTS, Boolean.TRUE);
        assertSame(retrieved, expected);

        List<String> booleanValue = retrievedParams.get(ImportParameter.INCLUDE_ACCESS_EVENTS.getParamName());
        Boolean val = Boolean.valueOf(booleanValue.get(0));

        assertTrue(val);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_add_string_param_to_private_field() throws IllegalAccessException {

        // When
        ImportTaskRequestAdapter expected = spy(new ImportTaskRequestAdapter(sessionStorageMock));
        ImportTaskRequestAdapter retrieved = expected.parameter(ImportParameter.ORGANIZATION, "org_1");

        Field field = field(ImportTaskRequestAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) field.get(retrieved);

        // Then
        verify(expected, times(1)).parameter(ImportParameter.ORGANIZATION, "org_1");
        assertSame(retrieved, expected);
        assertNotNull(retrievedParams);

        List<String> stringValues = retrievedParams.get(ImportParameter.ORGANIZATION.getParamName());
        String val = stringValues.get(0);
        assertEquals(val, "org_1");

    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_add_enum_param_to_private_field() throws IllegalAccessException {

        // When
        ImportTaskRequestAdapter expected = spy(new ImportTaskRequestAdapter(sessionStorageMock));
        ImportTaskRequestAdapter retrieved = expected.parameter(ImportParameter.BROKEN_DEPENDENCIES, BrokenDependenciesParameter.INCLUDE);

        Field field = field(ImportTaskRequestAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) field.get(retrieved);

        // Then
        verify(expected, times(1)).parameter(ImportParameter.BROKEN_DEPENDENCIES, BrokenDependenciesParameter.INCLUDE);
        assertSame(retrieved, expected);

        List<String> enumValue = retrievedParams.get(ImportParameter.BROKEN_DEPENDENCIES.getParamName());
        List<String> stringValues = retrievedParams.get(ImportParameter.BROKEN_DEPENDENCIES.getParamName());
        String val = stringValues.get(0);
        assertEquals(val, "include");

    }

    @Test(testName = "create_with_File_param")
    public void should_invoke_private_method_and_return_op_result() throws Exception {

        // Given
        ImportTaskRequestAdapter adapter = PowerMockito.spy(new ImportTaskRequestAdapter(sessionStorageMock));
        doReturn(operationResultStateDtoMock).when(adapter, "createImport", fileMock);

        // When
        OperationResult<State> retrieved = adapter.create(fileMock);

        // Then
        verifyPrivate(adapter).invoke("createImport", eq(fileMock));
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultStateDtoMock);
    }

    @Test(testName = "create_with_InputStream_param")
    public void should_pass_InputStream_param_and_invoke_private_method() throws Exception {

        InputStream streamMock = mock(InputStream.class);

        // Given
        ImportTaskRequestAdapter adapter = PowerMockito.spy(new ImportTaskRequestAdapter(sessionStorageMock));
        doReturn(operationResultStateDtoMock).when(adapter, "createImport", streamMock);

        // When
        OperationResult<State> retrieved = adapter.create(streamMock);

        // Then
        verifyPrivate(adapter).invoke("createImport", eq(streamMock));
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultStateDtoMock);
    }

    @Test(testName = "asyncCreate_with_File_param")
    public void should_invoke_private_method_with_File_parameter_and_return_RequestExecution_object() throws Exception {

        // Given
        RequestExecution requestExecutionMock = mock(RequestExecution.class);
        ImportTaskRequestAdapter adapter = PowerMockito.spy(new ImportTaskRequestAdapter(sessionStorageMock));
        doReturn(requestExecutionMock).when(adapter, "asyncCreateImport", fileMock, callbackMock);

        // When
        RequestExecution retrieved = adapter.asyncCreate(fileMock, callbackMock);

        // Then
        assertNotNull(retrieved);
        verifyPrivate(adapter).invoke("asyncCreateImport", eq(fileMock), eq(callbackMock));
        assertSame(retrieved, requestExecutionMock);
    }

    @Test(testName = "asyncCreate_with_InputStream_param")
    public void should_invoke_private_method_with_InputStream_parameter_and_return_RequestExecution_object() throws Exception {
        // Given
        RequestExecution requestExecutionMock = mock(RequestExecution.class);
        InputStream streamMock = mock(InputStream.class);
        ImportTaskRequestAdapter adapter = PowerMockito.spy(new ImportTaskRequestAdapter(sessionStorageMock));
        doReturn(requestExecutionMock).when(adapter, "asyncCreateImport", streamMock, callbackMock);

        // When
        RequestExecution retrieved = adapter.asyncCreate(streamMock, callbackMock);

        // Then
        assertNotNull(retrieved);
        verifyPrivate(adapter).invoke("asyncCreateImport", eq(streamMock), eq(callbackMock));
        assertSame(retrieved, requestExecutionMock);
    }

    @Test/*(timeOut = 2000)*/
    public void should_execute_create_operation_asynchronously() throws Exception {

        // Given
        Object resultMock = PowerMockito.mock(Object.class);
        ImportTaskRequestAdapter requestAdapterSpy = PowerMockito.spy(new ImportTaskRequestAdapter(sessionStorageMock));

        PowerMockito.whenNew(MultivaluedHashMap.class).withNoArguments().thenReturn(mapMock);
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(
                buildRequest(
                        eq(sessionStorageMock),
                        eq(State.class),
                        eq(new String[]{"/import"})))
                .thenReturn(requestStateDtoMock);

        InOrder inOrder = Mockito.inOrder(requestStateDtoMock);

        PowerMockito.doReturn(requestBuilderMock).when(requestStateDtoMock).setContentType("application/zip");
        PowerMockito.doReturn(requestBuilderMock).when(requestBuilderMock).addParams(mapMock);
        PowerMockito.doReturn(operationResultStateDtoMock).when(requestStateDtoMock).post(fileMock);
        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultStateDtoMock);

        // When
        RequestExecution retrieved = requestAdapterSpy.asyncCreate(fileMock, callbackMock);

        // Then
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(resultMock);

        PowerMockito.verifyStatic(times(1));
        JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(State.class),
                eq(new String[]{"/import"}));

        Thread.sleep(500);

        Mockito.verify(callbackMock, times(1)).execute(operationResultStateDtoMock);

        inOrder.verify(requestStateDtoMock, times(1)).setContentType("application/zip");
        inOrder.verify(requestStateDtoMock, times(1)).post(fileMock);

        Mockito.verifyNoMoreInteractions(requestStateDtoMock);
        Mockito.verifyNoMoreInteractions(callbackMock);
    }

    @Test
    public void should_create_resource_and_return_proper_op_result() throws Exception {

        // Given
        ImportTaskRequestAdapter requestAdapterSpy = PowerMockito.spy(new ImportTaskRequestAdapter(sessionStorageMock));

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(
                buildRequest(
                        eq(sessionStorageMock),
                        eq(State.class),
                        eq(new String[]{"/import"}),
                        any(DefaultErrorHandler.class)))
                .thenReturn(requestStateDtoMock);

        PowerMockito.doReturn(requestBuilderMock).when(requestStateDtoMock).setContentType("application/zip");
        PowerMockito.doReturn(requestBuilderMock).when(requestBuilderMock).addParams(mapMock);
        PowerMockito.doReturn(operationResultStateDtoMock).when(requestStateDtoMock).post(fileMock);

        InOrder inOrder = Mockito.inOrder(requestStateDtoMock);

        // When
        OperationResult<State> retrieved = requestAdapterSpy.create(fileMock);

        // Then
        Assert.assertSame(retrieved, operationResultStateDtoMock);

        PowerMockito.verifyStatic(times(1));
        JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(State.class),
                eq(new String[]{"/import"}),
                any(DefaultErrorHandler.class));

        inOrder.verify(requestStateDtoMock, times(1)).setContentType("application/zip");
        inOrder.verify(requestStateDtoMock, times(1)).post(fileMock);
    }

    @AfterMethod
    public void before() {
        reset(sessionStorageMock, requestStateDtoMock, operationResultStateDtoMock, callbackMock,
                mapMock, requestBuilderMock);
    }
}