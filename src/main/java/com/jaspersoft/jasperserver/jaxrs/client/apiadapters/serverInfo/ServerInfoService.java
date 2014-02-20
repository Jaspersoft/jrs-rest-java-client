package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ServerInfo;

import javax.ws.rs.core.MediaType;


public class ServerInfoService extends AbstractAdapter {

    public ServerInfoService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public OperationResult<ServerInfo> details(){
        return JerseyRequestBuilder.buildRequest(sessionStorage, ServerInfo.class, new String[]{"/serverInfo"}).get();
    }

    private JerseyRequestBuilder<String> buildServerInfoRequest(String path){
        JerseyRequestBuilder<String> builder =
                JerseyRequestBuilder.buildRequest(sessionStorage, String.class, new String[]{"/serverInfo", path});
        builder.setAccept(MediaType.TEXT_PLAIN);
        return builder;
    }

    public OperationResult<String> edition(){
        return buildServerInfoRequest("/edition").get();
    }

    public OperationResult<String> version(){
        return buildServerInfoRequest("/version").get();
    }

    public OperationResult<String> build(){
        return buildServerInfoRequest("/build").get();
    }

    public OperationResult<String> features(){
        return buildServerInfoRequest("/features").get();
    }

    public OperationResult<String> editionName(){
        return buildServerInfoRequest("/editionName").get();
    }

    public OperationResult<String> licenseType(){
        return buildServerInfoRequest("/licenseType").get();
    }

    public OperationResult<String> expiration(){
        return buildServerInfoRequest("/expiration").get();
    }

    public OperationResult<String> dateFormatPattern(){
        return buildServerInfoRequest("/dateFormatPattern").get();
    }

    public OperationResult<String> dateTimeFormatPattern(){
        return buildServerInfoRequest("/datetimeFormatPattern").get();
    }

}
