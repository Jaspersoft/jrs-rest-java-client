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
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.*;
import com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.client.ClientProperties;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.util.*;

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

        RestClientConfiguration configuration = new RestClientConfiguration("http://localhost:4444/jasperserver-pro/");
        //RestClientConfiguration configuration = new RestClientConfiguration("http://localhost:4444/jasperserver/");
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        Session session = client.authenticate("jasperadmin|organization_1", "jasperadmin");
        //Session session = client.authenticate("jasperadmin", "jasperadmin");

        Job job = new Job();
        job.setLabel("Test Job");
        job.setBaseOutputFilename("TestJobFile");

        JobSource source = new JobSource();
        source.setReportUnitURI("/public/Samples/Reports/14.PerformanceSummary");
        //source.setReportUnitURI("/reports/samples/EmployeeAccounts");
        Map<String, Object> reportParameters = new HashMap<String, Object>();
        reportParameters.put("Product_Family", Arrays.asList("Food"));
        //reportParameters.put("EmployeeID", Arrays.asList("kristen"));
        source.setParameters(reportParameters);
        job.setSource(source);

        SimpleTrigger trigger = new SimpleTrigger();
        trigger.setOccurrenceCount(2);
        trigger.setRecurrenceInterval(1000);
        trigger.setRecurrenceIntervalUnit(IntervalUnitType.HOUR);
        //trigger.setStartType(JobTrigger.START_TYPE_NOW);
        //trigger.setStartDate(new Date());
        job.setTrigger(trigger);

        Set<OutputFormat> outputFormats = new HashSet<OutputFormat>(Arrays.asList(OutputFormat.PDF));
        job.setOutputFormats(outputFormats);

        RepositoryDestination repositoryDestination = new RepositoryDestination();
        repositoryDestination.setFolderURI("/public/Samples/Reports");
        repositoryDestination.setSaveToRepository(true);
        job.setRepositoryDestination(repositoryDestination);

        OperationResult<Job> result = session
                .jobsService()
                .scheduleReport(job);
        System.out.println(result.getEntity());


        OperationResult<Job> jobOperationResult = session
                .jobsService()
                .job(3620)
                .get();

        System.out.println(jobOperationResult.getEntity());

    }*/
}
