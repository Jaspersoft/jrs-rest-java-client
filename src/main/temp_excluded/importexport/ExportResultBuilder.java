package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.concurrent.Future;

public class ExportResultBuilder {

    private static final String STATE_URI = "/state";

    private String taskId;

    public ExportResultBuilder(String taskId){
        this.taskId = taskId;
    }

    public ExportResultReceiver<StateDto> state(){
        ExportRequestBuilder<Object, StateDto> requestBuilder =
                new ExportRequestBuilder<Object, StateDto>(StateDto.class);
        requestBuilder.setPath(taskId).setPath(STATE_URI);
        return new StateReceiver(requestBuilder);
    }

    public ExportResultReceiver<InputStream> fetch(){
        ExportRequestBuilder<Object, InputStream> requestBuilder =
                new ExportRequestBuilder<Object, InputStream>(InputStream.class);
        requestBuilder.setPath(taskId).setPath("mockFilename");
        return new ExportReceiver(requestBuilder);
    }

    public abstract class ExportResultReceiver<ResponseType> {

        protected ExportRequestBuilder<Object, ResponseType> exportRequestBuilder;

        public ExportResultReceiver(ExportRequestBuilder<Object, ResponseType> exportRequestBuilder) {
            this.exportRequestBuilder = exportRequestBuilder;
        }

        public abstract OperationResult<ResponseType> get();
    }

    private class StateReceiver extends ExportResultReceiver<StateDto> {

        public StateReceiver(ExportRequestBuilder<Object, StateDto> exportRequestBuilder) {
            super(exportRequestBuilder);
        }

        @Override
        public OperationResult<StateDto> get() {
            return exportRequestBuilder.get();
        }
    }

    private class ExportReceiver extends ExportResultReceiver<InputStream> {

        public ExportReceiver(ExportRequestBuilder<Object, InputStream> exportRequestBuilder) {
            super(exportRequestBuilder);
        }

        @Override
        public OperationResult<InputStream> get() {
            try {
                AsyncInvoker asyncInvoker = exportRequestBuilder.getPath().request("application/zip").async();
                Future<Response> responseFuture = asyncInvoker.get();
                return new OperationResult<InputStream>(responseFuture.get(), InputStream.class);
            } catch (Exception e) {
                return null;
            }
        }
    }

}
