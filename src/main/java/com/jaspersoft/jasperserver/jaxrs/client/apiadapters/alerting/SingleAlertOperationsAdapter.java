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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting;

import com.jaspersoft.jasperserver.dto.alerting.ClientAlertingState;
import com.jaspersoft.jasperserver.dto.alerting.ClientReportAlert;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class SingleAlertOperationsAdapter extends AbstractAdapter {
    public static final String SERVICE_URI = "alerts";
    public static final String STATE = "state";
    private String jobId;
    private ClientReportAlert reportAlert;

    public SingleAlertOperationsAdapter(SessionStorage sessionStorage, String jobId) {
        super(sessionStorage);
        this.jobId = jobId;
    }

    public SingleAlertOperationsAdapter(SessionStorage sessionStorage, ClientReportAlert reportAlert) {
        super(sessionStorage);
        this.reportAlert = reportAlert;
    }

    public OperationResult<ClientReportAlert> getJob() {
        JerseyRequest<ClientReportAlert> request = buildRequest(sessionStorage, ClientReportAlert.class, new String[]{SERVICE_URI, jobId});
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/alert+{mime}"));
        } else {
            request.setAccept("application/alert+json");
        }
        return request.get();
    }

    public <R> RequestExecution asyncGetJob(final Callback<OperationResult<ClientReportAlert>, R> callback) {
        final JerseyRequest<ClientReportAlert> request = buildRequest(sessionStorage, ClientReportAlert.class, new String[]{SERVICE_URI, jobId});
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/alert+{mime}"));
        } else {
            request.setAccept("application/alert+json");
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

    public OperationResult<ClientReportAlert> create() {
        JerseyRequest<ClientReportAlert> request = buildRequest(sessionStorage, ClientReportAlert.class, new String[]{SERVICE_URI}, new AlertValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/alert+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/alert+{mime}"));
        } else {
            request.setContentType("application/alert+json");
            request.setAccept("application/alert+json");
        }
        return request.put(reportAlert);
    }

    public <R> RequestExecution asyncCreate(final Callback<OperationResult<ClientReportAlert>, R> callback) {
        final JerseyRequest<ClientReportAlert> request = buildRequest(sessionStorage, ClientReportAlert.class, new String[]{SERVICE_URI}, new AlertValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/alert+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/alert+{mime}"));
        } else {
            request.setContentType("application/alert+json");
            request.setAccept("application/alert+json");
        }
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(reportAlert));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientAlertingState> jobState() {
        return buildRequest(sessionStorage, ClientAlertingState.class, new String[]{SERVICE_URI, jobId, STATE}).get();
    }

    public <R> RequestExecution asyncJobState(final Callback<OperationResult<ClientAlertingState>, R> callback) {
        final JerseyRequest<ClientAlertingState> request = buildRequest(sessionStorage, ClientAlertingState.class, new String[]{SERVICE_URI, jobId, STATE});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientReportAlert> update(ClientReportAlert job) {
        JerseyRequest<ClientReportAlert> request = buildRequest(sessionStorage, ClientReportAlert.class, new String[]{SERVICE_URI, jobId}, new AlertValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/alert+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/alert+{mime}"));
        } else {
            request.setContentType("application/alert+json");
            request.setAccept("application/alert+json");
        }
        return request.post(job);
    }

    public <R> RequestExecution asyncUpdate(final ClientReportAlert job, final Callback<OperationResult<ClientReportAlert>, R> callback) {
        final JerseyRequest<ClientReportAlert> request = buildRequest(sessionStorage, ClientReportAlert.class, new String[]{SERVICE_URI, jobId}, new AlertValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/alert+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/alert+{mime}"));
        } else {
            request.setContentType("application/alert+json");
            request.setAccept("application/alert+json");
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
}
