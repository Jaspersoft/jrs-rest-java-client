package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.Spy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
* Unit tests for {@link DomainMetadataServiceTest}.
*/
public class DomainMetadataServiceTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Spy
    private DomainMetadataService serviceSpy = new DomainMetadataService(sessionStorageMock);

    private DomainMetadataAdapter adapter;
    private DomainMetadataService service;

    private final String VALID_URI_FAKE = "very/valid/resource/identifier/";

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        adapter = new DomainMetadataAdapter(sessionStorageMock, VALID_URI_FAKE);
        service = new DomainMetadataService(sessionStorageMock);
    }

    @Test
    public void should_pass_session_storage_to_super_class_without_any_changes() {
        assertEquals(sessionStorageMock, service.getSessionStorage());
    }

    @Test
    public void should_create_domain_adapter_from_uri_with_proper_parameters() {
        final DomainMetadataAdapter expected = service.domainMetadata(VALID_URI_FAKE);

        instanceOf(DomainMetadataAdapter.class).matches(expected);
        assertEquals(service.getSessionStorage(), adapter.getSessionStorage());
        assertEquals(expected.getDomainURI(), VALID_URI_FAKE);
    }

    @Test
    public void should_return_not_null_domain_metadata() {
        assertNotNull(serviceSpy.domainMetadata(anyString()));
        verify(serviceSpy).domainMetadata(anyString());
    }

    @AfterMethod
    public void tearDown() {
        reset(sessionStorageMock);
    }
}