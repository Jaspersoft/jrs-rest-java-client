package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.bundles.BundlesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo.ServerInfoService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings.SettingsService;
import org.mockito.Mock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;


/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.core.Session}
 */
public class AnonymousSessionTest {
    @Mock
    public SessionStorage storageMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void should_throw_exception_when_cannot_instantiate_service_class() {
        AnonymousSession anonymousSession = new AnonymousSession(storageMock);
        class CustomAdapter extends AbstractAdapter {
            public CustomAdapter(SessionStorage sessionStorage) {
                super(sessionStorage);
            }
        }
        anonymousSession.getService(CustomAdapter.class);
    }

    @Test
    public void should_return_proper_storage() {
        AnonymousSession anonymousSession = new AnonymousSession(storageMock);
        assertSame(anonymousSession.getStorage(), storageMock);
    }

    @Test
    public void should_return_not_null_ServerInfoService() {
        AnonymousSession anonymousSession = new AnonymousSession(storageMock);
        ServerInfoService retrieved = anonymousSession.serverInfoService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_settingsService() {
        AnonymousSession anonymousSession = new AnonymousSession(storageMock);
        SettingsService retrieved = anonymousSession.settingsService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_bundlesService() {
        AnonymousSession anonymousSession = new AnonymousSession(storageMock);
        BundlesService retrieved = anonymousSession.bundlesService();
        assertNotNull(retrieved);
    }

    @AfterMethod
    public void after() {
        storageMock = null;
    }
}
