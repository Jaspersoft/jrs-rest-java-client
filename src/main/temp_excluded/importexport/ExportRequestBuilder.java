package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport;

import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;

public class ExportRequestBuilder<RequestType, ResponseType>
        extends JerseyRequestBuilder<RequestType, ResponseType> {

    private static final String URI = "/export";

    public ExportRequestBuilder(Class<ResponseType> responseClass) {
        super(responseClass);
        this.setPath(URI);
    }

}
