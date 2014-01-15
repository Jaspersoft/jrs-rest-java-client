package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.importservice;

import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class ImportService {

    private final SessionStorage sessionStorage;

    public ImportService(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public ImportTaskRequestAdapter newTask(){
        return new ImportTaskRequestAdapter(sessionStorage);
    }

    public ImportRequestAdapter task(String taskId){
        return new ImportRequestAdapter(sessionStorage, taskId);
    }

}
