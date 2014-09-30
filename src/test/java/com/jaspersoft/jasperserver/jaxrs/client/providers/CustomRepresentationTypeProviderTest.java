package com.jaspersoft.jasperserver.jaxrs.client.providers;

import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.lang.annotation.Annotation;

import static org.testng.Assert.assertTrue;

public class CustomRepresentationTypeProviderTest {

    @Test
    public void should_return_true_when_params_are_proper() {
        CustomRepresentationTypeProvider provider = new CustomRepresentationTypeProvider();
        assertTrue(provider.isReadable(getClass(), getClass(), new Annotation[0], MediaType.APPLICATION_JSON_TYPE));
    }

    @Test
    public void method_should_return_true_when_params_are_proper() {
        CustomRepresentationTypeProvider provider = new CustomRepresentationTypeProvider();
        assertTrue(provider.isWriteable(getClass(), getClass(), new Annotation[0], MediaType.APPLICATION_JSON_TYPE));
    }
}