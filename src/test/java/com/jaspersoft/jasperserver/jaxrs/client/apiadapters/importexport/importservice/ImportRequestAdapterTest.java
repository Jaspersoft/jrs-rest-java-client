package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportRequestAdapter}
 */
@PrepareForTest({ImportRequestAdapter.class, JerseyRequest.class})
public class ImportRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private StateDto stateMock;

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<StateDto> requestStateDtoMock;

    @Mock
    private OperationResult<StateDto> operationResultStateDtoMock;

    private String taskId = "njkhfs8374";
    private String[] fakeArrayPath = new String[]{"/import", taskId, "/state"};

    @BeforeMethod
    public void after() {
        initMocks(this);
    }

    @Test(testName = "constructor")
    public void should_pass_corresponding_params_to_the_constructor_and_invoke_parent_class_constructor_with_these_params()
            throws IllegalAccessException {
        ImportRequestAdapter adapter = new ImportRequestAdapter(sessionStorageMock, taskId);
        SessionStorage retrievedSessionStorage = adapter.getSessionStorage();

        Field field = field(ImportRequestAdapter.class, "taskId");
        Object retrievedField = field.get(adapter);

        assertSame(retrievedField, taskId);
        assertEquals(retrievedSessionStorage, sessionStorageMock);
    }

    @Test(testName = "state")
    public void should_return_proper_OperationResult_object() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(StateDto.class), eq(fakeArrayPath), any(DefaultErrorHandler.class))).thenReturn(requestStateDtoMock);
        when(requestStateDtoMock.get()).thenReturn(operationResultStateDtoMock);

        // When
        ImportRequestAdapter adapter = new ImportRequestAdapter(sessionStorageMock, taskId);
        OperationResult<StateDto> opResult = adapter.state();

        // Then
        assertSame(opResult, operationResultStateDtoMock);
    }

    @AfterMethod
    public void before() {
        reset(sessionStorageMock, requestStateDtoMock, operationResultStateDtoMock, stateMock);
    }
}