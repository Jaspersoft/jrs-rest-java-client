package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.AbstractClientExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientExecutionListWrapper;
import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxisQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxisQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientProvidedQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ExecutionStatusObject;
import com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_AXIS_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_AXIS_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_LEVEL_DATA_JSON;
import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class QueryExecutionAdapterTest {
    public static final String QUERY_EXECUTIONS_URI = "queryExecutions";
    public static final String CONTENT_TYPE = "someContentType";
    public static final String ACCEPT_TYPE = "someAcceptType";
    public static final String SOME_UUID = "someUuid";
    public static final String DATA = "data";

    @Mock
    private SessionStorage storageMock;
    @Mock
    private JerseyRequest requestMock;
    @Mock
    private JerseyRequest<ClientMultiLevelQueryResultData> multiLevelRequestMock;
    @Mock
    private OperationResult operationResultMock;
    @Mock
    private OperationResult<ClientMultiLevelQueryResultData> multiLevelOperationResultMock;
    @Mock
    private JerseyRequest<ClientFlatQueryResultData> flatRequestMock;
    @Mock
    private OperationResult<ClientFlatQueryResultData> flatOperationResultMock;
    @Mock
    private JerseyRequest<ClientMultiAxisQueryResultData> multiAxisRequestMock;
    @Mock
    private OperationResult<ClientMultiAxisQueryResultData> multiAxisOperationResultMock;
    @Mock
    private JerseyRequest<ClientQueryResultData> clientQueryRequestMock;
    @Mock
    private OperationResult<ClientQueryResultData> clientQueryOperationResultMock;
    @Mock
    private RestClientConfiguration configurationMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
    }

    @AfterMethod
    public void after() throws Exception {
        reset(storageMock,
                requestMock,
                multiLevelRequestMock,
                operationResultMock,
                multiLevelOperationResultMock,
                flatRequestMock,
                flatOperationResultMock,
                multiAxisRequestMock,
                multiAxisOperationResultMock,
                clientQueryRequestMock,
                clientQueryOperationResultMock,
                configurationMock);
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
        throw new NoSuchFieldException(fieldName);
    }

    @Test
    public void should_set_proper_internal_state() throws Exception {
        // When
        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock, CONTENT_TYPE, Object.class, ACCEPT_TYPE);

        //Then
        assertEquals(getInternalState(adapter, "responseClass"), Object.class);
        assertEquals(getInternalState(adapter, "contentType"), CONTENT_TYPE);
        assertEquals(getInternalState(adapter, "acceptType"), new String[]{ACCEPT_TYPE});
    }

    @Test
    public void should_set_proper_internal_state_with_params() throws Exception {
        // When
        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock, CONTENT_TYPE, Object.class, ACCEPT_TYPE).
                offset(0).
                pageSize(10);

        //Then
        assertEquals(2, ((MultivaluedHashMap<String, String>) getInternalState(adapter, "params")).size());
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_query_is_null() {
        // When
        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock, CONTENT_TYPE, Object.class, ACCEPT_TYPE);
        adapter.execute(null);
        //Then
        // An exception should be thrown
    }

    @Test
    public void should_return_proper_operation_result_when_execute_multi_level_query() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientMultiLevelQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(multiLevelRequestMock);
            doReturn(multiLevelRequestMock).when(multiLevelRequestMock).setContentType(EXECUTION_MULTI_LEVEL_QUERY_JSON);
            doReturn(multiLevelRequestMock).when(multiLevelRequestMock).setAccept(MULTI_LEVEL_DATA_JSON);
            doReturn(multiLevelOperationResultMock).when(multiLevelRequestMock).post(any(ClientMultiLevelQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_MULTI_LEVEL_QUERY_JSON,
                    ClientMultiLevelQueryResultData.class,
                    MULTI_LEVEL_DATA_JSON);

            OperationResult<ClientMultiLevelQueryResultData> retrieved = adapter.execute(new ClientMultiLevelQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, multiLevelOperationResultMock);
            verify(multiLevelRequestMock).setContentType(EXECUTION_MULTI_LEVEL_QUERY_JSON);
            verify(multiLevelRequestMock).setAccept(MULTI_LEVEL_DATA_JSON);
            verify(multiLevelRequestMock).post(any(ClientMultiLevelQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientMultiLevelQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_flatData_operation_result_when_execute_multi_level_query() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
            doReturn(flatRequestMock).when(flatRequestMock).setContentType(EXECUTION_MULTI_LEVEL_QUERY_JSON);
            doReturn(flatRequestMock).when(flatRequestMock).setAccept(FLAT_DATA_JSON);
            doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_MULTI_LEVEL_QUERY_JSON,
                    ClientMultiLevelQueryResultData.class,
                    MULTI_LEVEL_DATA_JSON);

            OperationResult<ClientMultiLevelQueryResultData> retrieved = adapter.
                    asResultDataSet(QueryResultDataMediaType.FLAT_DATA_JSON).
                    execute(new ClientMultiLevelQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, flatOperationResultMock);
            verify(flatRequestMock).setContentType(EXECUTION_MULTI_LEVEL_QUERY_JSON);
            verify(flatRequestMock).setAccept(FLAT_DATA_JSON);
            verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_execute_flat_query() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
            doReturn(flatRequestMock).when(flatRequestMock).setContentType(EXECUTION_MULTI_LEVEL_QUERY_JSON);
            doReturn(flatRequestMock).when(flatRequestMock).setAccept(FLAT_DATA_JSON);
            doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_MULTI_LEVEL_QUERY_JSON,
                    ClientFlatQueryResultData.class,
                    FLAT_DATA_JSON);

            OperationResult<ClientFlatQueryResultData> retrieved = adapter.execute(new ClientMultiLevelQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, flatOperationResultMock);
            verify(flatRequestMock).setContentType(EXECUTION_MULTI_LEVEL_QUERY_JSON);
            verify(flatRequestMock).setAccept(FLAT_DATA_JSON);
            verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_execute_multi_axis_query() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientMultiAxisQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(multiAxisRequestMock);
            doReturn(multiAxisRequestMock).when(multiAxisRequestMock).setContentType(EXECUTION_MULTI_AXIS_QUERY_JSON);
            doReturn(multiAxisRequestMock).when(multiAxisRequestMock).setAccept(MULTI_AXIS_DATA_JSON);
            doReturn(multiAxisOperationResultMock).when(multiAxisRequestMock).post(any(ClientMultiAxisQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_MULTI_AXIS_QUERY_JSON,
                    ClientMultiAxisQueryResultData.class,
                    MULTI_AXIS_DATA_JSON);

            OperationResult<ClientMultiAxisQueryResultData> retrieved = adapter.execute(new ClientMultiAxisQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, multiAxisOperationResultMock);
            verify(multiAxisRequestMock).setContentType(EXECUTION_MULTI_AXIS_QUERY_JSON);
            verify(multiAxisRequestMock).setAccept(MULTI_AXIS_DATA_JSON);
            verify(multiAxisRequestMock).post(any(ClientMultiAxisQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientMultiAxisQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_multi_axis_query() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientMultiAxisQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(multiAxisRequestMock);
            doReturn(multiAxisRequestMock).when(multiAxisRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            doReturn(multiAxisRequestMock).when(multiAxisRequestMock).setAccept(MULTI_AXIS_DATA_JSON);
            doReturn(multiAxisOperationResultMock).when(multiAxisRequestMock).post(any(ClientProvidedQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_PROVIDED_QUERY_JSON,
                    ClientMultiAxisQueryResultData.class,
                    MULTI_AXIS_DATA_JSON);

            OperationResult<ClientMultiAxisQueryResultData> retrieved = adapter.execute(new ClientProvidedQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, multiAxisOperationResultMock);
            verify(multiAxisRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            verify(multiAxisRequestMock).setAccept(MULTI_AXIS_DATA_JSON);
            verify(multiAxisRequestMock).post(any(ClientProvidedQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientMultiAxisQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_multi_level_query() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientMultiLevelQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(multiLevelRequestMock);
            doReturn(multiLevelRequestMock).when(multiLevelRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            doReturn(multiLevelRequestMock).when(multiLevelRequestMock).setAccept(MULTI_LEVEL_DATA_JSON);
            doReturn(multiLevelOperationResultMock).when(multiLevelRequestMock).post(any(ClientProvidedQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_PROVIDED_QUERY_JSON,
                    ClientMultiLevelQueryResultData.class,
                    MULTI_LEVEL_DATA_JSON);

            OperationResult<ClientMultiLevelQueryResultData> retrieved = adapter.execute(new ClientProvidedQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, multiLevelOperationResultMock);
            verify(multiLevelRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            verify(multiLevelRequestMock).setAccept(MULTI_LEVEL_DATA_JSON);
            verify(multiLevelRequestMock).post(any(ClientProvidedQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientMultiLevelQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_flat_query() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
            doReturn(flatRequestMock).when(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            doReturn(flatRequestMock).when(flatRequestMock).setAccept(FLAT_DATA_JSON);
            doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_PROVIDED_QUERY_JSON,
                    ClientFlatQueryResultData.class,
                    FLAT_DATA_JSON);

            OperationResult<ClientFlatQueryResultData> retrieved = adapter.execute(new ClientMultiLevelQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, flatOperationResultMock);
            verify(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            verify(flatRequestMock).setAccept(FLAT_DATA_JSON);
            verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_flat_query_result_as_xml() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
            doReturn(configurationMock).when(storageMock).getConfiguration();
            doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
            doReturn(flatRequestMock).when(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            doReturn(flatRequestMock).when(flatRequestMock).setAccept(FLAT_DATA_XML);
            doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_PROVIDED_QUERY_JSON,
                    ClientFlatQueryResultData.class,
                    FLAT_DATA_JSON);

            OperationResult<ClientFlatQueryResultData> retrieved = adapter.asXml().execute(new ClientMultiLevelQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, flatOperationResultMock);
            verify(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            verify(flatRequestMock).setAccept(FLAT_DATA_XML);
            verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_flat_query_result_as_xml_from_config() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
            doReturn(configurationMock).when(storageMock).getConfiguration();
            doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
            doReturn(flatRequestMock).when(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            doReturn(flatRequestMock).when(flatRequestMock).setAccept(FLAT_DATA_XML);
            doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_PROVIDED_QUERY_JSON,
                    ClientFlatQueryResultData.class,
                    FLAT_DATA_XML);

            OperationResult<ClientFlatQueryResultData> retrieved = adapter.asXml().execute(new ClientMultiLevelQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, flatOperationResultMock);
            verify(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            verify(flatRequestMock).setAccept(FLAT_DATA_XML);
            verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_flat_query_result_as_json() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
            doReturn(configurationMock).when(storageMock).getConfiguration();
            doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
            doReturn(flatRequestMock).when(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            doReturn(flatRequestMock).when(flatRequestMock).setAccept(FLAT_DATA_JSON);
            doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_PROVIDED_QUERY_JSON,
                    ClientFlatQueryResultData.class,
                    FLAT_DATA_JSON);

            OperationResult<ClientFlatQueryResultData> retrieved = adapter.asJson().execute(new ClientMultiLevelQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, flatOperationResultMock);
            verify(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            verify(flatRequestMock).setAccept(FLAT_DATA_JSON);
            verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_flat_query_result_as_json_whn_config_xml() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
            doReturn(configurationMock).when(storageMock).getConfiguration();
            doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
            doReturn(flatRequestMock).when(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            doReturn(flatRequestMock).when(flatRequestMock).setAccept(FLAT_DATA_JSON);
            doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_PROVIDED_QUERY_JSON,
                    ClientFlatQueryResultData.class,
                    FLAT_DATA_JSON);

            OperationResult<ClientFlatQueryResultData> retrieved = adapter.asJson().execute(new ClientMultiLevelQueryExecution());

            assertNotNull(retrieved);
            assertSame(retrieved, flatOperationResultMock);
            verify(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            verify(flatRequestMock).setAccept(FLAT_DATA_JSON);
            verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_get_fragment_provided_flat_query() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                    any(DefaultErrorHandler.class))).thenReturn(clientQueryRequestMock);
            doReturn(clientQueryRequestMock).when(clientQueryRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            doReturn(clientQueryRequestMock).when(clientQueryRequestMock).setAccept(FLAT_DATA_JSON);
            doReturn(clientQueryOperationResultMock).when(clientQueryRequestMock).get();

            QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                    EXECUTION_PROVIDED_QUERY_JSON,
                    ClientQueryResultData.class,
                    FLAT_DATA_JSON);

            OperationResult<ClientQueryResultData> retrieved = adapter.retrieveData(SOME_UUID);

            assertNotNull(retrieved);
            assertSame(retrieved, clientQueryOperationResultMock);
            verify(clientQueryRequestMock).setAccept(FLAT_DATA_JSON);
            verify(clientQueryRequestMock).get();
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_get_fragment_provided_flat_query_with_params() {
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON));

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                    any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
            doReturn(adapter).when(adapter).offset(anyInt());
            doReturn(adapter).when(adapter).pageSize(anyInt());
            doReturn(flatRequestMock).when(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            doReturn(flatRequestMock).when(flatRequestMock).setAccept(FLAT_DATA_JSON);
            doReturn(flatOperationResultMock).when(flatRequestMock).get();

            OperationResult<ClientFlatQueryResultData> retrieved = adapter.offset(0).pageSize(100).retrieveData(SOME_UUID);

            assertNotNull(retrieved);
            assertSame(retrieved, flatOperationResultMock);
            verify(adapter).offset(anyInt());
            verify(adapter).pageSize(anyInt());
            verify(flatRequestMock).setAccept(FLAT_DATA_JSON);
            verify(flatRequestMock).get();
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_and_set_internal_state_when_get_fragment_provided_flat_query_with_params() throws Exception {
        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON);

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                    any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
            doReturn(flatRequestMock).when(flatRequestMock).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            doReturn(flatRequestMock).when(flatRequestMock).setAccept(FLAT_DATA_JSON);
            doReturn(flatOperationResultMock).when(flatRequestMock).get();

            OperationResult<ClientFlatQueryResultData> retrieved = adapter.offset(0).pageSize(100).retrieveData(SOME_UUID);

            assertNotNull(retrieved);
            assertSame(retrieved, flatOperationResultMock);
            assertEquals(2, ((MultivaluedHashMap<String, String>) getInternalState(adapter, "params")).size());
            verify(flatRequestMock, never()).setContentType(EXECUTION_PROVIDED_QUERY_JSON);
            verify(flatRequestMock).setAccept(FLAT_DATA_JSON);
            verify(flatRequestMock).get();
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientFlatQueryResultData.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_delete_execution() {
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock, SOME_UUID));

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(Object.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID}),
                    any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).delete();

            OperationResult<ClientFlatQueryResultData> retrieved = adapter.delete();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            verify(requestMock).delete();
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(Object.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_get_execution() {
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock, SOME_UUID));

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(AbstractClientExecution.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID}),
                    any(DefaultErrorHandler.class))).thenReturn(requestMock);

            ClientMultiLevelQueryExecution clientMultiLevelQueryExecution = mock(ClientMultiLevelQueryExecution.class);
            doReturn(clientMultiLevelQueryExecution).when(operationResultMock).getEntity();
            doReturn(operationResultMock).when(requestMock).get();

            OperationResult<ClientMultiLevelQueryExecution> retrieved = adapter.get();

            assertSame(retrieved.getEntity(), clientMultiLevelQueryExecution);
            assertSame(retrieved, operationResultMock);
            verify(requestMock).get();
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(AbstractClientExecution.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_get_all_executions() {
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock));

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientExecutionListWrapper.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();

            OperationResult<ClientExecutionListWrapper> retrieved = adapter.getExecutions();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            verify(requestMock).get();
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ClientExecutionListWrapper.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_get_status_of_execution() {
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock));

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(storageMock),
                    eq(ExecutionStatusObject.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, "status"}),
                    any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();

            OperationResult<ExecutionStatusObject> retrieved = adapter.status();

            assertSame(retrieved, operationResultMock);
            verify(requestMock).get();
            mockedStatic.verify(() -> buildRequest(
                    eq(storageMock),
                    eq(ExecutionStatusObject.class),
                    eq(new String[]{QUERY_EXECUTIONS_URI, "status"}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }
}
