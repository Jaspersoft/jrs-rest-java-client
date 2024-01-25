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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.jaspersoft.jasperserver.dto.alerting.ClientReportAlert;
import com.jaspersoft.jasperserver.dto.alerting.model.ClientReportAlertModel;
import com.jaspersoft.jasperserver.dto.alerting.wrappers.ClientAlertIdListWrapper;
import com.jaspersoft.jasperserver.dto.alerting.wrappers.ClientAlertSummariesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class BatchAlertsOperationsAdapter extends AbstractAdapter {

    private static final Logger log = LogManager.getLogger(BatchAlertsOperationsAdapter.class);
    public static final String SERVICE_URI = "alerts";
    public static final String PAUSE = "pause";
    public static final String RESUME = "resume";
    public static final String RESTART = "restart";

    private MultivaluedMap<String, String> params;
    private Long[] ids;


    public BatchAlertsOperationsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        params = new MultivaluedHashMap<>();
    }

    public BatchAlertsOperationsAdapter(SessionStorage sessionStorage, Long... ids) {
        super(sessionStorage);
        this.ids = ids;
    }

    public BatchAlertsOperationsAdapter parameter(AlertsParameter parameter, String value) {
        params.add(parameter.getName(), UrlUtils.encode(value));
        return this;
    }

    public BatchAlertsOperationsAdapter parameters(AlertsParameter parameter, String... values) {
        for (String value : values) {
            params.add(parameter.getName(), UrlUtils.encode(value));
        }
        return this;
    }

    public BatchAlertsOperationsAdapter parameters(AlertsParameter parameter, int... values) {
        for (int value : values) {
            params.add(parameter.getName(), String.valueOf(value));
        }
        return this;
    }

    public OperationResult<ClientAlertSummariesListWrapper> searchAlerts() {
        return searchAlerts(null);
    }

    public OperationResult<ClientAlertSummariesListWrapper> searchAlerts(ClientReportAlert searchCriteria) {
        JerseyRequest<ClientAlertSummariesListWrapper> request = prepareSearchRequest(searchCriteria);
        return request.get();
    }

    public <R> RequestExecution asyncSearchAlerts(final ClientReportAlert searchCriteria, final Callback<OperationResult<ClientAlertSummariesListWrapper>, R> callback) {
        final JerseyRequest<ClientAlertSummariesListWrapper> request = prepareSearchRequest(searchCriteria);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<ClientAlertSummariesListWrapper> prepareSearchRequest(ClientReportAlert searchCriteria) {
        JerseyRequest<ClientAlertSummariesListWrapper> request= buildRequest(sessionStorage, ClientAlertSummariesListWrapper.class, new String[]{SERVICE_URI});
        request.addParams(params);
        if (searchCriteria != null) {
            String criteriaJson = buildJson(searchCriteria);
            request.addParam("example", UrlUtils.encode(criteriaJson));
        }
        return request;
    }

    private String buildJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        mapper.setAnnotationIntrospector(introspector);


        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.warn("Can't marshal search criteria.");
            throw new RuntimeException("Failed inFolder build criteria json.", e);
        }
    }

    private String buildXml(ClientReportAlertModel reportAlertModel) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(ClientReportAlertModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(reportAlertModel, writer);
            return writer.toString();
        } catch (JAXBException e) {
            log.warn("Can't marshal report alert model.");
            throw new RuntimeException("Failed inFolder build report alert model xml.", e);
        }
    }

    /**
     * Updates all alert alerts which ids were specified. Updates only set fields, other fields are ignored.
     */
    public OperationResult<ClientAlertIdListWrapper> update(ClientReportAlertModel alertModel) {
        JerseyRequest<ClientAlertIdListWrapper> request = buildRequest(sessionStorage, ClientAlertIdListWrapper.class, new String[]{SERVICE_URI});
        addIdsToQueryParams();
        request.addParams(params);
        String content;

        if (sessionStorage.getConfiguration().getContentMimeType() == MimeType.JSON) {
            content = buildJson(alertModel);
        } else {
            content = buildXml(alertModel);
        }
        return request.post(content);
    }

    public <R> RequestExecution asyncUpdate(final ClientReportAlertModel alertModel, final Callback<OperationResult<ClientAlertIdListWrapper>, R> callback) {
        final JerseyRequest<ClientAlertIdListWrapper> request = buildRequest(sessionStorage, ClientAlertIdListWrapper.class, new String[]{SERVICE_URI});
        request.addParams(params);
        final String alertJson = buildJson(alertModel);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(alertJson));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private List<Long> getIds() {
        List<Long> ids = new ArrayList<Long>();
        if (this.ids != null && this.ids.length > 0) {
            for (Long id : ids) {
                ids.add(id);
            }
        }

        List<String> idsTemp = params.get(AlertsParameter.JOB_ID.getName());

        if (idsTemp != null) {
            for (String id : idsTemp) {
                ids.add(Long.parseLong(id));
            }
        }
        return ids;
    }

    public OperationResult<ClientAlertIdListWrapper> pauseAlerts() {
        ClientAlertIdListWrapper alertIdListWrapper = new ClientAlertIdListWrapper(getIds());
        return buildRequest(sessionStorage, ClientAlertIdListWrapper.class, new String[]{SERVICE_URI, PAUSE}).post(alertIdListWrapper);
    }

    public <R> RequestExecution asyncPauseAlerts(final Callback<OperationResult<ClientAlertIdListWrapper>, R> callback) {
        final ClientAlertIdListWrapper alertIdListWrapper = new ClientAlertIdListWrapper(getIds());
        final JerseyRequest<ClientAlertIdListWrapper> request = buildRequest(sessionStorage, ClientAlertIdListWrapper.class, new String[]{SERVICE_URI, PAUSE});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(alertIdListWrapper));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientAlertIdListWrapper> resumeAlerts() {
        ClientAlertIdListWrapper alertIdListWrapper = new ClientAlertIdListWrapper(getIds());
        return buildRequest(sessionStorage, ClientAlertIdListWrapper.class, new String[]{SERVICE_URI, RESUME}).post(alertIdListWrapper);
    }

    public <R> RequestExecution asyncResumeAlerts(final Callback<OperationResult<ClientAlertIdListWrapper>, R> callback) {
        final ClientAlertIdListWrapper alertIdListWrapper = new ClientAlertIdListWrapper(getIds());
        final JerseyRequest<ClientAlertIdListWrapper> request = buildRequest(sessionStorage, ClientAlertIdListWrapper.class, new String[]{SERVICE_URI, RESUME});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(alertIdListWrapper));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientAlertIdListWrapper> restartAlerts() {
        ClientAlertIdListWrapper alertIdListWrapper = new ClientAlertIdListWrapper(getIds());
        return buildRequest(sessionStorage, ClientAlertIdListWrapper.class, new String[]{SERVICE_URI, RESTART}).post(alertIdListWrapper);
    }

    public <R> RequestExecution asyncRestartAlerts(final Callback<OperationResult<ClientAlertIdListWrapper>, R> callback) {
        final ClientAlertIdListWrapper alertIdListWrapper = new ClientAlertIdListWrapper(getIds());
        final JerseyRequest<ClientAlertIdListWrapper> request = buildRequest(sessionStorage, ClientAlertIdListWrapper.class, new String[]{SERVICE_URI, RESTART});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(alertIdListWrapper));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientAlertIdListWrapper> delete() {
        JerseyRequest<ClientAlertIdListWrapper> jerseyRequest = buildRequest(sessionStorage, ClientAlertIdListWrapper.class, new String[]{SERVICE_URI});
        addIdsToQueryParams();
        jerseyRequest.addParams(params);
        return jerseyRequest.delete();
    }

    protected void addIdsToQueryParams() {
        if (ids != null && ids.length > 0) {
            for (Long id : ids) {
                this.params.add(AlertsParameter.JOB_ID.getName(), id.toString());
            }
        }
    }

}
