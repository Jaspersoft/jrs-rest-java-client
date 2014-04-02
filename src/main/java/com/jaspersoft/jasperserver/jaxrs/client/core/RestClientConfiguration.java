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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestClientConfiguration {

    private static final Pattern URL_PATTERN = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    private String jasperReportsServerUrl;

    public RestClientConfiguration(String jasperReportsServerUrl){
        Matcher matcher = URL_PATTERN.matcher(jasperReportsServerUrl);
        if (!matcher.matches())
            throw new IllegalArgumentException("Given parameter is not a URL");
        setJasperReportsServerUrl(jasperReportsServerUrl);
    }

    public String getJasperReportsServerUrl() {
        return jasperReportsServerUrl;
    }

    public void setJasperReportsServerUrl(String jasperReportsServerUrl) {
        this.jasperReportsServerUrl = jasperReportsServerUrl;
    }

    public static RestClientConfiguration loadConfiguration(String path){
        InputStream is = RestClientConfiguration.class.getClassLoader().getResourceAsStream(path);
        return new RestClientConfiguration(getProperty(is, "url"));
    }

    private static String getProperty(InputStream is, String name) {

        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(name);
    }
}
