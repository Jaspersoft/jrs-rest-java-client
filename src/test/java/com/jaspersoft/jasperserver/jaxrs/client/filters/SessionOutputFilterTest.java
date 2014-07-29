package com.jaspersoft.jasperserver.jaxrs.client.filters;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Unit test for {@link com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter}
 */
public class SessionOutputFilterTest {

    @Mock
    private Response responseMock;

    @Mock
    private ClientRequestContext contextMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_create_instance_of_SessionOutputFilter_with_proper_sessionId() {
        SessionOutputFilter filter = new SessionOutputFilter("sessionId");
        String sessionId = Whitebox.getInternalState(filter, "sessionId");
        assertEquals(sessionId, "sessionId");
    }

    @Test
    public void should_set_headers() throws IOException, IllegalAccessException {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>() {{
            add("key", new Object());
        }};

        Mockito.when(contextMock.getHeaders()).thenReturn(headers);

        SessionOutputFilter filter = new SessionOutputFilter("sessionId");
        filter.filter(contextMock);

        assertNotNull(contextMock.getHeaders().getFirst("Cookie"));
    }

    @AfterMethod
    public void after() {
        Mockito.reset(responseMock);
    }
}