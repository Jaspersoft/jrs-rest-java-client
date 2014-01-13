package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JasperserverRestServiceCatalog;

public class JasperserverRestClient {

    public static JasperserverRestServiceCatalog authenticate(String username, String password){
        return new JasperserverRestServiceCatalog(new AuthenticationCredentials(username, password));
    }

    public static JasperserverRestServiceCatalog authenticate(String username, String password, String sessionId){
        return new JasperserverRestServiceCatalog(new AuthenticationCredentials(username, password, sessionId));
    }

}
