package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

import com.jaspersoft.jasperserver.dto.resources.*;

import java.util.HashMap;
import java.util.Map;

public class ResourcesTypeResolverUtil {

    private static final Map<Class<? extends ClientResource>, String> typesMap =
            new HashMap<Class<? extends ClientResource>, String>(){{
                put(ClientAdhocDataView.class, "application/repository.adhocDataView+json");
                put(ClientAwsDataSource.class, "application/repository.awsDataSource+json");
                put(ClientBeanDataSource.class, "application/repository.beanDataSource+json");
                put(ClientCustomDataSource.class, "application/repository.customDataSource+json");
                put(ClientDataType.class, "application/repository.dataType+json");
                put(ClientFile.class, "application/repository.file+json");
                put(ClientFolder.class, "application/repository.folder+json");
                put(ClientInputControl.class, "application/repository.inputControl+json");
                put(ClientJdbcDataSource.class, "application/repository.jdbcDataSource+json");
                put(ClientJndiJdbcDataSource.class, "application/repository.jndiJdbcDataSource+json");
                put(ClientListOfValues.class, "application/repository.listOfValues+json");
                put(ClientMondrianConnection.class, "application/repository.mondrianConnection+json");
                put(ClientMondrianXmlaDefinition.class, "application/repository.mondrianXmlaDefinition+json");
                put(ClientOlapUnit.class, "application/repository.olapUnit+json");
                put(ClientQuery.class, "application/repository.query+json");
                put(ClientReportUnit.class, "application/repository.reportUnit+json");
                put(ClientSecureMondrianConnection.class, "application/repository.secureMondrianConnection+json");
                put(ClientSemanticLayerDataSource.class, "application/repository.semanticLayerDataSource+json");
                put(ClientVirtualDataSource.class, "application/repository.virtualDataSource+json");
                put(ClientXmlaConnection.class, "application/repository.xmlaConnection+json");
                put(ClientResourceLookup.class, "application/repository.resourceLookup+json");
            }};

    public static String getMimeType(Class<? extends ClientResource> clazz){
        return typesMap.get(clazz);
    }



}
