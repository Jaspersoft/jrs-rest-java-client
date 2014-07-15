package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Unit test for {@link NullEntityOperationResult}
 */
public class NullEntityOperationResultTest extends PowerMockTestCase {

    @Mock
    private Response responseMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_params_to_parent_class() {
        NullEntityOperationResult operationResult = new NullEntityOperationResult(responseMock, Class.class);
        assertEquals(Class.class, operationResult.getEntityClass());
        assertEquals(responseMock, operationResult.getResponse());
    }

    @Test
    public void should_return_null() {
        NullEntityOperationResult operationResult = new NullEntityOperationResult(responseMock, Class.class);
        assertNull(operationResult.getEntity());
    }

    @AfterMethod
    public void after() {
        Mockito.reset(responseMock);
    }
}