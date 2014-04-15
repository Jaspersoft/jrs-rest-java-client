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
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobState;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class SingleJobOperationsAdapter extends AbstractAdapter {

    private final String jobId;

    public SingleJobOperationsAdapter(SessionStorage sessionStorage, String jobId) {
        super(sessionStorage);
        this.jobId = jobId;
    }

    public OperationResult<Job> get() {
        JerseyRequest<Job> request =
                buildRequest(sessionStorage, Job.class, new String[]{"/jobs", jobId});
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0)
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        else
            request.setAccept("application/job+json");
        return request.get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<Job>, R> callback) {
        final JerseyRequest<Job> request =
                buildRequest(sessionStorage, Job.class, new String[]{"/jobs", jobId});

        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0)
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        else
            request.setAccept("application/job+json");

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<JobState> state() {
        return buildRequest(sessionStorage, JobState.class, new String[]{"/jobs", jobId, "/state"})
                .get();
    }

    public <R> RequestExecution asyncState(final Callback<OperationResult<JobState>, R> callback) {
        final JerseyRequest<JobState> request =
                buildRequest(sessionStorage, JobState.class, new String[]{"/jobs", jobId, "/state"});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<Job> update(Job job) {
        JerseyRequest<Job> request =
                buildRequest(sessionStorage, Job.class, new String[]{"/jobs", jobId}, new JobValidationErrorHandler());

        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0){
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        }
        else{
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }

        return request.post(job);
    }

    public <R> RequestExecution asyncUpdate(final Job job, final Callback<OperationResult<Job>, R> callback) {
        final JerseyRequest<Job> request =
                buildRequest(sessionStorage, Job.class, new String[]{"/jobs", jobId}, new JobValidationErrorHandler());

        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0){
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        }
        else{
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(job));
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
        final JerseyRequest request =
                buildRequest(sessionStorage, Object.class, new String[]{"/jobs", jobId});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
}
