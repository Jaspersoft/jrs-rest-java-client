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

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter;
import com.jaspersoft.jasperserver.jaxrs.client.providers.CustomRepresentationTypeProvider;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;


public class SessionStorage {

    private RestClientConfiguration configuration;
    private AuthenticationCredentials credentials;

    private TimeZone userTimeZone;
    private Locale userLocale;
    private WebTarget rootTarget;
    private String sessionId;


    private Client client;

    public SessionStorage(RestClientConfiguration configuration, AuthenticationCredentials credentials, Locale userLocale, TimeZone userTimeZone) {
        this.configuration = configuration;
        this.credentials = credentials;
        this.userTimeZone = userTimeZone;
        this.userLocale = userLocale;
        init();
    }


    protected Client getRawClient() {
        return client;
    }

    protected WebTarget getConfiguredClient() {
        return configClient();
    }

    private void initSSL(ClientBuilder clientBuilder) {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            HostnameVerifier hostnameVerifier = (s, sslSession) -> true;
            sslContext.init(null, configuration.getTrustManagers(), new SecureRandom());

            clientBuilder.sslContext(sslContext);
            clientBuilder.hostnameVerifier(hostnameVerifier);

        } catch (Exception e) {
            throw new RuntimeException("Unable inFolder init SSL context", e);
        }
    }

    private void init() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();

        if (configuration.getJasperReportsServerUrl().startsWith("https")) {
            initSSL(clientBuilder);
        }
        client = clientBuilder.build();

        Integer connectionTimeout = configuration.getConnectionTimeout();

        if (connectionTimeout != null) {
            client.property(ClientProperties.CONNECT_TIMEOUT, connectionTimeout);
        }

        Integer readTimeout = configuration.getReadTimeout();

        if (readTimeout != null) {
            client.property(ClientProperties.READ_TIMEOUT, readTimeout);
        }

        configClient();
    }

    protected WebTarget configClient() {
        JacksonJsonProvider customRepresentationTypeProvider = new CustomRepresentationTypeProvider();
        rootTarget = client.target(configuration.getJasperReportsServerUrl());
        rootTarget
                .register(customRepresentationTypeProvider)
                .register(JaxbAnnotationModule.class)
                .register(JacksonFeature.class)
                .register(MultiPartFeature.class);
        if (sessionId != null) {
            rootTarget.register(new SessionOutputFilter(sessionId));
        }
        if (configuration.getLogHttp()) {
            rootTarget.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL, "INFO");
            rootTarget.register(initLoggingFilter());
        }
        return rootTarget;
    }

    private LoggingFeature initLoggingFilter() {
        Logger logger = Logger.getLogger(this.getClass().getName());
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        LoggingFeature.Verbosity verbosity = configuration.getLogHttpEntity() ? LoggingFeature.Verbosity.PAYLOAD_ANY : LoggingFeature.Verbosity.HEADERS_ONLY;
        return new LoggingFeature(logger, verbosity);
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

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public WebTarget getRootTarget() {
        return rootTarget;
    }

    public TimeZone getUserTimeZone() {
        return userTimeZone;
    }

    public void setUserTimeZone(TimeZone userTimeZone) {
        this.userTimeZone = userTimeZone;
    }

    public Locale getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(Locale userLocale) {
        this.userLocale = userLocale;
    }
}
