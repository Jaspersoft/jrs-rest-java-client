package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.List;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

/**
 * Unit tests for {@link ServerBatchAttributeAdapter}
 */
@SuppressWarnings({"unchecked", "deprecation"})
@PrepareForTest({JerseyRequest.class})
public class ServerBatchAttributeAdapterTest extends PowerMockTestCase {

    private SessionStorage sessionStorageMock;
    private JerseyRequest<HypermediaAttributesListWrapper> jerseyRequestMock;
    private OperationResult<HypermediaAttributesListWrapper> operationResultMock;
    private RequestBuilder<HypermediaAttributesListWrapper> builderMock;

    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        jerseyRequestMock = mock(JerseyRequest.class);
        operationResultMock = mock(OperationResult.class);
        builderMock = mock(RequestBuilder.class);
    }

    @Test
    /**
     * for {@link ServerBatchAttributeAdapter#put(ServerAttributesListWrapper)}
     */
    public void should_return_proper_operation_result() {

        /** Given **/

        HypermediaAttributesListWrapper attributes = new HypermediaAttributesListWrapper();
        attributes.setProfileAttributes(asList(
                new HypermediaAttribute(new ClientUserAttribute().setName("max_threads").setValue("512")),
                new HypermediaAttribute(new ClientUserAttribute().setName("admin_cell_phone").setValue("03"))));

        PowerMockito.mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(
                eq(sessionStorageMock),
                eq(HypermediaAttributesListWrapper.class),
                eq(new String[]{"/attributes"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

        Mockito.when(jerseyRequestMock.put(attributes)).thenReturn(operationResultMock);


        /** When **/
        ServerBatchAttributeAdapter adapter = new ServerBatchAttributeAdapter(sessionStorageMock);
        OperationResult<HypermediaAttributesListWrapper> retrieved = adapter.createOrUpdate(attributes);


        /** Then **/
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved, operationResultMock);
        PowerMockito.verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(HypermediaAttributesListWrapper.class),
                eq(new String[]{"/attributes"}),
                any(DefaultErrorHandler.class));
    }

    @Test
    /**
     * for {@link ServerBatchAttributeAdapter#put(ServerAttributesListWrapper)}
     */
    public void should_delete_server_attributes() {

        /** Given **/
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<String, String>();
        map.add("key", "value");

        PowerMockito.mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(
                eq(sessionStorageMock),
                eq(HypermediaAttributesListWrapper.class),
                eq(new String[]{"/attributes"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

        Mockito.when(jerseyRequestMock.addParams(map)).thenReturn(builderMock);
        Mockito.when(builderMock.delete()).thenReturn(operationResultMock);


        /** When **/
        ServerBatchAttributeAdapter adapter = new ServerBatchAttributeAdapter(sessionStorageMock);
        Whitebox.setInternalState(adapter, "params", map);
        OperationResult<HypermediaAttributesListWrapper> retrieved = adapter.delete();


        /** Then **/
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved, operationResultMock);
        PowerMockito.verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(HypermediaAttributesListWrapper.class),
                eq(new String[]{"/attributes"}),
                any(DefaultErrorHandler.class));
        Mockito.verify(jerseyRequestMock, times(1)).addParams(map);
        Mockito.verify(builderMock, times(1)).delete();
        Mockito.verifyNoMoreInteractions(jerseyRequestMock, builderMock);
    }

    @Test
    /**
     * for {@link ServerBatchAttributeAdapter#get()}
     */
    public void should_retrieve_server_attributes() {

        /** Given **/
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<String, String>();
        map.add("key", "value");

        PowerMockito.mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(
                eq(sessionStorageMock),
                eq(HypermediaAttributesListWrapper.class),
                eq(new String[]{"/attributes"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

        Mockito.when(jerseyRequestMock.addParams(map)).thenReturn(builderMock);
        Mockito.when(builderMock.get()).thenReturn(operationResultMock);


        /** When **/
        ServerBatchAttributeAdapter adapter = new ServerBatchAttributeAdapter(sessionStorageMock);
        Whitebox.setInternalState(adapter, "params", map);
        OperationResult<HypermediaAttributesListWrapper> retrieved = adapter.get();


        /** Then **/
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved, operationResultMock);
        PowerMockito.verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(HypermediaAttributesListWrapper.class),
                eq(new String[]{"/attributes"}),
                any(DefaultErrorHandler.class));
        Mockito.verify(jerseyRequestMock, times(1)).addParams(map);
        Mockito.verify(builderMock, times(1)).get();
        Mockito.verifyNoMoreInteractions(jerseyRequestMock, builderMock);
    }

    @Test
    public void should_set_params() {
        ServerBatchAttributeAdapter adapter = new ServerBatchAttributeAdapter(sessionStorageMock, "x", "y", "z");
        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");
        List<String> list = params.get("name");
        Assert.assertSame(list.size(), 3);
        Assert.assertTrue(list.contains("y") && list.contains("z"));
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        jerseyRequestMock = null;
        operationResultMock = null;
        builderMock = null;
    }
}