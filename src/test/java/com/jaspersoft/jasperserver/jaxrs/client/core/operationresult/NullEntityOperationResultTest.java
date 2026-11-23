package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Unit test for {@link NullEntityOperationResult}
 */
@SuppressWarnings("unchecked")
public class NullEntityOperationResultTest {

    @Mock
    private Response responseMock;
    @Mock
    private GenericType<Object> genericTypeMock;
    @Mock
    private DefaultErrorHandler errorHandlerMock;

    @BeforeMethod
    public void before() {
        openMocks(this);
        Mockito.doReturn(Object.class).when(genericTypeMock).getRawType();
    }

    @Test
    public void should_pass_params_to_parent_class() {
        NullEntityOperationResult<Class> operationResult = new NullEntityOperationResult<>(responseMock, Class.class);

        assertEquals(Class.class, operationResult.getEntityClass());
        assertEquals(responseMock, operationResult.getResponse());
    }

    @Test
    public void should_set_correct_internal_state() {
        NullEntityOperationResult<Class> operationResult = new NullEntityOperationResult<>(responseMock, Class.class);

        assertEquals(responseMock, operationResult.getResponse());
        assertEquals(Class.class, operationResult.getEntityClass());
        assertNull(operationResult.getEntity());
        assertNull(operationResult.getSerializedContent());
    }

    @Test
    public void should_set_correct_response_class_and_errorHandler() {
        NullEntityOperationResult<Class> operationResult = new NullEntityOperationResult<>(responseMock, Class.class, errorHandlerMock);

        assertEquals(responseMock, operationResult.getResponse());
        assertEquals(Class.class, operationResult.getEntityClass());
    }

    @Test
    public void should_set_correct_response_and_genericType() {
        NullEntityOperationResult<Object> operationResult = new NullEntityOperationResult<>(responseMock, genericTypeMock);

        assertEquals(responseMock, operationResult.getResponse());
        assertEquals(Object.class, operationResult.getEntityClass());
    }

    @Test
    public void should_set_correct_response_genericType_and_errorHandler() {
        NullEntityOperationResult<Object> operationResult = new NullEntityOperationResult<>(responseMock, genericTypeMock, errorHandlerMock);

        assertEquals(responseMock, operationResult.getResponse());
        assertEquals(Object.class, operationResult.getEntityClass());
    }

    @Test
    public void should_return_null() {
        NullEntityOperationResult<Class> operationResult = new NullEntityOperationResult<>(responseMock, Class.class);

        assertNull(operationResult.getEntity());
    }

    @Test
    public void should_handle_response() {
        Mockito.doNothing().when(errorHandlerMock).handleError(responseMock);
        NullEntityOperationResult<Class> operationResult = new NullEntityOperationResult<>(responseMock, Class.class, errorHandlerMock);

        operationResult.getEntity();

        Mockito.verify(errorHandlerMock).handleError(responseMock);
    }

    @AfterMethod
    public void after() {
        Mockito.reset(responseMock);
    }
}