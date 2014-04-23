/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AuthenticationFailedException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.client.ClientProperties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionStorage {

    private static final Log log = LogFactory.getLog(SessionStorage.class);

    private RestClientConfiguration configuration;
    private AuthenticationCredentials credentials;
    private WebTarget rootTarget;
    private String sessionId;

    public SessionStorage(RestClientConfiguration configuration, AuthenticationCredentials credentials) {
        this.configuration = configuration;
        this.credentials = credentials;
        init();
    }

    private void initSSL(ClientBuilder clientBuilder) {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            };
            sslContext.init(null, configuration.getTrustManagers(), new SecureRandom());

            clientBuilder.sslContext(sslContext);
            clientBuilder.hostnameVerifier(hostnameVerifier);

        } catch (Exception e) {
            log.error("Unable to init SSL context", e);
            throw new RuntimeException("Unable to init SSL context", e);
        }
    }

    private void init() {

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        if (configuration.getJasperReportsServerUrl().startsWith("https")) {
            initSSL(clientBuilder);
        }

        Client client = clientBuilder.build();

        Long connectionTimeout = configuration.getConnectionTimeout();
        if (connectionTimeout != null)
            client.property(ClientProperties.CONNECT_TIMEOUT, connectionTimeout);

        Long readTimeout = configuration.getReadTimeout();
        if (readTimeout != null)
            client.property(ClientProperties.READ_TIMEOUT, readTimeout);

        rootTarget = client.target(configuration.getJasperReportsServerUrl());
        login();
        rootTarget.register(new SessionOutputFilter(sessionId));
    }

    private void login() {

        Map<String, String> securityAttributes = getSecurityAttributes();

        Form form = new Form();
        form
                .param("j_username", credentials.getUsername())
                .param("j_password", securityAttributes.get("password"));

        Response response = rootTarget
                .path("/j_spring_security_check")
                .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE)
                .request()
                .cookie("JSESSIONID", securityAttributes.get("sessionId"))
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if (response.getStatus() == ResponseStatus.FOUND) {
            this.sessionId = parseSessionId(response);
        } else
            new DefaultErrorHandler().handleError(response);
    }

    private Map<String, String> getSecurityAttributes() {
        Response encryptionParamsResponse = rootTarget.path("/GetEncryptionKey").request().get();
        Map<String, String> encryptionParams = EncryptionUtils.parseEncryptionParams(encryptionParamsResponse);
        String loginSessionId = encryptionParamsResponse.getCookies().get("JSESSIONID").getValue();
        String encryptedPassword;
        if (encryptionParams != null)
            encryptedPassword = EncryptionUtils.encryptPassword(credentials.getPassword(), encryptionParams.get("n"), encryptionParams.get("e"));
        else
            encryptedPassword = credentials.getPassword();

        Map<String, String> securityAttributes = new HashMap<String, String>();
        securityAttributes.put("password", encryptedPassword);
        securityAttributes.put("sessionId", loginSessionId);

        return securityAttributes;
    }

    private String parseSessionId(Response response) {
        String cookieList = response.getHeaderString("Set-Cookie");

        if (cookieList != null) {
            Pattern pattern = Pattern.compile("JSESSIONID=(\\w+);");
            Matcher matcher;
            matcher = pattern.matcher(cookieList);
            if (matcher.find())
                return matcher.group(1);
        }

        if (response.getHeaderString("Location").endsWith("error=1"))
            throw new AuthenticationFailedException("Wrong credentials");

        throw new JSClientException("Unable to obtain JSESSIONID");
    }

    public RestClientConfiguration getConfiguration() {
        return configuration;
    }

    public AuthenticationCredentials getCredentials() {
        return credentials;
    }

    public String getSessionId() {
        return sessionId;
    }

    public WebTarget getRootTarget() {
        return rootTarget;
    }

    /*public static void main(String[] args) throws InterruptedException {

        RestClientConfiguration configuration = new RestClientConfiguration("http://localhost:4444/jasperserver/");
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        Session session = client.authenticate("jasperadmin", "jasperadmin");

        OperationResult<UsersListWrapper> result = session
                .usersService()
                .allUsers()
                .get();

        System.out.println(result.getEntity());

    }*/
}
