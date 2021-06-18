package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport;

/**
 * @author Alexander Krasnyanskiy
 */
public class WrongResourceFormatException extends RuntimeException {
    public WrongResourceFormatException(String msg) {
        super(msg);
    }
}
