package com.jaspersoft.jasperserver.jaxrs.client.builder.api;

import javax.ws.rs.client.WebTarget;

public interface Request {

    Request setPath(String path);
    WebTarget getPath();
    Request setTarget(WebTarget webTarget);

    void setContentType(String mime);
    void setAccept(String acceptMime);
    void addHeader(String name, String... values);

}
