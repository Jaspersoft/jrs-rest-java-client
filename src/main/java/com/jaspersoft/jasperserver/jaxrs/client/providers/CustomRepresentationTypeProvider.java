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

package com.jaspersoft.jasperserver.jaxrs.client.providers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


@Provider
@Consumes({
        "application/xml",
        "application/collection+json",
        "application/collection+xml",
        "application/job+json",
        "application/json",
        "application/job+xml",
        ContextMediaTypes.FTP_JSON,
        ContextMediaTypes.FTP_XML,
        ContextMediaTypes.LOCAL_FILE_SYSTEM_JSON,
        ContextMediaTypes.LOCAL_FILE_SYSTEM_XML,
        ContextMediaTypes.TXT_FILE_JSON,
        ContextMediaTypes.TXT_FILE_XML,
        ContextMediaTypes.XLS_FILE_JSON,
        ContextMediaTypes.XLS_FILE_XML,
        ResourceMediaType.LIST_OF_VALUES_JSON,
        ResourceMediaType.LIST_OF_VALUES_XML,
        ResourceMediaType.ADHOC_DATA_VIEW_JSON,
        ResourceMediaType.ADHOC_DATA_VIEW_XML,
        ResourceMediaType.AWS_DATA_SOURCE_JSON,
        ResourceMediaType.AWS_DATA_SOURCE_XML,
        ResourceMediaType.BEAN_DATA_SOURCE_JSON,
        ResourceMediaType.BEAN_DATA_SOURCE_XML,
        ResourceMediaType.CUSTOM_DATA_SOURCE_JSON,
        ResourceMediaType.CUSTOM_DATA_SOURCE_XML,
        ResourceMediaType.DATA_TYPE_JSON,
        ResourceMediaType.DATA_TYPE_XML,
        ResourceMediaType.FILE_JSON,
        ResourceMediaType.FILE_XML,
        ResourceMediaType.FOLDER_JSON,
        ResourceMediaType.FOLDER_XML,
        ResourceMediaType.INPUT_CONTROL_JSON,
        ResourceMediaType.INPUT_CONTROL_XML,
        ResourceMediaType.JDBC_DATA_SOURCE_JSON,
        ResourceMediaType.JDBC_DATA_SOURCE_XML,
        ResourceMediaType.JNDI_JDBC_DATA_SOURCE_JSON,
        ResourceMediaType.JNDI_JDBC_DATA_SOURCE_XML,
        ResourceMediaType.MONDRIAN_CONNECTION_JSON,
        ResourceMediaType.MONDRIAN_CONNECTION_XML,
        ResourceMediaType.MONDRIAN_XMLA_DEFINITION_JSON,
        ResourceMediaType.MONDRIAN_XMLA_DEFINITION_XML,
        ResourceMediaType.OLAP_UNIT_JSON,
        ResourceMediaType.OLAP_UNIT_XML,
        ResourceMediaType.QUERY_JSON,
        ResourceMediaType.QUERY_XML,
        ResourceMediaType.REPORT_UNIT_JSON,
        ResourceMediaType.REPORT_UNIT_XML,
        ResourceMediaType.SECURE_MONDRIAN_CONNECTION_JSON,
        ResourceMediaType.SECURE_MONDRIAN_CONNECTION_XML,
        ResourceMediaType.SEMANTIC_LAYER_DATA_SOURCE_JSON,
        ResourceMediaType.SEMANTIC_LAYER_DATA_SOURCE_XML,
        ResourceMediaType.VIRTUAL_DATA_SOURCE_JSON,
        ResourceMediaType.VIRTUAL_DATA_SOURCE_XML,
        ResourceMediaType.XMLA_CONNECTION_JSON,
        ResourceMediaType.XMLA_CONNECTION_XML,
        ResourceMediaType.RESOURCE_LOOKUP_JSON,
        ResourceMediaType.RESOURCE_LOOKUP_XML,
        "application/jdbc.metadata+json",
        "application/jdbc.metadata+xml",
        "application/repository.dashboard+json",
        "application/repository.dashboard+xml",
        "application/repository.domainTopic+json",
        "application/repository.domainTopic+xml",
        "application/repository.domainSchema+json",
        "application/repository.domainSchema+xml",
        ContextMediaTypes.FTP_JSON,
        ContextMediaTypes.FTP_XML,
        ContextMediaTypes.LOCAL_FILE_SYSTEM_JSON,
        ContextMediaTypes.LOCAL_FILE_SYSTEM_XML,
        ContextMediaTypes.DOM_EL_CONTEXT_JSON,
        ContextMediaTypes.DOM_EL_COLLECTION_CONTEXT_JSON,
        "application/hal+json",
        "application/hal+xml",
        "text/json",
        "text/xml",
        "application/attributes.collection+json",
        "application/attributes.collection+xml",
        "application/repository.customDataSource.metadata+json",
        "application/repository.customDataSource.metadata+xml",
        "application/repository.semanticLayerDataSource+json",
        "application/repository.semanticLayerDataSource+xml",
        "application/repository.domain+json",
        "application/repository.domain+xml",
        "application/repository.domainSchema+json",
        "application/repository.domainSchema+xml",
        "application/repository.reportUnit+json",
        "application/repository.reportUnit+xml",
        "application/repository.reportOptions+json",
        "application/repository.reportOptions+xml",
        "application/repository.reportUnit.metadata+json",
        "application/repository.reportUnit.metadata+xml",
        "application/repository.domain+json",
        "application/execution.multiLevelQuery+json",
        "application/execution.multiAxisQuery+json",
        "application/execution.providedQuery+json",
        "application/execution.multiLevelQuery+xml",
        "application/execution.multiAxisQuery+xml",
        "application/execution.providedQuery+xml",
        "application/flatData+json",
        "application/multiLevelData+json",
        "application/multiAxisData+json",
        "application/flatData+xml",
        "application/multiLevelData+xml",
        "application/multiAxisData+xml",
        "application/contexts.partialMetadataOptions+json",
        "application/adhoc.multiLevelQuery+json"
})
@Produces({
        "application/collection+json",
        "application/collection+xml",
        "application/job+json",
        "application/json",
        "application/job+xml",
        ContextMediaTypes.FTP_JSON,
        ContextMediaTypes.FTP_XML,
        ContextMediaTypes.LOCAL_FILE_SYSTEM_JSON,
        ContextMediaTypes.LOCAL_FILE_SYSTEM_XML,
        ContextMediaTypes.TXT_FILE_JSON,
        ContextMediaTypes.TXT_FILE_XML,
        ContextMediaTypes.XLS_FILE_JSON,
        ContextMediaTypes.XLS_FILE_XML,
        ResourceMediaType.LIST_OF_VALUES_JSON,
        ResourceMediaType.LIST_OF_VALUES_XML,
        ResourceMediaType.ADHOC_DATA_VIEW_JSON,
        ResourceMediaType.ADHOC_DATA_VIEW_XML,
        ResourceMediaType.AWS_DATA_SOURCE_JSON,
        ResourceMediaType.AWS_DATA_SOURCE_XML,
        ResourceMediaType.BEAN_DATA_SOURCE_JSON,
        ResourceMediaType.BEAN_DATA_SOURCE_XML,
        ResourceMediaType.CUSTOM_DATA_SOURCE_JSON,
        ResourceMediaType.CUSTOM_DATA_SOURCE_XML,
        ResourceMediaType.DATA_TYPE_JSON,
        ResourceMediaType.DATA_TYPE_XML,
        ResourceMediaType.FILE_JSON,
        ResourceMediaType.FILE_XML,
        ResourceMediaType.FOLDER_JSON,
        ResourceMediaType.FOLDER_XML,
        ResourceMediaType.INPUT_CONTROL_JSON,
        ResourceMediaType.INPUT_CONTROL_XML,
        ResourceMediaType.JDBC_DATA_SOURCE_JSON,
        ResourceMediaType.JDBC_DATA_SOURCE_XML,
        ResourceMediaType.JNDI_JDBC_DATA_SOURCE_JSON,
        ResourceMediaType.JNDI_JDBC_DATA_SOURCE_XML,
        ResourceMediaType.MONDRIAN_CONNECTION_JSON,
        ResourceMediaType.MONDRIAN_CONNECTION_XML,
        ResourceMediaType.MONDRIAN_XMLA_DEFINITION_JSON,
        ResourceMediaType.MONDRIAN_XMLA_DEFINITION_XML,
        ResourceMediaType.OLAP_UNIT_JSON,
        ResourceMediaType.OLAP_UNIT_XML,
        ResourceMediaType.QUERY_JSON,
        ResourceMediaType.QUERY_XML,
        ResourceMediaType.REPORT_UNIT_JSON,
        ResourceMediaType.REPORT_UNIT_XML,
        ResourceMediaType.SECURE_MONDRIAN_CONNECTION_JSON,
        ResourceMediaType.SECURE_MONDRIAN_CONNECTION_XML,
        ResourceMediaType.SEMANTIC_LAYER_DATA_SOURCE_JSON,
        ResourceMediaType.SEMANTIC_LAYER_DATA_SOURCE_XML,
        ResourceMediaType.VIRTUAL_DATA_SOURCE_JSON,
        ResourceMediaType.VIRTUAL_DATA_SOURCE_XML,
        ResourceMediaType.XMLA_CONNECTION_JSON,
        ResourceMediaType.XMLA_CONNECTION_XML,
        ResourceMediaType.RESOURCE_LOOKUP_JSON,
        ResourceMediaType.RESOURCE_LOOKUP_XML,
        "application/jdbc.metadata+json",
        "application/jdbc.metadata+xml",
        "application/repository.dashboard+json",
        "application/repository.dashboard+xml",
        "application/repository.domainTopic+json",
        "application/repository.domainTopic+xml",
        ContextMediaTypes.FTP_JSON,
        ContextMediaTypes.FTP_XML,
        ContextMediaTypes.LOCAL_FILE_SYSTEM_JSON,
        ContextMediaTypes.LOCAL_FILE_SYSTEM_XML,
        ContextMediaTypes.DOM_EL_CONTEXT_JSON,
        ContextMediaTypes.DOM_EL_COLLECTION_CONTEXT_JSON,
        "application/repository.domainSchema+json",
        "application/repository.domainSchema+xml",
        "application/xml",
        "application/hal+json",
        "application/hal+xml",
        "text/json",
        "text/xml",
        "application/attributes.collection+json",
        "application/attributes.collection+xml",
        "application/repository.customDataSource.metadata+json",
        "application/repository.customDataSource.metadata+xml",
        "application/repository.semanticLayerDataSource+json",
        "application/repository.semanticLayerDataSource+xml",
        "application/repository.domain+json",
        "application/repository.domain+xml",
        "application/repository.domainSchema+json",
        "application/repository.domainSchema+xml",
        "application/repository.reportUnit+json",
        "application/repository.reportUnit+xml",
        "application/repository.reportOptions+json",
        "application/repository.reportOptions+xml",
        "application/repository.reportUnit.metadata+json",
        "application/repository.reportUnit.metadata+xml",
        "application/repository.domain+json",
        "application/execution.multiLevelQuery+json",
        "application/execution.multiAxisQuery+json",
        "application/execution.providedQuery+json",
        "application/execution.multiLevelQuery+xml",
        "application/execution.multiAxisQuery+xml",
        "application/execution.providedQuery+xml",
        "application/flatData+json",
        "application/multiLevelData+json",
        "application/multiAxisData+json",
        "application/flatData+xml",
        "application/multiLevelData+xml",
        "application/multiAxisData+xml",
        "application/contexts.partialMetadataOptions+json",
        "application/adhoc.multiLevelQuery+json"
})
public class CustomRepresentationTypeProvider extends JacksonJaxbJsonProvider {
    private static volatile JsonMapper mapper;

    public CustomRepresentationTypeProvider() {
        super();
        setMapper(getJsonMapper());
    }

    private JsonMapper getJsonMapper() {
        if (mapper == null) {
            synchronized (JacksonJaxbJsonProvider.class) {
                if (mapper == null) {
                    mapper = JsonMapper
                            .builder()
                            // Prevent exceptions from being thrown for unknown properties
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            // Use XML wrapper name as JSON property name
                            .configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true)
                            .configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true)
                            .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true)
                            .build();
                    // Serialize dates using ISO8601 format
                    // Jackson uses timestamps by default, so use StdDateFormat to get ISO8601
                    mapper.setDateFormat(new StdDateFormat());
                    // Deserialize dates using ISO8601 format
                    mapper.getDeserializationConfig().with(new StdDateFormat());
                    // ignore fields with null values
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    mapper.setDefaultPropertyInclusion(
                            JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.ALWAYS));
                    mapper.addHandler(new DeserializationProblemHandler() {
                        @Override
                        public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser jp,
                                                             JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) {
                            return true;
                        }
                    });
                    AnnotationIntrospector primary = new JaxbAnnotationIntrospector(mapper.getTypeFactory());
                    AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
                    AnnotationIntrospector pair = AnnotationIntrospector.pair(primary, secondary);
                    mapper.setAnnotationIntrospector(pair);
                }
            }
        }
        return mapper;
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

