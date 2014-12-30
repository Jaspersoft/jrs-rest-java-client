package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ServerAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ServerAttributesListWrapper;
import junit.framework.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link ServerBatchAttributeAdapter}
 */
@SuppressWarnings("unchecked")
@PrepareForTest({JerseyRequest.class})
public class ServerBatchAttributeAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<ServerAttributesListWrapper> jerseyRequestMock;
    @Mock
    private OperationResult<ServerAttributesListWrapper> operationResultMock;
    @Mock
    private RequestBuilder<ServerAttributesListWrapper> builderMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    /**
     * for {@link ServerBatchAttributeAdapter#put(ServerAttributesListWrapper)}
     */
    public void should_return_proper_operation_result() {

        /** Given **/

        ServerAttributesListWrapper attributes = new ServerAttributesListWrapper();
        attributes.setAttributes(asList(
                new ServerAttribute("max_threads", "512"),
                new ServerAttribute("admin_cell_phone", "03")));

        PowerMockito.mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(
                eq(sessionStorageMock),
                eq(ServerAttributesListWrapper.class),
                eq(new String[]{"/attributes"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

        Mockito.when(jerseyRequestMock.put(attributes)).thenReturn(operationResultMock);


        /** When **/
        ServerBatchAttributeAdapter adapter = new ServerBatchAttributeAdapter(sessionStorageMock);
        OperationResult<ServerAttributesListWrapper> retrieved = adapter.put(attributes);


        /** Than **/
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved, operationResultMock);
        PowerMockito.verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(ServerAttributesListWrapper.class),
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
                eq(ServerAttributesListWrapper.class),
                eq(new String[]{"/attributes"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

        Mockito.when(jerseyRequestMock.addParams(map)).thenReturn(builderMock);
        Mockito.when(builderMock.delete()).thenReturn(operationResultMock);


        /** When **/
        ServerBatchAttributeAdapter adapter = new ServerBatchAttributeAdapter(sessionStorageMock);
        Whitebox.setInternalState(adapter, "params", map);
        OperationResult<ServerAttributesListWrapper> retrieved = adapter.delete();


        /** Than **/
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved, operationResultMock);
        PowerMockito.verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(ServerAttributesListWrapper.class),
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
                eq(ServerAttributesListWrapper.class),
                eq(new String[]{"/attributes"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

        Mockito.when(jerseyRequestMock.addParams(map)).thenReturn(builderMock);
        Mockito.when(builderMock.get()).thenReturn(operationResultMock);


        /** When **/
        ServerBatchAttributeAdapter adapter = new ServerBatchAttributeAdapter(sessionStorageMock);
        Whitebox.setInternalState(adapter, "params", map);
        OperationResult<ServerAttributesListWrapper> retrieved = adapter.get();


        /** Than **/
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved, operationResultMock);
        PowerMockito.verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(ServerAttributesListWrapper.class),
                eq(new String[]{"/attributes"}),
                any(DefaultErrorHandler.class));
        Mockito.verify(jerseyRequestMock, times(1)).addParams(map);
        Mockito.verify(builderMock, times(1)).get();
        Mockito.verifyNoMoreInteractions(jerseyRequestMock, builderMock);
    }

    @Test
    public void should_set_params() {
        ServerBatchAttributeAdapter adapter = new ServerBatchAttributeAdapter(sessionStorageMock, "x", "y", "z");
        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>)Whitebox.getInternalState(adapter, "params");
        List<String> list = params.get("name");
        Assert.assertSame(list.size(), 3);
        Assert.assertTrue(list.contains("y") && list.contains("z"));

    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, jerseyRequestMock, operationResultMock, builderMock);
    }
}