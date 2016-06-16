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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControl;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
/**
 * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls.InputControlsAdapter}.
 */
public class ReorderingReportParametersAdapter extends ReportParametersAdapter {

    public static final String REPORTS_URI = "reports";
    public static final String INPUT_CONTROLS_URI = "inputControls";

    public ReorderingReportParametersAdapter(SessionStorage sessionStorage, String reportUnitUri) {
        super(sessionStorage, reportUnitUri);
    }

    public OperationResult<ReportInputControlsListWrapper> reorder(List<ReportInputControl> inputControls){
        ReportInputControlsListWrapper wrapper = new ReportInputControlsListWrapper(inputControls);
        return buildRequest(sessionStorage, ReportInputControlsListWrapper.class, new String[]{REPORTS_URI, reportUnitUri, INPUT_CONTROLS_URI})
                .put(wrapper);
    }

    public <R> RequestExecution asyncReorder(final List<ReportInputControl> inputControls,
                                             final Callback<OperationResult<ReportInputControlsListWrapper>, R> callback) {
        final ReportInputControlsListWrapper wrapper = new ReportInputControlsListWrapper(inputControls);
        final JerseyRequest<ReportInputControlsListWrapper> request =
                buildRequest(sessionStorage, ReportInputControlsListWrapper.class, new String[]{REPORTS_URI, reportUnitUri, INPUT_CONTROLS_URI});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(wrapper));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
}
