package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

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
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportTaskRequestAdapter}
 *
 * @author Alexander Krasnyanskiy
 */
@PrepareForTest({JerseyRequest.class, ImportTaskRequestAdapter.class})
public class ImportTaskRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private File fileMock;

    @Mock
    private SessionStorage storageMock;

    @Mock
    private JerseyRequest<StateDto> requestStateDtoMock;

    @Mock
    private OperationResult<StateDto> operationResultStateDtoMock;

    @Mock
    private Callback<OperationResult<StateDto>, Object> callbackMock;

    @BeforeMethod
    public void after() {
        initMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_add_param_to_private_field() throws IllegalAccessException {

        // When
        ImportTaskRequestAdapter expected = spy(new ImportTaskRequestAdapter(storageMock));
        ImportTaskRequestAdapter retrieved = expected.parameter(ImportParameter.INCLUDE_ACCESS_EVENTS, Boolean.TRUE);

        Field field = field(ImportTaskRequestAdapter.class, "params");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) field.get(retrieved);

        // Than
        verify(expected, times(1)).parameter(ImportParameter.INCLUDE_ACCESS_EVENTS, Boolean.TRUE);
        assertSame(retrieved, expected);

        List<String> booleanValue = retrievedParams.get(ImportParameter.INCLUDE_ACCESS_EVENTS.getParamName());
        Boolean val = Boolean.valueOf(booleanValue.get(0));

        assertTrue(val);
    }

    @Test(testName = "create_with_File_param")
    public void should_invoke_private_method_and_return_op_result() throws Exception {

        // Given
        ImportTaskRequestAdapter adapter = PowerMockito.spy(new ImportTaskRequestAdapter(storageMock));
        doReturn(operationResultStateDtoMock).when(adapter, "createImport", fileMock);

        // When
        OperationResult<StateDto> retrieved = adapter.create(fileMock);

        // Than
        verifyPrivate(adapter).invoke("createImport", eq(fileMock));
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultStateDtoMock);
    }

    @Test(testName = "create_with_InputStream_param")
    public void should_pass_InputStream_param_and_invoke_private_method() throws Exception {

        InputStream streamMock = mock(InputStream.class);

        // Given
        ImportTaskRequestAdapter adapter = PowerMockito.spy(new ImportTaskRequestAdapter(storageMock));
        doReturn(operationResultStateDtoMock).when(adapter, "createImport", streamMock);

        // When
        OperationResult<StateDto> retrieved = adapter.create(streamMock);

        // Than
        verifyPrivate(adapter).invoke("createImport", eq(streamMock));
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultStateDtoMock);
    }

    @Test(testName = "asyncCreate_with_File_param")
    public void should_invoke_private_method_with_File_parameter_and_return_RequestExecution_object() throws Exception {

        // Given
        RequestExecution requestExecutionMock = mock(RequestExecution.class);
        ImportTaskRequestAdapter adapter = PowerMockito.spy(new ImportTaskRequestAdapter(storageMock));
        doReturn(requestExecutionMock).when(adapter, "asyncCreateImport", fileMock, callbackMock);

        // When
        RequestExecution retrieved = adapter.asyncCreate(fileMock, callbackMock);

        // Than
        assertNotNull(retrieved);
        verifyPrivate(adapter).invoke("asyncCreateImport", eq(fileMock), eq(callbackMock));
        assertSame(retrieved, requestExecutionMock);
    }

    @Test(testName = "asyncCreate_with_InputStream_param")
    public void should_invoke_private_method_with_InputStream_parameter_and_return_RequestExecution_object() throws Exception {
        // Given
        RequestExecution requestExecutionMock = mock(RequestExecution.class);
        InputStream streamMock = mock(InputStream.class);
        ImportTaskRequestAdapter adapter = PowerMockito.spy(new ImportTaskRequestAdapter(storageMock));
        doReturn(requestExecutionMock).when(adapter, "asyncCreateImport", streamMock, callbackMock);

        // When
        RequestExecution retrieved = adapter.asyncCreate(streamMock, callbackMock);

        // Than
        assertNotNull(retrieved);
        verifyPrivate(adapter).invoke("asyncCreateImport", eq(streamMock), eq(callbackMock));
        assertSame(retrieved, requestExecutionMock);
    }

    @AfterMethod
    public void before() {
        reset(storageMock, requestStateDtoMock, operationResultStateDtoMock, callbackMock);
    }
}