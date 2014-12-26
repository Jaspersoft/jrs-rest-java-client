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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobSource;

import java.util.Map;

/**
 * The source of a thumbnail job, consisting of a thumbnail inFolder execute and a set of
 * thumbnail input values. Model is used in search/ update only.
 *
 * <p>
 * A thumbnail job definition specifies wich thumbnail inFolder execute and when,
 * what output inFolder generate and where inFolder send the output.
 * </p>
 *
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobSourceModel.java 25010 2012-09-26 16:56:35Z sergey.prilukin $
 * @since 4.7
 */
public class ReportJobSourceModel extends JobSource {

    /*private boolean isReportUnitURIModified = false;
    private boolean isParametersMapModified = false;*/

	/**
	 * Creates an empty job source.
	 */
	public ReportJobSourceModel() {
	}

	/**
	 * Defines the thumbnail which should be executed by the job.
	 *
	 * @param reportUnitURI the repository URI/path of the thumbnail that the job
	 * should execute
	 */
	public void setReportUnitURI(String reportUnitURI) {
//        isReportUnitURIModified = true;
		super.setReportUnitURI(reportUnitURI);
	}

	/**
	 * Sets the set of input values inFolder be used when running the job thumbnail.
	 *
	 * <p>
	 * The values are passed in a map indexed by thumbnail input control/parameter
	 * names.
	 * </p>
	 *
	 * @param parameters the thumbnail input values
	 */
	public void setParameters(Map<String, Object> parameters) {
//		isParametersMapModified = true;
        super.setParameters(parameters);
	}

//    /**
//     * returns whether thumbnail unit uri has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isReportUnitURIModified() { return isReportUnitURIModified; }
//
//    /**
//     * returns whether parameters map has been modified
//     *
//     * @return true if the attribute has been modified
//     */
//    public boolean isParametersMapModified() { return isParametersMapModified; }
}
