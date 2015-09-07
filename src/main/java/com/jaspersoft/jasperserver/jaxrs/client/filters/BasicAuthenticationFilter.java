package com.jaspersoft.jasperserver.jaxrs.client.filters;

import com.jaspersoft.jasperserver.jaxrs.client.core.AuthenticationCredentials;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Tetiana Iefimenko
 */
public class BasicAuthenticationFilter implements ClientRequestFilter {
    private final AuthenticationCredentials credentials;

    public BasicAuthenticationFilter(AuthenticationCredentials credentials) {
        this.credentials = credentials;

    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        MultivaluedMap<String, Object> headers = clientRequestContext.getHeaders();
        if (credentials.getUsername() == null && credentials.getPassword() == null) {
            return;
        }
        final String basicAuthentication = getBasicAuthentication();
        headers.add("Authorization", basicAuthentication);

    }

    private String getBasicAuthentication() {
        String token = this.credentials.getUsername() + ":" + this.credentials.getPassword();
        try {
            return "Basic " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Cannot encode with UTF-8", ex);
        }
    }
}

