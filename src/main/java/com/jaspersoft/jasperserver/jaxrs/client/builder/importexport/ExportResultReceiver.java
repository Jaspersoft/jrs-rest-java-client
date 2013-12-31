package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport;

import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

public class ExportResultReceiver<ResponseType> {

    private ExportRequestBuilder<Object, ResponseType> exportRequestBuilder;

    public ExportResultReceiver(ExportRequestBuilder<Object, ResponseType> exportRequestBuilder) {
        this.exportRequestBuilder = exportRequestBuilder;
    }


    public OperationResult<ResponseType> get() {
        return exportRequestBuilder.get();
    }
}
