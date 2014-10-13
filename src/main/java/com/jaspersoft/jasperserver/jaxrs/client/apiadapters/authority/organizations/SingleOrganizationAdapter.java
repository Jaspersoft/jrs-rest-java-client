/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.authority.Organization;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;


public class SingleOrganizationAdapter extends AbstractAdapter {

    private static final Log log = LogFactory.getLog(SingleOrganizationAdapter.class);

    private final String organizationId;

    public SingleOrganizationAdapter(SessionStorage sessionStorage, String organizationId) {
        super(sessionStorage);
        this.organizationId = organizationId;
    }

    public OperationResult<Organization> get() {
        return buildRequest().get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<Organization>, R> callback){
        final JerseyRequest<Organization> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private String prepareJsonForUpdate(Organization clientTenant){

//        ObjectMapper mapper = new ObjectMapper();
//        SerializationConfig serializationConfig = mapper.getSerializationConfig();
//        serializationConfig = serializationConfig.withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
//        mapper.setSerializationConfig(serializationConfig);
//        mapper.setAnnotationIntrospector(introspector);

        ObjectMapper mapper = new ObjectMapper();
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setAnnotationIntrospector(introspector);

        String json;
        try {
            json = mapper.writeValueAsString(clientTenant);
        } catch (IOException e) {
            log.error("Cannot marshal organization object.");
            throw new JSClientException("Cannot marshal organization object.");
        }
        return json;
    }

    public OperationResult<Organization> update(Organization clientTenant) {
        String json = prepareJsonForUpdate(clientTenant);
        return buildRequest().put(json);
    }

    public <R> RequestExecution asyncUpdate(Organization clientTenant, final Callback<OperationResult<Organization>, R> callback){
        final JerseyRequest<Organization> request = buildRequest();
        final String json = prepareJsonForUpdate(clientTenant);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(json));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete(){
        return buildRequest().delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback){
        final JerseyRequest request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<Organization> buildRequest() {
        return JerseyRequest.buildRequest(sessionStorage, Organization.class, new String[]{"/organizations", organizationId}, new DefaultErrorHandler());
    }
}
