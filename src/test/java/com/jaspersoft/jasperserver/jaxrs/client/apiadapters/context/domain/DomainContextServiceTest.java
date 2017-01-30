package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.domain;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertSame;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@PrepareForTest({DomainContextService.class})
public class DomainContextServiceTest extends PowerMockTestCase {
    private SessionStorage sessionStorageMock;
    private SingleDomainContextAdapter adapter;

    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        adapter = mock(SingleDomainContextAdapter.class);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock,
                adapter
        );
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        // When
        DomainContextService service = new DomainContextService(sessionStorageMock);
        SessionStorage retrieved = service.getSessionStorage();
        // Then
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        // When
        new DomainContextService(null);
        // Then
        // should be thrown an exception
    }

    @Test
    public void should_return_proper_DomainContextManager_for_string_uri() throws Exception {

        // Given
        whenNew(SingleDomainContextAdapter.class)
                .withArguments(eq(sessionStorageMock), any(ClientResourceLookup.class), any(Class.class), anyString())
                .thenReturn(adapter);

        DomainContextService service = new DomainContextService(sessionStorageMock);

        // When
        SingleDomainContextAdapter retrieved = service.context("URI");

        // Then
        assertSame(adapter, retrieved);
        verifyNew(SingleDomainContextAdapter.class, times(1))
                .withArguments(eq(sessionStorageMock),
                        any(ClientResourceLookup.class),
                        eq(ClientResourceLookup.class),
                        eq(ContextMediaTypes.RESOURCE_LOOKUP_JSON));
    }
    @Test
    public void should_return_proper_DomainContextManager_for_clientDomain() throws Exception {

        // Given
        whenNew(SingleDomainContextAdapter.class)
                .withArguments(eq(sessionStorageMock), any(ClientDomain.class), any(Class.class), anyString())
                .thenReturn(adapter);

        DomainContextService service = new DomainContextService(sessionStorageMock);

        // When
        SingleDomainContextAdapter retrieved = service.context(new ClientDomain());

        // Then
        assertSame(adapter, retrieved);
        verifyNew(SingleDomainContextAdapter.class, times(1))
                .withArguments(eq(sessionStorageMock),
                        any(ClientDomain.class),
                        eq(ClientDomain.class),
                        eq(ContextMediaTypes.DOMAIN_JSON));
    }
    @Test
    public void should_return_proper_DomainContextManager_for_clientSemanticLayerDataSource() throws Exception {

        // Given
        whenNew(SingleDomainContextAdapter.class)
                .withArguments(eq(sessionStorageMock), any(ClientSemanticLayerDataSource.class), any(Class.class), anyString())
                .thenReturn(adapter);

        DomainContextService service = new DomainContextService(sessionStorageMock);

        // When
        SingleDomainContextAdapter retrieved = service.context(new ClientSemanticLayerDataSource());

        // Then
        assertSame(adapter, retrieved);
        verifyNew(SingleDomainContextAdapter.class, times(1))
                .withArguments(eq(sessionStorageMock),
                        any(ClientSemanticLayerDataSource.class),
                        eq(ClientSemanticLayerDataSource.class),
                        eq(ContextMediaTypes.DOMAIN_DATA_SOURCE_JSON));
    }
}