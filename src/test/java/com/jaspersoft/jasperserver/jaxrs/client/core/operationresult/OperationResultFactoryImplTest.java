package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.dto.resources.ClientDashboard;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnail;
import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnailsListWrapper;
import jakarta.ws.rs.core.Response;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Unit test for {@link OperationResultFactoryImpl}
 */
@SuppressWarnings("unchecked")
public class OperationResultFactoryImplTest {

    private Response responseMock;
    private OperationResultFactoryImpl factory;

    @BeforeMethod
    public void before() {
        responseMock = Mockito.mock(Response.class);
        factory = new OperationResultFactoryImpl();
    }

    @Test
    public void should_return_with_entity_result_when_response_has_entity() {
        Mockito.when(responseMock.hasEntity()).thenReturn(true);
        Mockito.when(responseMock.getHeaderString("Content-Type"))
                .thenReturn("application/repository.secureMondrianConnection+xml");

        OperationResult<ClientResource> result = factory.getOperationResult(responseMock, ClientResource.class);

        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof WithEntityOperationResult);
        Assert.assertEquals(result.getEntityClass(), ClientSecureMondrianConnection.class);
    }

    @Test
    public void should_return_null_entity_result_when_response_has_no_entity() {
        Mockito.when(responseMock.hasEntity()).thenReturn(false);
        Mockito.when(responseMock.getHeaderString("Content-Type"))
                .thenReturn("application/repository.secureMondrianConnection+xml");

        OperationResult<ClientResource> result = factory.getOperationResult(responseMock, ClientResource.class);

        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof NullEntityOperationResult);
    }

    @Test
    public void should_resolve_specific_resource_type_from_content_type_header() {
        Mockito.when(responseMock.hasEntity()).thenReturn(true);
        Mockito.when(responseMock.getHeaderString("Content-Type"))
                .thenReturn("application/repository.dashboard+json");

        OperationResult<ClientResource> result = factory.getOperationResult(responseMock, ClientResource.class);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getEntityClass(), ClientDashboard.class);
    }

    @Test
    public void should_return_operation_result_with_wrapped_list_of_thumbnails() {
        Mockito.when(responseMock.hasEntity()).thenReturn(true);
        Mockito.when(responseMock.readEntity(ResourceThumbnailsListWrapper.class))
                .thenReturn(new ResourceThumbnailsListWrapper(asList(new ResourceThumbnail())));

        OperationResult<ResourceThumbnailsListWrapper> operationResult =
                factory.getOperationResult(responseMock, ResourceThumbnailsListWrapper.class);

        Assert.assertNotNull(operationResult);
        Mockito.verify(responseMock, Mockito.times(1)).hasEntity();
        Assert.assertEquals(operationResult.getEntity().getThumbnails().size(), 1);
    }

    @Test
    public void should_return_null_entity() {
        OperationResult<ResourceThumbnailsListWrapper> operationResult =
                factory.getOperationResult(responseMock, ResourceThumbnailsListWrapper.class);

        Assert.assertNull(operationResult.getEntity());
    }

    @Test
    public void should_return_operation_result_with_proper_class() {
        Mockito.when(responseMock.hasEntity()).thenReturn(true);
        Mockito.when(responseMock.readEntity(ClientSecureMondrianConnection.class))
                .thenReturn(new ClientSecureMondrianConnection());
        Mockito.when(responseMock.getHeaderString("Content-Type"))
                .thenReturn("application/repository.secureMondrianConnection+xml");

        OperationResult<ClientSecureMondrianConnection> operationResult =
                factory.getOperationResult(responseMock, ClientSecureMondrianConnection.class);

        Assert.assertNotNull(operationResult);
        Assert.assertTrue(instanceOf(ClientSecureMondrianConnection.class).matches(operationResult.getEntity()));
    }

    @AfterMethod
    public void after() {
        responseMock = null;
        factory = null;
    }
}
