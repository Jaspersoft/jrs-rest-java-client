package com.jaspersoft.jasperserver.jaxrs.client.filters;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SessionOutputFilter implements ClientRequestFilter {

    private final String sessionId;

    public SessionOutputFilter(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void filter(ClientRequestContext requestContext)
            throws IOException {

        List<Object> cookies = new ArrayList<Object>() {{
            add("JSESSIONID=" + sessionId);
        }};
        requestContext.getHeaders().put("Cookie", cookies);

    }
}
