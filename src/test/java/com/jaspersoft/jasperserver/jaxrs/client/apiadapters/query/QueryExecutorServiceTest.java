package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.BadRequestException;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.Query;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class QueryExecutorServiceTest extends Assert {

    @Mock
    private Query queryMock;

    @Mock
    private Query invalidQuery;

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private QueryExecutorAdapter queryExecutorAdapter;

    @Mock
    private QueryExecutorService queryExecutorService;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void should_pass_session_storage_to_super_class_without_any_changes() {
        final QueryExecutorService adapterSpy = spy(new QueryExecutorService(sessionStorageMock));

        assertNotNull(adapterSpy.getSessionStorage());
        assertEquals(sessionStorageMock, adapterSpy.getSessionStorage());

        verify(adapterSpy, times(2)).getSessionStorage();
    }

    @Test
    public void should_create_query_executor_adapter_from_uri_and_query() {
        final String validUri = "very/valid/path/to/resource";

        when(queryExecutorService.query(any(Query.class), anyString()))
                .thenReturn(queryExecutorAdapter);

        assertEquals(queryExecutorAdapter, queryExecutorService.query(queryMock, validUri));
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void should_throw_an_exception_when_pass_invalid_query() {
        final String validUri = "very/valid/path/to/resource";

        when(queryExecutorService.query(invalidQuery, validUri))
                .thenThrow(new BadRequestException());

        queryExecutorService.query(invalidQuery, validUri);
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void should_throw_an_exception_when_pass_invalid_uri() {
        final String invalidUri = "wrong/path/to/resource";

        when(queryExecutorService.query(any(Query.class), eq(invalidUri)))
                .thenThrow(new BadRequestException());

        queryExecutorService.query(queryMock, invalidUri);
    }
}