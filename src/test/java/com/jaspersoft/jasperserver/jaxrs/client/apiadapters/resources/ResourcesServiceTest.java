package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

@PrepareForTest({ResourcesService.class, SingleResourceAdapter.class})
public class ResourcesServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private SingleResourceAdapter resourceAdapterMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_return_proper_instance_of_BatchResourcesAdapter_class() {

        ResourcesService service = new ResourcesService(sessionStorageMock);
        Object retrieved = service.resources();

        assertNotNull(retrieved);
        assertTrue(instanceOf(BatchResourcesAdapter.class).matches(retrieved));
    }

    @Test
    public void should_return_proper_instance_of_SingleResourceAdapter_class() {

        ResourcesService service = new ResourcesService(sessionStorageMock);
        Object retrieved = service.resource("uri");

        assertNotNull(retrieved);
        assertTrue(instanceOf(SingleResourceAdapter.class).matches(retrieved));
    }

    @Test
    public void should_check_signature_of_SingleResourceAdapter_constructor() throws Exception {

        PowerMockito.whenNew(SingleResourceAdapter.class).withArguments(sessionStorageMock, "uri").thenReturn(resourceAdapterMock);

        ResourcesService service = new ResourcesService(sessionStorageMock);
        Object retrieved = service.resource("uri");

        assertNotNull(retrieved);
        assertTrue(instanceOf(SingleResourceAdapter.class).matches(retrieved));
        assertSame(retrieved, resourceAdapterMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, resourceAdapterMock);
    }
}