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
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings.SingleSettingsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.util.Locale;


public class BundlesService extends AbstractAdapter {

    private Locale locale;

    public BundlesService(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.locale = Locale.getDefault();
    }

    public BundlesService forLocale(String locale) {
        if (locale != null) {
            this.locale = new Locale(locale);
        }
        return this;
    }

    public OperationResult<JSONObject> bundles() {
        return buildBundleRequest("/bundles").addParam("expanded", "true").get();
    }

    public OperationResult<JSONObject> bundles(String name) {
        return buildBundleRequest("/bundles", name).get();
    }

    private JerseyRequest<JSONObject> buildBundleRequest(String... path) {
        JerseyRequest<JSONObject> request =
                JerseyRequest.buildRequest(sessionStorage, JSONObject.class, path, new DefaultErrorHandler());
        request.setAccept(MediaType.APPLICATION_JSON).addHeader("Accept-Language", locale.toString().replace('_', '-'));
        return request;
    }



}
