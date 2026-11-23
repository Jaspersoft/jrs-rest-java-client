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

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.PageRange;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.RunReportErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportExecution;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportExecutionOptions;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.OutputResourceDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionStatusEntity;
import java.io.InputStream;
import java.util.ArrayList;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.apache.commons.lang3.StringUtils;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;

public class ReportExecutionExportAdapter extends AbstractAdapter {

    public static final String REPORT_EXECUTIONS_URI = "reportExecutions";
    public static final String EXPORTS_URI = "exports";
    public static final String STATUS_URI = "status";
    private static final String ATTACHMENTS_URI = "attachments";
    private String OUTPUT_RESOURCES_URI = "outputResource";

    private ExportExecutionOptions executionOptions;
    private String executionId;
    private String exportId;
    private Boolean withErrorDescriptor = Boolean.FALSE;
    private ArrayList<String> path = new ArrayList<String>();
    private final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();

    public ReportExecutionExportAdapter(SessionStorage sessionStorage, String executionId) {
        super(sessionStorage);
        this.path.add(REPORT_EXECUTIONS_URI);
        this.executionId = executionId;
        this.executionOptions = new ExportExecutionOptions();
    }

    public ReportExecutionExportAdapter(SessionStorage sessionStorage, String executionId, String exportId) {
        super(sessionStorage);
        path.add(REPORT_EXECUTIONS_URI);
        this.executionId = executionId;
        this.exportId = exportId;
    }

    public ReportExecutionExportAdapter withOptions(ExportExecutionOptions options) {
        this.executionOptions = options;
        return this;
    }

    public ReportExecutionExportAdapter outputFormat(ReportOutputFormat outputFormat) {
        this.executionOptions.setOutputFormat(outputFormat.name().toLowerCase());
        return this;
    }

    public ReportExecutionExportAdapter outputFormat(String outputFormat) {
        this.executionOptions.setOutputFormat(outputFormat);
        return this;
    }

    public ReportExecutionExportAdapter attachmentsPrefix(String attachmentPrefix) {
        this.executionOptions.setAttachmentsPrefix(attachmentPrefix);
        return this;
    }

    public ReportExecutionExportAdapter pages(String pages) {
        this.executionOptions.setPages(pages);
        return this;
    }

    public ReportExecutionExportAdapter pages(Integer... pages) {
        this.executionOptions.setPages(StringUtils.join(asList(pages), ","));
        return this;
    }

    public ReportExecutionExportAdapter pages(PageRange pages) {
        this.executionOptions.setPages(pages.getRange());
        return this;
    }

    public ReportExecutionExportAdapter baseUrl(String uri) {
        this.executionOptions.setBaseUrl(uri);
        return this;
    }

    public ReportExecutionExportAdapter allowInlineScripts(Boolean value) {
        this.executionOptions.setAllowInlineScripts(value);
        return this;
    }

    public ReportExecutionExportAdapter markupType(String markupType) {
        this.executionOptions.setMarkupType(markupType);
        return this;
    }

    public ReportExecutionExportAdapter ignorePagination(Boolean value) {
        this.executionOptions.setIgnorePagination(value);
        return this;
    }

    public ReportExecutionExportAdapter withErrordescriptor(Boolean value) {
        this.withErrorDescriptor = value;
        return this;
    }

    public OperationResult<ExportExecution> run() {
        path.add(executionId);
        path.add(EXPORTS_URI);
        JerseyRequest<ExportExecution> jerseyRequest = buildRequest(sessionStorage,
                ExportExecution.class,
                path.toArray(new String[path.size()]),
                new RunReportErrorHandler());

        return jerseyRequest
                .post(executionOptions);
    }

    public OperationResult<ReportExecutionStatusEntity> status() {
        path.add(executionId);
        path.add(EXPORTS_URI);
        path.add(exportId);
        path.add(STATUS_URI);
        JerseyRequest<ReportExecutionStatusEntity> jerseyRequest = buildRequest(sessionStorage,
                ReportExecutionStatusEntity.class,
                path.toArray(new String[path.size()]),
                new RunReportErrorHandler());
        if (withErrorDescriptor)
            jerseyRequest.setAccept(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/status+{mime}"));
        return jerseyRequest.get();
    }

    public ReportExecutionExportAdapter suppressContentDisposition(Boolean value) {
        this.params.add("suppressContentDisposition", value.toString());
        return this;
    }

    public OperationResult<InputStream> getOutputResource() {
        path.add(executionId);
        path.add(EXPORTS_URI);
        path.add(exportId);
        path.add(OUTPUT_RESOURCES_URI);
        JerseyRequest<InputStream> jerseyRequest = buildRequest(sessionStorage,
                InputStream.class,
                path.toArray(new String[path.size()]),
                new RunReportErrorHandler());
        jerseyRequest.addParams(params);
        return jerseyRequest.get();
    }

    public OperationResult<String> getOutputResourceAsText() {
        path.add(executionId);
        path.add(EXPORTS_URI);
        path.add(exportId);
        path.add(OUTPUT_RESOURCES_URI);
        JerseyRequest<String> jerseyRequest = buildRequest(sessionStorage,
                String.class,
                path.toArray(new String[path.size()]),
                new RunReportErrorHandler());
        jerseyRequest.addParams(params);
        return jerseyRequest.get();
    }

    public OperationResult<InputStream> getOutputResourceAttachment(OutputResourceDescriptor attachmentDescriptor) {
        path.add(executionId);
        path.add(EXPORTS_URI);
        path.add(exportId);
        path.add(ATTACHMENTS_URI);
        path.add(attachmentDescriptor.getFileName());
        JerseyRequest<InputStream> jerseyRequest = buildRequest(sessionStorage,
                InputStream.class,
                path.toArray(new String[path.size()]),
                new RunReportErrorHandler());
        jerseyRequest.addParams(params);
        jerseyRequest.setAccept(attachmentDescriptor.getContentType());
        return jerseyRequest.get();
    }

    public OperationResult<InputStream> getOutputResourceAttachment(String atachmentName, String mediaType) {
        return  this.getOutputResourceAttachment(new OutputResourceDescriptor().setFileName(atachmentName).setContentType(mediaType));
    }

}