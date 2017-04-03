package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context;

import com.jaspersoft.jasperserver.dto.connection.metadata.TableMetadata;
import com.jaspersoft.jasperserver.dto.resources.ClientCustomDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
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
@PrepareForTest({ContextService.class})
public class ContextServiceTest extends PowerMockTestCase {
    public static final String SOME_UU_ID = "someUuId";
    public static final String SOME_MIME_TYPE = "someMimeType";
    public static final String METADATA_MIME_TYPE = "someMetadataMimeType";
    private SessionStorage sessionStorageMock;
    private SingleContextAdapter singleContextAdapterMock;

    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        singleContextAdapterMock = mock(SingleContextAdapter.class);

    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, singleContextAdapterMock);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        ContextService contextService = new ContextService(sessionStorageMock);
        SessionStorage retrieved = contextService.getSessionStorage();
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
    public void should_return_proper_SingleConnectionAdapter_instance_by_uuid() throws Exception {

        // Given
        whenNew(SingleContextAdapter.class)
                .withArguments(sessionStorageMock, Object.class, SOME_MIME_TYPE, SOME_UU_ID)
                .thenReturn(singleContextAdapterMock);

        ContextService contextService = new ContextService(sessionStorageMock);

        // When
        SingleContextAdapter retrieved = contextService.context(Object.class, SOME_MIME_TYPE, SOME_UU_ID);

        // Then
        assertSame(singleContextAdapterMock, retrieved);
        verifyNew(SingleContextAdapter.class, times(1))
                .withArguments(sessionStorageMock, Object.class, SOME_MIME_TYPE, SOME_UU_ID);
    }

    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_class_mimeType_uuid() throws Exception {

        // Given
        whenNew(SingleContextAdapter.class)
                .withArguments(sessionStorageMock, ClientCustomDataSource.class, SOME_MIME_TYPE, SOME_UU_ID)
                .thenReturn(singleContextAdapterMock);

        ContextService contextService = new ContextService(sessionStorageMock);

        // When
        SingleContextAdapter retrieved = contextService.context(ClientCustomDataSource.class, SOME_MIME_TYPE, SOME_UU_ID);

        // Then
        assertSame(singleContextAdapterMock, retrieved);
        verifyNew(SingleContextAdapter.class, times(1))
                .withArguments(sessionStorageMock, ClientCustomDataSource.class, SOME_MIME_TYPE, SOME_UU_ID);
    }


 @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_class_mimeType() throws Exception {

        // Given
        whenNew(SingleContextAdapter.class)
                .withArguments(sessionStorageMock, ClientCustomDataSource.class, SOME_MIME_TYPE)
                .thenReturn(singleContextAdapterMock);

        ContextService contextService = new ContextService(sessionStorageMock);

        // When
        SingleContextAdapter retrieved = contextService.context(ClientCustomDataSource.class, SOME_MIME_TYPE);

        // Then
        assertSame(singleContextAdapterMock, retrieved);
        verifyNew(SingleContextAdapter.class, times(1))
                .withArguments(sessionStorageMock, ClientCustomDataSource.class, SOME_MIME_TYPE);
    }

    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_connection_metadata_class_mimeType_uuid() throws Exception {

        // Given
        whenNew(SingleContextAdapter.class)
                .withArguments(sessionStorageMock, SOME_UU_ID, TableMetadata.class, METADATA_MIME_TYPE)
                .thenReturn(singleContextAdapterMock);

        ContextService contextService = new ContextService(sessionStorageMock);

        // When
        SingleContextAdapter retrieved = contextService.
                context(SOME_UU_ID, TableMetadata.class, METADATA_MIME_TYPE);

        // Then
        assertSame(singleContextAdapterMock, retrieved);
        verifyNew(SingleContextAdapter.class, times(1))
                .withArguments(sessionStorageMock, SOME_UU_ID, TableMetadata.class, METADATA_MIME_TYPE);
    }

    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_class_mimeType_metadata_class_mimeType() throws Exception {

        // Given
        whenNew(SingleContextAdapter.class)
                .withArguments(sessionStorageMock,
                        ClientDomain.class,
                        ContextMediaTypes.DOMAIN_JSON,
                        ResourceGroupElement.class,
                        ContextMediaTypes.DOMAIN_METADATA_JSON)
                .thenReturn(singleContextAdapterMock);

        ContextService contextService = new ContextService(sessionStorageMock);

        // When
        SingleContextAdapter retrieved = contextService.
                context(ClientDomain.class,
                        ContextMediaTypes.DOMAIN_JSON,
                        ResourceGroupElement.class,
                        ContextMediaTypes.DOMAIN_METADATA_JSON);

        // Then
        assertSame(singleContextAdapterMock, retrieved);
        verifyNew(SingleContextAdapter.class, times(1))
                .withArguments(sessionStorageMock,
                        ClientDomain.class,
                        ContextMediaTypes.DOMAIN_JSON,
                        ResourceGroupElement.class,
                        ContextMediaTypes.DOMAIN_METADATA_JSON);
    }
}