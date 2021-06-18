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
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionListWrapper;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
@Deprecated
public class ReportsAndJobsSearchAdapter extends AbstractAdapter {

    public static final String REPORT_EXECUTIONS_URI = "reportExecutions";
    private final MultivaluedMap<String, String> params;

    public ReportsAndJobsSearchAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.params = new MultivaluedHashMap<String, String>();
    }

    public ReportsAndJobsSearchAdapter parameter(ReportAndJobSearchParameter param, String value) {
        params.add(param.getName(), UrlUtils.encode(value));
        return this;
    }

    public OperationResult<ReportExecutionListWrapper> find(){
        JerseyRequest<ReportExecutionListWrapper> request =
                buildRequest();
        request.addParams(params);
        return request.get();
    }

    public <R> RequestExecution asyncFind(final Callback<OperationResult<ReportExecutionListWrapper>, R> callback) {
        final JerseyRequest<ReportExecutionListWrapper> request =
                buildRequest();
        request.addParams(params);

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    protected JerseyRequest<ReportExecutionListWrapper> buildRequest() {
        return JerseyRequest.buildRequest(sessionStorage,
                ReportExecutionListWrapper.class,
                new String[]{REPORT_EXECUTIONS_URI});
    }

}
