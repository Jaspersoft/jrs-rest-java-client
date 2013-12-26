package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport;

import com.jaspersoft.jasperserver.dto.importexport.ExportTaskDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;

import javax.ws.rs.core.Response;

public class ExportRequestBuilder extends RequestBuilder<ExportTaskDto> {

    private static final String URI = "/export";

    public ExportRequestBuilder(Class responseClass) {
        super(responseClass);
        this.setPath(URI);
    }

}
