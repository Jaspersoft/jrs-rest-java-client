package com.jaspersoft.jasperserver.jaxrs.client.providers;

import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
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
        ResourceMediaType.LIST_OF_VALUES_JSON,
        ResourceMediaType.ADHOC_DATA_VIEW_JSON,
        ResourceMediaType.AWS_DATA_SOURCE_JSON,
        ResourceMediaType.BEAN_DATA_SOURCE_JSON,
        ResourceMediaType.CUSTOM_DATA_SOURCE_JSON,
        ResourceMediaType.DATA_TYPE_JSON,
        ResourceMediaType.FILE_JSON,
        ResourceMediaType.FOLDER_JSON,
        ResourceMediaType.INPUT_CONTROL_JSON,
        ResourceMediaType.JDBC_DATA_SOURCE_JSON,
        ResourceMediaType.JNDI_JDBC_DATA_SOURCE_JSON,
        ResourceMediaType.MONDRIAN_CONNECTION_JSON,
        ResourceMediaType.MONDRIAN_XMLA_DEFINITION_JSON,
        ResourceMediaType.OLAP_UNIT_JSON,
        ResourceMediaType.QUERY_JSON,
        ResourceMediaType.REPORT_UNIT_JSON,
        ResourceMediaType.SECURE_MONDRIAN_CONNECTION_JSON,
        ResourceMediaType.SEMANTIC_LAYER_DATA_SOURCE_JSON,
        ResourceMediaType.VIRTUAL_DATA_SOURCE_JSON,
        ResourceMediaType.XMLA_CONNECTION_JSON,
        ResourceMediaType.RESOURCE_LOOKUP_JSON,
        "text/json"})
@Produces({
        "application/collection+json",
        ResourceMediaType.LIST_OF_VALUES_JSON,
        ResourceMediaType.ADHOC_DATA_VIEW_JSON,
        ResourceMediaType.AWS_DATA_SOURCE_JSON,
        ResourceMediaType.BEAN_DATA_SOURCE_JSON,
        ResourceMediaType.CUSTOM_DATA_SOURCE_JSON,
        ResourceMediaType.DATA_TYPE_JSON,
        ResourceMediaType.FILE_JSON,
        ResourceMediaType.FOLDER_JSON,
        ResourceMediaType.INPUT_CONTROL_JSON,
        ResourceMediaType.JDBC_DATA_SOURCE_JSON,
        ResourceMediaType.JNDI_JDBC_DATA_SOURCE_JSON,
        ResourceMediaType.MONDRIAN_CONNECTION_JSON,
        ResourceMediaType.MONDRIAN_XMLA_DEFINITION_JSON,
        ResourceMediaType.OLAP_UNIT_JSON,
        ResourceMediaType.QUERY_JSON,
        ResourceMediaType.REPORT_UNIT_JSON,
        ResourceMediaType.SECURE_MONDRIAN_CONNECTION_JSON,
        ResourceMediaType.SEMANTIC_LAYER_DATA_SOURCE_JSON,
        ResourceMediaType.VIRTUAL_DATA_SOURCE_JSON,
        ResourceMediaType.XMLA_CONNECTION_JSON,
        ResourceMediaType.RESOURCE_LOOKUP_JSON,
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

