package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

import com.jaspersoft.jasperserver.dto.resources.*;

import java.util.HashMap;
import java.util.Map;

public class ResourcesTypeResolverUtil {

    private static final Map<Class<? extends ClientResource>, String> classToMimeMap;


    private static final Map<String, Class<? extends ClientResource>> mimeToClassMap;

    static {
        classToMimeMap = new HashMap<Class<? extends ClientResource>, String>() {{
            put(ClientAdhocDataView.class, ResourceMediaType.ADHOC_DATA_VIEW_JSON);
            put(ClientAwsDataSource.class, ResourceMediaType.AWS_DATA_SOURCE_JSON);
            put(ClientBeanDataSource.class, ResourceMediaType.BEAN_DATA_SOURCE_JSON);
            put(ClientCustomDataSource.class, ResourceMediaType.CUSTOM_DATA_SOURCE_JSON);
            put(ClientDataType.class, ResourceMediaType.DATA_TYPE_JSON);
            put(ClientFile.class, ResourceMediaType.FILE_JSON);
            put(ClientFolder.class, ResourceMediaType.FOLDER_JSON);
            put(ClientInputControl.class, ResourceMediaType.INPUT_CONTROL_JSON);
            put(ClientJdbcDataSource.class, ResourceMediaType.JDBC_DATA_SOURCE_JSON);
            put(ClientJndiJdbcDataSource.class, ResourceMediaType.JNDI_JDBC_DATA_SOURCE_JSON);
            put(ClientListOfValues.class, ResourceMediaType.LIST_OF_VALUES_JSON);
            put(ClientMondrianConnection.class, ResourceMediaType.MONDRIAN_CONNECTION_JSON);
            put(ClientMondrianXmlaDefinition.class, ResourceMediaType.MONDRIAN_XMLA_DEFINITION_JSON);
            put(ClientOlapUnit.class, ResourceMediaType.OLAP_UNIT_JSON);
            put(ClientQuery.class, ResourceMediaType.QUERY_JSON);
            put(ClientReportUnit.class, ResourceMediaType.REPORT_UNIT_JSON);
            put(ClientSecureMondrianConnection.class, ResourceMediaType.SECURE_MONDRIAN_CONNECTION_JSON);
            put(ClientSemanticLayerDataSource.class, ResourceMediaType.SEMANTIC_LAYER_DATA_SOURCE_JSON);
            put(ClientVirtualDataSource.class, ResourceMediaType.VIRTUAL_DATA_SOURCE_JSON);
            put(ClientXmlaConnection.class, ResourceMediaType.XMLA_CONNECTION_JSON);
            put(ClientResourceLookup.class, ResourceMediaType.RESOURCE_LOOKUP_JSON);
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
        return mimeToClassMap.get(mime);
    }

    public static Class<? extends ClientResource> getResourceType(ClientResource resource) {
        return resource.getClass();
    }

}
