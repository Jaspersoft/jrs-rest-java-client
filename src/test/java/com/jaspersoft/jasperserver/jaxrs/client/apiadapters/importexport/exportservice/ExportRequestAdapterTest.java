package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ExportFailedException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.lang.reflect.Field;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter}
 */
@PrepareForTest({ExportRequestAdapter.class, JerseyRequest.class})
public class ExportRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private StateDto stateMock;

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<StateDto> requestStateDtoMock;

    @Mock
    private JerseyRequest<InputStream> requestInputStreamMock;

    @Mock
    private OperationResult<StateDto> operationResultStateDtoMock;

    @Mock
    private OperationResult<InputStream> operationResultInputStreamMock;

    private ExportRequestAdapter adapterSpy;
    private String taskId = "njkhfs8374";
    private String[] fakeArrayPath = new String[]{"/export", taskId, "/state"};

    @BeforeMethod
    public void after() {
        initMocks(this);
        adapterSpy = Mockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));
    }

    @Test(testName = "constructor")
    public void should_pass_corresponding_params_to_the_constructor_and_invoke_parent_class_constructor_with_these_params()
            throws IllegalAccessException {

        // When
        ExportRequestAdapter adapter = new ExportRequestAdapter(sessionStorageMock, taskId);
        SessionStorage retrievedSessionStorage = adapter.getSessionStorage();

        Field field = field(ExportRequestAdapter.class, "taskId");
        Object retrievedField = field.get(adapter);

        // Than
        assertSame(retrievedField, taskId);
        assertEquals(retrievedSessionStorage, sessionStorageMock);
    }

    @Test(testName = "state")
    public void should_return_proper_OperationResult_object() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(sessionStorageMock, StateDto.class, fakeArrayPath)).thenReturn(requestStateDtoMock);
        when(requestStateDtoMock.get()).thenReturn(operationResultStateDtoMock);

        // When
        ExportRequestAdapter adapter = new ExportRequestAdapter(sessionStorageMock, taskId);
        OperationResult<StateDto> opResult = adapter.state();

        // Then
        assertSame(opResult, operationResultStateDtoMock);
    }

    @Test(testName = "fetch")
    public void should_retrieve_streamed_OperationResult_object_when_status_is_finished() {

        /**
         * check this link out
         * {@link http://community.jaspersoft.com/documentation/jasperreports-server-web-services-guide/v56/checking-export-state}
         * for phase names
         */
        // Given
        mockStatic(JerseyRequest.class);
        doReturn(operationResultStateDtoMock).when(adapterSpy).state();
        doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        doReturn("finished").when(stateMock).getPhase();

        when(JerseyRequest.buildRequest(sessionStorageMock, InputStream.class,
                new String[]{"/export", taskId, "/exportFile"})).thenReturn(requestInputStreamMock);

        doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();

        // When
        OperationResult<InputStream> retrieved = adapterSpy.fetch();

        // Than
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultInputStreamMock);
    }

    @Test(testName = "fetch", expectedExceptions = ExportFailedException.class)
    public void should_retrieve_streamed_OperationResult_object_when_status_is_failed() {

        // Given
        mockStatic(JerseyRequest.class);
        doReturn(operationResultStateDtoMock).when(adapterSpy).state();
        doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        doReturn("inprogress").doReturn("failed").when(stateMock).getPhase();

        when(JerseyRequest.buildRequest(sessionStorageMock, InputStream.class,
                new String[]{"/export", taskId, "/exportFile"})).thenReturn(requestInputStreamMock);

        doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();

        // When
        OperationResult<InputStream> retrieved = adapterSpy.fetch();

        // Than
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultInputStreamMock);
    }

    @Test(testName = "fetch", expectedExceptions = ExportFailedException.class)
    public void should_retrieve_streamed_OperationResult_object_when_status_is_failed_but_state_has_error_descriptor() {

        // Given
        mockStatic(JerseyRequest.class);
        doReturn(operationResultStateDtoMock).when(adapterSpy).state();
        doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        doReturn("inprogress").doReturn("failed").when(stateMock).getPhase();
        doReturn(new ErrorDescriptor()).when(stateMock).getErrorDescriptor();

        when(JerseyRequest.buildRequest(sessionStorageMock, InputStream.class,
                new String[]{"/export", taskId, "/exportFile"})).thenReturn(requestInputStreamMock);

        doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();

        // When
        OperationResult<InputStream> retrieved = adapterSpy.fetch();

        // Than
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultInputStreamMock);
    }

    @Test(testName = "fetch", timeOut = 600)
    public void should_retrieve_streamed_OperationResult_object_when_status_is_failed_() {

        // Given
        mockStatic(JerseyRequest.class);
        doReturn(operationResultStateDtoMock).when(adapterSpy).state();
        doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        doReturn("inprogress").doReturn("inprogress").doReturn("finished").when(stateMock).getPhase();

        when(JerseyRequest.buildRequest(sessionStorageMock, InputStream.class,
                new String[]{"/export", taskId, "/exportFile"})).thenReturn(requestInputStreamMock);

        doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();

        // When
        OperationResult<InputStream> retrieved = adapterSpy.fetch();

        // Than
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultInputStreamMock);
    }

    @AfterMethod
    public void before() {
        reset(sessionStorageMock, requestStateDtoMock, operationResultStateDtoMock, stateMock);
    }
}