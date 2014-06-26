package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.Query;
import org.mockito.Mock;
import org.mockito.Spy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class QueryExecutorServiceTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private Query dummyQuery;

    @Spy
    private QueryExecutorService serviceSpy = new QueryExecutorService(sessionStorageMock);

    private QueryExecutorAdapter adapter;
    private QueryExecutorService service;

    private final String VALID_URI_FAKE = "very/valid/resource/identifier/";

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        service = new QueryExecutorService(sessionStorageMock);
        adapter = new QueryExecutorAdapter(sessionStorageMock, dummyQuery, VALID_URI_FAKE);
    }

    @Test
    public void should_pass_session_storage_to_super_class_without_any_changes() {
        assertEquals(sessionStorageMock, service.getSessionStorage());
    }

    @Test
    public void should_create_domain_adapter_from_uri_with_proper_parameters() {
        final QueryExecutorAdapter expected = service.query(dummyQuery, VALID_URI_FAKE);

        instanceOf(QueryExecutorAdapter.class).matches(expected);
        assertEquals(service.getSessionStorage(), adapter.getSessionStorage());
        assertEquals(expected.getResourceUri(), VALID_URI_FAKE);
    }

    @Test
    public void should_return_not_null_domain_metadata() {
        assertNotNull(serviceSpy.query(dummyQuery, VALID_URI_FAKE));
        verify(serviceSpy).query(dummyQuery, VALID_URI_FAKE);
    }

    @AfterMethod
    public void tearDown() {
        reset(sessionStorageMock, dummyQuery);
    }
}