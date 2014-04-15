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

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
        rootTarget = client.target(configuration.getJasperReportsServerUrl());
        login();
        rootTarget.register(new SessionOutputFilter(sessionId));
    }

    private void login() {
        Form form = new Form();
        form
                .param("j_username", credentials.getUsername())
                .param("j_password", credentials.getPassword());

        WebTarget target = rootTarget.path("/rest/login");
        Response response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if (response.getStatus() == ResponseStatus.OK)
            this.sessionId = response.getCookies().get("JSESSIONID").getValue();
        else
            new DefaultErrorHandler().handleError(response);
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
//        RestClientConfiguration configuration1 = new RestClientConfiguration("http://localhost:8081/jasperserver-pro");
        RestClientConfiguration configuration1 = new RestClientConfiguration("http://localhost:4444/jasperserver");
//        RestClientConfiguration configuration1 = new RestClientConfiguration("http://localhost:8080/jasperserver");
        JasperserverRestClient client = new JasperserverRestClient(configuration1);

        //Session session = client.authenticate("jasperadmin|organization_1", "jasperadmin");
        Session session = client.authenticate("jasperadmin", "jasperadmin");

        OperationResult<Job> result1 = session
                .jobsService()
                .job(21281)
                .get();

        Job jobExtension = result1.getEntity();

        Job job = new Job();
        job.setAlert(jobExtension.getAlert());
        job.setBaseOutputFilename(jobExtension.getBaseOutputFilename() + "2");
        job.setDescription(jobExtension.getDescription());
        job.setLabel(jobExtension.getLabel());
        job.setOutputFormats(jobExtension.getOutputFormats());
        job.setTrigger(jobExtension.getTrigger());
        job.setSource(jobExtension.getSource());
        job.setRepositoryDestination(jobExtension.getRepositoryDestination());

        OperationResult<? extends Job> result = session
                .jobsService()
                .scheduleReport(job);

        System.out.println(result.getEntity());

    }*/
}
