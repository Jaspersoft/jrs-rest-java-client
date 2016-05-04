package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientProvidedQueryExecution;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryExecutionsMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType;
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

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
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
                configurationMock);
    }

    @Test
    public void should_set_proper_internal_state() {
        // When
        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock, CONTENT_TYPE, ACCEPT_TYPE, Object.class);

        //Then
        assertEquals(Whitebox.getInternalState(adapter, "dataSetClass"), Object.class);
        assertEquals(Whitebox.getInternalState(adapter, "contentType"), CONTENT_TYPE);
        assertEquals(Whitebox.getInternalState(adapter, "acceptType"), ACCEPT_TYPE);
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_query_is_null() {
        // When
        QueryExecutionAdapter adapter = new QueryExecutionAdapter(storageMock, CONTENT_TYPE, ACCEPT_TYPE, Object.class);
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
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(multiLevelRequestMock).when(multiLevelRequestMock).
                setContentType(QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_JSON);
        doReturn(multiLevelRequestMock).when(multiLevelRequestMock).
                setAccept(QueryResultDataMediaType.MULTI_LEVEL_DATA_JSON);
        doReturn(multiLevelOperationResultMock).when(multiLevelRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        QueryExecutionAdapter<ClientMultiLevelQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_TYPE,
                QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE,
                ClientMultiLevelQueryResultData.class);

        // When /
        OperationResult<ClientMultiLevelQueryResultData> retrieved = adapter.execute(new ClientMultiLevelQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, multiLevelOperationResultMock);
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
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(flatRequestMock).when(flatRequestMock).
                setContentType(QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_JSON);
        doReturn(flatRequestMock).when(flatRequestMock).
                setAccept(QueryResultDataMediaType.FLAT_DATA_JSON);
        doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_TYPE,
                QueryResultDataMediaType.FLAT_DATA_TYPE,
                ClientFlatQueryResultData.class);

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.execute(new ClientMultiLevelQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
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
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(multiAxesRequestMock).when(multiAxesRequestMock).
                setContentType(QueryExecutionsMediaType.EXECUTION_MULTI_AXES_QUERY_JSON);
        doReturn(multiAxesRequestMock).when(multiAxesRequestMock).
                setAccept(QueryResultDataMediaType.MULTI_AXES_DATA_JSON);
        doReturn(multiAxesOperationResultMock).when(multiAxesRequestMock).post(any(ClientMultiAxesQueryExecution.class));

        QueryExecutionAdapter<ClientMultiAxesQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                QueryExecutionsMediaType.EXECUTION_MULTI_AXES_QUERY_TYPE,
                QueryResultDataMediaType.MULTI_AXES_DATA_TYPE,
                ClientMultiAxesQueryResultData.class);

        // When /
        OperationResult<ClientMultiAxesQueryResultData> retrieved = adapter.execute(new ClientMultiAxesQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, multiAxesOperationResultMock);
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
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(multiAxesRequestMock).when(multiAxesRequestMock).
                setContentType(QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(multiAxesRequestMock).when(multiAxesRequestMock).
                setAccept(QueryResultDataMediaType.MULTI_AXES_DATA_JSON);
        doReturn(multiAxesOperationResultMock).when(multiAxesRequestMock).post(any(ClientProvidedQueryExecution.class));

        QueryExecutionAdapter<ClientMultiAxesQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                QueryResultDataMediaType.MULTI_AXES_DATA_TYPE,
                ClientMultiAxesQueryResultData.class);

        // When /
        OperationResult<ClientMultiAxesQueryResultData> retrieved = adapter.execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, multiAxesOperationResultMock);
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
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(multiLevelRequestMock).when(multiLevelRequestMock).
                setContentType(QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(multiLevelRequestMock).when(multiLevelRequestMock).
                setAccept(QueryResultDataMediaType.MULTI_LEVEL_DATA_JSON);
        doReturn(multiLevelOperationResultMock).when(multiLevelRequestMock).post(any(ClientProvidedQueryExecution.class));

        QueryExecutionAdapter<ClientMultiLevelQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE,
                ClientMultiLevelQueryResultData.class);

        // When /
        OperationResult<ClientMultiLevelQueryResultData> retrieved = adapter.execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, multiLevelOperationResultMock);
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
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(flatRequestMock).when(flatRequestMock).
                setContentType(QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_JSON);
        doReturn(flatRequestMock).when(flatRequestMock).
                setAccept(QueryResultDataMediaType.FLAT_DATA_JSON);
        doReturn(flatOperationResultMock).when(flatRequestMock).post(any(ClientMultiLevelQueryExecution.class));

        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = new QueryExecutionAdapter(storageMock,
                QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                QueryResultDataMediaType.FLAT_DATA_TYPE,
                ClientFlatQueryResultData.class);

        // When /
        OperationResult<ClientFlatQueryResultData> retrieved = adapter.execute(new ClientProvidedQueryExecution());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, flatOperationResultMock);
        verify(flatRequestMock).post(any(ClientProvidedQueryExecution.class));
        verifyStatic(times(1));
        buildRequest(
                eq(storageMock),
                eq(ClientFlatQueryResultData.class),
                eq(new String[]{QUERY_EXECUTIONS_URI}),
                any(DefaultErrorHandler.class));

    }
}