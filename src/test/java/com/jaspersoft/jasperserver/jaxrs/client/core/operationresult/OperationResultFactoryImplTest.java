package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.dto.resources.ClientDashboard;
import com.jaspersoft.jasperserver.dto.resources.ClientFolder;
import com.jaspersoft.jasperserver.dto.resources.ClientQuery;
import com.jaspersoft.jasperserver.dto.resources.ClientVirtualDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourcesTypeResolverUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.support.TestableClientResource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit test for {@link com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl}
 */
@PrepareForTest({OperationResultFactoryImpl.class, WithEntityOperationResult.class, ResourcesTypeResolverUtil.class})
public class OperationResultFactoryImplTest extends PowerMockTestCase {

    @Mock
    private Response responseMock;

    @Mock
    private OperationResult<ClientQuery> operationResultMock;

    @Mock
    private WithEntityOperationResult withEntityOperationResultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl#getOperationResult(javax.ws.rs.core.Response, Class)}
     */
    public void should_return_proper_OperationResult_object() {
        OperationResult<TestableClientResource> retrieved = new OperationResultFactoryImpl().getOperationResult(responseMock, TestableClientResource.class);
        assertNotNull(retrieved);
        assertTrue(instanceOf(NullEntityOperationResult.class).matches(retrieved));
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl#getOperationResult(javax.ws.rs.core.Response, Class)}
     */
    public void should_invoke_all_private_logic_of_method() throws Exception {

        /* Given */
        OperationResultFactoryImpl factorySpy = spy(new OperationResultFactoryImpl());
        doReturn(true).when(factorySpy, "isClientResource", ClientQuery.class);
        doReturn(ClientQuery.class).when(factorySpy, "getSpecificResourceType", responseMock);
        doReturn(operationResultMock).when(factorySpy, "getAppropriateOperationResultInstance", responseMock, ClientQuery.class);

        /* When */
        OperationResult<ClientQuery> operationResult = factorySpy.getOperationResult(responseMock, ClientQuery.class);

        /* Than */
        assertNotNull(operationResult);
        verifyPrivate(factorySpy, times(1)).invoke("isClientResource", ClientQuery.class);
        verifyPrivate(factorySpy, times(1)).invoke("getSpecificResourceType", responseMock);
        verifyPrivate(factorySpy, times(1)).invoke("getAppropriateOperationResultInstance", responseMock, ClientQuery.class);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResultFactoryImpl#getAppropriateOperationResultInstance(javax.ws.rs.core.Response, Class)}
     */
    public void should_return_result_with_entity_when_response_has_entity_when_invoking_getAppropriateOperationResultInstance_method() throws Exception {

        /* Given */
        OperationResultFactoryImpl factorySpy = spy(new OperationResultFactoryImpl());

        doReturn(true).when(factorySpy, "isClientResource", ClientQuery.class);
        doReturn(ClientQuery.class).when(factorySpy, "getSpecificResourceType", responseMock);
        doReturn(true).when(responseMock).hasEntity();

        whenNew(WithEntityOperationResult.class).withArguments(responseMock, ClientQuery.class).thenReturn(withEntityOperationResultMock);

        /* When */
        OperationResult<ClientQuery> retrievedOperationResult = factorySpy.getOperationResult(responseMock, ClientQuery.class);

        /* Than */
        assertNotNull(retrievedOperationResult);
        assertSame(retrievedOperationResult, withEntityOperationResultMock);

        verify(responseMock).hasEntity();
        verifyNew(WithEntityOperationResult.class, times(1)).withArguments(responseMock, ClientQuery.class);
    }

    @Test(enabled = false)
    public void test() throws Exception {

        /* Given */
        mockStatic(ResourcesTypeResolverUtil.class);
        //PowerMockito.when(ResourcesTypeResolverUtil.getClassForMime("abc")).thenReturn(Class.class);
        //EasyMock.expect(ResourcesTypeResolverUtil.getClassForMime("abc")).andReturn(ClientFolder.class);

        OperationResultFactoryImpl factorySpy = spy(new OperationResultFactoryImpl());
        doReturn(true).when(factorySpy, "isClientResource", ClientFolder.class);
        doReturn("application/json").when(responseMock).getHeaderString("Content-Type");
        doReturn(operationResultMock).when(factorySpy, "getAppropriateOperationResultInstance", responseMock, ClientFolder.class);

        /* When */
        OperationResult<ClientFolder> operationResult = factorySpy.getOperationResult(responseMock, ClientFolder.class);

        /* Than */
        assertNotNull(operationResult);
        verifyPrivate(factorySpy, times(1)).invoke("isClientResource", ClientFolder.class);
        verifyPrivate(factorySpy, times(1)).invoke("getAppropriateOperationResultInstance", responseMock, ClientFolder.class);
    }

    @Test
    public void should_invoke_private_method_getSpecificResourceType() throws Exception {

        /* When */

        Class dashboardClass = ClientDashboard.class;

        PowerMockito.mockStatic(ResourcesTypeResolverUtil.class);
        PowerMockito.when(ResourcesTypeResolverUtil.getClassForMime(anyString())).thenReturn(dashboardClass);

        OperationResultFactoryImpl factorySpy = PowerMockito.spy(new OperationResultFactoryImpl());
        PowerMockito.doReturn(true).when(factorySpy, "isClientResource", ClientDashboard.class);
//        PowerMockito.doReturn(ClientDashboard.class).when(factorySpy, "getSpecificResourceType", responseMock);
        PowerMockito.doReturn("json").when(responseMock).getHeaderString("Content-Type");

        /* Indirect call of private method getSpecificResourceType() */
        OperationResult<ClientDashboard> retrievedResult = factorySpy.getOperationResult(responseMock, ClientDashboard.class);

        /* Than */
        assertNotNull(retrievedResult);
//        PowerMockito.verifyPrivate(factorySpy, times(1)).invoke("getSpecificResourceType", responseMock);

        PowerMockito.verifyStatic(times(1));
        ResourcesTypeResolverUtil.getClassForMime(anyString());
    }

    @Test
    public void should_() {
        OperationResultFactoryImpl factory = new OperationResultFactoryImpl();
        OperationResult<ClientVirtualDataSource> retrieved = factory.getOperationResult(responseMock, ClientVirtualDataSource.class);

    }

    @AfterMethod
    public void after() {
        Mockito.reset(responseMock, withEntityOperationResultMock, operationResultMock);
    }
}