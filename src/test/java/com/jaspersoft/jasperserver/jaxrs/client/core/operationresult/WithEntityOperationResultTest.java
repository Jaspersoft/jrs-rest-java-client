package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

/**
 * Unit test for {@link WithEntityOperationResult}
 */
public class WithEntityOperationResultTest {

    @Mock
    private Response responseMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_params_to_parent_class() {
        WithEntityOperationResult operationResult = new WithEntityOperationResult(responseMock, Class.class);
        assertEquals(Class.class, operationResult.getEntityClass());
        assertEquals(responseMock, operationResult.getResponse());
    }

    @AfterMethod
    public void after() {
        Mockito.reset(responseMock);
    }
}