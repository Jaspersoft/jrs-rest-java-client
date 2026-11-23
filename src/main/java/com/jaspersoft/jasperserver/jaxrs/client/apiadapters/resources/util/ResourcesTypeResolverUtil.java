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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util;

import com.jaspersoft.jasperserver.dto.resources.ClientAdhocDataView;
import com.jaspersoft.jasperserver.dto.resources.ClientAwsDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientBeanDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientCustomDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientDashboard;
import com.jaspersoft.jasperserver.dto.resources.ClientDataType;
import com.jaspersoft.jasperserver.dto.resources.ClientDomainTopic;
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
import com.jaspersoft.jasperserver.dto.resources.ClientReportOptions;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientVirtualDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientXmlaConnection;
import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomainSchema;
import java.util.HashMap;
import java.util.Map;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

public class ResourcesTypeResolverUtil {

    private static final Map<Class<? extends ClientResource>, String> classToMimeMap;
    private static final Map<String, Class<? extends ClientResource>> mimeToClassMap;
    public static final String RESOURCE_MEDIA_TYPE_PREFIX = "application/repository.";
    public static final String RESOURCE_MIME_TYPE = "+{mime}";

    static {
        classToMimeMap = new HashMap<Class<? extends ClientResource>, String>() {{
            put(ClientAdhocDataView.class, ResourceMediaType.ADHOC_DATA_VIEW_CLIENT_TYPE);
            put(ClientAwsDataSource.class, ResourceMediaType.AWS_DATA_SOURCE_CLIENT_TYPE);
            put(ClientBeanDataSource.class, ResourceMediaType.BEAN_DATA_SOURCE_CLIENT_TYPE);
            put(ClientCustomDataSource.class, ResourceMediaType.CUSTOM_DATA_SOURCE_CLIENT_TYPE);
            put(ClientDataType.class, ResourceMediaType.DATA_TYPE_CLIENT_TYPE);
            put(ClientFile.class, ResourceMediaType.FILE_CLIENT_TYPE);
            put(ClientFolder.class, ResourceMediaType.FOLDER_CLIENT_TYPE);
            put(ClientInputControl.class, ResourceMediaType.INPUT_CONTROL_CLIENT_TYPE);
            put(ClientJdbcDataSource.class, ResourceMediaType.JDBC_DATA_SOURCE_CLIENT_TYPE);
            put(ClientJndiJdbcDataSource.class, ResourceMediaType.JNDI_JDBC_DATA_SOURCE_CLIENT_TYPE);
            put(ClientListOfValues.class, ResourceMediaType.LIST_OF_VALUES_CLIENT_TYPE);
            put(ClientMondrianConnection.class, ResourceMediaType.MONDRIAN_CONNECTION_CLIENT_TYPE);
            put(ClientMondrianXmlaDefinition.class, ResourceMediaType.MONDRIAN_XMLA_DEFINITION_CLIENT_TYPE);
            put(ClientOlapUnit.class, ResourceMediaType.OLAP_UNIT_CLIENT_TYPE);
            put(ClientQuery.class, ResourceMediaType.QUERY_CLIENT_TYPE);
            put(ClientReportUnit.class, ResourceMediaType.REPORT_UNIT_CLIENT_TYPE);
            put(ClientSecureMondrianConnection.class, ResourceMediaType.SECURE_MONDRIAN_CONNECTION_CLIENT_TYPE);
            put(ClientSemanticLayerDataSource.class, ResourceMediaType.SEMANTIC_LAYER_DATA_SOURCE_CLIENT_TYPE);
            put(ClientVirtualDataSource.class, ResourceMediaType.VIRTUAL_DATA_SOURCE_CLIENT_TYPE);
            put(ClientXmlaConnection.class, ResourceMediaType.XMLA_CONNECTION_CLIENT_TYPE);
            put(ClientResourceLookup.class, ResourceMediaType.RESOURCE_LOOKUP_CLIENT_TYPE);
            put(ClientDashboard.class, ResourceMediaType.DASHBOARD_CLIENT_TYPE);
            put(ClientDomainTopic.class, ResourceMediaType.DOMAIN_TOPIC_CLIENT_TYPE);
            put(ClientDomain.class, ResourceMediaType.DOMAIN_CLIENT_TYPE);
            put(ClientDomainSchema.class, "domainSchema");
            put(ClientReportOptions.class, "reportOptions");
        }};

        for (Map.Entry<Class<? extends ClientResource>, String> entry : classToMimeMap.entrySet()) {
            entry.setValue(typeToMime(entry.getValue()));
        }

        mimeToClassMap = new HashMap<String, Class<? extends ClientResource>>();
        for (Map.Entry<Class<? extends ClientResource>, String> entry : classToMimeMap.entrySet()) {
            mimeToClassMap.put(entry.getValue(), entry.getKey());
        }
    }
    private static String typeToMime(String type) {
        return RESOURCE_MEDIA_TYPE_PREFIX + type + RESOURCE_MIME_TYPE;
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

    public static String extractClientType(Class<?> clientObjectClass) {
        String clientResourceType = null;
        final XmlRootElement xmlRootElement = clientObjectClass.getAnnotation(XmlRootElement.class);
        if (xmlRootElement != null && !"##default".equals(xmlRootElement.name())) {
            clientResourceType = xmlRootElement.name();
        } else {
            final XmlType xmlType = clientObjectClass.getAnnotation(XmlType.class);
            if (xmlType != null && !"##default".equals(xmlType.name())) {
                clientResourceType = xmlType.name();
            }
        }
        if (clientResourceType == null) {
            final String classSimpleName = clientObjectClass.getSimpleName();
            clientResourceType = classSimpleName.replaceFirst("^.", classSimpleName.substring(0, 1).toLowerCase());
        }
        return typeToMime(clientResourceType);
    }
}
