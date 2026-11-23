package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ResourcesServiceTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private SingleResourceAdapter resourceAdapterMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
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

        try (MockedConstruction<SingleResourceAdapter> mockedAdapter = Mockito.mockConstruction(SingleResourceAdapter.class)) {
            ResourcesService service = new ResourcesService(sessionStorageMock);
            Object retrieved = service.resource("uri");

            assertNotNull(retrieved);
            assertTrue(instanceOf(SingleResourceAdapter.class).matches(retrieved));
        }
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, resourceAdapterMock);
        mockitoSession.close();
    }
}
