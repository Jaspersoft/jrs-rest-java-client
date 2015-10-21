package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
* Unit test for {@link com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.NullEntityOperationResult}
*/

@SuppressWarnings({"AssertEqualsBetweenInconvertibleTypesTestNG", "unchecked"})
public class NullEntityOperationResultTest extends PowerMockTestCase {

    @Mock
    private Response responseMock;
    @Mock
    private GenericType genericTypeMock;
    @Mock
    private DefaultErrorHandler errorHandlerMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_params_to_parent_class() {
        // When
        NullEntityOperationResult<Class> operationResult = new NullEntityOperationResult<Class>(responseMock, Class.class);
        // Then
        assertEquals(Class.class, operationResult.getEntityClass());
        assertEquals(responseMock, operationResult.getResponse());
    }

    @Test
        public void should_set_correct_internal_state() {
        // When
        NullEntityOperationResult<Class> operationResult = new NullEntityOperationResult<Class>(responseMock, Class.class);
        // Then
        assertEquals(responseMock, Whitebox.getInternalState(operationResult, "response"));
        assertEquals(Class.class, Whitebox.getInternalState(operationResult, "entityClass"));
        assertNull(Whitebox.getInternalState(operationResult, "genericEntity"));
        assertNull(Whitebox.getInternalState(operationResult, "defaultErrorHandler"));
        assertNull(Whitebox.getInternalState(operationResult, "entity"));
        assertNull(Whitebox.getInternalState(operationResult, "serializedContent"));
    }


    @Test
        public void should_set_correct_response_class_and_errorHandler() {
        // When
        NullEntityOperationResult<Class> operationResult = new NullEntityOperationResult<Class>(responseMock, Class.class, errorHandlerMock);
        // Then
        assertEquals(responseMock, Whitebox.getInternalState(operationResult, "response"));
        assertEquals(Class.class, Whitebox.getInternalState(operationResult, "entityClass"));
        assertEquals(errorHandlerMock, Whitebox.getInternalState(operationResult, "defaultErrorHandler"));
        assertNull(Whitebox.getInternalState(operationResult, "genericEntity"));
    }

    @Test
    public void should_set_correct_response_and_genericType() {
        // When
        NullEntityOperationResult<GenericType> operationResult = new NullEntityOperationResult<GenericType>(responseMock, genericTypeMock);
        // Then
        assertEquals(responseMock, Whitebox.getInternalState(operationResult, "response"));
        assertEquals(genericTypeMock, Whitebox.getInternalState(operationResult, "genericEntity"));
    }

    @Test
    public void should_set_correct_response_genericType_and_errorHandler() {
        // When
        NullEntityOperationResult<GenericType> operationResult = new NullEntityOperationResult<GenericType>(responseMock, genericTypeMock, errorHandlerMock);
        // Then
        assertEquals(responseMock, Whitebox.getInternalState(operationResult, "response"));
        assertEquals(genericTypeMock, Whitebox.getInternalState(operationResult, "genericEntity"));
        assertEquals(errorHandlerMock, Whitebox.getInternalState(operationResult, "defaultErrorHandler"));
    }

    @Test
    public void should_return_null() {
        // When
        NullEntityOperationResult<Class> operationResult = new NullEntityOperationResult<Class>(responseMock, Class.class);
        // Then
        assertNull(operationResult.getEntity());
    }

    @Test
    public void should_handle_response() {
        // Given
        doNothing().when(errorHandlerMock).handleError(responseMock);
        // When
        NullEntityOperationResult<Class> operationResult  = new NullEntityOperationResult<Class>(responseMock, Class.class, errorHandlerMock);
        operationResult.getEntity();
        // Then
        Mockito.verify(errorHandlerMock).handleError(responseMock);
    }

    @AfterMethod
    public void after() {
        Mockito.reset(responseMock);
    }
}