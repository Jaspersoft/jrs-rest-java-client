package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections;

import com.jaspersoft.jasperserver.dto.connection.metadata.TableMetadata;
import com.jaspersoft.jasperserver.dto.resources.ClientCustomDataSource;
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
@PrepareForTest({ConnectionsService.class})
public class ConnectionsServiceTest extends PowerMockTestCase {

    private SessionStorage sessionStorageMock;
    private SingleConnectionsAdapter singleConnectionsAdapterMock;

    @BeforeMethod
    public void before() {
        sessionStorageMock = mock(SessionStorage.class);
        singleConnectionsAdapterMock = mock(SingleConnectionsAdapter.class);

    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, singleConnectionsAdapterMock);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        ConnectionsService attributesService = new ConnectionsService(sessionStorageMock);
        SessionStorage retrieved = attributesService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        // When
        new ConnectionsService(null);
        // Then
        // should be thrown an exception
    }


    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_uuid() throws Exception {

        // Given
        whenNew(SingleConnectionsAdapter.class)
                .withArguments(sessionStorageMock, Object.class, null, Object.class, null, "someUuId")
                .thenReturn(singleConnectionsAdapterMock);

        ConnectionsService connectionsService = new ConnectionsService(sessionStorageMock);

        // When
        SingleConnectionsAdapter retrieved = connectionsService.connection("someUuId");

        // Then
        assertSame(singleConnectionsAdapterMock, retrieved);
        verifyNew(SingleConnectionsAdapter.class, times(1))
                .withArguments(sessionStorageMock, Object.class, null, Object.class, null, "someUuId");
    }

    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_class_mimeType_uuid() throws Exception {

        // Given
        whenNew(SingleConnectionsAdapter.class)
                .withArguments(sessionStorageMock, ClientCustomDataSource.class, "someMimeType", Object.class, null, "someUuId")
                .thenReturn(singleConnectionsAdapterMock);

        ConnectionsService connectionsService = new ConnectionsService(sessionStorageMock);

        // When
        SingleConnectionsAdapter retrieved = connectionsService.connection(ClientCustomDataSource.class, "someMimeType", "someUuId");

        // Then
        assertSame(singleConnectionsAdapterMock, retrieved);
        verifyNew(SingleConnectionsAdapter.class, times(1))
                .withArguments(sessionStorageMock, ClientCustomDataSource.class, "someMimeType", Object.class, null, "someUuId");
    }

    @Test
    public void should_return_proper_SingleConnectionAdapter_instance_by_connection_metadata_class_mimeType_uuid() throws Exception {

        // Given
        whenNew(SingleConnectionsAdapter.class)
                .withArguments(sessionStorageMock,
                        ClientCustomDataSource.class,
                        "someConnectionMimeType",
                        TableMetadata.class,
                        "someMetadataMimeType", "someUuId")
                .thenReturn(singleConnectionsAdapterMock);

        ConnectionsService connectionsService = new ConnectionsService(sessionStorageMock);

        // When
        SingleConnectionsAdapter retrieved = connectionsService.
                connection(ClientCustomDataSource.class, "someConnectionMimeType", TableMetadata.class,
                        "someMetadataMimeType", "someUuId");

        // Then
        assertSame(singleConnectionsAdapterMock, retrieved);
        verifyNew(SingleConnectionsAdapter.class, times(1))
                .withArguments(sessionStorageMock,
                        ClientCustomDataSource.class,
                        "someConnectionMimeType",
                        TableMetadata.class,
                        "someMetadataMimeType", "someUuId");
    }

}