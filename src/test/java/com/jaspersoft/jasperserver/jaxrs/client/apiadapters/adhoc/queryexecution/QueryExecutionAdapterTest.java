package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientProvidedQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import javax.ws.rs.core.MultivaluedHashMap;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_AXES_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_AXES_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_LEVEL_DATA_JSON;
import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
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
@PrepareForTest({QueryExecutionService.class, JerseyRequest.class})
public class QueryExecutionAdapterTest extends PowerMockTestCase {
    public static final String QUERY_EXECUTIONS_URI = "queryExecutions";
    public static final String CONTENT_TYPE = "someContentType";
    public static final String ACCEPT_TYPE = "someAcceptType";
    public static final String SOME_UUID = "someUuid";
    public static final String DATA = "data";
    @Mock
    private SessionStorage storageMock;
    @Mock
    private JerseyRequest<ClientMultiLevelQueryResultData> multiLevelRequestMock;
    @Mock
    private OperationResult<ClientMultiLevelQueryResultData> multiLevelOperationResultMock;
    @Mock
    private JerseyRequest<ClientFlatQueryResultData> flatRequestMock;
    @Mock
    private OperationResult<ClientFlatQueryResultData> flatOperationResultMock;
    @Mock
    private JerseyRequest<ClientMultiAxesQueryResultData> multiAxesRequestMock;
    @Mock
    private OperationResult<ClientMultiAxesQueryResultData> multiAxesOperationResultMock;
    @Mock
    private JerseyRequest<ClientQueryResultData> requestMock;
    @Mock
    private OperationResult<ClientQueryResultData> operationResultMock;
    @Mock
    private RestClientConfiguration configurationMock;


    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @AfterMethod
    public void after() {
        reset(storageMock,
                multiLevelRequestMock,
                multiLevelOperationResultMock,
                flatRequestMock,
                flatOperationResultMock,
                multiAxesRequestMock,
                multiAxesOperationResultMock,
                requestMock,
                operationResultMock,
                configurationMock);
    }

    @Test
    public void should_set_proper_internal_state() {
        // When
        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock, CONTENT_TYPE, Object.class, ACCEPT_TYPE);

        //Then
        assertEquals(Whitebox.getInternalState(adapter, "dataSetClass"), Object.class);
        assertEquals(Whitebox.getInternalState(adapter, "contentType"), CONTENT_TYPE);
        assertEquals(Whitebox.getInternalState(adapter, "acceptType"), new String[]{ACCEPT_TYPE});
    }

    @Test
    public void should_set_proper_internal_state_with_params() {
        // When
        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock, CONTENT_TYPE, Object.class, ACCEPT_TYPE).
                offset(0).
                pageSize(10);

        //Then
        assertEquals(2, ((MultivaluedHashMap<String, String>) Whitebox.getInternalState(adapter, "params")).size());
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
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientMultiLevelQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(multiLevelRequestMock);
        doReturn(multiLevelRequestMock).when(multiLevelRequestMock).
                setContentType(EXECUTION_MULTI_LEVEL_QUERY_JSON);
        doReturn(multiLevelRequestMock).when(multiLevelRequestMock).
                setAccept(MULTI_LEVEL_DATA_JSON);
        doReturn(multiLevelOperationResultMock).when(multiLevelRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        QueryExecutionAdapter<ClientMultiLevelQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_MULTI_LEVEL_QUERY_JSON,
                ClientMultiLevelQueryResultData.class,
                MULTI_LEVEL_DATA_JSON);

        // When /
        OperationResult<ClientMultiLevelQueryResultData> retrieved = adapter.execute(new ClientMultiLevelQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, multiLevelOperationResultMock);
        verify(multiLevelRequestMock).
                setContentType(EXECUTION_MULTI_LEVEL_QUERY_JSON);
        verify(multiLevelRequestMock).
                setAccept(MULTI_LEVEL_DATA_JSON);
        verify(multiLevelRequestMock).post(any(ClientMultiLevelQueryExecution.class));
        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientMultiLevelQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_execute_flat_query() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
        doReturn(flatRequestMock).when(flatRequestMock).
                setContentType(EXECUTION_MULTI_LEVEL_QUERY_JSON);
        doReturn(flatRequestMock).when(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_MULTI_LEVEL_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON);

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.execute(new ClientMultiLevelQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
        verify(flatRequestMock).
                setContentType(EXECUTION_MULTI_LEVEL_QUERY_JSON);
        verify(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));
        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }


    @Test
    public void should_return_proper_operation_result_when_execute_multi_axes_query() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientMultiAxesQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(multiAxesRequestMock);
        doReturn(multiAxesRequestMock).when(multiAxesRequestMock).
                setContentType(EXECUTION_MULTI_AXES_QUERY_JSON);
        doReturn(multiAxesRequestMock).when(multiAxesRequestMock).
                setAccept(MULTI_AXES_DATA_JSON);
        doReturn(multiAxesOperationResultMock).when(multiAxesRequestMock).post(any(ClientMultiAxesQueryExecution.class));

        QueryExecutionAdapter<ClientMultiAxesQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_MULTI_AXES_QUERY_JSON,
                ClientMultiAxesQueryResultData.class,
                MULTI_AXES_DATA_JSON);

        // When /
        OperationResult<ClientMultiAxesQueryResultData> retrieved = adapter.execute(new ClientMultiAxesQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, multiAxesOperationResultMock);
        verify(multiAxesRequestMock).
                setContentType(EXECUTION_MULTI_AXES_QUERY_JSON);
        verify(multiAxesRequestMock).
                setAccept(MULTI_AXES_DATA_JSON);
        verify(multiAxesRequestMock).post(any(ClientMultiAxesQueryExecution.class));

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientMultiAxesQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_multi_axes_query() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientMultiAxesQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(multiAxesRequestMock);
        doReturn(multiAxesRequestMock).when(multiAxesRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(multiAxesRequestMock).when(multiAxesRequestMock).
                setAccept(MULTI_AXES_DATA_JSON);
        doReturn(multiAxesOperationResultMock).when(multiAxesRequestMock).post(any(ClientProvidedQueryExecution.class));

        QueryExecutionAdapter<ClientMultiAxesQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientMultiAxesQueryResultData.class,
                MULTI_AXES_DATA_JSON);

        // When /
        OperationResult<ClientMultiAxesQueryResultData> retrieved = adapter.execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, multiAxesOperationResultMock);
        verify(multiAxesRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        verify(multiAxesRequestMock).
                setAccept(MULTI_AXES_DATA_JSON);
        verify(multiAxesRequestMock).post(any(ClientProvidedQueryExecution.class));

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientMultiAxesQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }


    @Test
    public void should_return_proper_operation_result_when_execute_provided_multi_level_query() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientMultiLevelQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(multiLevelRequestMock);
        doReturn(multiLevelRequestMock).when(multiLevelRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(multiLevelRequestMock).when(multiLevelRequestMock).
                setAccept(MULTI_LEVEL_DATA_JSON);
        doReturn(multiLevelOperationResultMock).when(multiLevelRequestMock).post(any(ClientProvidedQueryExecution.class));

        QueryExecutionAdapter<ClientMultiLevelQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientMultiLevelQueryResultData.class,
                MULTI_LEVEL_DATA_JSON);

        // When /
        OperationResult<ClientMultiLevelQueryResultData> retrieved = adapter.execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, multiLevelOperationResultMock);
        verify(multiLevelRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        verify(multiLevelRequestMock).
                setAccept(MULTI_LEVEL_DATA_JSON);
        verify(multiLevelRequestMock).post(any(ClientProvidedQueryExecution.class));

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientMultiLevelQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_flat_query() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
        doReturn(flatRequestMock).when(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(flatRequestMock).when(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON);

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
        verify(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        verify(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_flat_query_result_as_xml() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(flatRequestMock).when(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(flatRequestMock).when(flatRequestMock).
                setAccept(FLAT_DATA_XML);
        doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON);

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.asXml().execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
        verify(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        verify(flatRequestMock).
                setAccept(FLAT_DATA_XML);
        verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }
    @Test
    public void should_return_proper_operation_result_when_execute_provided_flat_query_result_as_xml_from_config() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        doReturn(flatRequestMock).when(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(flatRequestMock).when(flatRequestMock).
                setAccept(FLAT_DATA_XML);
        doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_XML);

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.asXml().execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
        verify(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        verify(flatRequestMock).
                setAccept(FLAT_DATA_XML);
        verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_flat_query_result_as_json() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(flatRequestMock).when(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(flatRequestMock).when(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON);

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.asJson().execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
        verify(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        verify(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_flat_query_result_as_json_whn_config_xml() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        doReturn(flatRequestMock).when(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(flatRequestMock).when(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON);

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.asJson().execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
        verify(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        verify(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        verify(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_get_fragment_provided_flat_query() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(requestMock).when(requestMock).
                setAccept(FLAT_DATA_JSON);
        doReturn(operationResultMock).when(requestMock).get();

        QueryExecutionAdapter<ClientQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientQueryResultData.class,
                FLAT_DATA_JSON);

        // When /
        OperationResult<ClientQueryResultData> retrieved = adapter.retrieveData(SOME_UUID);

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(requestMock).
                setAccept(FLAT_DATA_JSON);
        verify(requestMock).get();
        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_get_fragment_provided_flat_query_with_params() {
        // Given
        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = spy(new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON));
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
        doReturn(adapter).when(adapter).offset(anyInt());
        doReturn(adapter).when(adapter).pageSize(anyInt());
        doReturn(flatRequestMock).when(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(flatRequestMock).when(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        doReturn(flatOperationResultMock).when(flatRequestMock).get();

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.offset(0).pageSize(100).retrieveData(SOME_UUID);

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
        verify(adapter).offset(anyInt());
        verify(adapter).pageSize(anyInt());
        verify(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        verify(flatRequestMock).get();

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_proper_operation_result_and_set_internal_state_when_get_fragment_provided_flat_query_with_params() {
        // Given
        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = spy(new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON));
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
        doReturn(flatRequestMock).when(flatRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(flatRequestMock).when(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        doReturn(flatOperationResultMock).when(flatRequestMock).get();

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.offset(0).pageSize(100).retrieveData(SOME_UUID);

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
        verify(adapter).offset(anyInt());
        verify(adapter).pageSize(anyInt());
        assertEquals(2, ((MultivaluedHashMap<String, String>) Whitebox.getInternalState(adapter, "params")).size());
        verify(flatRequestMock, never()).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        verify(flatRequestMock).
                setAccept(FLAT_DATA_JSON);
        verify(flatRequestMock).get();

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID, DATA}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_proper_operation_result_when_delete_query_execution() {
        // Given
        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = spy(new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON));
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID}),
                any(DefaultErrorHandler.class))).thenReturn(flatRequestMock);
        doReturn(flatOperationResultMock).when(flatRequestMock).delete();

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.deleteExecution(SOME_UUID);

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
        verify(flatRequestMock).delete();
        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID}),
                any(DefaultErrorHandler.class));
    }
}