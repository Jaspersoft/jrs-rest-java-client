package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections;

import com.jaspersoft.jasperserver.dto.connection.FtpConnection;
import com.jaspersoft.jasperserver.dto.connection.LfsConnection;
import com.jaspersoft.jasperserver.dto.connection.metadata.TableMetadata;
import com.jaspersoft.jasperserver.dto.resources.ClientCustomDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientJdbcDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientJndiJdbcDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ConnectionMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertSame;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@SuppressWarnings("unchecked")
@PrepareForTest({JerseyRequest.class})
public class SingleConnectionAdapterTest extends PowerMockTestCase {

    public static final String TEST_UUID = "someUuId";
    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private JerseyRequest<FtpConnection> ftpConnectionJerseyRequestMock;
    @Mock
    private JerseyRequest jerseyRequestMock;
    @Mock
    private JerseyRequest<LfsConnection> lfsConnectionJerseyRequestMock;
    @Mock
    private JerseyRequest<ClientCustomDataSource> customDataSourceJerseyRequestMock;
    @Mock
    private JerseyRequest<ClientJndiJdbcDataSource> jndiJdbcDataSourceJerseyRequestMock;
    @Mock
    private JerseyRequest<ClientJdbcDataSource> jdbcDataSourceJerseyRequestMock;
    @Mock
    private JerseyRequest<TableMetadata> tableMetadataRequestMock;
    @Mock
    private OperationResult operationResultMock;
    @Mock
    private OperationResult<FtpConnection> ftpConnectionOperationResultMock;
    @Mock
    private OperationResult<LfsConnection> lfsConnectionOperationResultMock;
    @Mock
    private OperationResult<ClientCustomDataSource> customDataSourceOperationResultMock;
    @Mock
    private OperationResult<ClientJndiJdbcDataSource> jndiDataSourceOperationResultMock;
    @Mock
    private OperationResult<ClientJdbcDataSource> jdbcDataSourceOperationResultMock;
    @Mock
    private OperationResult<TableMetadata> tableMetadataOperationResultMock;

    private ConnectionsService connectionsService;

    @BeforeMethod
    public void before() {
        initMocks(this);
        connectionsService = new ConnectionsService(sessionStorageMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock
                , configurationMock
                , jerseyRequestMock
                , ftpConnectionJerseyRequestMock
                , lfsConnectionJerseyRequestMock
                , customDataSourceJerseyRequestMock
                , jndiJdbcDataSourceJerseyRequestMock
                , jdbcDataSourceJerseyRequestMock
                , tableMetadataRequestMock
                , operationResultMock
                , ftpConnectionOperationResultMock
                , lfsConnectionOperationResultMock
                , customDataSourceOperationResultMock
                , jndiDataSourceOperationResultMock
                , jdbcDataSourceOperationResultMock
                , tableMetadataOperationResultMock);
    }

    @Test
    public void should_return_proper_ftp_connection_operationResult_when_get() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(FtpConnection.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class))).thenReturn(ftpConnectionJerseyRequestMock);

        doReturn(ftpConnectionOperationResultMock).when(ftpConnectionJerseyRequestMock).get();

        OperationResult<FtpConnection> connection = connectionsService
                .connection(FtpConnection.class, "someMiMeType", TEST_UUID)
                .get();
        //then
        assertSame(ftpConnectionOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(FtpConnection.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class));
        verify(ftpConnectionJerseyRequestMock).get();
    }

    @Test
    public void should_return_proper_ftp_connection_operationResult_when_create() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(FtpConnection.class)
                , eq(new String[]{"/connections"})
                , any(DefaultErrorHandler.class))).thenReturn(ftpConnectionJerseyRequestMock);

        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(ftpConnectionJerseyRequestMock).when(ftpConnectionJerseyRequestMock).setContentType(ConnectionMediaType.FTP_JSON);
        doReturn(ftpConnectionOperationResultMock).when(ftpConnectionJerseyRequestMock).post(any(FtpConnection.class));

        OperationResult<FtpConnection> connection = connectionsService
                .connection(FtpConnection.class, ConnectionMediaType.FTP_TYPE, TEST_UUID)
                .create(new FtpConnection());
        //then
        assertSame(ftpConnectionOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(FtpConnection.class)
                , eq(new String[]{"/connections"})
                , any(DefaultErrorHandler.class));
        verify(sessionStorageMock).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(ftpConnectionJerseyRequestMock).setContentType(ConnectionMediaType.FTP_JSON);
        verify(ftpConnectionJerseyRequestMock).post(any(FtpConnection.class));
    }

    @Test
    public void should_return_proper_ftp_connection_operationResult_when_update() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(FtpConnection.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class))).thenReturn(ftpConnectionJerseyRequestMock);

        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(ftpConnectionJerseyRequestMock).when(ftpConnectionJerseyRequestMock).setContentType(ConnectionMediaType.FTP_JSON);
        doReturn(ftpConnectionOperationResultMock).when(ftpConnectionJerseyRequestMock).put(any(FtpConnection.class));

        OperationResult<FtpConnection> connection = connectionsService
                .connection(FtpConnection.class, ConnectionMediaType.FTP_TYPE, TEST_UUID)
                .update(new FtpConnection());
        //then
        assertSame(ftpConnectionOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(FtpConnection.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class));
        verify(sessionStorageMock).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(ftpConnectionJerseyRequestMock).setContentType(ConnectionMediaType.FTP_JSON);
        verify(ftpConnectionJerseyRequestMock).put(any(FtpConnection.class));
    }

    @Test
    public void should_return_proper_ftp_connection_operationResult_when_delete() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(Object.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

        doReturn(operationResultMock).when(jerseyRequestMock).delete();

        OperationResult connection = connectionsService
                .connection(TEST_UUID)
                .delete();
        //then
        assertSame(operationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(Object.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class));
        verify(jerseyRequestMock).delete();
    }

    @Test
    public void should_return_proper_local_file_system_connection_operationResult_when_get() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(LfsConnection.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class))).thenReturn(lfsConnectionJerseyRequestMock);

        doReturn(lfsConnectionOperationResultMock).when(lfsConnectionJerseyRequestMock).get();

        OperationResult<LfsConnection> connection = connectionsService
                .connection(LfsConnection.class, ConnectionMediaType.LOCAL_FILE_SYSTEM_TYPE, TEST_UUID)
                .get();
        //then
        assertSame(lfsConnectionOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(LfsConnection.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class));
        verify(lfsConnectionJerseyRequestMock).get();
    }

    @Test
    public void should_return_proper_local_file_system_connection_operationResult_when_create() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(LfsConnection.class)
                , eq(new String[]{"/connections"})
                , any(DefaultErrorHandler.class))).thenReturn(lfsConnectionJerseyRequestMock);

        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(lfsConnectionJerseyRequestMock).when(lfsConnectionJerseyRequestMock).setContentType(ConnectionMediaType.LOCAL_FILE_SYSTEM_JSON);
        doReturn(lfsConnectionOperationResultMock).when(lfsConnectionJerseyRequestMock).post(any(LfsConnection.class));

        OperationResult<LfsConnection> connection = connectionsService
                .connection(LfsConnection.class, ConnectionMediaType.LOCAL_FILE_SYSTEM_TYPE, TEST_UUID)
                .create(new LfsConnection());
        //then
        assertSame(lfsConnectionOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(LfsConnection.class)
                , eq(new String[]{"/connections"})
                , any(DefaultErrorHandler.class));
        verify(sessionStorageMock).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(lfsConnectionJerseyRequestMock).setContentType(ConnectionMediaType.LOCAL_FILE_SYSTEM_JSON);
        verify(lfsConnectionJerseyRequestMock).post(any(LfsConnection.class));

    }

    @Test
    public void should_return_proper_custom_data_source_connection_operationResult_when_get() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientCustomDataSource.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class))).thenReturn(customDataSourceJerseyRequestMock);

        doReturn(customDataSourceOperationResultMock).when(customDataSourceJerseyRequestMock).get();

        OperationResult<ClientCustomDataSource> connection = connectionsService
                .connection(ClientCustomDataSource.class, ConnectionMediaType.CUSTOM_DATA_SOURCE_TYPE, TEST_UUID)
                .get();
        //then
        assertSame(customDataSourceOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientCustomDataSource.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class));
        verify(customDataSourceJerseyRequestMock).get();

    }

    @Test
    public void should_return_proper_custom_data_source_connection_operationResult_when_create() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientCustomDataSource.class)
                , eq(new String[]{"/connections"})
                , any(DefaultErrorHandler.class))).thenReturn(customDataSourceJerseyRequestMock);

        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(customDataSourceJerseyRequestMock).when(customDataSourceJerseyRequestMock).setContentType(ConnectionMediaType.CUSTOM_DATA_SOURCE_JSON);
        doReturn(customDataSourceOperationResultMock).when(customDataSourceJerseyRequestMock).post(any(ClientCustomDataSource.class));

        OperationResult<ClientCustomDataSource> connection = connectionsService
                .connection(ClientCustomDataSource.class, ConnectionMediaType.CUSTOM_DATA_SOURCE_TYPE, TEST_UUID)
                .create(new ClientCustomDataSource());
        //then
        assertSame(customDataSourceOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientCustomDataSource.class)
                , eq(new String[]{"/connections"})
                , any(DefaultErrorHandler.class));
        verify(sessionStorageMock).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(customDataSourceJerseyRequestMock).setContentType(ConnectionMediaType.CUSTOM_DATA_SOURCE_JSON);
        verify(customDataSourceJerseyRequestMock).post(any(ClientCustomDataSource.class));

    }

    @Test
    public void should_return_proper_jndi_connection_operationResult_when_get() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientJndiJdbcDataSource.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class))).thenReturn(jndiJdbcDataSourceJerseyRequestMock);

        doReturn(jndiDataSourceOperationResultMock).when(jndiJdbcDataSourceJerseyRequestMock).get();

        OperationResult<ClientJndiJdbcDataSource> connection = connectionsService
                .connection(ClientJndiJdbcDataSource.class, ConnectionMediaType.JNDI_JDBC_DATA_SOURCE_TYPE, TEST_UUID)
                .get();
        //then
        assertSame(jndiDataSourceOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientJndiJdbcDataSource.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class));
        verify(jndiJdbcDataSourceJerseyRequestMock).get();
    }


    @Test
    public void should_return_proper_jndi_connection_operationResult_when_create() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientJndiJdbcDataSource.class)
                , eq(new String[]{"/connections"})
                , any(DefaultErrorHandler.class))).thenReturn(jndiJdbcDataSourceJerseyRequestMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(jndiJdbcDataSourceJerseyRequestMock).when(jndiJdbcDataSourceJerseyRequestMock).setContentType(ConnectionMediaType.JNDI_JDBC_DATA_SOURCE_JSON);
        doReturn(jndiDataSourceOperationResultMock).when(jndiJdbcDataSourceJerseyRequestMock).post(any(ClientJndiJdbcDataSource.class));

        OperationResult<ClientJndiJdbcDataSource> connection = connectionsService
                .connection(ClientJndiJdbcDataSource.class, ConnectionMediaType.JNDI_JDBC_DATA_SOURCE_TYPE, TEST_UUID)
                .create(new ClientJndiJdbcDataSource());
        //then
        assertSame(jndiDataSourceOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientJndiJdbcDataSource.class)
                , eq(new String[]{"/connections"})
                , any(DefaultErrorHandler.class));
        verify(sessionStorageMock).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(jndiJdbcDataSourceJerseyRequestMock).setContentType(ConnectionMediaType.JNDI_JDBC_DATA_SOURCE_JSON);
        verify(jndiJdbcDataSourceJerseyRequestMock).post(any(ClientJndiJdbcDataSource.class));

    }

    @Test
    public void should_return_proper_jdbc_connection_operationResult_when_get() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientJdbcDataSource.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class))).thenReturn(jdbcDataSourceJerseyRequestMock);

        doReturn(jdbcDataSourceOperationResultMock).when(jdbcDataSourceJerseyRequestMock).get();

        OperationResult<ClientJdbcDataSource> connection = connectionsService
                .connection(ClientJdbcDataSource.class, ConnectionMediaType.JDBC_DATA_SOURCE_TYPE, TEST_UUID)
                .get();
        //then
        assertSame(jdbcDataSourceOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientJdbcDataSource.class)
                , eq(new String[]{"/connections", TEST_UUID})
                , any(DefaultErrorHandler.class));
        verify(jdbcDataSourceJerseyRequestMock).get();
    }


    @Test
    public void should_return_proper_jdbc_connection_operationResult_when_create() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientJdbcDataSource.class)
                , eq(new String[]{"/connections"})
                , any(DefaultErrorHandler.class))).thenReturn(jdbcDataSourceJerseyRequestMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(jdbcDataSourceJerseyRequestMock).when(jdbcDataSourceJerseyRequestMock).setContentType(ConnectionMediaType.JDBC_DATA_SOURCE_JSON);
        doReturn(jdbcDataSourceOperationResultMock).when(jdbcDataSourceJerseyRequestMock).post(any(ClientJdbcDataSource.class));

        OperationResult<ClientJdbcDataSource> connection = connectionsService
                .connection(ClientJdbcDataSource.class, ConnectionMediaType.JDBC_DATA_SOURCE_TYPE, TEST_UUID)
                .create(new ClientJdbcDataSource());
        //then
        assertSame(jdbcDataSourceOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(ClientJdbcDataSource.class)
                , eq(new String[]{"/connections"})
                , any(DefaultErrorHandler.class));
        verify(sessionStorageMock).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(jdbcDataSourceJerseyRequestMock).setContentType(ConnectionMediaType.JDBC_DATA_SOURCE_JSON);
        verify(jdbcDataSourceJerseyRequestMock).post(any(ClientJdbcDataSource.class));
    }

    @Test
    public void should_return_proper_operationResult_when_get_metadata() throws Exception {
        //when
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(TableMetadata.class)
                , eq(new String[]{"/connections", TEST_UUID, "metadata"})
                , any(DefaultErrorHandler.class))).thenReturn(tableMetadataRequestMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(tableMetadataRequestMock).when(tableMetadataRequestMock).setAccept(ConnectionMediaType.JDBC_DATA_SOURCE_JSON);

        doReturn(tableMetadataOperationResultMock).when(tableMetadataRequestMock).get();

        OperationResult<TableMetadata> connection = connectionsService
                .connection(TEST_UUID,
                        TableMetadata.class,
                        ConnectionMediaType.JDBC_DATA_SOURCE_METADATA_TYPE)
                .metadata();
        //then
        assertSame(tableMetadataOperationResultMock, connection);
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock)
                , eq(TableMetadata.class)
                , eq(new String[]{"/connections", TEST_UUID, "metadata"})
                , any(DefaultErrorHandler.class));
        verify(sessionStorageMock).getConfiguration();
        verify(configurationMock).getAcceptMimeType();
        verify(tableMetadataRequestMock).setAccept(ConnectionMediaType.JDBC_DATA_SOURCE_METADATA_JSON);
        verify(tableMetadataRequestMock).get();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_return_throw_exception_when_create_connection_and_get_metadata_connection_is_invalid() throws Exception {
        //when
        connectionsService
                .connection(TEST_UUID,
                        TableMetadata.class,
                        ConnectionMediaType.JDBC_DATA_SOURCE_METADATA_TYPE)
                .createAndGetMetadata(new Object());
        //then
        // an exception should be thrown
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_update_connection_is_null() throws Exception {
        //when
        connectionsService
                .connection(TEST_UUID)
                .update(null);
        //then
        // an exception should be thrown

    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_create_connection_uuid_is_null() throws Exception {
        //when
        connectionsService
                .connection(null)
                .get();
        //then
        // an exception should be thrown
    }


    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_get_connection_uuid_is_empty() throws Exception {
        //when
        connectionsService
                .connection("")
                .get();
        //then
        // an exception should be thrown
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_update_connection_type_is_invalid() throws Exception {
        //when
        connectionsService
                .connection(TEST_UUID)
                .update(new Object());
        //then
        // an exception should be thrown
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_connection_class_is_invalid() throws Exception {
        //when
        connectionsService
                .connection(TEST_UUID)
                .create(new Object());
        //then
        // an exception should be thrown
    }

}