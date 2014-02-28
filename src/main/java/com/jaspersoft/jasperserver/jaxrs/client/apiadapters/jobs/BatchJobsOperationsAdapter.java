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
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.job.JobIdListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobExtension;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel.ReportJobModel;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.JobSummaryListWrapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class BatchJobsOperationsAdapter extends AbstractAdapter {

    private final MultivaluedMap<String, String> params;

    public BatchJobsOperationsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        params = new MultivaluedHashMap<String, String>();
    }

    public BatchJobsOperationsAdapter parameter(JobsParameter parameter, String value) {
        params.add(parameter.getName(), value);
        return this;
    }

    public OperationResult<JobSummaryListWrapper> get() {
        return buildRequest(sessionStorage, JobSummaryListWrapper.class, new String[]{"/jobs"})
                .get();
    }

    public OperationResult<JobSummaryListWrapper> search() {
        return search(null);
    }

    public OperationResult<JobSummaryListWrapper> search(JobExtension searchCriteria) {
        JerseyRequestBuilder<JobSummaryListWrapper> builder =
                buildRequest(sessionStorage, JobSummaryListWrapper.class, new String[]{"/jobs"});
        builder.addParams(params);
        if (searchCriteria != null) {
            ObjectMapper mapper = new ObjectMapper();
            SerializationConfig serializationConfig =
                    mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            mapper.setSerializationConfig(serializationConfig);
            try {
                builder.addParam("example", URLEncoder.encode(mapper.writeValueAsString(searchCriteria), "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.get();
    }

    public OperationResult<JobIdListWrapper> update(ReportJobModel jobModel) {
        JerseyRequestBuilder<JobIdListWrapper> builder =
                buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs"});
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");
        builder.addParams(params);

        return builder.post(jobModel);
    }

    public OperationResult<JobIdListWrapper> update(Job jobDescriptor) {
        ObjectMapper mapper = new ObjectMapper();
        SerializationConfig serializationConfig =
                mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.setSerializationConfig(serializationConfig);

        JerseyRequestBuilder<JobIdListWrapper> builder =
                buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs"});
        builder.addParams(params);

        String jobJson = null;
        try {
            jobJson = mapper.writeValueAsString(jobDescriptor);
        } catch (IOException ignored) {
        }

        return builder.post(jobJson);
    }


    private List<Long> getIds() {
        List<Long> ids = new ArrayList<Long>();
        for (String id : params.get(JobsParameter.JOB_ID.getName())) {
            ids.add(Long.parseLong(id));
        }
        return ids;
    }

    public OperationResult<JobIdListWrapper> pause() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/pause"})
                .post(jobIdListWrapper);
    }

    public OperationResult<JobIdListWrapper> resume() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/pause"})
                .post(jobIdListWrapper);
}

    public OperationResult<JobIdListWrapper> restart() {
        JobIdListWrapper jobIdListWrapper = new JobIdListWrapper(getIds());
        return buildRequest(sessionStorage, JobIdListWrapper.class, new String[]{"/jobs", "/pause"})
                .post(jobIdListWrapper);
    }

}
