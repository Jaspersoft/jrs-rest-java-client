package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport;

import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.DomainResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.SemanticLayerResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.MondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.ReportUnitResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.SecureMondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.DomainOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.MondrianConnectionResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.ReportUnitResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.SecureMondrianConnectionResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.SemanticLayerResourceOperationProcessorDecorator;
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
    public static SemanticLayerResourceBuilder getBuilder(ClientSemanticLayerDataSource entity, SessionStorage sessionStorage) {
        return new SemanticLayerResourceBuilder(entity, sessionStorage);
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

    public static DomainResourceBuilder getBuilder(ClientDomain entity, SessionStorage sessionStorage) {
        return new DomainResourceBuilder(entity, sessionStorage);
    }

    public static SemanticLayerResourceOperationProcessorDecorator getDecorator(ClientSemanticLayerDataSource entity, SessionStorage sessionStorage) {
        return new SemanticLayerResourceOperationProcessorDecorator(sessionStorage, entity);
    }

    public static ReportUnitResourceOperationProcessorDecorator getDecorator(ClientReportUnit entity, SessionStorage sessionStorage) {
        return new ReportUnitResourceOperationProcessorDecorator(entity, sessionStorage);
    }

    public static SecureMondrianConnectionResourceOperationProcessorDecorator getDecorator(ClientSecureMondrianConnection entity, SessionStorage sessionStorage) {
        return new SecureMondrianConnectionResourceOperationProcessorDecorator(sessionStorage, entity);
    }

    public static MondrianConnectionResourceOperationProcessorDecorator getDecorator(ClientMondrianConnection entity, SessionStorage sessionStorage) {
        return new MondrianConnectionResourceOperationProcessorDecorator(sessionStorage, entity);
    }

    public static DomainOperationProcessorDecorator getDecorator(ClientDomain entity, SessionStorage sessionStorage) {
        return new DomainOperationProcessorDecorator(sessionStorage, entity);
    }
}
