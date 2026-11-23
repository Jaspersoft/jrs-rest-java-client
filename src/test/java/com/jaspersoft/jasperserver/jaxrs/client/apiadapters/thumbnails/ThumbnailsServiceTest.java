package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link ThumbnailsService}
 */
public class ThumbnailsServiceTest {

    private SessionStorage sessionStorageMock;
    private BatchThumbnailAdapter batchThumbnailAdapterMock;
    private SingleThumbnailAdapter singleThumbnailAdapterMock;

    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        singleThumbnailAdapterMock = mock(SingleThumbnailAdapter.class);
        batchThumbnailAdapterMock = mock(BatchThumbnailAdapter.class);
    }

    /**
     * for {@link ThumbnailsService#ThumbnailsService(SessionStorage)}
     */
    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        // When
        ThumbnailsService thumbnailsService = new ThumbnailsService(sessionStorageMock);
        SessionStorage retrieved = thumbnailsService.getSessionStorage();
        //Then
        assertSame(retrieved, sessionStorageMock);
    }

    /**
     * for {@link ThumbnailsService#thumbnail()}
     */
    @Test
    public void should_invoke_proper_single_thumbnail_adapter_constructor_and_pass_proper_session_storage_instance() throws Exception {

        try (MockedConstruction<SingleThumbnailAdapter> mockedAdapter =
                     Mockito.mockConstruction(SingleThumbnailAdapter.class)) {

            // When
            ThumbnailsService thumbnailsService = new ThumbnailsService(sessionStorageMock);
            SingleThumbnailAdapter retrieved = thumbnailsService.thumbnail();

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, mockedAdapter.constructed().get(0));
        }
    }

    /**
     * for {@link ThumbnailsService#thumbnails()}
     */
    @Test
    public void should_invoke_proper_batch_thumbnail_adapter_constructor_and_pass_proper_session_storage_instance() throws Exception {

        try (MockedConstruction<BatchThumbnailAdapter> mockedAdapter =
                     Mockito.mockConstruction(BatchThumbnailAdapter.class)) {

            // When
            ThumbnailsService thumbnailsService = new ThumbnailsService(sessionStorageMock);
            BatchThumbnailAdapter retrieved = thumbnailsService.thumbnails();

            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, mockedAdapter.constructed().get(0));
        }
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        singleThumbnailAdapterMock = null;
        batchThumbnailAdapterMock = null;
    }
}
