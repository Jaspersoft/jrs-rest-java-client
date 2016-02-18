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

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.annotations.VisibleForTesting;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobSource;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.OutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.SimpleTrigger;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.CalendarNameListWrapper;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class JobsService extends AbstractAdapter {

    public JobsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public BatchJobsOperationsAdapter jobs() {
        return new BatchJobsOperationsAdapter(sessionStorage);
    }

    public SingleJobOperationsAdapter job(long jobId) {
        return new SingleJobOperationsAdapter(sessionStorage, String.valueOf(jobId));
    }

    public OperationResult<Job> scheduleReport(Job report) {
        JerseyRequest<Job> request = buildRequest(sessionStorage, Job.class, new String[]{"/jobs"}, new JobValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }
        return request.put(report);
    }

    public <R> RequestExecution asyncScheduleReport(final Job report, final Callback<OperationResult<Job>, R> callback) {
        final JerseyRequest<Job> request = buildRequest(sessionStorage, Job.class, new String[]{"/jobs"}, new JobValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(report));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<CalendarNameListWrapper> calendars() {
        return calendars(null);
    }

    public <R> RequestExecution asyncCalendars(final Callback<OperationResult<CalendarNameListWrapper>, R> callback) {
        return asyncCalendars(null, callback);
    }

    public OperationResult<CalendarNameListWrapper> calendars(CalendarType type) {
        JerseyRequest<CalendarNameListWrapper> request = buildRequest(sessionStorage, CalendarNameListWrapper.class, new String[]{"/jobs", "/calendars"});
        if (type != null) {
            request.addParam("calendarType", type.name().toLowerCase());
        }
        return request.get();
    }

    public <R> RequestExecution asyncCalendars(final CalendarType type, final Callback<OperationResult<CalendarNameListWrapper>, R> callback) {
        final JerseyRequest<CalendarNameListWrapper> request = buildRequest(sessionStorage, CalendarNameListWrapper.class, new String[]{"/jobs", "/calendars"});
        if (type != null) {
            request.addParam("calendarType", type.name().toLowerCase());
        }
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public SingleCalendarOperationsAdapter calendar(String calendarName) {
        if ("".equals(calendarName) || "/".equals(calendarName)) {
            throw new IllegalArgumentException("'calendarName' mustn't be an empty string");
        }
        return new SingleCalendarOperationsAdapter(sessionStorage, calendarName);
    }

    //START: palash: changes to get report scheduling working
    public long scheduleReportWithHack(Job report) {
        JerseyRequest<String> request = buildRequest(sessionStorage, String.class, new String[]{"/jobs"}, new JobValidationErrorHandler());
        request.setContentType("application/job+json");
        request.setAccept("application/job+json");

        String inputJson = getJobAsJsonString(report);

        System.err.println("inputJson:\n" + inputJson);

        OperationResult<String> result = request.put(inputJson);
        String jsonResult = result.getEntity();

        System.err.println("Result:\n" + jsonResult);

        JsonNode jsonNode;
        try {
            jsonNode = new ObjectMapper().readTree(jsonResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return jsonNode.findValue("id").asLong();
    }

    @VisibleForTesting
    String getJobAsJsonString(Job job) {

        String jsonStringTemplate = "{\"label\":\"%s\",\"description\":\"%s\"," 
        + "\"trigger\":{\"simpleTrigger\":{\"startType\":%d,\"misfireInstruction\":%d,\"occurrenceCount\":%d%s}},"
        + "\"source\":{\"reportUnitURI\":\"%s\",\"parameters\":{\"parameterValues\":%s}},\"baseOutputFilename\":\"%s\"," 
        + "\"repositoryDestination\":{\"folderURI\":\"%s\",\"outputLocalFolder\":\"%s\",\"sequentialFilenames\":false,\"overwriteFiles\":true,\"saveToRepository\":%s," 
                + "\"usingDefaultReportOutputFolderURI\":false},\"outputFormats\":{\"outputFormat\":%s}}";

        
        JobSource jobSource = job.getSource();
        SimpleTrigger simpleTrigger = (SimpleTrigger) job.getTrigger();
        
        String recurrenceDetails;
        
        if (simpleTrigger.getOccurrenceCount() > 1){
            recurrenceDetails = ",\"recurrenceInterval\":"+simpleTrigger.getRecurrenceInterval()
                    + ",\"recurrenceIntervalUnit\":\""+simpleTrigger.getRecurrenceIntervalUnit().name()
                    + "\"";
        } else {
            recurrenceDetails="";
        }
        
        String  jsonString = String.format(jsonStringTemplate, job.getLabel(), job.getDescription(), simpleTrigger.getStartType(), 
                simpleTrigger.getMisfireInstruction(), simpleTrigger.getOccurrenceCount(),recurrenceDetails,
                jobSource.getReportUnitURI(), getReportParamsAsJsonString(jobSource.getParameters()), job.getBaseOutputFilename(), job.getRepositoryDestination().getFolderURI(), 
                job.getRepositoryDestination().getOutputLocalFolder(),job.getRepositoryDestination().isSaveToRepository(), getOutputFormatsAsJsonString(job.getOutputFormats()));

        
        return jsonString;
    }

    private String getReportParamsAsJsonString(Map<String, Object> reportParams) {
        StringBuilder sb = new StringBuilder(100);
        sb.append("{");

        for (Entry<String, Object> paramKeyValue : reportParams.entrySet()) {
            //key
            sb.append('"').append(paramKeyValue.getKey()).append('"');
            sb.append(":");
            //value
            sb.append("[\"").append(paramKeyValue.getValue()).append("\"]");
            sb.append(",");
        }

        //remove the xtra ,
        sb.setLength(sb.length() - 1);

        sb.append("}");
        return sb.toString();
    }

    private String getOutputFormatsAsJsonString(Set<OutputFormat> outputFormats) {
        StringBuilder sb = new StringBuilder(60);

        sb.append("[");

        for (OutputFormat outputFormat : outputFormats) {
            sb.append('"').append(outputFormat.name()).append('"').append(",");
        }

        //remove the xtra ,
        sb.setLength(sb.length() - 1);

        sb.append("]");

        return sb.toString();
    }

    //END: palash: changes to get report scheduling working

}