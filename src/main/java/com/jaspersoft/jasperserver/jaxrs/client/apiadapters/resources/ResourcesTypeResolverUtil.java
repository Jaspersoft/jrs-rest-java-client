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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientAdhocDataView;
import com.jaspersoft.jasperserver.dto.resources.ClientAwsDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientBeanDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientCustomDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientDataType;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientFolder;
import com.jaspersoft.jasperserver.dto.resources.ClientInputControl;
import com.jaspersoft.jasperserver.dto.resources.ClientJdbcDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientJndiJdbcDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientListOfValues;
import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientMondrianXmlaDefinition;
import com.jaspersoft.jasperserver.dto.resources.ClientOlapUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientQuery;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientVirtualDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientXmlaConnection;
import com.jaspersoft.jasperserver.jaxrs.client.core.ResourceMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.resources.ClientDashboard;
import com.jaspersoft.jasperserver.jaxrs.client.dto.resources.ClientDomainTopic;

import java.util.HashMap;
import java.util.Map;

public class ResourcesTypeResolverUtil {

    private static final Map<Class<? extends ClientResource>, String> classToMimeMap;
    private static final Map<String, Class<? extends ClientResource>> mimeToClassMap;

    static {
        classToMimeMap = new HashMap<Class<? extends ClientResource>, String>() {{
            put(ClientAdhocDataView.class, ResourceMediaType.ADHOC_DATA_VIEW_MIME);
            put(ClientAwsDataSource.class, ResourceMediaType.AWS_DATA_SOURCE_MIME);
            put(ClientBeanDataSource.class, ResourceMediaType.BEAN_DATA_SOURCE_MIME);
            put(ClientCustomDataSource.class, ResourceMediaType.CUSTOM_DATA_SOURCE_MIME);
            put(ClientDataType.class, ResourceMediaType.DATA_TYPE_MIME);
            put(ClientFile.class, ResourceMediaType.FILE_MIME);
            put(ClientFolder.class, ResourceMediaType.FOLDER_MIME);
            put(ClientInputControl.class, ResourceMediaType.INPUT_CONTROL_MIME);
            put(ClientJdbcDataSource.class, ResourceMediaType.JDBC_DATA_SOURCE_MIME);
            put(ClientJndiJdbcDataSource.class, ResourceMediaType.JNDI_JDBC_DATA_SOURCE_MIME);
            put(ClientListOfValues.class, ResourceMediaType.LIST_OF_VALUES_MIME);
            put(ClientMondrianConnection.class, ResourceMediaType.MONDRIAN_CONNECTION_MIME);
            put(ClientMondrianXmlaDefinition.class, ResourceMediaType.MONDRIAN_XMLA_DEFINITION_MIME);
            put(ClientOlapUnit.class, ResourceMediaType.OLAP_UNIT_MIME);
            put(ClientQuery.class, ResourceMediaType.QUERY_MIME);
            put(ClientReportUnit.class, ResourceMediaType.REPORT_UNIT_MIME);
            put(ClientSecureMondrianConnection.class, ResourceMediaType.SECURE_MONDRIAN_CONNECTION_MIME);
            put(ClientSemanticLayerDataSource.class, ResourceMediaType.SEMANTIC_LAYER_DATA_SOURCE_MIME);
            put(ClientVirtualDataSource.class, ResourceMediaType.VIRTUAL_DATA_SOURCE_MIME);
            put(ClientXmlaConnection.class, ResourceMediaType.XMLA_CONNECTION_MIME);
            put(ClientResourceLookup.class, ResourceMediaType.RESOURCE_LOOKUP_MIME);
            put(ClientDashboard.class, ResourceMediaType.DASHBOARD_MIME);
            put(ClientDomainTopic.class, ResourceMediaType.DOMAIN_TOPIC_MIME);
        }};

        mimeToClassMap = new HashMap<String, Class<? extends ClientResource>>();
        for (Map.Entry<Class<? extends ClientResource>, String> entry : classToMimeMap.entrySet()) {
            mimeToClassMap.put(entry.getValue(), entry.getKey());
        }
    }

    public static String getMimeType(Class<? extends ClientResource> clazz) {
        return classToMimeMap.get(clazz);
    }

    public static Class<? extends ClientResource> getClassForMime(String mime) {
        return mimeToClassMap.get(mime.substring(0, mime.indexOf("+")) + "+{mime}");
    }

    public static Class<? extends ClientResource> getResourceType(ClientResource resource) {
        return resource.getClass();
    }
}
