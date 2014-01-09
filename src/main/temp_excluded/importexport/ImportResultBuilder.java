package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

public class ImportResultBuilder {

    private static final String STATE_URI = "/state";

    private String taskId;

    public ImportResultBuilder(String taskId){
        this.taskId = taskId;
    }

    public StateReceiver state(){
        ImportRequestBuilder requestBuilder = new ImportRequestBuilder();
        requestBuilder.setPath(taskId).setPath(STATE_URI);
        return new StateReceiver(requestBuilder);
    }


    public class StateReceiver {

        private ImportRequestBuilder importRequestBuilder;

        public StateReceiver(ImportRequestBuilder importRequestBuilder) {
            this.importRequestBuilder = importRequestBuilder;
        }

        public OperationResult<StateDto> get(){
            return importRequestBuilder.get();
        }

    }

}
