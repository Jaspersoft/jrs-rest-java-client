package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.api.CommonRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.GetDeleteRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.PutPostRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.Request;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class RequestBuilder<RequestType, ResponseType>
        implements CommonRequest<RequestType, ResponseType> {

    //static members
    private static final String PROTOCOL = "http://";
    private static final String URI = "/rest_v2";

    private static final Client client;
    private static final WebTarget usersWebTarget;

    //instance members
    protected GetDeleteRequest<ResponseType> getDeleteRequest;
    protected PutPostRequest<RequestType, ResponseType> putPostRequest;

    static  {
        client = ClientBuilder.newClient();
        client.register(HttpAuthenticationFeature.basic("jasperadmin", "jasperadmin"))
                //.property(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE)
                //.register(JacksonFeature.class)
        ;

        String host = getUrlProperty("host");
        String port = getUrlProperty("port");
        String context = getUrlProperty("context");

        usersWebTarget = client.target(PROTOCOL + host + ":" + port + "/" + context + URI);
    }

    public RequestBuilder(Class<ResponseType> responseClass){
        getDeleteRequest = new GetDeleteBuilder<ResponseType>(usersWebTarget, responseClass);
        putPostRequest = new PutPostBuilder<RequestType, ResponseType>(usersWebTarget, responseClass);
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

    public RequestBuilder<RequestType, ResponseType> setPath(String path){
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
    public OperationResult<ResponseType> get() {
        return getDeleteRequest.get();
    }

    @Override
    public OperationResult<ResponseType> delete() {
        return getDeleteRequest.delete();
    }

    @Override
    public GetDeleteRequest<ResponseType> addParam(String name, String... values) {
        return getDeleteRequest.addParam(name, values);
    }

    @Override
    public GetDeleteRequest<ResponseType> addParams(Map<String, String[]> params) {
        return getDeleteRequest.addParams(params);
    }

    @Override
    public GetDeleteRequest<ResponseType> addMatrixParam(String name, String... values) {
        return getDeleteRequest.addMatrixParam(name, values);
    }

    @Override
    public GetDeleteRequest<ResponseType> addMatrixParams(Map<String, String[]> params) {
        return getDeleteRequest.addMatrixParams(params);
    }

    @Override
    public OperationResult<ResponseType> put(RequestType entity) {
        return putPostRequest.put(entity);
    }

    @Override
    public OperationResult<ResponseType> post(RequestType entity) {
        return putPostRequest.post(entity);
    }

    @Override
    public void addHeader(String name, String value) {
        getDeleteRequest.addHeader(name, value);
        putPostRequest.addHeader(name, value);
    }

    @Override
    public void setContentType(String mime) {
        getDeleteRequest.setContentType(mime);
        putPostRequest.setContentType(mime);
    }
}
