package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.bundles;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import java.util.List;
import java.util.Map;

public class BundlesService extends AbstractAdapter {

    public BundlesService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public OperationResult<List<String>> bundles(){
        Class clazz = List.class;
        Class<List<String>> clazz_t = clazz;
        return JerseyRequestBuilder.buildRequest(sessionStorage, clazz_t, new String[]{"/bundles"}).get();
    }

    public OperationResult<Map<String, String>> bundle(String name){
        Class clazz = Map.class;
        Class<Map<String, String>> clazz_t = clazz;
        return JerseyRequestBuilder.buildRequest(sessionStorage, clazz_t, new String[]{"/bundles", name}).get();
    }
}
