package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.JobSummaryListWrapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.util.JSONPObject;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import java.io.IOException;
import java.net.URLEncoder;

import static com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder.buildRequest;

public class BatchJobsOperationsAdapter {

    private final MultivaluedMap<String, String> params;
    private final SessionStorage sessionStorage;

    public BatchJobsOperationsAdapter(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
        params = new MultivaluedHashMap<String, String>();
    }

    public BatchJobsOperationsAdapter parameter(JobsParameter parameter, String value){
        params.add(parameter.getName(), value);
        return this;
    }

    public OperationResult<JobSummaryListWrapper> get(){
        return buildRequest(sessionStorage, JobSummaryListWrapper.class, new String[]{"/jobs"})
                .get();
    }

    public OperationResult<JobSummaryListWrapper> search(){
        return search(null);
    }

    public OperationResult<JobSummaryListWrapper> search(Job searchCriteria){
        JerseyRequestBuilder<JobSummaryListWrapper> builder =
                buildRequest(sessionStorage, JobSummaryListWrapper.class, new String[]{"/jobs"});
        builder.addParams(params);
        if (searchCriteria != null){
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

    public OperationResult update(Job job){
        ObjectMapper mapper = new ObjectMapper();
        SerializationConfig serializationConfig =
                mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.setSerializationConfig(serializationConfig);

        buildRequest(sessionStorage, Object.class, new String[]{"/jobs"});
    }

    public OperationResult pause(){
        throw new UnsupportedOperationException();
    }

    public OperationResult resume(){
        throw new UnsupportedOperationException();
    }

    public OperationResult restart(){
        throw new UnsupportedOperationException();
    }

}
