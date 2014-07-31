package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.jaxrs.client.core.support.TestableClientResource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit test for {@link com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult}
 */
public class OperationResultTest {

    @Mock
    private Response responseMock;

    @Mock
    private TestableClientResource testableClientResource;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_return_proper_entity() {

        // Given
        when(responseMock.readEntity(TestableClientResource.class))
                .thenReturn(testableClientResource);
        WithEntityOperationResult operationResult =
                new WithEntityOperationResult(responseMock, TestableClientResource.class);

        // When
        Object retrieved = operationResult.getEntity();

        // Than
        assertTrue(instanceOf(TestableClientResource.class).matches(retrieved));
        assertEquals(retrieved, testableClientResource);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_return_null_entity_because_of_exception_while_reading_an_entity() {

        // Given
        when(responseMock.readEntity(TestableClientResource.class)).thenThrow(new ProcessingException("msg"));
        WithEntityOperationResult operationResult =
                new WithEntityOperationResult(responseMock, TestableClientResource.class);

        // When
        Object retrieved = operationResult.getEntity();

        // Than
        assertNull(retrieved);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_return_string_serializedContent() {

        // Given
        when(responseMock.readEntity(String.class))
                .thenReturn("serializedContent");
        WithEntityOperationResult operationResult =
                new WithEntityOperationResult(responseMock, String.class);

        // When
        Object retrieved = operationResult.getSerializedContent();

        // Than
        assertTrue(instanceOf(String.class).matches(retrieved));
        assertEquals(retrieved, "serializedContent");
    }


    @Test
    @SuppressWarnings("unchecked")
    public void should_return_null_entity_because_of_exception_while_reading_an_serializedContent() {
        // Given
        when(responseMock.readEntity(String.class)).thenThrow(new ProcessingException("msg"));
        WithEntityOperationResult operationResult =
                new WithEntityOperationResult(responseMock, String.class);

        // When
        Object retrieved = operationResult.getSerializedContent();

        // Than
        assertNull(retrieved);
    }

    @AfterMethod
    public void after() {
        Mockito.reset(responseMock, testableClientResource);
    }
}