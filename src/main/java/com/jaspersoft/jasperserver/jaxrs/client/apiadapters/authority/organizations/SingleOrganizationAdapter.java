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
import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.attributes.OrganizationBatchAttributeAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.attributes.OrganizationSingleAttributeAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.io.IOException;
import java.util.Collection;
import javax.ws.rs.core.MultivaluedHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SingleOrganizationAdapter extends AbstractAdapter {

    private static final Log log = LogFactory.getLog(SingleOrganizationAdapter.class);

    private final String organizationId;
    private final MultivaluedHashMap<String, String> params;

    public SingleOrganizationAdapter(SessionStorage sessionStorage, String organizationId) {
        super(sessionStorage);
        this.organizationId = organizationId;
        this.params = new MultivaluedHashMap<String, String>();
    }

    public OperationResult<ClientTenant> get() {
        return buildRequest().get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<ClientTenant>, R> callback) {
        final JerseyRequest<ClientTenant> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private String prepareJsonForUpdate(ClientTenant clientTenant) {
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

    public OperationResult<ClientTenant> createOrUpdate(ClientTenant clientTenant) {
        String json = prepareJsonForUpdate(clientTenant);
        return buildRequest().put(json);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final ClientTenant clientTenant,
                                                    final Callback<OperationResult<ClientTenant>, R> callback) {
        final JerseyRequest<ClientTenant> request = buildRequest();
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

    /**
     * Saves Organization. Use this method only if you want to create a new single Organization
     * with or without passing OrganizationParameter.
     *
     * @param clientTenant instance of Organization
     * @return persisted wrapped Organization
     */
    public OperationResult<ClientTenant> create(ClientTenant clientTenant) {
        JerseyRequest<ClientTenant> request = request();
        return params.size() != 0
                ? request.addParams(params).post(clientTenant)
                : request.post(clientTenant);
    }

    /**
     * Adds parameter to the URI
     *
     * @param parameter URI parameter
     * @param value     parameter value
     * @return this
     */
    public SingleOrganizationAdapter parameter(OrganizationParameter parameter, boolean value) {
        params.add(parameter.getParamName(), String.valueOf(value));
        return this;
    }

    public SingleOrganizationAdapter parameter(OrganizationParameter parameter, String value) {
        params.add(parameter.getParamName(), value);
        return this;
    }

    public OperationResult delete() {
        return buildRequest().delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback) {
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

    public OrganizationSingleAttributeAdapter attribute() {
        return new OrganizationSingleAttributeAdapter(sessionStorage, organizationId);
    }

    public OrganizationBatchAttributeAdapter attributes(Collection<String> attributesNames) {
        return new OrganizationBatchAttributeAdapter(sessionStorage, organizationId, attributesNames);
    }

    public OrganizationBatchAttributeAdapter attributes(String... attributesNames) {
        return new OrganizationBatchAttributeAdapter(sessionStorage, organizationId, attributesNames);
    }

    public OrganizationBatchAttributeAdapter attributes() {
        return new OrganizationBatchAttributeAdapter(sessionStorage, organizationId);
    }

    private JerseyRequest<ClientTenant> buildRequest() {
        return JerseyRequest.buildRequest(
                sessionStorage,
                ClientTenant.class,
                new String[]{"/organizations", organizationId},
                new DefaultErrorHandler()
        );
    }

    private JerseyRequest<ClientTenant> request() {
        return JerseyRequest.buildRequest(
                sessionStorage,
                ClientTenant.class,
                new String[]{"/organizations"},
                new DefaultErrorHandler()
        );
    }
}
