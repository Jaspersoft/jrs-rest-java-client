package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.jaxrs.client.core.support.TestableClientResource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit test for {@link OperationResultFactoryImpl}
 */
public class OperationResultFactoryImplTest {

    @Mock
    private Response responseMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_return_proper_OperationResult_object() {
        OperationResult<TestableClientResource> retrieved =
                new OperationResultFactoryImpl()
                        .getOperationResult(responseMock, TestableClientResource.class);

        assertNotNull(retrieved);
        assertTrue(instanceOf(NullEntityOperationResult.class).matches(retrieved));
    }

    @AfterMethod
    public void after() {
        Mockito.reset(responseMock);
    }
}