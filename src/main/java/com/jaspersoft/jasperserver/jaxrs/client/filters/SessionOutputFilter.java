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
package com.jaspersoft.jasperserver.jaxrs.client.filters;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import java.util.Map;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.NewCookie;

public class SessionOutputFilter implements ClientRequestFilter {

    private final SessionStorage sessionStorage;

    public SessionOutputFilter(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    @Override
    public void filter(ClientRequestContext requestContext) {
        StringBuilder cookiesString = new StringBuilder();

        for (Map.Entry<String, NewCookie> entry : sessionStorage.getCookies().entrySet()) {
            cookiesString.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue().getValue())
                    .append(";");
        }
        List<Object> cookies = new ArrayList<>();
        cookies.add(cookiesString.toString());
        requestContext.getHeaders().put("Cookie", cookies);
    }
}
