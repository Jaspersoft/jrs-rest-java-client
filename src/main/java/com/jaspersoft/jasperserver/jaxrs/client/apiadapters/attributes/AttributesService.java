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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import java.util.Collection;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1-ALPHA
 */
public class AttributesService extends AbstractAdapter {

    public AttributesService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public SingleAttributeAdapter attribute() {
        return new SingleAttributeAdapter(sessionStorage, "/");
    }

    public SingleAttributeAdapter attribute(String attributeName) {
        return new SingleAttributeAdapter(sessionStorage, "/", attributeName);
    }

    public ServerBatchAttributeAdapter attributes() {
        return new ServerBatchAttributeAdapter(sessionStorage);
    }

    public ServerBatchAttributeAdapter attributes(Collection<String> attributesNames) {
        return new ServerBatchAttributeAdapter(sessionStorage, attributesNames);
    }

    public ServerBatchAttributeAdapter attributes(String... attributesNames) {
        return new ServerBatchAttributeAdapter(sessionStorage, attributesNames);
    }
}
