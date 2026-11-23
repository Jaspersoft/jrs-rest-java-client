package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.doReturn;

/**
 * Unit tests for {@link OperationResult}
 */
public class OperationResultTest {

    private Response responseMock;

    @BeforeMethod
    public void before() {
        responseMock = Mockito.mock(Response.class);
    }

    @Test
    public void should_return_string_serializedContent() {
        Mockito.when(responseMock.readEntity(String.class)).thenReturn("serializedContent");
        WithEntityOperationResult<String> operationResult =
                new WithEntityOperationResult<>(responseMock, String.class);

        Object retrieved = operationResult.getSerializedContent();

        Assert.assertTrue(instanceOf(String.class).matches(retrieved));
        Assert.assertEquals(retrieved, "serializedContent");
    }

    @Test
    public void should_return_server_status_code() {
        doReturn(200).when(responseMock).getStatus();
        NullEntityOperationResult<String> operationResult =
                new NullEntityOperationResult<>(responseMock, String.class);

        int retrieved = operationResult.getResponseStatus();

        Assert.assertEquals(retrieved, 200);
    }

    @Test
    public void should_return_null_entity_because_of_exception_while_reading_an_serializedContent() {
        Mockito.when(responseMock.readEntity(String.class)).thenThrow(new ProcessingException("msg"));
        WithEntityOperationResult<String> operationResult =
                new WithEntityOperationResult<>(responseMock, String.class);

        Object retrieved = operationResult.getSerializedContent();

        Assert.assertNull(retrieved);
    }

    @Test
    public void should_return_null_entity_when_fail_to_read_entity() {
        Mockito.when(responseMock.readEntity(Void.class)).thenThrow(new RuntimeException());
        OperationResult<Void> result = new OperationResult<>(responseMock, Void.class) {};

        Void entity = result.getEntity();

        Assert.assertNull(entity);
    }

    @Test
    public void should_return_passed_response_instance() {
        OperationResult<Void> result = new OperationResult<>(responseMock, Void.class) {};

        Assert.assertSame(result.getResponse(), responseMock);
    }

    @Test
    public void should_return_passed_entity_class() {
        OperationResult<Void> result = new OperationResult<>(responseMock, Void.class) {};

        Assert.assertEquals(result.getEntityClass(), Void.class);
    }

    @AfterMethod
    public void after() {
        responseMock = null;
    }
}
