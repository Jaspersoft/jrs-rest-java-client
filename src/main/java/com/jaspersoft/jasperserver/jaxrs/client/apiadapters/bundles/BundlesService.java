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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.bundles;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.Locale;
import java.util.Map;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;


public class BundlesService extends AbstractAdapter {

    public static final String SERVICE_URI = "bundles";
    private Locale locale;

    public BundlesService(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.locale = sessionStorage.getUserLocale();
    }

    public BundlesService forLocale(String locale) {
        if (locale != null) {
            this.forLocale(new Locale(locale));
        }
        return this;
    }

    public BundlesService forLocale(Locale locale) {
        if (locale != null) {
            this.locale = locale;
        }
        return this;
    }

    public OperationResult<Map<String, Map<String, String>>> allBundles() {
        return buildBundlesRequest().addParam("expanded", "true").get();
    }

    public OperationResult<Map<String, String>> bundle(String name) {
        return buildBundleRequest(name).get();
    }

    private JerseyRequest<Map<String, String>> buildBundleRequest(String bundleName) {
        JerseyRequest<Map<String, String>> request =
                JerseyRequest.buildRequest(sessionStorage, new GenericType<Map<String, String>>() {
                }, new String[]{SERVICE_URI, bundleName}, new DefaultErrorHandler());
        request.setAccept(MediaType.APPLICATION_JSON);
        if (!"".equals(this.locale.getLanguage())) {
            request.addHeader("Accept-Language", locale.toString().replace('_', '-'));
        }
        return request;
    }

    private JerseyRequest<Map<String, Map<String, String>>> buildBundlesRequest() {
        JerseyRequest<Map<String, Map<String, String>>> request =
                JerseyRequest.buildRequest(sessionStorage, new GenericType<Map<String, Map<String, String>>>() {
                }, new String[]{SERVICE_URI}, new DefaultErrorHandler());

        request.setAccept(MediaType.APPLICATION_JSON);
        if (!"".equals(this.locale.getLanguage())) {
            request.addHeader("Accept-Language", locale.toString().replace('_', '-'));
        }
        return request;
    }
}
