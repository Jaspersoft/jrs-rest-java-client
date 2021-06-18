package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support;

import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder.DomainResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder.MondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder.ReportUnitResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder.SecureMondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

/**
 * @author Alexander Krasnyanskiy
 */
public class ResourceBuilderFactory {

    /**
     * Factory Method for the certain builder.
     *
     * @param entity         plain entity on the basis of which we createInFolder ResourceDescriptor
     * @param sessionStorage side effect of new API
     * @return builder for certain entity
     */
    public static DomainResourceBuilder getBuilder(ClientSemanticLayerDataSource entity, SessionStorage sessionStorage) {
        return new DomainResourceBuilder(entity, sessionStorage);
    }

    public static ReportUnitResourceBuilder getBuilder(ClientReportUnit entity, SessionStorage sessionStorage) {
        return new ReportUnitResourceBuilder(entity, sessionStorage);
    }

    public static SecureMondrianConnectionResourceBuilder getBuilder(ClientSecureMondrianConnection entity, SessionStorage sessionStorage) {
        return new SecureMondrianConnectionResourceBuilder(entity, sessionStorage);
    }

    public static MondrianConnectionResourceBuilder getBuilder(ClientMondrianConnection entity, SessionStorage sessionStorage) {
        return new MondrianConnectionResourceBuilder(entity, sessionStorage);
    }
}
