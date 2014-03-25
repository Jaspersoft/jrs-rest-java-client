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
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.CommonExceptionHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ExceptionHandler;
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
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class BatchJobsOperationsAdapter extends AbstractAdapter {

    private static final Log log = LogFactory.getLog(BatchJobsOperationsAdapter.class);

    private final MultivaluedMap<String, String> params;
    private ExceptionHandler exceptionHandler;

    public BatchJobsOperationsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        params = new MultivaluedHashMap<String, String>();
        exceptionHandler = new CommonExceptionHandler();
    }

    public BatchJobsOperationsAdapter parameter(JobsParameter parameter, String value) {
        params.add(parameter.getName(), value);
        return this;
    }

    public OperationResult<JobSummaryListWrapper> search() {
        return search(null);
    }

    public OperationResult<JobSummaryListWrapper> search(Job searchCriteria) {
        JerseyRequestBuilder<JobSummaryListWrapper> builder =
                buildRequest(sessionStorage, JobSummaryListWrapper.class, new String[]{"/jobs"}, exceptionHandler);
        builder.addParams(params);
        if (searchCriteria != null) {
            ObjectMapper mapper = new ObjectMapper();

            SerializationConfig serializationConfig = mapper.getSerializationConfig();
            serializationConfig = serializationConfig.withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();

            mapper.setSerializationConfig(serializationConfig);
            mapper.setAnnotationIntrospector(introspector);

            try {
                builder.addParam("example", URLEncoder.encode(mapper.writeValueAsString(searchCriteria), "UTF-8"));
            } catch (IOException e) {
                log.warn("Can't marshal search criteria.");
            }
        }
        return builder.get();
    }

    public OperationResult<JobIdListWrapper> updateWithProcessedParameters(ReportJobModel jobModel) {
        throw new UnsupportedOperationException("Operation is not supported yet");
        /*JerseyRequestBuilder<JobIdListWrapper> builder =
                buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs"}, exceptionHandler);
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");
        builder.addParams(params);

        return builder.post(jobModel);*/
    }


    /**
     * Updates all jobs which ids were specified. Updates only set fields, other fields are ignored.
     */
    public OperationResult<JobIdListWrapper> update(ReportJobModel jobModel) {
        ObjectMapper mapper = new ObjectMapper();

        SerializationConfig serializationConfig = mapper.getSerializationConfig();
        serializationConfig = serializationConfig.withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();

        mapper.setSerializationConfig(serializationConfig);
        mapper.setAnnotationIntrospector(introspector);

        JerseyRequestBuilder<JobIdListWrapper> builder =
                buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs"}, exceptionHandler);
        builder.addParams(params);

        String jobJson = null;
        try {
            jobJson = mapper.writeValueAsString(jobModel);
        } catch (IOException e) {
            if (e.getCause() instanceof JSClientException)
                throw (JSClientException) e.getCause();
            log.warn("Unable map job model to JSON", e);
        }

        return builder.post(jobJson);
    }


    private List<Long> getIds() {
        List<Long> ids = new ArrayList<Long>();
        List<String> idsTemp = params.get(JobsParameter.JOB_ID.getName());
        if (idsTemp != null) {
            for (String id : idsTemp)
                ids.add(Long.parseLong(id));
        }
        return ids;
    }

    public OperationResult<JobIdListWrapper> pause() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/pause"}, exceptionHandler)
                .post(jobIdListWrapper);
    }

    public OperationResult<JobIdListWrapper> resume() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/resume"}, exceptionHandler)
                .post(jobIdListWrapper);
    }

    public OperationResult<JobIdListWrapper> restart() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/restart"}, exceptionHandler)
                .post(jobIdListWrapper);
    }

}
