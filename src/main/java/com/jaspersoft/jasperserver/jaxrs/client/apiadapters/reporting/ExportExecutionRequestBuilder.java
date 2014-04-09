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
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionStatusEntity;

import java.io.InputStream;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class ExportExecutionRequestBuilder extends AbstractAdapter {

    private String requestId;
    private String exportOutput;

    public ExportExecutionRequestBuilder(SessionStorage sessionStorage, String requestId, String exportOutput){
        super(sessionStorage);
        this.requestId = requestId;
        this.exportOutput = exportOutput;
    }

    public OperationResult<InputStream> outputResource(){
        return buildRequest(sessionStorage, InputStream.class,
                new String[]{"/reportExecutions", requestId, "/exports", exportOutput, "/outputResource"})
                .get();
    }

    public <R> RequestExecution asyncOutputResource(final Callback<OperationResult<InputStream>, R> callback) {
        final JerseyRequest<InputStream> request =
                buildRequest(sessionStorage, InputStream.class,
                        new String[]{"/reportExecutions", requestId, "/exports", exportOutput, "/outputResource"});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<InputStream> attachment(String attachmentId){

        if ("".equals(attachmentId) || "/".equals(attachmentId))
            throw new  IllegalArgumentException("'attachmentId' mustn't be an empty string");

        while (!"ready".equals(status().getEntity().getValue())){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }

        return buildRequest(sessionStorage, InputStream.class,
                new String[]{"/reportExecutions", requestId, "/exports", exportOutput, "/attachments", attachmentId})
                .get();
    }

    public <R> RequestExecution asyncAttachment(final String attachmentId, final Callback<OperationResult<InputStream>, R> callback) {

        if ("".equals(attachmentId) || "/".equals(attachmentId))
            throw new  IllegalArgumentException("'attachmentId' mustn't be an empty string");

        final JerseyRequest<InputStream> request =
                buildRequest(sessionStorage, InputStream.class,
                        new String[]{"/reportExecutions", requestId, "/exports", exportOutput, "/attachments", attachmentId});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                while (!"ready".equals(status().getEntity().getValue())){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {}
                }
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ReportExecutionStatusEntity> status(){
        return buildRequest(sessionStorage, ReportExecutionStatusEntity.class,
                new String[]{"/reportExecutions", requestId, "/exports", exportOutput, "/status"})
                .get();
    }

    public <R> RequestExecution asyncStatus(final Callback<OperationResult<ReportExecutionStatusEntity>, R> callback) {
        final JerseyRequest<ReportExecutionStatusEntity> request =
                buildRequest(sessionStorage, ReportExecutionStatusEntity.class,
                        new String[]{"/reportExecutions", requestId, "/exports", exportOutput, "/status"});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
}
