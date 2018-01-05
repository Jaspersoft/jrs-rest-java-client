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

import com.jaspersoft.jasperserver.dto.job.ClientJobState;
import com.jaspersoft.jasperserver.dto.job.ClientReportJob;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobState;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class SingleJobOperationsAdapter extends AbstractAdapter {
    public static final String SERVICE_URI = "jobs";
    public static final String STATE = "state";
    private String jobId;
    private ClientReportJob reportJob;

    public SingleJobOperationsAdapter(SessionStorage sessionStorage, String jobId) {
        super(sessionStorage);
        this.jobId = jobId;
    }

    public SingleJobOperationsAdapter(SessionStorage sessionStorage, ClientReportJob reportJob) {
        super(sessionStorage);
        this.reportJob = reportJob;
    }

    public OperationResult<ClientReportJob> getJob() {
        JerseyRequest<ClientReportJob> request = buildRequest(sessionStorage, ClientReportJob.class, new String[]{SERVICE_URI, jobId});
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setAccept("application/job+json");
        }
        return request.get();
    }

    public <R> RequestExecution asyncGetJob(final Callback<OperationResult<ClientReportJob>, R> callback) {
        final JerseyRequest<ClientReportJob> request = buildRequest(sessionStorage, ClientReportJob.class, new String[]{SERVICE_URI, jobId});
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setAccept("application/job+json");
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

    public OperationResult<ClientReportJob> create() {
        JerseyRequest<ClientReportJob> request = buildRequest(sessionStorage, ClientReportJob.class, new String[]{SERVICE_URI}, new JobValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }
        return request.put(reportJob);
    }
    public <R> RequestExecution asyncCreate(final Callback<OperationResult<ClientReportJob>, R> callback) {
        final JerseyRequest<ClientReportJob> request = buildRequest(sessionStorage, ClientReportJob.class, new String[]{SERVICE_URI}, new JobValidationErrorHandler());
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
                callback.execute(request.put(reportJob));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientJobState> jobState() {
        return buildRequest(sessionStorage, ClientJobState.class, new String[]{SERVICE_URI, jobId, STATE}).get();
    }

    public <R> RequestExecution asyncJobState(final Callback<OperationResult<ClientJobState>, R> callback) {
        final JerseyRequest<ClientJobState> request = buildRequest(sessionStorage, ClientJobState.class, new String[]{SERVICE_URI, jobId, STATE});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientReportJob> update(ClientReportJob job) {
        JerseyRequest<ClientReportJob> request = buildRequest(sessionStorage, ClientReportJob.class, new String[]{SERVICE_URI, jobId}, new JobValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }
        return request.post(job);
    }

    public <R> RequestExecution asyncUpdate(final ClientReportJob job, final Callback<OperationResult<ClientReportJob>, R> callback) {
        final JerseyRequest<ClientReportJob> request = buildRequest(sessionStorage, ClientReportJob.class, new String[]{SERVICE_URI, jobId}, new JobValidationErrorHandler());
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
                callback.execute(request.post(job));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete() {
        return buildRequest(sessionStorage, Object.class, new String[]{SERVICE_URI, jobId}).delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback) {
        final JerseyRequest request = buildRequest(sessionStorage, Object.class, new String[]{SERVICE_URI, jobId});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    /**
     * @deprecated Replaced by {@link SingleJobOperationsAdapter#update(com.jaspersoft.jasperserver.dto.job.ClientReportJob)}.
     */
    public OperationResult<Job> update(Job job) {
        JerseyRequest<Job> request = buildRequest(sessionStorage, Job.class, new String[]{SERVICE_URI, jobId}, new JobValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }
        return request.post(job);
    }
    /**
     * @deprecated Replaced by {@link SingleJobOperationsAdapter#asyncUpdate(com.jaspersoft.jasperserver.dto.job.ClientReportJob, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncUpdate(final Job job, final Callback<OperationResult<Job>, R> callback) {
        final JerseyRequest<Job> request = buildRequest(sessionStorage, Job.class, new String[]{SERVICE_URI, jobId}, new JobValidationErrorHandler());
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
                callback.execute(request.post(job));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    /**
     * @deprecated Replaced by {@link SingleJobOperationsAdapter#asyncJobState(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncState(final Callback<OperationResult<JobState>, R> callback) {
        final JerseyRequest<JobState> request = buildRequest(sessionStorage, JobState.class, new String[]{SERVICE_URI, jobId, STATE});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
    /**
     * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.SingleJobOperationsAdapter#jobState()}.
     */
    public OperationResult<JobState> state() {
        return buildRequest(sessionStorage, JobState.class, new String[]{SERVICE_URI, jobId, STATE}).get();
    }

    /**
     * @deprecated Replaced by {@link SingleJobOperationsAdapter#asyncGetJob(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncGet(final Callback<OperationResult<Job>, R> callback) {
        final JerseyRequest<Job> request = buildRequest(sessionStorage, Job.class, new String[]{SERVICE_URI, jobId});
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setAccept("application/job+json");
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
    /**
     * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.SingleJobOperationsAdapter#getJob()}.
     */
    public OperationResult<Job> get() {
        JerseyRequest<Job> request = buildRequest(sessionStorage, Job.class, new String[]{SERVICE_URI, jobId});
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setAccept("application/job+json");
        }
        return request.get();
    }
}
