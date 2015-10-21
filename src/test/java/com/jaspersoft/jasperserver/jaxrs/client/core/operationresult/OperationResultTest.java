package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.powermock.api.mockito.PowerMockito.doReturn;

/**
 * Unit tests for {@link OperationResult}
 */
@PrepareForTest(Response.class)
public class OperationResultTest extends PowerMockTestCase {

    private Response responseMock;

    @BeforeMethod
    public void before() {
        responseMock = Mockito.mock(Response.class);
    }

    @Test
    public void should_return_string_serializedContent() {

        // Given
        Mockito.when(responseMock.readEntity(String.class)).thenReturn("serializedContent");
        WithEntityOperationResult<String> operationResult =
                new WithEntityOperationResult<String>(responseMock, String.class);

        // When
        Object retrieved = operationResult.getSerializedContent();

        // Then
        Assert.assertTrue(instanceOf(String.class).matches(retrieved));
        Assert.assertEquals(retrieved, "serializedContent");
    }


    @Test
    public void should_return_server_status_code() {

        // Given
        doReturn(200).when(responseMock).getStatus();

        NullEntityOperationResult operationResult =
                new NullEntityOperationResult(responseMock, String.class);

        // When
        int retrieved = operationResult.getResponseStatus();

        // Then
        Assert.assertEquals(retrieved, 200);
    }


    @Test
    public void should_return_null_entity_because_of_exception_while_reading_an_serializedContent() {

        // Given
        Mockito.when(responseMock.readEntity(String.class)).thenThrow(new ProcessingException("msg"));
        WithEntityOperationResult<String> operationResult =
                new WithEntityOperationResult<String>(responseMock, String.class);

        // When
        Object retrieved = operationResult.getSerializedContent();

        // Then
        Assert.assertNull(retrieved);
    }

    @Test
    public void should_return_null_entity_when_fail_to_read_entity() {

        // Given
        Mockito.when(responseMock.readEntity(Void.class)).thenThrow(new RuntimeException());
        OperationResult<Void> result = new OperationResult<Void>(responseMock, Void.class) {
        };

        // When
        Void entity = result.getEntity();

        // Then
        Assert.assertNull(entity);
    }

    @Test
    public void should_return_passed_response_instance() {
// When
        OperationResult<Void> result = new OperationResult<Void>(responseMock, Void.class) {
        };
        // Then
        Assert.assertSame(result.getResponse(), responseMock);
    }

    @Test
    public void should_return_passed_entity_class() {
// When
        OperationResult<Void> result = new OperationResult<Void>(responseMock, Void.class) {
        };
        // Then
        Assert.assertEquals(result.getEntityClass(), Void.class);
    }

    @AfterMethod
    public void after() {
        responseMock = null;
    }
}