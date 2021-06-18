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

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.AuthenticationType;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import java.util.regex.Pattern;


public class RestClientConfiguration {

    private static final Log log = LogFactory.getLog(RestClientConfiguration.class);
    private static final Pattern URL_PATTERN = Pattern.compile("\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    private static final Pattern VERSION_PATTERN = Pattern.compile("^v(\\d_){2}\\d$");
    private static final Pattern BOOLEAN_PATTERN = Pattern.compile("^(true|false)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");

    private String jasperReportsServerUrl;
    private MimeType contentMimeType = MimeType.JSON;
    private MimeType acceptMimeType = MimeType.JSON;
    private JRSVersion jrsVersion = JRSVersion.v5_5_0;
    private AuthenticationType authenticationType = AuthenticationType.SPRING;
    private Boolean restrictedHttpMethods = false;
    private Boolean logHttp = false;
    private Boolean logHttpEntity = false;
    private Boolean handleErrors = true;
    private Integer connectionTimeout;
    private Integer readTimeout;
    private TrustManager[] trustManagers;

    public RestClientConfiguration(String jasperReportsServerUrl) {
        this();

        setJasperReportsServerUrl(jasperReportsServerUrl);
    }

    protected RestClientConfiguration() {
        trustManagers = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }};
    }

    public static RestClientConfiguration loadConfiguration(String path) {
        Properties properties = null;
        if (path != null) {
            properties = loadProperties(path);
        }
        if (properties == null) {
            log.info("The properties file was not loaded");
            return new RestClientConfiguration();
        }

        return loadConfiguration(properties);
    }

    private static Boolean isStringValid(String string) {
        return (string != null && string.length() > 0);
    }


    public static RestClientConfiguration loadConfiguration(Properties properties) {

        RestClientConfiguration configuration = new RestClientConfiguration();
        String url = properties.getProperty("url");
        if (isStringValid(url) && URL_PATTERN.matcher(url).matches()) {
            configuration.setJasperReportsServerUrl(url);
        }

        String connectionTimeout = properties.getProperty("connectionTimeout");
        if (isStringValid(connectionTimeout) && NUMBER_PATTERN.matcher(connectionTimeout).matches()) {
            configuration.setConnectionTimeout(Integer.valueOf(connectionTimeout));
        }

        String readTimeout = properties.getProperty("readTimeout");

        if (isStringValid(readTimeout) && NUMBER_PATTERN.matcher(readTimeout).matches()) {
            configuration.setReadTimeout(Integer.valueOf(readTimeout));
        }
        String jrsVersion = properties.getProperty("jasperserverVersion");
        if (isStringValid(jrsVersion) && VERSION_PATTERN.matcher(jrsVersion).matches()) {
            try {
                configuration.setJrsVersion(JRSVersion.valueOf(jrsVersion));
            } catch (Exception e) {
                log.info("There is no version for JasperReportsServer or it isn't supported.", e);
            }
        }

        String authenticationType = properties.getProperty("authenticationType");
        if (isStringValid(authenticationType)) {
            try {
                configuration.setAuthenticationType(AuthenticationType.valueOf(authenticationType.toUpperCase()));
            } catch (Exception e) {
                log.info("There is no authentication type or it isn't supported.", e);
            }
        }

        String logHttp = properties.getProperty("logHttp");
        if (isStringValid(logHttp) && BOOLEAN_PATTERN.matcher(logHttp).matches()) {
            configuration.setLogHttp(Boolean.valueOf(logHttp));
        }

        String logHttpEntity = properties.getProperty("logHttpEntity");
        if (isStringValid(logHttpEntity) && BOOLEAN_PATTERN.matcher(logHttpEntity).matches()) {
            configuration.setLogHttpEntity(Boolean.valueOf(logHttpEntity));
        }

        String restrictedHttpMethods = properties.getProperty("restrictedHttpMethods");
        if (isStringValid(restrictedHttpMethods) && BOOLEAN_PATTERN.matcher(restrictedHttpMethods).matches()) {
            configuration.setRestrictedHttpMethods(Boolean.valueOf(restrictedHttpMethods));
        }

        String handleErrors = properties.getProperty("handleErrors");
        if (isStringValid(handleErrors) && BOOLEAN_PATTERN.matcher(handleErrors).matches()) {
            configuration.setHandleErrors(Boolean.valueOf(handleErrors));
        }

        String contentMimeType = properties.getProperty("contentMimeType");
        if (isStringValid(contentMimeType)) {
            try {
                configuration.setContentMimeType(MimeType.valueOf(contentMimeType));
            } catch (Exception e) {
                log.info("There is no mime type for content type or it isn't supported.", e);
            }
        }

        String acceptMimeType = properties.getProperty("acceptMimeType");

        if (isStringValid(acceptMimeType)) {
            try {
                configuration.setAcceptMimeType(MimeType.valueOf(acceptMimeType));
            } catch (Exception e) {
                log.info("There is no mime type for accept type or it isn't supported.", e);
            }
        }
        return configuration;
    }

    private static Properties loadProperties(String path) {
        Properties properties = new Properties();
        try {
            InputStream is = RestClientConfiguration.class.getClassLoader().getResourceAsStream(path);
            properties.load(is);
        } catch (Exception e) {
            log.info("Error when loading properties file", e);
            return null;
        }
        return properties;
    }

    public Boolean getLogHttp() {
        return logHttp;
    }

    public String getJasperReportsServerUrl() {
        return jasperReportsServerUrl;
    }

    public RestClientConfiguration setJasperReportsServerUrl(String jasperReportsServerUrl) {
        if (!isStringValid(jasperReportsServerUrl) || !URL_PATTERN.matcher(jasperReportsServerUrl).matches())
            throw new IllegalArgumentException("Given parameter is not a URL");
        this.jasperReportsServerUrl = jasperReportsServerUrl;
        return this;
    }

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public RestClientConfiguration setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public Boolean getRestrictedHttpMethods() {
        return restrictedHttpMethods;
    }

    public RestClientConfiguration setRestrictedHttpMethods(Boolean restrictedHttpMethods) {
        this.restrictedHttpMethods = restrictedHttpMethods;
        return this;
    }

    public Boolean getHandleErrors() {
        return handleErrors;
    }

    public RestClientConfiguration setHandleErrors(Boolean handleErrors) {
        this.handleErrors = handleErrors;
        return this;
    }

    public RestClientConfiguration setLogHttp(Boolean logHttp) {
        this.logHttp = logHttp;
        return this;
    }

    public Boolean getLogHttpEntity() {
        return logHttpEntity;
    }

    public RestClientConfiguration setLogHttpEntity(Boolean logHttpEntity) {
        this.logHttpEntity = logHttpEntity;
        return this;
    }

    public MimeType getContentMimeType() {
        return contentMimeType;
    }

    public RestClientConfiguration setContentMimeType(MimeType contentMimeType) {
        this.contentMimeType = contentMimeType;
        return this;
    }

    public MimeType getAcceptMimeType() {
        return acceptMimeType;
    }

    public RestClientConfiguration setAcceptMimeType(MimeType acceptMimeType) {
        this.acceptMimeType = acceptMimeType;
        return this;
    }

    public JRSVersion getJrsVersion() {
        return jrsVersion;
    }

    public RestClientConfiguration setJrsVersion(JRSVersion jrsVersion) {
        this.jrsVersion = jrsVersion;
        return this;
    }

    public TrustManager[] getTrustManagers() {
        return trustManagers;
    }

    public RestClientConfiguration setTrustManagers(TrustManager[] trustManagers) {
        this.trustManagers = trustManagers;
        return this;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public RestClientConfiguration setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public RestClientConfiguration setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }
}
