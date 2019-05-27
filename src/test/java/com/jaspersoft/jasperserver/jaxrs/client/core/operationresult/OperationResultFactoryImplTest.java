package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.dto.resources.ClientDashboard;
import com.jaspersoft.jasperserver.dto.resources.ClientQuery;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnail;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourcesTypeResolverUtil;
import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnailsListWrapper;
import javax.ws.rs.core.Response;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.testng.Assert.assertNotNull;

/**
 * Unit test for {@link OperationResultFactoryImpl}
 */
@PrepareForTest({
        OperationResultFactoryImpl.class,
        WithEntityOperationResult.class,
        ResourcesTypeResolverUtil.class
})
@SuppressWarnings("unchecked")
public class OperationResultFactoryImplTest extends PowerMockTestCase {

    private Response responseMock;
    private OperationResult<ClientQuery> operationResultMock;
    private WithEntityOperationResult withEntityOperationResultMock;

    @BeforeMethod
    public void before() {
        responseMock = Mockito.mock(Response.class);
        operationResultMock = Mockito.mock(OperationResult.class);
        withEntityOperationResultMock = Mockito.mock(WithEntityOperationResult.class);
    }

    @Test
    /**
     * for {@link OperationResultFactoryImpl#getOperationResult(Response, Class)}
     */
    public void should_invoke_all_private_logic_of_method() throws Exception {

        /* Given */
        OperationResultFactoryImpl factorySpy = PowerMockito.spy(new OperationResultFactoryImpl());
        PowerMockito.doReturn(true).when(factorySpy, "isClientResource", ClientQuery.class);
        PowerMockito.doReturn(ClientQuery.class).when(factorySpy, "getSpecificResourceType", responseMock);
        PowerMockito.doReturn(operationResultMock).when(factorySpy, "getAppropriateOperationResultInstance", responseMock, ClientQuery.class);

        /* When */
        OperationResult<ClientQuery> operationResult = factorySpy.getOperationResult(responseMock, ClientQuery.class);

        /* Then */
        Assert.assertNotNull(operationResult);
        PowerMockito.verifyPrivate(factorySpy, times(1)).invoke("isClientResource", ClientQuery.class);
        PowerMockito.verifyPrivate(factorySpy, times(1)).invoke("getSpecificResourceType", responseMock);
        PowerMockito.verifyPrivate(factorySpy, times(1)).invoke("getAppropriateOperationResultInstance", responseMock, ClientQuery.class);
    }

    @Test
    /**
     * for {@link OperationResultFactoryImpl#getAppropriateOperationResultInstance(Response, Class)}
     */
    public void should_return_result_with_entity_when_response_has_entity_when_invoking_getAppropriateOperationResultInstance_method() throws Exception {

        /* Given */
        OperationResultFactoryImpl factorySpy = PowerMockito.spy(new OperationResultFactoryImpl());

        PowerMockito.doReturn(true).when(factorySpy, "isClientResource", ClientQuery.class);
        PowerMockito.doReturn(ClientQuery.class).when(factorySpy, "getSpecificResourceType", responseMock);
        PowerMockito.doReturn(true).when(responseMock).hasEntity();

        PowerMockito.whenNew(WithEntityOperationResult.class).withArguments(responseMock, ClientQuery.class).thenReturn(withEntityOperationResultMock);

        /* When */
        OperationResult<ClientQuery> retrievedOperationResult = factorySpy.getOperationResult(responseMock, ClientQuery.class);

        /* Then */
        Assert.assertNotNull(retrievedOperationResult);
        Assert.assertSame(retrievedOperationResult, withEntityOperationResultMock);

        Mockito.verify(responseMock, times(1)).hasEntity();
        PowerMockito.verifyNew(WithEntityOperationResult.class, times(1)).withArguments(responseMock, ClientQuery.class);
    }


    @Test
    public void should_invoke_private_method_getSpecificResourceType() throws Exception {

        /* When */

        Class dashboardClass = ClientDashboard.class;

        PowerMockito.mockStatic(ResourcesTypeResolverUtil.class);
        PowerMockito.when(ResourcesTypeResolverUtil.getClassForMime(anyString())).thenReturn(dashboardClass);

        OperationResultFactoryImpl factorySpy = PowerMockito.spy(new OperationResultFactoryImpl());
        PowerMockito.doReturn(true).when(factorySpy, "isClientResource", ClientDashboard.class);
        PowerMockito.doReturn("json").when(responseMock).getHeaderString("Content-Type");

        /* Indirect call of private method getSpecificResourceType() */
        OperationResult<ClientDashboard> retrievedResult = factorySpy.getOperationResult(responseMock, ClientDashboard.class);

        /* Then */
        assertNotNull(retrievedResult);
        PowerMockito.verifyStatic(times(1));
        ResourcesTypeResolverUtil.getClassForMime(anyString());
    }

    @Test
    public void should_return_operation_result_with_wrapped_list_of_thumbnails() {

        /** Given **/
        Mockito.when(responseMock.hasEntity()).thenReturn(true);
        Mockito.when(responseMock.readEntity(ResourceThumbnailsListWrapper.class)).thenReturn(new ResourceThumbnailsListWrapper(asList(new ResourceThumbnail())));
        OperationResultFactoryImpl factory = new OperationResultFactoryImpl();

        /** When **/
        OperationResult<ResourceThumbnailsListWrapper> operationResult = factory.getOperationResult(responseMock, ResourceThumbnailsListWrapper.class);

        /** Then **/
        Assert.assertNotNull(operationResult);
        Mockito.verify(responseMock, times(1)).hasEntity();
        Assert.assertTrue(operationResult.getEntity().getThumbnails().size() == 1);
    }

    @Test
    public void should_return_null_entity() {
        OperationResultFactoryImpl factory = new OperationResultFactoryImpl();
        OperationResult<ResourceThumbnailsListWrapper> operationResult = factory.getOperationResult(responseMock, ResourceThumbnailsListWrapper.class);
        Assert.assertNull(operationResult.getEntity());
    }

    @Test
    public void should_return_operation_result_with_proper_class() {

        /** Given **/
        Mockito.when(responseMock.hasEntity()).thenReturn(true);
        Mockito.when(responseMock.readEntity(ClientSecureMondrianConnection.class)).thenReturn(new ClientSecureMondrianConnection());
        Mockito.when(responseMock.getHeaderString("Content-Type")).thenReturn("application/repository.secureMondrianConnection+xml");

        /** When **/
        OperationResultFactoryImpl factory = new OperationResultFactoryImpl();
        OperationResult<ClientSecureMondrianConnection> operationResult = factory.getOperationResult(responseMock, ClientSecureMondrianConnection.class);

        /** Then **/
        Assert.assertNotNull(operationResult);
        Assert.assertTrue(instanceOf(ClientSecureMondrianConnection.class).matches(operationResult.getEntity()));
    }

    @AfterMethod
    public void after() {
        responseMock = null;
        withEntityOperationResultMock = null;
        operationResultMock = null;
    }
}