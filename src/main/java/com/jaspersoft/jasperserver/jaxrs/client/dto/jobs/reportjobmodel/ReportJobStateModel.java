/*
 * Copyright (C) 2005 - 2011 Jaspersoft Corporation. All rights reserved.
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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobState;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Searching report jobs by runtime information
 * 
 * @author Ivan Chan (ichan@jaspersoft.com)
 * @version $Id: ReportJobStateModel.java 25420 2012-10-20 16:36:09Z sergey.prilukin $
 * @since 1.0
 */
@XmlRootElement(name = "stateModel")
public class ReportJobStateModel extends JobState {

    private boolean isNextFireTimeModified = false;
    private boolean isPreviousFireTimeModified = false;
    private boolean isStateModified = false;

	/**
	 * Creates an empty object.
	 */
	public ReportJobStateModel() {
	}


	/**
	 * Sets the job next fire time.
	 * 
	 * @param nextFireTime the next fire time for the job
	 */
	public void setNextFireTime(String nextFireTime) {
        isNextFireTimeModified = true;
		super.setNextFireTime(nextFireTime);
	}

	/**
	 * Sets the job previous fire time.
	 * 
	 * @param previousFireTime the previous fire time of the job
	 */
	public void setPreviousFireTime(String previousFireTime) {
        isPreviousFireTimeModified = true;
		super.setPreviousFireTime(previousFireTime);
	}


	/**
	 * Sets the execution state of the job trigger.
	 *
	 * @param state one of the <code>STATE_*</code> constants
	 */
	public void setValue(String state) {
        isStateModified = true;
		super.setValue(state);
	}

    /**
     * returns whether NextFireTime has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isNextFireTimeModified() { return isNextFireTimeModified; }

    /**
     * returns whether PreviousFireTime has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isPreviousFireTimeModified() { return isPreviousFireTimeModified; }

    /**
     * returns whether State has been modified
     *
     * @return true if the attribute has been modified
     */
    public boolean isStateModified() { return isStateModified; }
}
