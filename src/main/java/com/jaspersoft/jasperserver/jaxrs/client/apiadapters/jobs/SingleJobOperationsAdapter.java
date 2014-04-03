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
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobExtension;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobState;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class SingleJobOperationsAdapter extends AbstractAdapter {

    private final String jobId;

    public SingleJobOperationsAdapter(SessionStorage sessionStorage, String jobId) {
        super(sessionStorage);
        this.jobId = jobId;
    }

    public OperationResult<JobExtension> get(){
        JerseyRequest<JobExtension> builder =
                buildRequest(sessionStorage, JobExtension.class, new String[]{"/jobs", jobId});
        builder.setAccept("application/job+json");
        return builder.get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<JobExtension>, R> callback) {
        final JerseyRequest<JobExtension> builder =
                buildRequest(sessionStorage, JobExtension.class, new String[]{"/jobs", jobId});
        builder.setAccept("application/job+json");

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<JobState> state(){
        return buildRequest(sessionStorage, JobState.class, new String[]{"/jobs", jobId, "/state"})
                .get();
    }

    public <R> RequestExecution asyncState(final Callback<OperationResult<JobState>, R> callback) {
        final JerseyRequest<JobState> builder =
                buildRequest(sessionStorage, JobState.class, new String[]{"/jobs", jobId, "/state"});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<JobExtension> update(JobExtension job){
        JerseyRequest<JobExtension> builder =
                buildRequest(sessionStorage, JobExtension.class, new String[]{"/jobs", jobId}, new JobValidationErrorHandler());
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");

        return builder.post(job);
    }

    public <R> RequestExecution asyncUpdate(final JobExtension job, final Callback<OperationResult<JobExtension>, R> callback) {
        final JerseyRequest<JobExtension> builder =
                buildRequest(sessionStorage, JobExtension.class, new String[]{"/jobs", jobId}, new JobValidationErrorHandler());
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.post(job));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete() {
        return buildRequest(sessionStorage, Object.class, new String[]{"/jobs", jobId})
                .delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback) {
        final JerseyRequest builder =
                buildRequest(sessionStorage, Object.class, new String[]{"/jobs", jobId});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.delete());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
}
