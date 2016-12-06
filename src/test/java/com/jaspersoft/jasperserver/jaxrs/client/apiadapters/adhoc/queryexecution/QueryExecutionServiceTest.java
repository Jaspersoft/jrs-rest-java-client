package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_AXES_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_AXES_QUERY_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_AXES_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_AXES_DATA_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_LEVEL_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_LEVEL_DATA_XML;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_MULTI_LEVEL_QUERY_JSON,
                ClientFlatQueryResultData.class,
                FLAT_DATA_JSON).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<ClientFlatQueryResultData> adapter = executionService.flatQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }

    @Test
    public void should_return_proper_adapter_for_multi_axes_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_MULTI_AXES_QUERY_JSON,
                ClientMultiAxesQueryResultData.class,
                MULTI_AXES_DATA_JSON).thenReturn(multiAxesAdapterMock);

        // When
        QueryExecutionAdapter<ClientMultiAxesQueryResultData> adapter = executionService.multiAxesQuery();

        // Then
        assertEquals(adapter, multiAxesAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }

    @Test
    public void should_return_proper_adapter_for_multi_level_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_MULTI_LEVEL_QUERY_JSON,
                ClientMultiLevelQueryResultData.class,
                MULTI_LEVEL_DATA_JSON).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<ClientMultiLevelQueryResultData> adapter = executionService.multiLevelQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }

    @Test
    public void should_return_proper_adapter_for_provided_flat_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientQueryResultData.class,
                FLAT_DATA_JSON,
                MULTI_LEVEL_DATA_JSON,
                MULTI_AXES_DATA_JSON).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }

    @Test
    public void should_return_proper_adapter_for_provided_multi_level_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientQueryResultData.class,
                FLAT_DATA_JSON,
                MULTI_LEVEL_DATA_JSON,
                MULTI_AXES_DATA_JSON).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }


    @Test
    public void should_return_proper_adapter_for_provided_multi_axes_query() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_PROVIDED_QUERY_JSON,
                ClientQueryResultData.class,
                FLAT_DATA_JSON,
                MULTI_LEVEL_DATA_JSON,
                MULTI_AXES_DATA_JSON).thenReturn(multiAxesAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery();

        // Then
        assertEquals(adapter, multiAxesAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }

    @Test
    public void should_return_proper_adapter_for_multi_axes_query_for_xml() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_MULTI_AXES_QUERY_XML,
                ClientMultiAxesQueryResultData.class,
                MULTI_AXES_DATA_XML).thenReturn(multiAxesAdapterMock);

        // When
        QueryExecutionAdapter<ClientMultiAxesQueryResultData> adapter = executionService.multiAxesQuery();

        // Then
        assertEquals(adapter, multiAxesAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }

    @Test
    public void should_return_proper_adapter_for_multi_level_query_for_xml() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_MULTI_LEVEL_QUERY_XML,
                ClientMultiLevelQueryResultData.class,
                MULTI_LEVEL_DATA_XML).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<ClientMultiLevelQueryResultData> adapter = executionService.multiLevelQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }

    @Test
    public void should_return_proper_adapter_for_provided_flat_query_for_xml() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_PROVIDED_QUERY_XML,
                ClientQueryResultData.class,
                FLAT_DATA_XML,
                MULTI_LEVEL_DATA_XML,
                MULTI_AXES_DATA_XML).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }

    @Test
    public void should_return_proper_adapter_for_provided_multi_level_query_for_xml() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_PROVIDED_QUERY_XML,
                ClientQueryResultData.class,
                FLAT_DATA_XML,
                MULTI_LEVEL_DATA_XML,
                MULTI_AXES_DATA_XML).thenReturn(multiLevelAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery();

        // Then
        assertEquals(adapter, multiLevelAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }


    @Test
    public void should_return_proper_adapter_for_provided_multi_axes_query_for_xml() throws Exception {
        // Given
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        whenNew(QueryExecutionAdapter.class).withArguments(sessionStorageMock,
                EXECUTION_PROVIDED_QUERY_XML,
                ClientQueryResultData.class,
                FLAT_DATA_XML,
                MULTI_LEVEL_DATA_XML,
                MULTI_AXES_DATA_XML).thenReturn(multiAxesAdapterMock);

        // When
        QueryExecutionAdapter<? extends ClientQueryResultData> adapter = executionService.providedQuery();

        // Then
        assertEquals(adapter, multiAxesAdapterMock);
        verify(sessionStorageMock, times(2)).getConfiguration();
        verify(configurationMock).getContentMimeType();
        verify(configurationMock).getAcceptMimeType();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_in_parent_adapter() {
        // When
        new QueryExecutionService(null);
        //Then
        // exception should be thrown
    }

}