package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query;//package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query;

import com.jaspersoft.jasperserver.dto.query.QueryResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.Query;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link QueryExecutorAdapter}.
 */
@PrepareForTest(JerseyRequest.class)
public class QueryExecutorAdapterTest extends PowerMockTestCase {

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

    @BeforeMethod
    public void setUp() throws Exception {
        initMocks(this);
        queryExecutorAdapter = new QueryExecutorAdapter(sessionStorageMock, dummyQuery, VALID_URI_FAKE);
    }

    @Test
    public void should_pass_not_null_SessionStorage__and_domainUri_to_super_class_without_any_changes() {
        assertEquals(sessionStorageMock, queryExecutorAdapter.getSessionStorage());
        assertEquals(VALID_URI_FAKE, queryExecutorAdapter.getResourceUri());
    }

    @Test
    public void should_return_proper_OperationResult_object() {
        mockStatic(JerseyRequest.class);

        when(JerseyRequest.buildRequest(
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

    @AfterMethod
    public void tearDown() throws Exception {
        reset(sessionStorageMock, jerseyRequestMock, operationResultMock, dummyQuery);
    }
}