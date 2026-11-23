package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientExecutionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
public class QueryExecutionServiceTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private RestClientConfiguration configurationMock;

    private AutoCloseable mocks;

    @BeforeMethod
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mocks.close();
        reset(sessionStorageMock, configurationMock);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        assertSame(executionService.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_return_proper_adapter_for_flat_query() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.flatQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_multi_axis_query() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.multiAxisQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_multi_level_query() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.multiLevelQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_provided_flat_query() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.providedQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_provided_multi_level_query() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.providedQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_provided_multi_axis_query() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.providedQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_multi_axis_query_for_xml() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.multiAxisQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_multi_level_query_for_xml() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.multiLevelQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_provided_flat_query_for_xml() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.providedQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_provided_multi_level_query_for_xml() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.providedQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_provided_multi_axis_query_for_xml() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class)) {
            QueryExecutionAdapter adapter = executionService.providedQuery();

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
            verify(sessionStorageMock, times(2)).getConfiguration();
            verify(configurationMock).getContentMimeType();
            verify(configurationMock).getAcceptMimeType();
        }
    }

    @Test
    public void should_return_proper_adapter_for_execution_by_UUID_builder() {
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class, (mock, ctx) -> {
                         assertEquals(2, ctx.arguments().size());
                         assertSame(sessionStorageMock, ctx.arguments().get(0));
                         assertEquals("testUuid", ctx.arguments().get(1));
                     })) {
            QueryExecutionAdapter adapter = executionService.execution("testUuid");

            assertEquals(1, mockedConstruction.constructed().size());
            assertSame(adapter, mockedConstruction.constructed().get(0));
        }
    }

    @Test
    public void should_return_query_executions_for_get_builder() {
        OperationResult<ClientExecutionListWrapper> operationResult = Mockito.mock(OperationResult.class);
        QueryExecutionService executionService = new QueryExecutionService(sessionStorageMock);

        try (MockedConstruction<QueryExecutionAdapter> mockedConstruction =
                     Mockito.mockConstruction(QueryExecutionAdapter.class, (mock, ctx) -> {
                         doReturn(operationResult).when(mock).getExecutions();
                     })) {
            OperationResult<ClientExecutionListWrapper> result = executionService.get();

            assertEquals(1, mockedConstruction.constructed().size());
            assertEquals(result, operationResult);
            verify(mockedConstruction.constructed().get(0)).getExecutions();
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_in_parent_adapter() {
        new QueryExecutionService(null);
    }
}
