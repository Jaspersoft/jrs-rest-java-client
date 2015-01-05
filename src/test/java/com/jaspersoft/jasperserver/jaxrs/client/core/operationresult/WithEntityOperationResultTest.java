package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

/**
 * Unit test for {@link WithEntityOperationResult}
 */
@SuppressWarnings("unchecked")
public class WithEntityOperationResultTest {

    @Test
    public void should_pass_params_to_parent_class() {
        Response responseMock = Mockito.mock(Response.class);
        WithEntityOperationResult operationResult = 
                new WithEntityOperationResult(responseMock, ClientUser.class);
        Assert.assertEquals(ClientUser.class, operationResult.getEntityClass());
        Assert.assertEquals(responseMock, operationResult.getResponse());
    }
}