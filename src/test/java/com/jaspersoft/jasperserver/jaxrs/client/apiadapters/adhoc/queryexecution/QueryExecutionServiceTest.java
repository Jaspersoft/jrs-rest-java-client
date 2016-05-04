package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryExecutionsMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryType;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@PrepareForTest(QueryExecutionService.class)
public class QueryExecutionServiceTest extends PowerMockTestCase {
    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private QueryExecutionAdapter<ClientMultiLevelQueryResultData> multiLevelAdapterMock;
    @Mock
    private QueryExecutionAdapter<ClientMultiAxesQueryResultData> multiAxesAdapterMock;


    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, multiLevelAdapterMock, multiAxesAdapterMock);
    }


    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        // When
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);

        // Then
        assertSame(Whitebox.getInternalState(executionService, "sessionStorage"), sessionStorageMock);
    }

    @Test
    public void should_return_proper_adapter_for_flat_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_TYPE,
                QueryResultDataMediaType.FLAT_DATA_TYPE,
                ClientFlatQueryResultData.class).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = executionService.flatQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
    }

    @Test
    public void should_return_proper_adapter_for_multi_axes_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                QueryExecutionsMediaType.EXECUTION_MULTI_AXES_QUERY_TYPE,
                QueryResultDataMediaType.MULTI_AXES_DATA_TYPE,
                ClientMultiAxesQueryResultData.class).thenReturn(multiAxesAdapterMock);

        // When
        QueryExecutionAdapter<ClientMultiAxesQueryResultData> adapter = executionService.multiAxesQuery();

        // Then
        assertEquals(adapter, multiAxesAdapterMock);
    }

    @Test
    public void should_return_proper_adapter_for_multi_level_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_TYPE,
                QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE,
                ClientMultiLevelQueryResultData.class).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<ClientMultiLevelQueryResultData> adapter = executionService.multiLevelQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
    }

    @Test
    public void should_return_proper_adapter_for_provided_flat_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                QueryResultDataMediaType.FLAT_DATA_TYPE,
                ClientFlatQueryResultData.class).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery(QueryType.FLAT_QUERY);

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
    }

    @Test
    public void should_return_proper_adapter_for_provided_multi_level_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE,
                ClientMultiLevelQueryResultData.class).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery(QueryType.MULTI_LEVEL_QUERY);

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
    }


    @Test
    public void should_return_proper_adapter_for_provided_multi_axes_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                QueryResultDataMediaType.MULTI_AXES_DATA_TYPE,
                ClientMultiAxesQueryResultData.class).thenReturn(multiAxesAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery(QueryType.MULTI_AXES_QUERY);

        // Then
        assertEquals(adapter, multiAxesAdapterMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_in_parent_adapter() {
        // When
        new QueryExecutionService(null);
        //Then
        // exception should be thrown
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_query_type_is_null() {

        // When
        new QueryExecutionService(sessionStorageMock).providedQuery(null);

        //Then
        // exception should be thrown
    }

}