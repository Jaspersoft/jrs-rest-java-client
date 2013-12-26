package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.api.CommonRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.GetDeleteRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.PutPostRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.Request;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public abstract class RequestBuilder<T> implements CommonRequest<T> {

    //static members
    private static final String PROTOCOL = "http://";
    private static final String URI = "/rest_v2";

    private static final Client client;
    private static final WebTarget usersWebTarget;

    //instance members
    protected GetDeleteRequest<T> getDeleteRequest;
    protected PutPostRequest<T> putPostRequest;

    static  {
        client = ClientBuilder.newClient();
        client.register(HttpAuthenticationFeature.basic("jasperadmin", "jasperadmin"))
                .register(JacksonFeature.class);

        String host = getUrlProperty("host");
        String port = getUrlProperty("port");
        String context = getUrlProperty("context");

        usersWebTarget = client.target(PROTOCOL + host + ":" + port + "/" + context + URI);
    }

    protected RequestBuilder(Class responseClass){
        getDeleteRequest = new GetDeleteBuilder<T>(usersWebTarget, responseClass);
        putPostRequest = new PutPostBuilder<T>(usersWebTarget);
    }

    private static String getUrlProperty(String name){
        InputStream is = RequestBuilder.class.getClassLoader().getResourceAsStream("url.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(name);
    }

    public RequestBuilder<T> setPath(String path){
        getDeleteRequest.setPath(path);
        putPostRequest.setPath(path);
        return this;
    }

    @Override
    public WebTarget getPath() {
        return getDeleteRequest.getPath();
    }

    @Override
    public Request setTarget(WebTarget webTarget) {
        getDeleteRequest.setTarget(webTarget);
        putPostRequest.setTarget(webTarget);
        return this;
    }

    @Override
    public T get() {
        return getDeleteRequest.get();
    }

    @Override
    public Response delete() {
        return getDeleteRequest.delete();
    }

    @Override
    public GetDeleteRequest<T> addParam(String name, String... values) {
        return getDeleteRequest.addParam(name, values);
    }

    @Override
    public GetDeleteRequest<T> addParams(Map<String, String[]> params) {
        return getDeleteRequest.addParams(params);
    }

    @Override
    public GetDeleteRequest<T> addMatrixParam(String name, String... values) {
        return getDeleteRequest.addMatrixParam(name, values);
    }

    @Override
    public GetDeleteRequest<T> addMatrixParams(Map<String, String[]> params) {
        return getDeleteRequest.addMatrixParams(params);
    }

    @Override
    public Response put(T entity) {
        return putPostRequest.put(entity);
    }

    @Override
    public Response post(T entity) {
        return putPostRequest.post(entity);
    }

}
