package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.Query;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.QueryResult;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class QueryExecutorAdapterTest extends Assert {

    @Mock
    private Query queryMock;

    @Mock
    private SessionStorage storageMock;

    @Mock
    private QueryExecutorAdapter adapterMock;

    @Mock
    private OperationResult<QueryResult> expected;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void should_pass_not_null_session_storage_to_super_class_without_any_changes() {
        final String fakeValidUri = "very/valid/path/to/resource";
        final QueryExecutorAdapter adapterSpy = spy(new QueryExecutorAdapter(storageMock, queryMock, fakeValidUri));

        assertNotNull(adapterSpy.getSessionStorage());
        assertEquals(storageMock, adapterSpy.getSessionStorage());
        verify(adapterSpy, times(2)).getSessionStorage();
    }

    @Test
    public void should_return_operation_result() {
        when(adapterMock.execute()).thenReturn(expected);
        assertEquals(expected, adapterMock.execute());
    }
}