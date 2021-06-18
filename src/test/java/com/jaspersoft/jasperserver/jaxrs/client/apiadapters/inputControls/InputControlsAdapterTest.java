package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControl;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.LinkedList;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
@PrepareForTest({InputControlsAdapter.class, JerseyRequest.class, OperationResult.class})
public class InputControlsAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<ReportInputControlsListWrapper> requestMock;
    @Mock
    private OperationResult<ReportInputControlsListWrapper> operationResultMock;
    @Mock
    private InputControlsValuesAdapter inputControlsValuesAdapterMock;

    private String uri = "uri";

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_an_exception_when_uri_is_null() {
        // When
        new InputControlsAdapter(sessionStorageMock).container(null).get();
        // Then
        // should be thrown an exception
    }


    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        // Given
        InputControlsAdapter adapter= new InputControlsAdapter(sessionStorageMock);
        // When
        SessionStorage retrieved = adapter.getSessionStorage();
        // Then
        assertSame(retrieved, sessionStorageMock);
    }


    @Test
    public void should_return_adapter_instance() throws Exception {
        // Given
        InputControlsAdapter adapter= new InputControlsAdapter(sessionStorageMock);
        whenNew(InputControlsValuesAdapter.class).withArguments(eq(sessionStorageMock), anyString()).thenReturn(inputControlsValuesAdapterMock);
        // When
        InputControlsValuesAdapter retrieved = adapter.values();
        // Then
        assertSame(retrieved, inputControlsValuesAdapterMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        // When
        new InputControlsAdapter(null);
        // Then
        // should be thrown an exception
    }

    @Test
    public void should_return_proper_operation_result_when_invoke_get() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                        eq(sessionStorageMock),
                        eq(ReportInputControlsListWrapper.class),
                        eq(new String[]{"reports", uri, "inputControls"}))
        ).thenReturn(requestMock);

        doReturn(operationResultMock)
                .when(requestMock)
                .get();

        InputControlsAdapter adapterSpy = spy(new InputControlsAdapter(sessionStorageMock));
        adapterSpy.container(uri);

        // When
        OperationResult<ReportInputControlsListWrapper> retrieved = adapterSpy.get();

        // Then
        verifyStatic();
        buildRequest(
                eq(sessionStorageMock),
                eq(ReportInputControlsListWrapper.class),
                eq(new String[]{"reports", uri, "inputControls"}));

        Mockito.verify(requestMock).get();
        assertEquals(Whitebox.getInternalState(adapterSpy, "containerUri"), uri);
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_proper_operation_result_when_invoke_reorder() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                        eq(sessionStorageMock),
                        eq(ReportInputControlsListWrapper.class),
                        eq(new String[]{"reports", uri, "inputControls"}))
        ).thenReturn(requestMock);

        doReturn(operationResultMock)
                .when(requestMock)
                .put(anyObject());

        InputControlsAdapter adapterSpy = spy(new InputControlsAdapter(sessionStorageMock));
        adapterSpy.container(uri);

        // When
        OperationResult<ReportInputControlsListWrapper> retrieved = adapterSpy
                .reorder(new LinkedList<ReportInputControl>());

        // Then
        verifyStatic();
        buildRequest(
                eq(sessionStorageMock),
                eq(ReportInputControlsListWrapper.class),
                eq(new String[]{"reports", uri, "inputControls"}));

        Mockito.verify(requestMock).put(anyObject());
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_invoke_adding_parameter() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                        eq(sessionStorageMock),
                        eq(ReportInputControlsListWrapper.class),
                        eq(new String[]{"reports", uri, "inputControls"}))
        ).thenReturn(requestMock);

        doReturn(requestMock)
                .when(requestMock)
                .addParam("exclude", "state");
        doReturn(operationResultMock)
                .when(requestMock)
                .get();

        InputControlsAdapter adapterSpy = spy(new InputControlsAdapter(sessionStorageMock));
        adapterSpy.container(uri).excludeState(true);

        // When
        OperationResult<ReportInputControlsListWrapper> retrieved = adapterSpy.get();

        // Then
        verifyStatic();
        buildRequest(
                eq(sessionStorageMock),
                eq(ReportInputControlsListWrapper.class),
                eq(new String[]{"reports", uri, "inputControls"}));

        Mockito.verify(requestMock).get();
        Mockito.verify(requestMock).addParam("exclude", "state");
        assertEquals(Whitebox.getInternalState(adapterSpy, "containerUri"), uri);
        assertEquals(Whitebox.getInternalState(adapterSpy, "excludeState"), Boolean.TRUE);
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, operationResultMock, inputControlsValuesAdapterMock);
    }

}