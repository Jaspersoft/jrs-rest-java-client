package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;

import java.util.zip.GZIPOutputStream;

public class ImportRequestBuilder extends RequestBuilder<GZIPOutputStream, StateDto> {

    private static final String URI = "/import";

    public ImportRequestBuilder(){
        this(StateDto.class);
    }

    private ImportRequestBuilder(Class<StateDto> responseClass) {
        super(responseClass);
        this.setPath(URI);
    }
}
