package com.jaspersoft.jasperserver.jaxrs.client.providers;

import org.codehaus.jackson.jaxrs.Annotations;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


@Provider
@Consumes({
        "application/collection+json",
        "application/repository.listOfValues+json",
        "application/repository.adhocDataView+json",
        "application/repository.awsDataSource+json",
        "application/repository.beanDataSource+json",
        "application/repository.customDataSource+json",
        "application/repository.dataType+json",
        "application/repository.file+json",
        "application/repository.folder+json",
        "application/repository.inputControl+json",
        "application/repository.jdbcDataSource+json",
        "application/repository.jndiJdbcDataSource+json",
        "application/repository.mondrianConnection+json",
        "application/repository.mondrianXmlaDefinition+json",
        "application/repository.olapUnit+json",
        "application/repository.query+json",
        "application/repository.reportUnit+json",
        "application/repository.secureMondrianConnection+json",
        "application/repository.semanticLayerDataSource+json",
        "application/repository.virtualDataSource+json",
        "application/repository.xmlaConnection+json",
        "application/repository.resourceLookup+json",
        "text/json"})
@Produces({
        "application/collection+json",
        "application/repository.listOfValues+json",
        "application/repository.adhocDataView+json",
        "application/repository.awsDataSource+json",
        "application/repository.beanDataSource+json",
        "application/repository.customDataSource+json",
        "application/repository.dataType+json",
        "application/repository.file+json",
        "application/repository.folder+json",
        "application/repository.inputControl+json",
        "application/repository.jdbcDataSource+json",
        "application/repository.jndiJdbcDataSource+json",
        "application/repository.mondrianConnection+json",
        "application/repository.mondrianXmlaDefinition+json",
        "application/repository.olapUnit+json",
        "application/repository.query+json",
        "application/repository.reportUnit+json",
        "application/repository.secureMondrianConnection+json",
        "application/repository.semanticLayerDataSource+json",
        "application/repository.virtualDataSource+json",
        "application/repository.xmlaConnection+json",
        "application/repository.resourceLookup+json",
        "text/json"})
public class CustomRepresentationTypeProvider extends JacksonJaxbJsonProvider{

    public CustomRepresentationTypeProvider() {
        super(Annotations.JACKSON, Annotations.JAXB);
    }

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return super.isReadable(aClass, type, annotations, mediaType);
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return super.isWriteable(aClass, type, annotations, mediaType);
    }

}

