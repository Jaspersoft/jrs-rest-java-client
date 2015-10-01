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
 * Unit tests for {@link AttributesService}
 */
@PrepareForTest({AttributesService.class})
public class ServerAttributesServiceTest extends PowerMockTestCase {

    private SessionStorage sessionStorageMock;
    private SingleAttributeAdapter singleAttributeAdapterMock;
    private BatchAttributeAdapter batchAttributeAdapterMock;

    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        singleAttributeAdapterMock = mock(SingleAttributeAdapter.class);
        batchAttributeAdapterMock = mock(BatchAttributeAdapter.class);
    }

    @Test
    /**
     * for {@link AttributesService#ServerAttributesService(SessionStorage)}
     */
    public void should_pass_session_storage_to_parent_adapter() {
        AttributesService attributesService = new AttributesService(sessionStorageMock);
        SessionStorage retrieved = attributesService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test
    /**
     * for {@link AttributesService#attribute()}
     */
    public void should_return_proper_ServerSingleAttributeAdapter_instance() throws Exception {

        /** Given **/
        PowerMockito.whenNew(SingleAttributeAdapter.class)
                .withArguments(sessionStorageMock)
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        /** When **/
        SingleAttributeAdapter retrieved = attributesService.attribute();

        /** Then **/
        Assert.assertSame(retrieved, singleAttributeAdapterMock);
        PowerMockito.verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments(sessionStorageMock);
    }


    @Test
    /**
     * for {@link AttributesService#attribute(String)}
     */
    public void should_construct_an_object_with_proper_params() throws Exception {

        /** Given **/
        PowerMockito.whenNew(SingleAttributeAdapter.class)
                .withArguments(sessionStorageMock, "status")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        /** When **/
        SingleAttributeAdapter retrieved = attributesService.attribute("status");

        /** Then **/
        Assert.assertSame(retrieved, singleAttributeAdapterMock);
        PowerMockito.verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments(sessionStorageMock, "status");
    }

    @Test
    /**
     * for {@link AttributesService#attribute()}
     */
    public void should_return_proper_ServerBatchAttributeAdapter_instance() throws Exception {

        /** Given **/
        PowerMockito.whenNew(BatchAttributeAdapter.class)
                .withArguments(sessionStorageMock)
                .thenReturn(batchAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        /** When **/
        BatchAttributeAdapter retrieved = attributesService.attributes();

        /** Then **/
        Assert.assertSame(retrieved, batchAttributeAdapterMock);
        PowerMockito.verifyNew(BatchAttributeAdapter.class, times(1))
                .withArguments(sessionStorageMock);
    }

    @Test
    /**
     * for {@link AttributesService#attributes(java.util.Collection)}
     */
    public void should_instantiate_proper_ServerBatchAttributeAdapter_instance() throws Exception {

        /** Given **/
        List<String> list = asList("attr1", "attr2", "attr3");
        PowerMockito.whenNew(BatchAttributeAdapter.class)
                .withArguments(sessionStorageMock, list)
                .thenReturn(batchAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        /** When **/
        BatchAttributeAdapter retrieved = attributesService.attributes(list);

        /** Then **/
        Assert.assertSame(retrieved, batchAttributeAdapterMock);
        PowerMockito.verifyNew(BatchAttributeAdapter.class, times(1))
                .withArguments(sessionStorageMock, list);
    }

    @Test
    /**
     * for {@link AttributesService#attributes(java.util.Collection)}
     */
    public void should_instantiate_proper_ServerBatchAttributeAdapter_instance_when_pass_vararg() throws Exception {

        /** Given **/
        PowerMockito.whenNew(BatchAttributeAdapter.class)
                .withArguments(sessionStorageMock, new String[]{"attr1", "attr2", "attr3"})
                .thenReturn(batchAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        /** When **/
        BatchAttributeAdapter retrieved = attributesService.attributes("attr1", "attr2", "attr3");

        /** Then **/
        Assert.assertSame(retrieved, batchAttributeAdapterMock);
        PowerMockito.verifyNew(BatchAttributeAdapter.class, times(1))
                .withArguments(sessionStorageMock, new String[]{"attr1", "attr2", "attr3"});
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        singleAttributeAdapterMock = null;
        batchAttributeAdapterMock = null;
    }
}