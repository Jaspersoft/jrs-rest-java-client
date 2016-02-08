package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.metadata.DomainMetadataAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.newDomain.DomainAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.schema.DomainSchemaAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService}
 */
@PrepareForTest({DomainService.class})
public class DomainServiceTest extends PowerMockTestCase {

    public static final String URI = "uri";
    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private DomainAdapter domainAdapterMock;
    @Mock
    private DomainMetadataAdapter domainMetadataAdapterMock;
    @Mock
    private DomainSchemaAdapter domainSchemaAdapterMock;


    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        DomainService domainService = new DomainService(sessionStorageMock);
        SessionStorage retrieved = domainService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        // When
        new DomainService(null);
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_an_exception_when_domain_uri_is_null() {
        // When
        new DomainService(sessionStorageMock).forDomain((String) null);
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_an_exception_when_domain_is_null() {
        // When
        new DomainService(sessionStorageMock).domain(null);
        // Then
        // should be thrown an exception
    }

    @Test
    public void should_return_proper_DomainAdapter_instance() throws Exception {

        // Given
        whenNew(DomainAdapter.class)
                .withArguments(sessionStorageMock, URI)
                .thenReturn(domainAdapterMock);

        DomainService domainService = new DomainService(sessionStorageMock);

        // When
        DomainAdapter retrieved = domainService.domain(URI);

        // Then
        assertSame(retrieved, domainAdapterMock);
        verifyNew(DomainAdapter.class, times(1))
                .withArguments(sessionStorageMock, URI);
    }

    @Test
    public void should_return_proper_DomainMetadataAdapter_instance() throws Exception {

        // Given
        whenNew(DomainMetadataAdapter.class)
                .withArguments(sessionStorageMock, URI)
                .thenReturn(domainMetadataAdapterMock);

        DomainService domainService = new DomainService(sessionStorageMock);

        // When
        DomainMetadataAdapter retrieved = domainService.forDomain(URI).metadata();

        // Then
        assertSame(retrieved, domainMetadataAdapterMock);
        verifyNew(DomainMetadataAdapter.class, times(1))
                .withArguments(sessionStorageMock, URI);
    }

    @Test
    public void should_return_proper_DomainSchemaAdapter_instance() throws Exception {

        // Given
        whenNew(DomainSchemaAdapter.class)
                .withArguments(sessionStorageMock, URI)
                .thenReturn(domainSchemaAdapterMock);

        DomainService domainService = new DomainService(sessionStorageMock);

        // When
        DomainSchemaAdapter retrieved = domainService
                .forDomain(URI)
                .schema();

        // Then
        assertSame(retrieved, domainSchemaAdapterMock);
        verifyNew(DomainSchemaAdapter.class, times(1))
                .withArguments(sessionStorageMock, URI);
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        domainAdapterMock = null;
        domainMetadataAdapterMock = null;
        domainSchemaAdapterMock = null;

    }
}