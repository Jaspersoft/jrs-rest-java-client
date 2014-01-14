package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.api.GetDeleteRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.Request;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJacksonProvider;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JerseyRequestBuilder<ResponseType>
        implements RequestBuilder<ResponseType> {

    private static final String PROTOCOL = "http://";
    private static final String URI = "/rest_v2";

    private WebTarget usersWebTarget;

    private final Class<ResponseType> responseClass;
    private String contentType;


    public JerseyRequestBuilder(AuthenticationCredentials credentials, Class<ResponseType> responseClass) {

        Client client = ClientBuilder.newClient();
        client
                .property(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE)
                .register(CustomJsonFeature.class)
                //.register(JacksonFeature.class)
                .register(HttpAuthenticationFeature.basic(credentials.getUsername(), credentials.getPassword()));

        String host = getUrlProperty("host");
        String port = getUrlProperty("port");
        String context = getUrlProperty("context");

        this.responseClass = responseClass;
        this.contentType = MediaType.APPLICATION_JSON;

        usersWebTarget = client.target(PROTOCOL + host + ":" + port + "/" + context + URI);

        if (credentials.getSessionId() != null)
            usersWebTarget.register(new SessionOutputFilter(credentials.getSessionId()));

    }

    private static String getUrlProperty(String name) {
        InputStream is = JerseyRequestBuilder.class.getClassLoader().getResourceAsStream("url.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(name);
    }

    public JerseyRequestBuilder<ResponseType> setPath(String path) {
        usersWebTarget = usersWebTarget.path(path);
        return this;
    }

    @Override
    public WebTarget getPath() {
        return usersWebTarget;
    }

    @Override
    public Request setTarget(WebTarget webTarget) {
        this.usersWebTarget = webTarget;
        return this;
    }

    @Override
    public OperationResult<ResponseType> get() {
        try {
            Invocation.Builder request = usersWebTarget.request(contentType);
            Response response = request.get();
            return new OperationResult<ResponseType>(response, responseClass);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OperationResult<ResponseType> delete() {
        Invocation.Builder request = usersWebTarget.request();
        Response response = request.delete();
        return new OperationResult<ResponseType>(response, responseClass);
    }

    @Override
    public GetDeleteRequest<ResponseType> addParam(String name, String... values) {
        usersWebTarget = usersWebTarget.queryParam(name, values);
        return this;
    }

    @Override
    public GetDeleteRequest<ResponseType> addParams(MultivaluedMap<String, String> params) {
        for (MultivaluedMap.Entry<String, List<String>> entry : params.entrySet()) {
            usersWebTarget = usersWebTarget.queryParam(entry.getKey(), entry.getValue().toArray());
        }
        return this;
    }

    @Override
    public GetDeleteRequest<ResponseType> addMatrixParam(String name, String... values) {
        usersWebTarget = usersWebTarget.matrixParam(name, values);
        return this;
    }

    @Override
    public GetDeleteRequest<ResponseType> addMatrixParams(MultivaluedMap<String, String> params) {
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            usersWebTarget = usersWebTarget.matrixParam(entry.getKey(), entry.getValue().toArray());
        }
        return this;
    }

    @Override
    public <RequestType> OperationResult<ResponseType> put(RequestType entity) {
        Invocation.Builder request = usersWebTarget.request();
        Response response = request.put(Entity.entity(entity, contentType));
        return new OperationResult<ResponseType>(response, responseClass);
    }

    @Override
    public <RequestType> OperationResult<ResponseType> post(RequestType entity) {
        Invocation.Builder request = usersWebTarget.request();
        Response response = request.post(Entity.entity(entity, contentType));
        return new OperationResult<ResponseType>(response, responseClass);
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
