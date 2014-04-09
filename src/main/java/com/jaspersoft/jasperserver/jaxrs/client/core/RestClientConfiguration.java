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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestClientConfiguration {

    private static final Log log = LogFactory.getLog(RestClientConfiguration.class);
    private static final Pattern URL_PATTERN = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    private String jasperReportsServerUrl;
    private MimeType contentMimeType = MimeType.JSON;
    private MimeType acceptMimeType = MimeType.JSON;
    private JRSVersion jrsVersion = JRSVersion.v5_5_0;

    public RestClientConfiguration(String jasperReportsServerUrl){
        setJasperReportsServerUrl(jasperReportsServerUrl);
    }

    public RestClientConfiguration() {}

    public String getJasperReportsServerUrl() {
        return jasperReportsServerUrl;
    }

    public void setJasperReportsServerUrl(String jasperReportsServerUrl) {
        Matcher matcher = URL_PATTERN.matcher(jasperReportsServerUrl);
        if (!matcher.matches())
            throw new IllegalArgumentException("Given parameter is not a URL");
        this.jasperReportsServerUrl = jasperReportsServerUrl;
    }

    public MimeType getContentMimeType() {
        return contentMimeType;
    }

    public void setContentMimeType(MimeType contentMimeType) {
        this.contentMimeType = contentMimeType;
    }

    public MimeType getAcceptMimeType() {
        return acceptMimeType;
    }

    public void setAcceptMimeType(MimeType acceptMimeType) {
        this.acceptMimeType = acceptMimeType;
    }

    public JRSVersion getJrsVersion() {
        return jrsVersion;
    }

    public void setJrsVersion(JRSVersion jrsVersion) {
        this.jrsVersion = jrsVersion;
    }

    public static RestClientConfiguration loadConfiguration(String path){
        Properties properties = loadProperties(path);

        RestClientConfiguration configuration = new RestClientConfiguration();
        configuration.setJasperReportsServerUrl(properties.getProperty("url"));

        try {
            configuration.setContentMimeType(MimeType.valueOf(properties.getProperty("contentMimeType")));
        } catch (Exception e) {
            log.info("There is no mime type for content type or it isn't supported.", e);
        }

        try {
            configuration.setAcceptMimeType(MimeType.valueOf(properties.getProperty("acceptMimeType")));
        } catch (Exception e) {
            log.info("There is no mime type for accept type or it isn't supported.", e);
        }

        return configuration;
    }

    private static Properties loadProperties(String path){
        Properties properties = new Properties();
        try {
            InputStream is = RestClientConfiguration.class.getClassLoader().getResourceAsStream(path);
            properties.load(is);
        } catch (Exception e) {
            log.info("Error when loading properties file", e);
        }
        return properties;
    }

}
