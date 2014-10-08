package com.jaspersoft.jasperserver.jaxrs.client.core.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeType;

/**
 * @author Alexander Krasnyanskiy
 */
public class Headers {

    private MimeType accept;
    private MimeType contentType;

    public MimeType getAccept() {
        return accept;
    }

    public void setAccept(MimeType accept) {
        this.accept = accept;
    }

    public MimeType getContentType() {
        return contentType;
    }

    @JsonProperty("content-type")
    public void setContentType(MimeType contentType) {
        this.contentType = contentType;
    }
}
