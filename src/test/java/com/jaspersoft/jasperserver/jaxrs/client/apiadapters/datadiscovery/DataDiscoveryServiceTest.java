package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
@PrepareForTest({DataDiscoveryService.class})
public class DataDiscoveryServiceTest extends PowerMockTestCase {
    private SessionStorage sessionStorageMock;
    private DomainContextManager domainContextManager;
    private TopicContextManager topicContextManager;
    private DomElContextManager domElContextManager;
    private DerivedTableContextManager derivedTableContextManager;

    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        domainContextManager = mock(DomainContextManager.class);
        topicContextManager = mock(TopicContextManager.class);
        domElContextManager= mock(DomElContextManager.class);
        derivedTableContextManager = mock(DerivedTableContextManager.class);

    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock,
                domainContextManager,
                domElContextManager,
                topicContextManager,
                derivedTableContextManager);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        ContextService attributesService = new ContextService(sessionStorageMock);
        SessionStorage retrieved = attributesService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        // When
        new ContextService(null);
        // Then
        // should be thrown an exception
    }


    @Test
    public void should_return_proper_DomainContextManager() throws Exception {

        // Given
        whenNew(DomainContextManager.class)
                .withArguments(sessionStorageMock)
                .thenReturn(domainContextManager);

        DataDiscoveryService dataDiscoveryService = new DataDiscoveryService(sessionStorageMock);

        // When
        DomainContextManager retrieved = dataDiscoveryService.domainContext();

        // Then
        assertSame(domainContextManager, retrieved);
        verifyNew(DomainContextManager.class, times(1))
                .withArguments(sessionStorageMock);
    }

    @Test
    public void should_return_proper_TopicContextManager() throws Exception {

        // Given
        whenNew(TopicContextManager.class)
                .withArguments(sessionStorageMock)
                .thenReturn(topicContextManager);

        DataDiscoveryService dataDiscoveryService = new DataDiscoveryService(sessionStorageMock);

        // When
        TopicContextManager retrieved = dataDiscoveryService.topicContext();

        // Then
        assertSame(topicContextManager, retrieved);
        verifyNew(TopicContextManager.class, times(1))
                .withArguments(sessionStorageMock);
    }

    @Test
    public void should_return_proper_DomElContextManager() throws Exception {

        // Given
        whenNew(DomElContextManager.class)
                .withArguments(sessionStorageMock)
                .thenReturn(domElContextManager);

        DataDiscoveryService dataDiscoveryService = new DataDiscoveryService(sessionStorageMock);

        // When
        DomElContextManager retrieved = dataDiscoveryService.domElContext();

        // Then
        assertSame(domElContextManager, retrieved);
        verifyNew(DomElContextManager.class, times(1))
                .withArguments(sessionStorageMock);
    }

    @Test
    public void should_return_proper_deridved_table_context_manager() throws Exception {

        // Given
        whenNew(DerivedTableContextManager.class)
                .withArguments(sessionStorageMock)
                .thenReturn(derivedTableContextManager);

        DataDiscoveryService dataDiscoveryService = new DataDiscoveryService(sessionStorageMock);

        // When
        DerivedTableContextManager retrieved = dataDiscoveryService.derivedTableContext();

        // Then
        assertSame(derivedTableContextManager, retrieved);
        verifyNew(DerivedTableContextManager.class, times(1))
                .withArguments(sessionStorageMock);
    }


}