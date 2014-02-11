/*
 * Copyright (C) 2005 - 2012 Jaspersoft Corporation. All rights reserved.
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel;

import com.jaspersoft.jasperserver.api.JasperServerAPI;
import com.jaspersoft.jasperserver.api.engine.scheduling.domain.ReportJobSource;

import java.util.Map;

/**
 * The source of a report job, consisting of a report to execute and a set of
 * report input values. Model is used in search/ update only.
 *
 * <p>
 * A report job definition specifies wich report to execute and when,
 * what output to generate and where to send the output.
 * </p>
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobSourceModel.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7
 */
@JasperServerAPI
public class ReportJobSourceModel extends ReportJobSource {

    private boolean isReportUnitURIModified = false;
    private boolean isParametersMapModified = false;

	/**
	 * Creates an empty job source.
	 */
	public ReportJobSourceModel() {
	}

	/**
	 * Defines the report which should be executed by the job.
	 *
	 * @param reportUnitURI the repository URI/path of the report that the job
	 * should execute
	 */
	public void setReportUnitURI(String reportUnitURI) {
        isReportUnitURIModified = true;
		super.setReportUnitURI(reportUnitURI);
	}

	/**
	 * Sets the set of input values to be used when running the job report.
	 *
	 * <p>
	 * The values are passed in a map indexed by report input control/parameter
	 * names.
	 * </p>
	 *
	 * @param parameters the report input values
	 */
	public void setParametersMap(Map parameters) {
		isParametersMapModified = true;
        super.setParametersMap(parameters);
	}

    /**
     * returns whether report unit uri has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isReportUnitURIModified() { return isReportUnitURIModified; }

    /**
     * returns whether parameters map has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isParametersMapModified() { return isParametersMapModified; }
}
