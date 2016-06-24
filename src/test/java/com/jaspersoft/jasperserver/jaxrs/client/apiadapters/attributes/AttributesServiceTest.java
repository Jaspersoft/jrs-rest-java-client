package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import java.util.Collections;
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
    public void should_pass_session_storage_to_parent_adapter() {
        AttributesService attributesService = new AttributesService(sessionStorageMock);
        SessionStorage retrieved = attributesService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        // When
        new AttributesService(null);
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_organization_as_string_is_null() {
        // When
        new AttributesService(sessionStorageMock).forOrganization((String)null);
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_organization_as_object_is_null() {
        // When
        new AttributesService(sessionStorageMock).forOrganization((ClientTenant)null);
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_organization__id_is_null() {
        // When
        new AttributesService(sessionStorageMock).forOrganization(new ClientTenant().setId(null));
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_organization__id_is_empty() {
        // When
        new AttributesService(sessionStorageMock).forOrganization(new ClientTenant().setId(""));
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_organization__name_is_empty() {
        // When
        new AttributesService(sessionStorageMock).forOrganization("");
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_user_as_string_is_null() {
        // When
        new AttributesService(sessionStorageMock).forUser((String) null);
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_user_as_object_is_null() {
        // When
        new AttributesService(sessionStorageMock).forUser((ClientUser) null);
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_user_name_is_null() {
        // When
        new AttributesService(sessionStorageMock).forUser(new ClientUser().setUsername(null));
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_user_name_is_empty() {
        // When
        new AttributesService(sessionStorageMock).forUser(new ClientUser().setUsername(""));
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_user__name_is_empty() {
        // When
        new AttributesService(sessionStorageMock).forUser("");
        // Then
        // should be thrown an exception
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments(null, null, sessionStorageMock, "attrName")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService.attribute("attrName");

        // Then
        assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments(null, null, sessionStorageMock, "attrName");
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance_for_organization() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments("orgName", null, sessionStorageMock, "attrName")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService.forOrganization("orgName").attribute("attrName");

        // Then
        assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments("orgName", null, sessionStorageMock, "attrName");
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance_for_organization_as_object() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments("orgName", null, sessionStorageMock, "attrName")
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
                .withArguments("orgName", null, sessionStorageMock, "attrName");
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance_for_user() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments(null, "userName", sessionStorageMock, "attrName")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService.forUser("userName").attribute("attrName");

        // Then
        assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments(null, "userName", sessionStorageMock, "attrName");
    }


    @Test
    public void should_return_proper_SingleAttributeAdapter_instance_for_user_as_object() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments(null, "userName", sessionStorageMock, "attrName")
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
                .withArguments(null, "userName", sessionStorageMock, "attrName");
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance_for_organization_for_user() throws Exception {

        // Given
        whenNew(SingleAttributeAdapter.class)
                .withArguments("orgName", "userName", sessionStorageMock, "attrName")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService.forOrganization("orgName").forUser("userName").attribute("attrName");

        // Then
        assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments("orgName", "userName", sessionStorageMock, "attrName");
    }


    @Test
    public void should_construct_an_object_with_proper_params() throws Exception {

        // Given /
        whenNew(SingleAttributeAdapter.class)
                .withArguments(null, null, sessionStorageMock, "status")
                .thenReturn(singleAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        SingleAttributeAdapter retrieved = attributesService.attribute("status");

        // Then
        Assert.assertSame(retrieved, singleAttributeAdapterMock);
        verifyNew(SingleAttributeAdapter.class, times(1))
                .withArguments(null, null, sessionStorageMock, "status");
    }

    @Test
    public void should_return_proper_BatchAttributeAdapter_instance() throws Exception {

        // Given
        whenNew(BatchAttributeAdapter.class)
                .withArguments(null, null, sessionStorageMock)
                .thenReturn(batchAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        BatchAttributeAdapter retrieved = attributesService.allAttributes();

        // Then
        assertSame(retrieved, batchAttributeAdapterMock);
        verifyNew(BatchAttributeAdapter.class, times(1))
                .withArguments(null, null, sessionStorageMock);
    }

    @Test
    public void should_instantiate_proper_BatchAttributeAdapter_instance() throws Exception {

        // Given
        List<String> list = asList("attr1", "attr2", "attr3");
        whenNew(BatchAttributeAdapter.class)
                .withArguments(null, null, sessionStorageMock, list)
                .thenReturn(batchAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        BatchAttributeAdapter retrieved = attributesService.attributes(list);

        // Then
        assertSame(retrieved, batchAttributeAdapterMock);
        verifyNew(BatchAttributeAdapter.class, times(1))
                .withArguments(null, null, sessionStorageMock, list);
    }

    @Test
    public void should_instantiate_proper_BatchAttributeAdapter_instance_when_pass_vararg() throws Exception {

        // Given
        whenNew(BatchAttributeAdapter.class)
                .withArguments(null, null, sessionStorageMock, asList("attr1", "attr2", "attr3"))
                .thenReturn(batchAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        BatchAttributeAdapter retrieved = attributesService.attributes("attr1", "attr2", "attr3");

        // Then
        assertSame(retrieved, batchAttributeAdapterMock);
        verifyNew(BatchAttributeAdapter.class, times(1))
                .withArguments(null, null, sessionStorageMock, asList("attr1", "attr2", "attr3"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exceptopn_when_attributes_list_is_empty() throws Exception {

        // Given
        List<String> list = Collections.EMPTY_LIST;
        whenNew(BatchAttributeAdapter.class)
                .withArguments(null, null, sessionStorageMock, list)
                .thenReturn(batchAttributeAdapterMock);

        AttributesService attributesService = new AttributesService(sessionStorageMock);

        // When
        attributesService.attributes(list);

        // Then
        // an exception should be thrown
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        singleAttributeAdapterMock = null;
        batchAttributeAdapterMock = null;
    }
}