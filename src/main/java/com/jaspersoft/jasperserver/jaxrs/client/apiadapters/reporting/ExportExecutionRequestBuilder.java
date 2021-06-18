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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.Attachment;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.AttachmentDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.HtmlReport;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionStatusEntity;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
@Deprecated
public class ExportExecutionRequestBuilder extends AbstractAdapter {

    public static final String REPORT_EXECUTIONS = "reportExecutions";
    public static final String EXPORTS = "exports";
    public static final String OUTPUT_RESOURCE = "outputResource";
    public static final String ATTACHMENTS = "attachments";
    public static final String STATUS = "status";
    private String requestId;
    private String exportId;

    public ExportExecutionRequestBuilder(SessionStorage sessionStorage, String requestId, String exportId) {
        super(sessionStorage);
        this.requestId = requestId;
        this.exportId = exportId;
    }

    /**
     * <code>OperationResult</code> should be parametrized with <code>String</code> if you're exporting HTML,
     * in other cases it should be parametrized with <code>InputStream</code>
     */
    private OperationResult outputResource(boolean isHtmlExport) {
        return buildRequest(sessionStorage, isHtmlExport ? String.class : InputStream.class,
                new String[]{REPORT_EXECUTIONS, requestId, EXPORTS, exportId, OUTPUT_RESOURCE})
                .get();
    }

    public OperationResult<InputStream> outputResource() {
        return outputResource(false);
    }

    public <R> RequestExecution asyncOutputResource(final Callback<OperationResult<InputStream>, R> callback) {
        final JerseyRequest<InputStream> request =
                buildRequest(sessionStorage, InputStream.class,
                        new String[]{REPORT_EXECUTIONS, requestId, EXPORTS, exportId, OUTPUT_RESOURCE});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<InputStream> attachment(String attachmentId) {

        if ("".equals(attachmentId) || "/".equals(attachmentId))
            throw new IllegalArgumentException("'attachmentId' mustn't be an empty string");

        while (!"ready".equals(status().getEntity().getValue())) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }

        return buildRequest(sessionStorage, InputStream.class,
                new String[]{REPORT_EXECUTIONS, requestId, EXPORTS, exportId, ATTACHMENTS, attachmentId})
                .get();
    }

    public <R> RequestExecution asyncAttachment(final String attachmentId, final Callback<OperationResult<InputStream>, R> callback) {

        if ("".equals(attachmentId) || "/".equals(attachmentId))
            throw new IllegalArgumentException("'attachmentId' mustn't be an empty string");

        final JerseyRequest<InputStream> request =
                buildRequest(sessionStorage, InputStream.class,
                        new String[]{REPORT_EXECUTIONS, requestId, EXPORTS, exportId, ATTACHMENTS, attachmentId});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                while (!"ready".equals(status().getEntity().getValue())) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    }
                }
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ReportExecutionStatusEntity> status() {
        return buildRequest(sessionStorage, ReportExecutionStatusEntity.class,
                new String[]{REPORT_EXECUTIONS, requestId, EXPORTS, exportId, STATUS})
                .get();
    }

    public <R> RequestExecution asyncStatus(final Callback<OperationResult<ReportExecutionStatusEntity>, R> callback) {
        final JerseyRequest<ReportExecutionStatusEntity> request =
                buildRequest(sessionStorage, ReportExecutionStatusEntity.class,
                        new String[]{REPORT_EXECUTIONS, requestId, EXPORTS, exportId, STATUS});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public HtmlReport htmlReport(ExportDescriptor htmlExport) {
        //if (exportId.toLowerCase().startsWith("html") && htmlExport.getId().toLowerCase().startsWith("html")) {
            HtmlReport htmlReport = new HtmlReport(htmlExport.getId());

            OperationResult<String> markup = outputResource(true);
            htmlReport.setHtml(markup.getEntity());

            List<AttachmentDescriptor> attachments = htmlExport.getAttachments();
            if (attachments != null) {
                for (AttachmentDescriptor attachmentDescriptor : attachments) {
                    String fileName = attachmentDescriptor.getFileName();
                    OperationResult<InputStream> streamOperationResult = attachment(fileName);

                    Attachment attachment = new Attachment();
                    attachment.setName(attachmentDescriptor.getFileName());
                    attachment.setMimeType(attachmentDescriptor.getContentType());
                    attachment.setContent(toByteArray(streamOperationResult.getEntity()));

                    htmlReport.addAttachment(attachment);
                }
            }

            return htmlReport;
        //}
        //throw new JSClientException("Output format is not 'HTML'");
    }

    public <R> RequestExecution asyncHtmlReport(final ExportDescriptor htmlExport, final Callback<HtmlReport, R> callback) {
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(htmlReport(htmlExport));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private byte[] toByteArray(InputStream is) {
        try {
            List<Byte> bytes = new ArrayList<Byte>();
            byte[] buff = new byte[1024];
            while (is.read(buff) != -1) {
                for (byte b : buff)
                    bytes.add(b);
            }

            byte[] result = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++) {
                result[i] = bytes.get(i);
            }
            return result;
        } catch (IOException e) {
            throw new JSClientException("Error while reading thumbnail content", e);
        }
    }
}
