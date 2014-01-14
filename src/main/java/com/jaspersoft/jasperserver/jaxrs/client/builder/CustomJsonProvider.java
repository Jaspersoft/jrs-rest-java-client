package com.jaspersoft.jasperserver.jaxrs.client.builder;

import org.codehaus.jackson.jaxrs.*;
import org.jboss.resteasy.annotations.providers.NoJackson;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import org.codehaus.jackson.jaxrs.Annotations;


@Provider
@Consumes({"application/*+json", "text/json"})
@Produces({"application/*+json", "text/json"})
public class CustomJsonProvider extends JacksonJaxbJsonProvider
{
    public CustomJsonProvider() {
        super(Annotations.JACKSON, Annotations.JAXB);
    }

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType)
    {
        if (mediaType.getType().endsWith("+json")) return true;
        return super.isReadable(aClass, type, annotations, mediaType);
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType)
    {
        if (mediaType.getType().endsWith("+json")) return true;
        return super.isWriteable(aClass, type, annotations, mediaType);
    }
}

