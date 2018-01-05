package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.inputcontrols.InputControlStateListWrapper;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
@PrepareForTest({InputControlsValuesAdapter.class, JerseyRequest.class, OperationResult.class})
public class InputControlsValuesAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<InputControlStateListWrapper> requestMock;
    @Mock
    private OperationResult<InputControlStateListWrapper> operationResultMock;

    private String uri = "uri";

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        // Given
        InputControlsValuesAdapter adapter = new InputControlsValuesAdapter(sessionStorageMock, uri);
        // When
        SessionStorage retrieved = adapter.getSessionStorage();
        // Then
        assertSame(retrieved, sessionStorageMock);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        // When
        new InputControlsValuesAdapter(null, uri);
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_an_exception_when_uri_is_null() {
        // When
        new InputControlsValuesAdapter(sessionStorageMock, null);
        // Then
        // should be thrown an exception
    }

    @Test
    public void should_return_proper_operation_result_when_invoke_get() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                        eq(sessionStorageMock),
                        eq(InputControlStateListWrapper.class),
                        eq(new String[]{"reports", uri, "inputControls", "values"}))
        ).thenReturn(requestMock);

        doReturn(operationResultMock)
                .when(requestMock)
                .get();

        InputControlsValuesAdapter adapterSpy = spy(new InputControlsValuesAdapter(sessionStorageMock, uri));

        // When
        OperationResult<InputControlStateListWrapper> retrieved = adapterSpy.get();

        // Then
        verifyStatic();
        buildRequest(
                eq(sessionStorageMock),
                eq(InputControlStateListWrapper.class),
                eq(new String[]{"reports", uri, "inputControls", "values"}));

        Mockito.verify(requestMock).get();
        assertEquals(Whitebox.getInternalState(adapterSpy, "containerUri"), uri);
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_invoke_use_cashed_data() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                        eq(sessionStorageMock),
                        eq(InputControlStateListWrapper.class),
                        eq(new String[]{"reports", uri, "inputControls", "values"}))
        ).thenReturn(requestMock);

        doReturn(requestMock)
                .when(requestMock)
                .addParam("freshData", "true");

        doReturn(operationResultMock)
                .when(requestMock)
                .get();

        InputControlsValuesAdapter adapterSpy = spy(new InputControlsValuesAdapter(sessionStorageMock, uri));
        adapterSpy.useCashedData(false);

        // When
        OperationResult<InputControlStateListWrapper> retrieved = adapterSpy.get();

        // Then
        verifyStatic();
        buildRequest(
                eq(sessionStorageMock),
                eq(InputControlStateListWrapper.class),
                eq(new String[]{"reports", uri, "inputControls", "values"}));

        Mockito.verify(requestMock).get();
        Mockito.verify(requestMock).addParam("freshData", "true");
        assertEquals(Whitebox.getInternalState(adapterSpy, "useFreshData"), Boolean.TRUE);
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_proper_operation_result__when_invoke_post() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                        eq(sessionStorageMock),
                        eq(InputControlStateListWrapper.class),
                        eq(new String[]{"reports", uri, "inputControls", "param1;param2", "values"}))
        ).thenReturn(requestMock);

        doReturn(operationResultMock)
                .when(requestMock)
                .post(anyObject());

        InputControlsValuesAdapter adapterSpy = spy(new InputControlsValuesAdapter(sessionStorageMock, uri));

        // When
        OperationResult<InputControlStateListWrapper> retrieved = adapterSpy
                .parameter("param1", "value1")
                .parameter("param2", "value2", "value3")
                .run();

        // Then
        verifyStatic();
        buildRequest(
                eq(sessionStorageMock),
                eq(InputControlStateListWrapper.class),
                eq(new String[]{"reports", uri, "inputControls", "param1;param2", "values"}));

        Mockito.verify(requestMock).post(anyObject());
        Mockito.verify(requestMock).post(anyObject());
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_invoke_include_full_structure() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                        eq(sessionStorageMock),
                        eq(InputControlStateListWrapper.class),
                        eq(new String[]{"reports", uri, "inputControls", "values"}))
        ).thenReturn(requestMock);

        doReturn(operationResultMock)
                .when(requestMock)
                .post(anyObject());

        InputControlsValuesAdapter adapterSpy = spy(new InputControlsValuesAdapter(sessionStorageMock, uri));

        // When
        OperationResult<InputControlStateListWrapper> retrieved = adapterSpy
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .includeFullStructure(true)
                .run();

        // Then
        verifyStatic();
        buildRequest(
                eq(sessionStorageMock),
                eq(InputControlStateListWrapper.class),
                eq(new String[]{"reports", uri, "inputControls", "values"}));

        Mockito.verify(requestMock).post(anyObject());
        assertEquals(Whitebox.getInternalState(adapterSpy, "includeFullStructure"), Boolean.TRUE);
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, operationResultMock);
    }

}