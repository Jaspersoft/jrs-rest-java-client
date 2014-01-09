package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;

import java.util.zip.GZIPOutputStream;

public class ImportRequestBuilder extends JerseyRequestBuilder<GZIPOutputStream, StateDto> {

    private static final String URI = "/import";

    public ImportRequestBuilder(){
        this(StateDto.class);
    }

    private ImportRequestBuilder(Class<StateDto> responseClass) {
        super(responseClass);
        this.setPath(URI);
    }
}
