package com.jaspersoft.jasperserver.jaxrs.client.builder;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.glassfish.jersey.CommonProperties;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

public class CustomJsonFeature implements Feature {

    @Override
    public boolean configure(final FeatureContext context) {
        final String disableMoxy = CommonProperties.MOXY_JSON_FEATURE_DISABLE + '.'
                + context.getConfiguration().getRuntimeType().name().toLowerCase();
        context.property(disableMoxy, true);

        context.register(JacksonJaxbJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
        context.register(CustomJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
        return true;
    }
}