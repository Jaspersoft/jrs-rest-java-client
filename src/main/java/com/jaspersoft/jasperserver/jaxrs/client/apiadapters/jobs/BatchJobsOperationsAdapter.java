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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs;

import com.jaspersoft.jasperserver.dto.job.ClientReportJob;
import com.jaspersoft.jasperserver.dto.job.model.ClientReportJobModel;
import com.jaspersoft.jasperserver.dto.job.wrappers.ClientJobIdListWrapper;
import com.jaspersoft.jasperserver.dto.job.wrappers.ClientJobSummariesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobIdListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.JobSummaryListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel.ReportJobModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class BatchJobsOperationsAdapter extends AbstractAdapter {

    private static final Log log = LogFactory.getLog(BatchJobsOperationsAdapter.class);
    public static final String SERVICE_URI = "jobs";
    public static final String PAUSE = "pause";
    public static final String RESUME = "resume";
    public static final String RESTART = "restart";

    private MultivaluedMap<String, String> params;
    private Long[] ids;


    public BatchJobsOperationsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        params = new MultivaluedHashMap<>();
    }

    public BatchJobsOperationsAdapter(SessionStorage sessionStorage, Long... ids) {
        super(sessionStorage);
        this.ids = ids;
    }

    public BatchJobsOperationsAdapter parameter(JobsParameter parameter, String value) {
        params.add(parameter.getName(), UrlUtils.encode(value));
        return this;
    }

    public BatchJobsOperationsAdapter parameters(JobsParameter parameter, String... values) {
        for (String value : values) {
            params.add(parameter.getName(), UrlUtils.encode(value));
        }
        return this;
    }

    public BatchJobsOperationsAdapter parameters(JobsParameter parameter, int... values) {
        for (int value : values) {
            params.add(parameter.getName(), String.valueOf(value));
        }
        return this;
    }

    public OperationResult<ClientJobSummariesListWrapper> searchJobs() {
        return searchJobs(null);
    }

    public OperationResult<ClientJobSummariesListWrapper> searchJobs(ClientReportJob searchCriteria) {
        JerseyRequest<ClientJobSummariesListWrapper> request = prepareSearchRequest(searchCriteria);
        return request.get();
    }

    public <R> RequestExecution asyncSearchJobs(final ClientReportJob searchCriteria, final Callback<OperationResult<ClientJobSummariesListWrapper>, R> callback) {
        final JerseyRequest<ClientJobSummariesListWrapper> request = prepareSearchRequest(searchCriteria);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<ClientJobSummariesListWrapper> prepareSearchRequest(ClientReportJob searchCriteria) {
        JerseyRequest<ClientJobSummariesListWrapper> request = buildRequest(sessionStorage, ClientJobSummariesListWrapper.class, new String[]{SERVICE_URI});
        request.addParams(params);
        if (searchCriteria != null) {
            String criteriaJson = buildJson(searchCriteria);
            request.addParam("example", UrlUtils.encode(criteriaJson));
        }
        return request;
    }

    private String buildJson(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        SerializationConfig serializationConfig = mapper.getSerializationConfig();
        serializationConfig = serializationConfig.withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
        mapper.setSerializationConfig(serializationConfig);
        mapper.setAnnotationIntrospector(introspector);

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.warn("Can't marshal search criteria.");
            throw new RuntimeException("Failed inFolder build criteria json.", e);
        }
    }

    private String buildXml(ClientReportJobModel reportJobModel) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(ClientReportJobModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(reportJobModel, writer);
            return writer.toString();
        } catch (JAXBException e) {
            log.warn("Can't marshal report job model.");
            throw new RuntimeException("Failed inFolder build report job model xml.", e);
        }
    }

    @Deprecated
    public OperationResult<ClientJobIdListWrapper> updateWithProcessedParameters(ClientReportJobModel jobModel) {
        throw new UnsupportedOperationException("Operation is not supported.");
    }

    /**
     * Updates all jobs which ids were specified. Updates only set fields, other fields are ignored.
     */
    public OperationResult<ClientJobIdListWrapper> update(ClientReportJobModel jobModel) {
        JerseyRequest<ClientJobIdListWrapper> request = buildRequest(sessionStorage, ClientJobIdListWrapper.class, new String[]{SERVICE_URI});
        addIdsToQueryParams();
        request.addParams(params);
        String content;

        if (sessionStorage.getConfiguration().getContentMimeType() == MimeType.JSON) {
            content = buildJson(jobModel);
        } else {
            content = buildXml(jobModel);
        }
        return request.post(content);
    }

    public <R> RequestExecution asyncUpdate(final ClientReportJobModel jobModel, final Callback<OperationResult<ClientJobIdListWrapper>, R> callback) {
        final JerseyRequest<ClientJobIdListWrapper> request = buildRequest(sessionStorage, ClientJobIdListWrapper.class, new String[]{SERVICE_URI});
        request.addParams(params);
        final String jobJson = buildJson(jobModel);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(jobJson));
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

        List<String> idsTemp = params.get(JobsParameter.JOB_ID.getName());

        if (idsTemp != null) {
            for (String id : idsTemp) {
                ids.add(Long.parseLong(id));
            }
        }
        return ids;
    }

    public OperationResult<ClientJobIdListWrapper> pauseJobs() {
        ClientJobIdListWrapper jobIdListWrapper = new ClientJobIdListWrapper(getIds());
        return buildRequest(sessionStorage, ClientJobIdListWrapper.class, new String[]{SERVICE_URI, PAUSE}).post(jobIdListWrapper);
    }

    public <R> RequestExecution asyncPauseJobs(final Callback<OperationResult<ClientJobIdListWrapper>, R> callback) {
        final ClientJobIdListWrapper jobIdListWrapper = new ClientJobIdListWrapper(getIds());
        final JerseyRequest<ClientJobIdListWrapper> request = buildRequest(sessionStorage, ClientJobIdListWrapper.class, new String[]{SERVICE_URI, PAUSE});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(jobIdListWrapper));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientJobIdListWrapper> resumeJobs() {
        ClientJobIdListWrapper jobIdListWrapper = new ClientJobIdListWrapper(getIds());
        return buildRequest(sessionStorage, ClientJobIdListWrapper.class, new String[]{SERVICE_URI, RESUME}).post(jobIdListWrapper);
    }

    public <R> RequestExecution asyncResumeJobs(final Callback<OperationResult<ClientJobIdListWrapper>, R> callback) {
        final ClientJobIdListWrapper jobIdListWrapper = new ClientJobIdListWrapper(getIds());
        final JerseyRequest<ClientJobIdListWrapper> request = buildRequest(sessionStorage, ClientJobIdListWrapper.class, new String[]{SERVICE_URI, RESUME});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(jobIdListWrapper));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientJobIdListWrapper> restartJobs() {
        ClientJobIdListWrapper jobIdListWrapper = new ClientJobIdListWrapper(getIds());
        return buildRequest(sessionStorage, ClientJobIdListWrapper.class, new String[]{SERVICE_URI, RESTART}).post(jobIdListWrapper);
    }

    public <R> RequestExecution asyncRestartJobs(final Callback<OperationResult<ClientJobIdListWrapper>, R> callback) {
        final ClientJobIdListWrapper jobIdListWrapper = new ClientJobIdListWrapper(getIds());
        final JerseyRequest<ClientJobIdListWrapper> request = buildRequest(sessionStorage, ClientJobIdListWrapper.class, new String[]{SERVICE_URI, RESTART});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(jobIdListWrapper));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    /**
     * @deprecated Replaced by {@link BatchJobsOperationsAdapter#searchJobs()}.
     */
    public OperationResult<JobSummaryListWrapper> search() {
        return search(null);
    }

    /**
     * @deprecated Replaced by {@link BatchJobsOperationsAdapter#searchJobs(com.jaspersoft.jasperserver.dto.job.ClientReportJob)}.
     */
    public OperationResult<JobSummaryListWrapper> search(Job searchCriteria) {
        JerseyRequest<JobSummaryListWrapper> request = prepareSearchRequest(searchCriteria);
        return request.get();
    }

    /**
     * @deprecated Replaced by {@link BatchJobsOperationsAdapter#asyncSearchJobs(com.jaspersoft.jasperserver.dto.job.ClientReportJob, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncSearch(final Job searchCriteria, final Callback<OperationResult<JobSummaryListWrapper>, R> callback) {
        final JerseyRequest<JobSummaryListWrapper> request = prepareSearchRequest(searchCriteria);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    /**
     * @deprecated Replaced by {@link BatchJobsOperationsAdapter#prepareSearchRequest(com.jaspersoft.jasperserver.dto.job.ClientReportJob)}.
     */
    private JerseyRequest<JobSummaryListWrapper> prepareSearchRequest(Job searchCriteria) {
        JerseyRequest<JobSummaryListWrapper> request = buildRequest(sessionStorage, JobSummaryListWrapper.class, new String[]{SERVICE_URI});
        request.addParams(params);
        if (searchCriteria != null) {
            String criteriaJson = buildJson(searchCriteria);
            request.addParam("example", URLEncoder.encode(criteriaJson), "UTF-8");
        }
        return request;
    }

    /**
     * @deprecated Replaced by {@link BatchJobsOperationsAdapter#update(com.jaspersoft.jasperserver.dto.job.model.ClientReportJobModel)}.
     */
    public OperationResult<JobIdListWrapper> update(ReportJobModel jobModel) {
        JerseyRequest<JobIdListWrapper> request = buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{SERVICE_URI});
        request.addParams(params);
        String content;

        if (sessionStorage.getConfiguration().getContentMimeType() == MimeType.JSON) {
            content = buildJson(jobModel);
        } else {
            content = buildXml(jobModel);
        }
        return request.post(content);
    }

    /**
     * @deprecated Replaced by {@link BatchJobsOperationsAdapter#buildXml(com.jaspersoft.jasperserver.dto.job.model.ClientReportJobModel)}.
     */
    private String buildXml(ReportJobModel reportJobModel) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(ReportJobModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(reportJobModel, writer);
            return writer.toString();
        } catch (JAXBException e) {
            log.warn("Can't marshal report job model.");
            throw new RuntimeException("Failed inFolder build report job model xml.", e);
        }
    }

    /**
     * @deprecated Replaced by {@link BatchJobsOperationsAdapter#asyncUpdate(com.jaspersoft.jasperserver.dto.job.model.ClientReportJobModel, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncUpdate(final ReportJobModel jobModel, final Callback<OperationResult<JobIdListWrapper>, R> callback) {
        final JerseyRequest<JobIdListWrapper> request = buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{SERVICE_URI});
        request.addParams(params);
        final String jobJson = buildJson(jobModel);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(jobJson));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    /**
     * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.BatchJobsOperationsAdapter#pauseJobs()}.
     */
    public OperationResult<JobIdListWrapper> pause() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{SERVICE_URI, PAUSE}).post(jobIdListWrapper);
    }

    /**
     * @deprecated Replaced by {@link BatchJobsOperationsAdapter#asyncPauseJobs(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncPause(final Callback<OperationResult<JobIdListWrapper>, R> callback) {
        final JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        final JerseyRequest<JobIdListWrapper> request = buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{SERVICE_URI, PAUSE});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(jobIdListWrapper));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    /**
     * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.BatchJobsOperationsAdapter#resumeJobs()}.
     */
    public OperationResult<JobIdListWrapper> resume() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{SERVICE_URI, RESUME}).post(jobIdListWrapper);
    }

    /**
     * @deprecated Replaced by {@link BatchJobsOperationsAdapter#asyncResumeJobs(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncResume(final Callback<OperationResult<JobIdListWrapper>, R> callback) {
        final JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        final JerseyRequest<JobIdListWrapper> request = buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{SERVICE_URI, RESUME});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(jobIdListWrapper));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    /**
     * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.BatchJobsOperationsAdapter#restartJobs()}.
     */
    public OperationResult<JobIdListWrapper> restart() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{SERVICE_URI, RESTART}).post(jobIdListWrapper);
    }

    /**
     * @deprecated Replaced by {@link BatchJobsOperationsAdapter#asyncRestartJobs(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncRestart(final Callback<OperationResult<JobIdListWrapper>, R> callback) {
        final JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        final JerseyRequest<JobIdListWrapper> request = buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{SERVICE_URI, RESTART});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(jobIdListWrapper));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientJobIdListWrapper> delete() {
        JerseyRequest<ClientJobIdListWrapper> jerseyRequest = buildRequest(sessionStorage, ClientJobIdListWrapper.class, new String[]{SERVICE_URI});
        addIdsToQueryParams();
        jerseyRequest.addParams(params);
        return jerseyRequest.delete();
    }

    protected void addIdsToQueryParams() {
        if (ids != null && ids.length > 0) {
            for (Long id : ids) {
                this.params.add(JobsParameter.JOB_ID.getName(), id.toString());
            }
        }
    }

}
