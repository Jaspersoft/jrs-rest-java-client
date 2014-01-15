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
@Consumes({"application/collection+json", "text/json"})
@Produces({"application/collection+json", "text/json"})
public class CollectionJsonProvider extends JacksonJaxbJsonProvider{

    public CollectionJsonProvider() {
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

