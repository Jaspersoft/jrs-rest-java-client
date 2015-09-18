package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
* Unit tests for {@link ThumbnailsService}
*/
@PrepareForTest({BatchThumbnailAdapter.class, ThumbnailsService.class})
public class ThumbnailsServiceTest extends PowerMockTestCase {

    private SessionStorage sessionStorageMock;
    private BatchThumbnailAdapter batchThumbnailAdapterMock;
    private SingleThumbnailAdapter singleThumbnailAdapterMock;

    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        singleThumbnailAdapterMock = mock(SingleThumbnailAdapter.class);
        batchThumbnailAdapterMock = mock(BatchThumbnailAdapter.class);
    }

    @Test
    /**
     * for {@link ThumbnailsService#ThumbnailsService(SessionStorage)}
     */
    public void should_pass_session_storage_to_parent_adapter() {
        // When
        ThumbnailsService thumbnailsService = new ThumbnailsService(sessionStorageMock);
        SessionStorage retrieved = thumbnailsService.getSessionStorage();
        //Then
        assertSame(retrieved, sessionStorageMock);
    }

    @Test
    /**
     * for {@link ThumbnailsService#thumbnail()}
     */
    public void should_invoke_proper_single_thumbnail_adapter_constructor_and_pass_proper_session_storage_instance() throws Exception {

        // Given
        whenNew(SingleThumbnailAdapter.class).withArguments(sessionStorageMock).thenReturn(singleThumbnailAdapterMock);

        // When
        ThumbnailsService thumbnailsService = new ThumbnailsService(sessionStorageMock);
        SingleThumbnailAdapter retrieved = thumbnailsService.thumbnail();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, singleThumbnailAdapterMock);
        verifyNew(SingleThumbnailAdapter.class, times(1)).withArguments(sessionStorageMock);
    }

    @Test
    /**
     * for {@link ThumbnailsService#thumbnails()}
     */
    public void should_invoke_proper_batch_thumbnail_adapter_constructor_and_pass_proper_session_storage_instance() throws Exception {

        // Given
        whenNew(BatchThumbnailAdapter.class).withArguments(sessionStorageMock).thenReturn(batchThumbnailAdapterMock);

        // When
        ThumbnailsService thumbnailsService = new ThumbnailsService(sessionStorageMock);
        BatchThumbnailAdapter retrieved = thumbnailsService.thumbnails();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, batchThumbnailAdapterMock);
        verifyNew(BatchThumbnailAdapter.class, times(1)).withArguments(sessionStorageMock);
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        singleThumbnailAdapterMock = null;
        batchThumbnailAdapterMock = null;
    }
}