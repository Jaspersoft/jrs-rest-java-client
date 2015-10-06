package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import java.util.List;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService}
 */
@PrepareForTest({AttributesService.class})
public class AttributesServiceTest extends PowerMockTestCase {

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
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#ServerAttributesService(com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage)}
     */
    public void should_pass_session_storage_to_parent_adapter() {
        AttributesService attributesService = new AttributesService(sessionStorageMock);
        SessionStorage retrieved = attributesService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#attribute()}
     */
    public void should_return_proper_ServerSingleAttributeAdapter_instance() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments("/", sessionStorageMock, "attrName")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService.attribute("attrName");

        // Then
        assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments("/", sessionStorageMock, "attrName");
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#attribute()}
     */
    public void should_return_proper_ServerSingleAttributeAdapter_instance_for_organization() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments("/organizations/orgName/", sessionStorageMock, "attrName")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService.forOrganization("orgName").attribute("attrName");

        // Then
        assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments("/organizations/orgName/", sessionStorageMock, "attrName");
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#attribute()}
     */
    public void should_return_proper_ServerSingleAttributeAdapter_instance_for_organization_as_object() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments("/organizations/orgName/", sessionStorageMock, "attrName")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService
                        .forOrganization(new ClientTenant()
                                .setId("orgName"))
                        .attribute("attrName");

        // Then
        assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments("/organizations/orgName/", sessionStorageMock, "attrName");
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#attribute()}
     */
    public void should_return_proper_ServerSingleAttributeAdapter_instance_for_user() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments("/users/userName/", sessionStorageMock, "attrName")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService.forUser("userName").attribute("attrName");

        // Then
        assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments("/users/userName/", sessionStorageMock, "attrName");
    }


    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#attribute()}
     */
    public void should_return_proper_ServerSingleAttributeAdapter_instance_for_user_as_object() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments("/users/userName/", sessionStorageMock, "attrName")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService
                        .forUser(new ClientUser()
                                .setUsername("userName"))
                        .attribute("attrName");

        // Then
        assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments("/users/userName/", sessionStorageMock, "attrName");
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#attribute()}
     */
    public void should_return_proper_ServerSingleAttributeAdapter_instance_for_organization_for_user() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments("/organizations/orgName/users/userName/", sessionStorageMock, "attrName")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService.forOrganization("orgName").forUser("userName").attribute("attrName");

        // Then
        assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments("/organizations/orgName/users/userName/", sessionStorageMock, "attrName");
    }


    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#attribute(String)}
     */
    public void should_construct_an_object_with_proper_params() throws Exception {

        // Given /
        whenNew(SingleAttributeAdapter.class)
                .withArguments("/", sessionStorageMock, "status")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService.attribute("status");

        // Then
        Assert.assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments("/", sessionStorageMock, "status");
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#attribute()}
     */
    public void should_return_proper_BatchAttributeAdapter_instance() throws Exception {

        // Given
        whenNew(BatchAttributeAdapter.class)
                .withArguments("/", sessionStorageMock)
                .thenReturn(batchAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        BatchAttributeAdapter retrieved = attributesService.allAttributes();

        // Then
        assertSame(retrieved, batchAttributeAdapterMock);
        verifyNew(BatchAttributeAdapter.class, times(1))
                .withArguments("/", sessionStorageMock);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#attributes(java.util.Collection)}
     */
    public void should_instantiate_proper_BatchAttributeAdapter_instance() throws Exception {

        // Given
        List<String> list = asList("attr1", "attr2", "attr3");
        whenNew(BatchAttributeAdapter.class)
                .withArguments("/", sessionStorageMock, list)
                .thenReturn(batchAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        BatchAttributeAdapter retrieved = attributesService.attributes(list);

        // Then
        assertSame(retrieved, batchAttributeAdapterMock);
        verifyNew(BatchAttributeAdapter.class, times(1))
                .withArguments("/", sessionStorageMock, list);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService#attributes(java.util.Collection)}
     */
    public void should_instantiate_proper_BatchAttributeAdapter_instance_when_pass_vararg() throws Exception {

        // Given
        whenNew(BatchAttributeAdapter.class)
                .withArguments("/", sessionStorageMock, "attr1", "attr2", "attr3")
                .thenReturn(batchAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        BatchAttributeAdapter retrieved = attributesService.attributes("attr1", "attr2", "attr3");

        // Then
        assertSame(retrieved, batchAttributeAdapterMock);
        verifyNew(BatchAttributeAdapter.class, times(1))
                .withArguments("/", sessionStorageMock, "attr1", "attr2", "attr3");
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        singleAttributeAdapterMock = null;
        batchAttributeAdapterMock = null;
    }
}