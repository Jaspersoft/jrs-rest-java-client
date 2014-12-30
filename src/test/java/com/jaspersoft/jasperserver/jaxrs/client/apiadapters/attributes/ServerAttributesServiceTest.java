package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link ServerAttributesService}
 */
@PrepareForTest({ServerAttributesService.class})
public class ServerAttributesServiceTest extends PowerMockTestCase {

    private SessionStorage sessionStorageMock;
    private ServerSingleAttributeAdapter singleAttributeAdapterMock;
    private ServerBatchAttributeAdapter batchAttributeAdapterMock;

    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        singleAttributeAdapterMock = mock(ServerSingleAttributeAdapter.class);
        batchAttributeAdapterMock = mock(ServerBatchAttributeAdapter.class);
    }

    @Test
    /**
     * for {@link ServerAttributesService#ServerAttributesService(SessionStorage)}
     */
    public void should_pass_session_storage_to_parent_adapter() {
        ServerAttributesService attributesService = new ServerAttributesService(sessionStorageMock);
        SessionStorage retrieved = attributesService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test
    /**
     * for {@link ServerAttributesService#attribute()}
     */
    public void should_return_proper_ServerSingleAttributeAdapter_instance() throws Exception {

        /** Given **/
        PowerMockito.whenNew(ServerSingleAttributeAdapter.class)
                .withArguments(sessionStorageMock)
                .thenReturn(singleAttributeAdapterMock);

        ServerAttributesService attributesService = new ServerAttributesService(sessionStorageMock);

        /** When **/
        ServerSingleAttributeAdapter retrieved = attributesService.attribute();

        /** Than **/
        Assert.assertSame(retrieved, singleAttributeAdapterMock);
        PowerMockito.verifyNew(ServerSingleAttributeAdapter.class, times(1))
                .withArguments(sessionStorageMock);
    }


    @Test
    /**
     * for {@link ServerAttributesService#attribute(String)}
     */
    public void should_construct_an_object_with_proper_params() throws Exception {

        /** Given **/
        PowerMockito.whenNew(ServerSingleAttributeAdapter.class)
                .withArguments(sessionStorageMock, "status")
                .thenReturn(singleAttributeAdapterMock);

        ServerAttributesService attributesService = new ServerAttributesService(sessionStorageMock);

        /** When **/
        ServerSingleAttributeAdapter retrieved = attributesService.attribute("status");

        /** Than **/
        Assert.assertSame(retrieved, singleAttributeAdapterMock);
        PowerMockito.verifyNew(ServerSingleAttributeAdapter.class, times(1))
                .withArguments(sessionStorageMock, "status");
    }

    @Test
    /**
     * for {@link ServerAttributesService#attribute()}
     */
    public void should_return_proper_ServerBatchAttributeAdapter_instance() throws Exception {

        /** Given **/
        PowerMockito.whenNew(ServerBatchAttributeAdapter.class)
                .withArguments(sessionStorageMock)
                .thenReturn(batchAttributeAdapterMock);

        ServerAttributesService attributesService = new ServerAttributesService(sessionStorageMock);

        /** When **/
        ServerBatchAttributeAdapter retrieved = attributesService.attributes();

        /** Than **/
        Assert.assertSame(retrieved, batchAttributeAdapterMock);
        PowerMockito.verifyNew(ServerBatchAttributeAdapter.class, times(1))
                .withArguments(sessionStorageMock);
    }

    @Test
    /**
     * for {@link ServerAttributesService#attributes(java.util.Collection)}
     */
    public void should_instantiate_proper_ServerBatchAttributeAdapter_instance() throws Exception {

        /** Given **/
        List<String> list = asList("attr1", "attr2", "attr3");
        PowerMockito.whenNew(ServerBatchAttributeAdapter.class)
                .withArguments(sessionStorageMock, list)
                .thenReturn(batchAttributeAdapterMock);

        ServerAttributesService attributesService = new ServerAttributesService(sessionStorageMock);

        /** When **/
        ServerBatchAttributeAdapter retrieved = attributesService.attributes(list);

        /** Than **/
        Assert.assertSame(retrieved, batchAttributeAdapterMock);
        PowerMockito.verifyNew(ServerBatchAttributeAdapter.class, times(1))
                .withArguments(sessionStorageMock, list);
    }

    @Test
    /**
     * for {@link ServerAttributesService#attributes(java.util.Collection)}
     */
    public void should_instantiate_proper_ServerBatchAttributeAdapter_instance_when_pass_vararg() throws Exception {

        /** Given **/
        PowerMockito.whenNew(ServerBatchAttributeAdapter.class)
                .withArguments(sessionStorageMock, new String[]{"attr1", "attr2", "attr3"})
                .thenReturn(batchAttributeAdapterMock);

        ServerAttributesService attributesService = new ServerAttributesService(sessionStorageMock);

        /** When **/
        ServerBatchAttributeAdapter retrieved = attributesService.attributes("attr1", "attr2", "attr3");

        /** Than **/
        Assert.assertSame(retrieved, batchAttributeAdapterMock);
        PowerMockito.verifyNew(ServerBatchAttributeAdapter.class, times(1))
                .withArguments(sessionStorageMock, new String[]{"attr1", "attr2", "attr3"});
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        singleAttributeAdapterMock = null;
        batchAttributeAdapterMock = null;
    }
}