package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryExecutionsMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType.FLAT_DATA_TYPE;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType.MULTI_AXES_DATA_TYPE;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE;
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
    @Mock
    private RestClientConfiguration configurationMock;

    private String[] providedQueryAccept = new String[]{QueryResultDataMediaType.FLAT_DATA_TYPE,
            QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE,
            QueryResultDataMediaType.MULTI_AXES_DATA_TYPE};



    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, multiLevelAdapterMock, multiAxesAdapterMock, configurationMock);
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
                ClientFlatQueryResultData.class,
                QueryResultDataMediaType.FLAT_DATA_TYPE).thenReturn(multiLevelAdapterMock);

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
                ClientMultiAxesQueryResultData.class,
                QueryResultDataMediaType.MULTI_AXES_DATA_TYPE).thenReturn(multiAxesAdapterMock);

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
                ClientMultiLevelQueryResultData.class,
                QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE).thenReturn(multiLevelAdapterMock);

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
                ClientQueryResultData.class,
                FLAT_DATA_TYPE,
                MULTI_LEVEL_DATA_TYPE,
                MULTI_AXES_DATA_TYPE).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
    }

    @Test
    public void should_return_proper_adapter_for_provided_multi_level_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                ClientQueryResultData.class,
                FLAT_DATA_TYPE,
                MULTI_LEVEL_DATA_TYPE,
                MULTI_AXES_DATA_TYPE).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
    }


    @Test
    public void should_return_proper_adapter_for_provided_multi_axes_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                ClientQueryResultData.class,
                FLAT_DATA_TYPE,
                MULTI_LEVEL_DATA_TYPE,
                MULTI_AXES_DATA_TYPE).thenReturn(multiAxesAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery();

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

}