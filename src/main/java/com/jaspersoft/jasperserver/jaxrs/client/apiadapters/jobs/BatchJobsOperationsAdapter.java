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

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
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

    private final MultivaluedMap<String, String> params;

    public BatchJobsOperationsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        params = new MultivaluedHashMap<String, String>();
    }

    public BatchJobsOperationsAdapter parameter(JobsParameter parameter, String value) {
        params.add(parameter.getName(), value);
        return this;
    }

    public OperationResult<JobSummaryListWrapper> search() {
        return search(null);
    }

    public OperationResult<JobSummaryListWrapper> search(Job searchCriteria) {
        JerseyRequest<JobSummaryListWrapper> request = prepareSearchRequest(searchCriteria);
        return request.get();
    }

    public <R> RequestExecution asyncSearch(final Job searchCriteria, final Callback<OperationResult<JobSummaryListWrapper>, R> callback) {
//        final JerseyRequest<JobSummaryListWrapper> request = prepareSearchRequest(searchCriteria);
//        RequestExecution task = new RequestExecution(new Runnable() {
//            @Override
//            public void run() {
//                callback.execute(request.get());
//            }
//        });
//        ThreadPoolUtil.runAsynchronously(task);
//        return task;
        throw new RuntimeException();
    }

    private JerseyRequest<JobSummaryListWrapper> prepareSearchRequest(Job searchCriteria) {
        JerseyRequest<JobSummaryListWrapper> request = buildRequest(sessionStorage, JobSummaryListWrapper.class, new String[]{"/jobs"});
        request.addParams(params);
        if (searchCriteria != null) {
            String criteriaJson = buildJson(searchCriteria);
            request.addParam("example", URLEncoder.encode(criteriaJson), "UTF-8");
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
            throw new RuntimeException("Failed to build criteria json.", e);
        }
    }

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
            throw new RuntimeException("Failed to build report job model xml.", e);
        }
    }

    @Deprecated
    public OperationResult<JobIdListWrapper> updateWithProcessedParameters(ReportJobModel jobModel) {
        throw new UnsupportedOperationException("Operation is not supported yet");
        /*JerseyRequest<JobIdListWrapper> request =
                buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs"}, errorHandler);
        request.setContentType("application/job+json");
        request.setAccept("application/job+json");
        request.addParams(params);

        return request.post(jobModel);*/
    }


    /**
     * Updates all jobs which ids were specified. Updates only set fields, other fields are ignored.
     */
    public OperationResult<JobIdListWrapper> update(ReportJobModel jobModel) {
        JerseyRequest<JobIdListWrapper> request = buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs"});
        request.addParams(params);
        String content = null;

        if (sessionStorage.getConfiguration().getContentMimeType() == MimeType.JSON) {
            content = buildJson(jobModel);
        } else {
            content = buildXml(jobModel);
        }
        return request.post(content);
    }

    public <R> RequestExecution asyncUpdate(final ReportJobModel jobModel, final Callback<OperationResult<JobIdListWrapper>, R> callback) {
//        final JerseyRequest<JobIdListWrapper> request = buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs"});
//        request.addParams(params);
//        final String jobJson = buildJson(jobModel);
//        RequestExecution task = new RequestExecution(new Runnable() {
//            @Override
//            public void run() {
//                callback.execute(request.post(jobJson));
//            }
//        });
//        ThreadPoolUtil.runAsynchronously(task);
//        return task;
        throw new RuntimeException();
    }


    private List<Long> getIds() {
        List<Long> ids = new ArrayList<Long>();
        List<String> idsTemp = params.get(JobsParameter.JOB_ID.getName());

        if (idsTemp != null) {
            for (String id : idsTemp) {
                ids.add(Long.parseLong(id));
            }
        }
        return ids;
    }

    public OperationResult<JobIdListWrapper> pause() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/pause"}).post(jobIdListWrapper);
    }

    public <R> RequestExecution asyncPause(final Callback<OperationResult<JobIdListWrapper>, R> callback) {
//        final JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
//        final JerseyRequest<JobIdListWrapper> request = buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/pause"});
//        RequestExecution task = new RequestExecution(new Runnable() {
//            @Override
//            public void run() {
//                callback.execute(request.post(jobIdListWrapper));
//            }
//        });
//        ThreadPoolUtil.runAsynchronously(task);
//        return task;
        throw new RuntimeException();
    }

    public OperationResult<JobIdListWrapper> resume() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/resume"}).post(jobIdListWrapper);
    }

    public <R> RequestExecution asyncResume(final Callback<OperationResult<JobIdListWrapper>, R> callback) {
//        final JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
//        final JerseyRequest<JobIdListWrapper> request = buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/resume"});
//        RequestExecution task = new RequestExecution(new Runnable() {
//            @Override
//            public void run() {
//                callback.execute(request.post(jobIdListWrapper));
//            }
//        });
//        ThreadPoolUtil.runAsynchronously(task);
//        return task;
        throw new RuntimeException();
    }

    public OperationResult<JobIdListWrapper> restart() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/restart"}).post(jobIdListWrapper);
    }

    public <R> RequestExecution asyncRestart(final Callback<OperationResult<JobIdListWrapper>, R> callback) {
//        final JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
//        final JerseyRequest<JobIdListWrapper> request = buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/restart"});
//        RequestExecution task = new RequestExecution(new Runnable() {
//            @Override
//            public void run() {
//                callback.execute(request.post(jobIdListWrapper));
//            }
//        });
//        ThreadPoolUtil.runAsynchronously(task);
//        return task;
        throw new RuntimeException();
    }
}
