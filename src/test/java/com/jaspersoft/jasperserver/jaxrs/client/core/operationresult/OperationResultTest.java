package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Unit tests for {@link OperationResult}
 */
@SuppressWarnings("unchecked")
public class OperationResultTest {

    private Response responseMock;

    @BeforeMethod
    public void before() {
        responseMock = Mockito.mock(Response.class);
    }

    @Test
    public void should_return_string_serializedContent() {

        /** Given **/
        Mockito.when(responseMock.readEntity(String.class)).thenReturn("serializedContent");
        WithEntityOperationResult operationResult =
                new WithEntityOperationResult(responseMock, String.class);

        /** When **/
        Object retrieved = operationResult.getSerializedContent();

        /** Then **/
        Assert.assertTrue(instanceOf(String.class).matches(retrieved));
        Assert.assertEquals(retrieved, "serializedContent");
    }


    @Test
    public void should_return_null_entity_because_of_exception_while_reading_an_serializedContent() {

        /** Given **/
        Mockito.when(responseMock.readEntity(String.class)).thenThrow(new ProcessingException("msg"));
        WithEntityOperationResult operationResult =
                new WithEntityOperationResult(responseMock, String.class);

        /** When **/
        Object retrieved = operationResult.getSerializedContent();

        /** Then **/
        Assert.assertNull(retrieved);
    }

    @Test
    public void should_return_null_entity_when_fail_to_read_entity() {

        /** Given **/
        Mockito.when(responseMock.readEntity(Void.class)).thenThrow(new RuntimeException());
        OperationResult<Void> result = new OperationResult<Void>(responseMock, Void.class) {};

        /** When **/
        Void entity = result.getEntity();

        /** Then **/
        Assert.assertNull(entity);
    }

    @Test
    public void should_return_passed_response_instance() {
        OperationResult<Void> result = new OperationResult<Void>(responseMock, Void.class) {};
        Assert.assertSame(result.getResponse(), responseMock);
    }

    @Test
    public void should_return_passed_entity_class() {
        OperationResult<Void> result = new OperationResult<Void>(responseMock, Void.class) {};
        Assert.assertEquals(result.getEntityClass(), Void.class);
    }

    @AfterMethod
    public void after() {
        responseMock = null;
        //testableClientResource = null;
    }
}