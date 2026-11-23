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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportexecution;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.dto.reports.ReportParameters;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.PageRange;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportSearchParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.RunReportErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecution;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionStatusObject;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionsSetWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.reportinfo.ReportInfo;
import org.apache.commons.lang3.StringUtils;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;

public class ReportExecutionAdapter extends AbstractAdapter {

    public static final String REPORT_EXECUTIONS_URI = "reportExecutions";
    public static final String STATUS_URI = "status";
    public static final String PARAMETERS_URI = "parameters";
    public static final String INFO_URI = "info";

    private ReportExecutionRequest executionRequest;
    private String executionId;
    private final ArrayList<String> path = new ArrayList<>();
    private final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();

    public ReportExecutionAdapter(SessionStorage sessionStorage, ReportExecutionRequest executionRequest) {
        super(sessionStorage);
        this.executionRequest = executionRequest;
        this.path.add(REPORT_EXECUTIONS_URI);
    }
    public ReportExecutionAdapter(SessionStorage sessionStorage, String executionId) {
        super(sessionStorage);
        this.executionId = executionId;
        this.path.add(REPORT_EXECUTIONS_URI);
    }

    public ReportExecutionAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.path.add(REPORT_EXECUTIONS_URI);
        this.executionRequest = new ReportExecutionRequest();
    }

    public ReportExecutionAdapter queryParameter(ReportSearchParameter name, String value) {
        params.add(name.getName(), UrlUtils.encode(value));
        return this;
    }

    public ReportExecutionAdapter queryParameter(ReportSearchParameter name, String... value) {
        params.addAll(name.getName(), UrlUtils.encode(asList(value)));
        return this;
    }

    public ReportExecutionAdapter queryParameter(ReportSearchParameter name, List<String> value) {
        params.addAll(name.getName(), UrlUtils.encode(value));
        return this;

    }
    public ReportExecutionAdapter queryParameter(String name, String value) {
        params.add(name, UrlUtils.encode(value));
        return this;

    }

    public ReportExecutionAdapter outputFormat(ReportOutputFormat outputFormat) {
        this.executionRequest.setOutputFormat(outputFormat.name().toLowerCase());
        return this;
    }

    public ReportExecutionAdapter reportUri(String reportUri) {
        this.executionRequest.setReportUnitUri(reportUri);
        return this;
    }

    public ReportExecutionAdapter async(Boolean value) {
        this.executionRequest.setAsync(value);
        return this;
    }
    public ReportExecutionAdapter interactive(boolean value) {
        this.executionRequest.setInteractive(value);
        return this;
    }
    public ReportExecutionAdapter freshData(boolean value) {
        this.executionRequest.setFreshData(value);
        return this;
    }
    public ReportExecutionAdapter saveDataSnapshot(boolean value) {
        this.executionRequest.setSaveDataSnapshot(value);
        return this;
    }
    public ReportExecutionAdapter ignorePagination(boolean value) {
        this.executionRequest.setIgnorePagination(value);
        return this;
    }
    public ReportExecutionAdapter allowInlineScripts(Boolean value) {
        this.executionRequest.setAllowInlineScripts(value);
        return this;
    }
    public ReportExecutionAdapter transformerKey(String transformerKey) {
        this.executionRequest.setTransformerKey(transformerKey);
        return this;
    }
    public ReportExecutionAdapter baseUrl(String baseUri) {
        this.executionRequest.setBaseUrl(baseUri);
        return this;
    }
    public ReportExecutionAdapter markupType(String markupType) {
        this.executionRequest.setMarkupType(markupType);
        return this;
    }
    public ReportExecutionAdapter anchor(String anchor) {
        this.executionRequest.setAnchor(anchor);
        return this;
    }
    public ReportExecutionAdapter pages(String pages) {
        this.executionRequest.setPages(pages);
        return this;
    }
    public ReportExecutionAdapter pages(Integer... pages) {
        this.executionRequest.setPages(StringUtils.join(asList(pages), ","));
        return this;
    }
    public ReportExecutionAdapter pages(PageRange pages) {
        this.executionRequest.setPages(pages.getRange());
        return this;
    }
    public ReportExecutionAdapter attachmentsPrefix(String attachmentsPrefix) {
        this.executionRequest.setAttachmentsPrefix(attachmentsPrefix);
        return this;
    }

    public ReportExecutionAdapter reportParameters(ReportParameters reportParameters) {
        this.executionRequest.setParameters(reportParameters);
        return this;
    }
    public ReportExecutionAdapter reportParameter(String name, String... values) {
        this.reportParameter(new ReportParameter().setName(name).setValues(asList(values)));
        return this;
    }
    public ReportExecutionAdapter reportParameter(ReportParameter reportParameter) {
        if(executionRequest.getParameters() == null) executionRequest.setParameters(new ReportParameters(new ArrayList<ReportParameter>()));
        final ReportParameters parameters = executionRequest.getParameters();
        final List<ReportParameter> reportParametersList = parameters.getReportParameters();
        if(reportParametersList == null) parameters.setReportParameters(new ArrayList<>());
        reportParametersList.add(reportParameter);
        return this;
    }

    public ReportExecutionAdapter timeZone(TimeZone timeZone) {
        this.executionRequest.setTimeZone(timeZone);
        return this;
    }

    public ReportExecutionExportAdapter export() {
        return new ReportExecutionExportAdapter(sessionStorage, executionId);
    }
    public ReportExecutionExportAdapter export(String exportId) {
        return new ReportExecutionExportAdapter(sessionStorage, executionId, exportId);
    }

    public OperationResult<ReportExecutionsSetWrapper> search() {
        JerseyRequest<ReportExecutionsSetWrapper> request = JerseyRequest.buildRequest(
                sessionStorage,
                ReportExecutionsSetWrapper.class,
                path.toArray(new String[0]),
                new RunReportErrorHandler());

        request.addParams(params);
        return request.get();
    }

    public OperationResult<ReportExecution> details() {
        path.add(executionId);
        JerseyRequest<ReportExecution> request = JerseyRequest.buildRequest(
                sessionStorage,
                ReportExecution.class,
                path.toArray(new String[0]),
                new RunReportErrorHandler());

        return request.get();
    }

    public OperationResult<ReportExecutionStatusObject> status() {
        path.add(executionId);
        path.add(STATUS_URI);
        JerseyRequest<ReportExecutionStatusObject> request = JerseyRequest.buildRequest(
                sessionStorage,
                ReportExecutionStatusObject.class,
                path.toArray(new String[0]),
                new RunReportErrorHandler());

        return request.get();
    }

    public OperationResult<ReportInfo> info() {
        path.add(executionId);
        path.add(INFO_URI);
        JerseyRequest<ReportInfo> request = JerseyRequest.buildRequest(
                sessionStorage,
                ReportInfo.class,
                path.toArray(new String[0]),
                new RunReportErrorHandler());

        return request.get();
    }

    public OperationResult updateParameters(List<ReportParameter> reportParameters) {
        path.add(executionId);
        path.add(PARAMETERS_URI);
        JerseyRequest<Object> request = JerseyRequest.buildRequest(
                sessionStorage,
                Object.class,
                path.toArray(new String[0]),
                new RunReportErrorHandler());
        request.addParams(params);
        return request.post(reportParameters);
    }

    public OperationResult<ReportExecutionStatusEntity> cancel() {
        path.add(executionId);
        path.add(STATUS_URI);
        JerseyRequest<ReportExecutionStatusEntity> request = JerseyRequest.buildRequest(
                sessionStorage,
                ReportExecutionStatusEntity.class,
                path.toArray(new String[0]),
                new RunReportErrorHandler());

        return request.put(new ReportExecutionStatusEntity());
    }

    public OperationResult delete() {
        path.add(executionId);
        JerseyRequest request = JerseyRequest.buildRequest(
                sessionStorage,
                Object.class,
                path.toArray(new String[0]),
                new RunReportErrorHandler());

        return request.delete();
    }

    public OperationResult<ReportExecution> run() {
        JerseyRequest<ReportExecution> jerseyRequest = buildRequest(sessionStorage,
                ReportExecution.class,
                new String[]{REPORT_EXECUTIONS_URI},
                new RunReportErrorHandler());
        if (executionRequest.getTimeZone() != null) {
            jerseyRequest.addHeader("Accept-Timezone", executionRequest.getTimeZone().getID());
        }
        return jerseyRequest
                .post(executionRequest);
    }

}