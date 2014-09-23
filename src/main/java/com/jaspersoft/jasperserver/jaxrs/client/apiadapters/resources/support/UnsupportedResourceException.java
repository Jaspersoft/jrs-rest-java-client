package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support;

/**
 * @author Alexander Krasnyanskiy
 */
public class UnsupportedResourceException extends RuntimeException {
    public UnsupportedResourceException(String msg) {
        super(msg);
    }
}
