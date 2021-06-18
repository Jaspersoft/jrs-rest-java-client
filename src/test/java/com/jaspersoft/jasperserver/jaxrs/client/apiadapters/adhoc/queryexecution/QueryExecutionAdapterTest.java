package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxisQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxisQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientProvidedQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ExecutionStatusObject;
import com.jaspersoft.jasperserver.dto.executions.AbstractClientExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientExecutionListWrapper;
import com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;

import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_AXIS_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_AXIS_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_LEVEL_DATA_JSON;
import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
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
@PrepareForTest({JerseyRequest.class})
public class QueryExecutionAdapterTest extends PowerMockTestCase {
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


    @BeforeMethod
    public void before() {
        initMocks(this);
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
    }

    @AfterMethod
    public void after() {
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
    }

    @Test
    public void should_set_proper_internal_state() {
        // When
        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock, CONTENT_TYPE, Object.class, ACCEPT_TYPE);

        //Then
        assertEquals(Whitebox.getInternalState(adapter, "responseClass"), Object.class);
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

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
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
    public void should_return_proper_flatData_operation_result_when_execute_multi_level_query() {
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

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_MULTI_LEVEL_QUERY_JSON,
                ClientMultiLevelQueryResultData.class,
                MULTI_LEVEL_DATA_JSON);

        // When /
        OperationResult<ClientMultiLevelQueryResultData> retrieved = adapter.
                asResultDataSet(QueryResultDataMediaType.FLAT_DATA_JSON).
                execute(new ClientMultiLevelQueryExecution());

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

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
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
    public void should_return_proper_operation_result_when_execute_multi_axis_query() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientMultiAxisQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(multiAxisRequestMock);
        doReturn(multiAxisRequestMock).when(multiAxisRequestMock).
                setContentType(EXECUTION_MULTI_AXIS_QUERY_JSON);
        doReturn(multiAxisRequestMock).when(multiAxisRequestMock).
                setAccept(MULTI_AXIS_DATA_JSON);
        doReturn(multiAxisOperationResultMock).when(multiAxisRequestMock).post(any(ClientMultiAxisQueryExecution.class));

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_MULTI_AXIS_QUERY_JSON,
                ClientMultiAxisQueryResultData.class,
                MULTI_AXIS_DATA_JSON);

        // When /
        OperationResult<ClientMultiAxisQueryResultData> retrieved = adapter.execute(new ClientMultiAxisQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, multiAxisOperationResultMock);
        verify(multiAxisRequestMock).
                setContentType(EXECUTION_MULTI_AXIS_QUERY_JSON);
        verify(multiAxisRequestMock).
                setAccept(MULTI_AXIS_DATA_JSON);
        verify(multiAxisRequestMock).post(any(ClientMultiAxisQueryExecution.class));

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientMultiAxisQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_execute_provided_multi_axis_query() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientMultiAxisQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(multiAxisRequestMock);
        doReturn(multiAxisRequestMock).when(multiAxisRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(multiAxisRequestMock).when(multiAxisRequestMock).
                setAccept(MULTI_AXIS_DATA_JSON);
        doReturn(multiAxisOperationResultMock).when(multiAxisRequestMock).post(any(ClientProvidedQueryExecution.class));

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientMultiAxisQueryResultData.class,
                MULTI_AXIS_DATA_JSON);

        // When /
        OperationResult<ClientMultiAxisQueryResultData> retrieved = adapter.execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, multiAxisOperationResultMock);
        verify(multiAxisRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        verify(multiAxisRequestMock).
                setAccept(MULTI_AXIS_DATA_JSON);
        verify(multiAxisRequestMock).post(any(ClientProvidedQueryExecution.class));

        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientMultiAxisQueryResultData.class),
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

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
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

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
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

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
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

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
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

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
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

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
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
                any(DefaultErrorHandler.class))).thenReturn(clientQueryRequestMock);
        doReturn(clientQueryRequestMock).when(clientQueryRequestMock).
                setContentType(EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(clientQueryRequestMock).when(clientQueryRequestMock).
                setAccept(FLAT_DATA_JSON);
        doReturn(clientQueryOperationResultMock).when(clientQueryRequestMock).get();

        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientQueryResultData.class,
                FLAT_DATA_JSON);

        // When /
        OperationResult<ClientQueryResultData> retrieved = adapter.retrieveData(SOME_UUID);

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, clientQueryOperationResultMock);
        verify(clientQueryRequestMock).
                setAccept(FLAT_DATA_JSON);
        verify(clientQueryRequestMock).get();
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
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock,
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
        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON);
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
//        verify(adapter).offset(anyInt());
//        verify(adapter).pageSize(anyInt());
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

    /**
     * @deprecated
     */
    @Test
    public void should_return_proper_operation_result_when_delete_query_execution() {
        // Given
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock,
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

    @Test
    public void should_return_proper_operation_result_when_delete_execution() {
        // Given
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock,
                SOME_UUID));
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(Object.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).delete();

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.delete();

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(requestMock).delete();
        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(Object.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_proper_operation_result_when_get_execution() {
        // Given
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock,
                SOME_UUID));
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(AbstractClientExecution.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);

        ClientMultiLevelQueryExecution clientMultiLevelQueryExecution = mock(ClientMultiLevelQueryExecution.class);
        doReturn(clientMultiLevelQueryExecution).when(operationResultMock).getEntity();

        doReturn(operationResultMock).when(requestMock).get();

        // When /
        OperationResult<ClientMultiLevelQueryExecution> retrieved = adapter.get();

        // Then /
        assertSame(retrieved.getEntity(), clientMultiLevelQueryExecution);
        assertSame(retrieved, operationResultMock);
        verify(requestMock).get();
        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(AbstractClientExecution.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, SOME_UUID}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_proper_operation_result_when_get_all_executions() {
        // Given
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock));
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ClientExecutionListWrapper.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).get();

        // When /
        OperationResult<ClientExecutionListWrapper> retrieved = adapter.getExecutions();

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(requestMock).get();
        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientExecutionListWrapper.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_proper_operation_result_when_get_status_of_execution() {
        // Given
        QueryExecutionAdapter adapter = spy(new QueryExecutionAdapter(storageMock));
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(storageMock),
                eq(ExecutionStatusObject.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, "status"}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).get();

        // When /
        OperationResult<ExecutionStatusObject> retrieved = adapter.status();

        // Then /
        assertSame(retrieved, operationResultMock);
        verify(requestMock).get();
        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ExecutionStatusObject.class),
                eq(new String[]{QUERY_EXECUTIONS_URI, "status"}),
                any(DefaultErrorHandler.class));
    }

}