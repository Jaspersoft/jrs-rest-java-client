package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query;

import com.jaspersoft.jasperserver.dto.query.QueryResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.Query;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link QueryExecutorAdapter}.
 */
public class QueryExecutorAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private Query dummyQuery;

    @Mock
    private JerseyRequest<QueryResult> jerseyRequestMock;

    @Mock
    OperationResult<QueryResult> operationResultMock;

    private final String VALID_URI_FAKE = "very/valid/path/to/domain";

    private QueryExecutorAdapter queryExecutorAdapter;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void setUp() throws Exception {
        mockitoSession = MockitoAnnotations.openMocks(this);
        queryExecutorAdapter = new QueryExecutorAdapter(sessionStorageMock, dummyQuery, VALID_URI_FAKE);
    }

    @Test
    public void should_pass_not_null_SessionStorage__and_domainUri_to_super_class_without_any_changes() {
        assertEquals(sessionStorageMock, queryExecutorAdapter.getSessionStorage());
        assertEquals(VALID_URI_FAKE, queryExecutorAdapter.getResourceUri());
    }

    @Test
    public void should_return_proper_OperationResult_object() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(
                    any(SessionStorage.class),
                    any(Class.class),
                    any(String[].class),
                    any(ErrorHandler.class)
            )).thenReturn(jerseyRequestMock);

            doReturn(operationResultMock)
                    .when(jerseyRequestMock)
                    .post(dummyQuery);

            assertEquals(operationResultMock, queryExecutorAdapter.execute());
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        reset(sessionStorageMock, jerseyRequestMock, operationResultMock, dummyQuery);
        mockitoSession.close();
    }
}
