package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context;

import com.jaspersoft.jasperserver.dto.connection.FtpConnection;
import com.jaspersoft.jasperserver.dto.connection.LfsConnection;
import com.jaspersoft.jasperserver.dto.connection.metadata.TableMetadata;
import com.jaspersoft.jasperserver.dto.resources.ClientCustomDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientJdbcDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientJndiJdbcDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
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
public class SingleContextAdapterTest {

    public static final String TEST_UUID = "someUuId";
    public static final String CONTEXTS_URI = "contexts";

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
    private JerseyRequest<PresentationGroupElement> presentationGroupElementRequestMock;
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
    @Mock
    private OperationResult<PresentationGroupElement> presentationGroupElementOperationResultMock;

    private ContextService contextService;
    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
        contextService = new ContextService(sessionStorageMock);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock
                , configurationMock
                , jerseyRequestMock
                , ftpConnectionJerseyRequestMock
                , lfsConnectionJerseyRequestMock
                , customDataSourceJerseyRequestMock
                , jndiJdbcDataSourceJerseyRequestMock
                , jdbcDataSourceJerseyRequestMock
                , tableMetadataRequestMock
                , presentationGroupElementRequestMock
                , operationResultMock
                , ftpConnectionOperationResultMock
                , lfsConnectionOperationResultMock
                , customDataSourceOperationResultMock
                , jndiDataSourceOperationResultMock
                , jdbcDataSourceOperationResultMock
                , tableMetadataOperationResultMock
                , presentationGroupElementOperationResultMock);
        mockitoSession.close();
    }

    private Object getInternalState(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName + " not found in class hierarchy of " + obj.getClass().getName());
    }

    @Test
    public void should_return_proper_adapter_with_proper_internal_state() throws Exception {
        //when
        SingleContextAdapter contextAdapter = new SingleContextAdapter(sessionStorageMock,
                ClientDomain.class,
                ContextMediaTypes.DOMAIN_JSON);
        //then
        assertEquals(getInternalState(contextAdapter, "contextContentMimeType"), ContextMediaTypes.DOMAIN_JSON);
        assertEquals(getInternalState(contextAdapter, "contextClass"), ClientDomain.class);
        assertNull(getInternalState(contextAdapter, "uuId"));
        assertNull(getInternalState(contextAdapter, "metadataClass"));
        assertNull(getInternalState(contextAdapter, "metadataMimeType"));
    }

    @Test
    public void should_return_proper_ftp_connection_operationResult_when_get() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(FtpConnection.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class))).thenReturn(ftpConnectionJerseyRequestMock);
            doReturn(ftpConnectionOperationResultMock).when(ftpConnectionJerseyRequestMock).get();

            OperationResult<FtpConnection> connection = contextService
                    .context(FtpConnection.class, "someMiMeType", TEST_UUID)
                    .get();

            assertSame(ftpConnectionOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(FtpConnection.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(ftpConnectionJerseyRequestMock).get();
        }
    }

    @Test
    public void should_return_proper_ftp_connection_operationResult_when_create() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(FtpConnection.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class))).thenReturn(ftpConnectionJerseyRequestMock);
            doReturn(ftpConnectionJerseyRequestMock).when(ftpConnectionJerseyRequestMock).setContentType(ContextMediaTypes.FTP_JSON);
            doReturn(ftpConnectionOperationResultMock).when(ftpConnectionJerseyRequestMock).post(any(FtpConnection.class));

            OperationResult<FtpConnection> connection = contextService
                    .context(FtpConnection.class, ContextMediaTypes.FTP_JSON, TEST_UUID)
                    .create(new FtpConnection());

            assertSame(ftpConnectionOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(FtpConnection.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(ftpConnectionJerseyRequestMock).setContentType(ContextMediaTypes.FTP_JSON);
            verify(ftpConnectionJerseyRequestMock).post(any(FtpConnection.class));
        }
    }

    @Test
    public void should_return_proper_ftp_connection_operationResult_when_update() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(FtpConnection.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class))).thenReturn(ftpConnectionJerseyRequestMock);
            doReturn(ftpConnectionJerseyRequestMock).when(ftpConnectionJerseyRequestMock).setContentType(ContextMediaTypes.FTP_JSON);
            doReturn(ftpConnectionOperationResultMock).when(ftpConnectionJerseyRequestMock).put(any(FtpConnection.class));

            OperationResult<FtpConnection> connection = contextService
                    .context(FtpConnection.class, ContextMediaTypes.FTP_JSON, TEST_UUID)
                    .update(new FtpConnection());

            assertSame(ftpConnectionOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(FtpConnection.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(ftpConnectionJerseyRequestMock).setContentType(ContextMediaTypes.FTP_JSON);
            verify(ftpConnectionJerseyRequestMock).put(any(FtpConnection.class));
        }
    }

    @Test
    public void should_return_proper_ftp_connection_operationResult_when_delete() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(Object.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).delete();

            OperationResult connection = contextService
                    .context(TEST_UUID)
                    .delete();

            assertSame(operationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(Object.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(jerseyRequestMock).delete();
        }
    }

    @Test
    public void should_return_proper_local_file_system_connection_operationResult_when_get() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(LfsConnection.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class))).thenReturn(lfsConnectionJerseyRequestMock);
            doReturn(lfsConnectionOperationResultMock).when(lfsConnectionJerseyRequestMock).get();

            OperationResult<LfsConnection> connection = contextService
                    .context(LfsConnection.class, ContextMediaTypes.LOCAL_FILE_SYSTEM_JSON, TEST_UUID)
                    .get();

            assertSame(lfsConnectionOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(LfsConnection.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(lfsConnectionJerseyRequestMock).get();
        }
    }

    @Test
    public void should_return_proper_local_file_system_connection_operationResult_when_create() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(LfsConnection.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class))).thenReturn(lfsConnectionJerseyRequestMock);
            doReturn(lfsConnectionJerseyRequestMock).when(lfsConnectionJerseyRequestMock).setContentType(ContextMediaTypes.LOCAL_FILE_SYSTEM_JSON);
            doReturn(lfsConnectionOperationResultMock).when(lfsConnectionJerseyRequestMock).post(any(LfsConnection.class));

            OperationResult<LfsConnection> connection = contextService
                    .context(LfsConnection.class, ContextMediaTypes.LOCAL_FILE_SYSTEM_JSON, TEST_UUID)
                    .create(new LfsConnection());

            assertSame(lfsConnectionOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(LfsConnection.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(lfsConnectionJerseyRequestMock).setContentType(ContextMediaTypes.LOCAL_FILE_SYSTEM_JSON);
            verify(lfsConnectionJerseyRequestMock).post(any(LfsConnection.class));
        }
    }

    @Test
    public void should_return_proper_custom_data_source_connection_operationResult_when_get() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientCustomDataSource.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class))).thenReturn(customDataSourceJerseyRequestMock);
            doReturn(customDataSourceOperationResultMock).when(customDataSourceJerseyRequestMock).get();

            OperationResult<ClientCustomDataSource> connection = contextService
                    .context(ClientCustomDataSource.class, ContextMediaTypes.CUSTOM_DATA_SOURCE_JSON, TEST_UUID)
                    .get();

            assertSame(customDataSourceOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientCustomDataSource.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(customDataSourceJerseyRequestMock).get();
        }
    }

    @Test
    public void should_return_proper_custom_data_source_connection_operationResult_when_create() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientCustomDataSource.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class))).thenReturn(customDataSourceJerseyRequestMock);
            doReturn(customDataSourceJerseyRequestMock).when(customDataSourceJerseyRequestMock).setContentType(ContextMediaTypes.CUSTOM_DATA_SOURCE_JSON);
            doReturn(customDataSourceOperationResultMock).when(customDataSourceJerseyRequestMock).post(any(ClientCustomDataSource.class));

            OperationResult<ClientCustomDataSource> connection = contextService
                    .context(ClientCustomDataSource.class, ContextMediaTypes.CUSTOM_DATA_SOURCE_JSON, TEST_UUID)
                    .create(new ClientCustomDataSource());

            assertSame(customDataSourceOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientCustomDataSource.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(customDataSourceJerseyRequestMock).setContentType(ContextMediaTypes.CUSTOM_DATA_SOURCE_JSON);
            verify(customDataSourceJerseyRequestMock).post(any(ClientCustomDataSource.class));
        }
    }

    @Test
    public void should_return_proper_jndi_connection_operationResult_when_get() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientJndiJdbcDataSource.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class))).thenReturn(jndiJdbcDataSourceJerseyRequestMock);
            doReturn(jndiDataSourceOperationResultMock).when(jndiJdbcDataSourceJerseyRequestMock).get();

            OperationResult<ClientJndiJdbcDataSource> connection = contextService
                    .context(ClientJndiJdbcDataSource.class, ContextMediaTypes.JNDI_JDBC_DATA_SOURCE_JSON, TEST_UUID)
                    .get();

            assertSame(jndiDataSourceOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientJndiJdbcDataSource.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(jndiJdbcDataSourceJerseyRequestMock).get();
        }
    }

    @Test
    public void should_return_proper_jndi_connection_operationResult_when_create() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientJndiJdbcDataSource.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class))).thenReturn(jndiJdbcDataSourceJerseyRequestMock);
            doReturn(jndiJdbcDataSourceJerseyRequestMock).when(jndiJdbcDataSourceJerseyRequestMock).setContentType(ContextMediaTypes.JNDI_JDBC_DATA_SOURCE_JSON);
            doReturn(jndiDataSourceOperationResultMock).when(jndiJdbcDataSourceJerseyRequestMock).post(any(ClientJndiJdbcDataSource.class));

            OperationResult<ClientJndiJdbcDataSource> connection = contextService
                    .context(ClientJndiJdbcDataSource.class, ContextMediaTypes.JNDI_JDBC_DATA_SOURCE_JSON, TEST_UUID)
                    .create(new ClientJndiJdbcDataSource());

            assertSame(jndiDataSourceOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientJndiJdbcDataSource.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(jndiJdbcDataSourceJerseyRequestMock).setContentType(ContextMediaTypes.JNDI_JDBC_DATA_SOURCE_JSON);
            verify(jndiJdbcDataSourceJerseyRequestMock).post(any(ClientJndiJdbcDataSource.class));
        }
    }

    @Test
    public void should_return_proper_jdbc_connection_operationResult_when_get() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientJdbcDataSource.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class))).thenReturn(jdbcDataSourceJerseyRequestMock);
            doReturn(jdbcDataSourceOperationResultMock).when(jdbcDataSourceJerseyRequestMock).get();

            OperationResult<ClientJdbcDataSource> connection = contextService
                    .context(ClientJdbcDataSource.class, ContextMediaTypes.JDBC_DATA_SOURCE_JSON, TEST_UUID)
                    .get();

            assertSame(jdbcDataSourceOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientJdbcDataSource.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(jdbcDataSourceJerseyRequestMock).get();
        }
    }

    @Test
    public void should_return_proper_jdbc_connection_operationResult_when_create() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientJdbcDataSource.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class))).thenReturn(jdbcDataSourceJerseyRequestMock);
            doReturn(jdbcDataSourceJerseyRequestMock).when(jdbcDataSourceJerseyRequestMock).setContentType(ContextMediaTypes.JDBC_DATA_SOURCE_JSON);
            doReturn(jdbcDataSourceOperationResultMock).when(jdbcDataSourceJerseyRequestMock).post(any(ClientJdbcDataSource.class));

            OperationResult<ClientJdbcDataSource> connection = contextService
                    .context(ClientJdbcDataSource.class, ContextMediaTypes.JDBC_DATA_SOURCE_JSON, TEST_UUID)
                    .create(new ClientJdbcDataSource());

            assertSame(jdbcDataSourceOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(ClientJdbcDataSource.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(jdbcDataSourceJerseyRequestMock).setContentType(ContextMediaTypes.JDBC_DATA_SOURCE_JSON);
            verify(jdbcDataSourceJerseyRequestMock).post(any(ClientJdbcDataSource.class));
        }
    }

    @Test
    public void should_return_proper_operationResult_when_create_context_and_get_metadata() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(PresentationGroupElement.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class))).thenReturn(presentationGroupElementRequestMock);
            doReturn(presentationGroupElementRequestMock).when(presentationGroupElementRequestMock).setAccept(ContextMediaTypes.DOMAIN_METADATA_JSON);
            doReturn(presentationGroupElementRequestMock).when(presentationGroupElementRequestMock).setContentType(ContextMediaTypes.DOMAIN_JSON);
            doReturn(presentationGroupElementOperationResultMock).when(presentationGroupElementRequestMock).post(any(ClientDomain.class));

            OperationResult<PresentationGroupElement> metadata = contextService
                    .context(ClientDomain.class, ContextMediaTypes.DOMAIN_JSON, PresentationGroupElement.class,
                            ContextMediaTypes.DOMAIN_METADATA_JSON)
                    .createAndGetMetadata(new ClientDomain());

            assertSame(presentationGroupElementOperationResultMock, metadata);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(PresentationGroupElement.class)
                    , eq(new String[]{CONTEXTS_URI})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(presentationGroupElementRequestMock).setAccept(ContextMediaTypes.DOMAIN_METADATA_JSON);
            verify(presentationGroupElementRequestMock).post(any(ClientDomain.class));
        }
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_get_metadata_Uuid_is_null() {
        contextService
                .context(null, TableMetadata.class,
                        ContextMediaTypes.JDBC_DATA_SOURCE_METADATA_JSON)
                .metadata();
    }

    @Test
    public void should_return_proper_operationResult_when_get_metadata() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(TableMetadata.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID, "metadata"})
                    , any(DefaultErrorHandler.class))).thenReturn(tableMetadataRequestMock);
            doReturn(tableMetadataRequestMock).when(tableMetadataRequestMock).setAccept(ContextMediaTypes.JDBC_DATA_SOURCE_JSON);
            doReturn(tableMetadataOperationResultMock).when(tableMetadataRequestMock).get();

            OperationResult<TableMetadata> connection = contextService
                    .context(TEST_UUID, TableMetadata.class,
                            ContextMediaTypes.JDBC_DATA_SOURCE_METADATA_JSON)
                    .metadata();

            assertSame(tableMetadataOperationResultMock, connection);
            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock)
                    , eq(TableMetadata.class)
                    , eq(new String[]{CONTEXTS_URI, TEST_UUID, "metadata"})
                    , any(DefaultErrorHandler.class)), times(1));
            verify(tableMetadataRequestMock).setAccept(ContextMediaTypes.JDBC_DATA_SOURCE_METADATA_JSON);
            verify(tableMetadataRequestMock).get();
        }
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_update_connection_is_null() {
        contextService
                .context(Object.class, "someMiMeType")
                .create(null);
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_create_connection_uuid_is_null() {
        contextService
                .context(null)
                .get();
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_get_connection_uuid_is_empty() {
        contextService
                .context("")
                .get();
    }
}
