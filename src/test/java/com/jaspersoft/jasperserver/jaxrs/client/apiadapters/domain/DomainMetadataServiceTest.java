package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DomainMetadataServiceTest extends Assert {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private DomainMetadataService metadataServiceMock;

    @Mock
    private DomainMetadataAdapter metadataAdapterMock;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void should_pass_session_storage_to_super_class_without_any_changes() {
        final DomainMetadataService metadataServiceSpy = spy(new DomainMetadataService(sessionStorageMock));
        assertEquals(sessionStorageMock, metadataServiceSpy.getSessionStorage());
        verify(metadataServiceSpy).getSessionStorage();
    }

    @Test
    public void should_create_domain_adapter_from_uri() {
        final String fakeValidUri = "very/valid/path/to/domain";

        when(metadataServiceMock.domainMetadata(anyString()))
                .thenReturn(metadataAdapterMock);

        assertEquals(metadataAdapterMock, metadataServiceMock.domainMetadata(fakeValidUri));
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void should_throw_an_exception_when_passing_wrong_uri() {
        final String fakeWrongUri = "wrong/path/to/domain";

        doThrow(new ResourceNotFoundException())
                .when(metadataServiceMock)
                .domainMetadata(fakeWrongUri);

        metadataServiceMock.domainMetadata(fakeWrongUri);
    }

    @Test
    public void should_return_not_null_domain_detadata() {
        final DomainMetadataService metadataServiceSpy = spy(new DomainMetadataService(sessionStorageMock));
        assertNotNull(metadataServiceSpy.domainMetadata(anyString()));
        verify(metadataServiceSpy).domainMetadata(anyString());
    }
}