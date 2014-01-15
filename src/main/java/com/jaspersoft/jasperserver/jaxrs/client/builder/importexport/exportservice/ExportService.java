package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.exportservice;

import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class ExportService {

    private final SessionStorage sessionStorage;

    public ExportService(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public ExportTaskBuilder newTask(){
        return new ExportTaskBuilder(sessionStorage);
    }

    public ExportRequestAdapter task(String taskId){
        return new ExportRequestAdapter(sessionStorage, taskId);
    }


}
