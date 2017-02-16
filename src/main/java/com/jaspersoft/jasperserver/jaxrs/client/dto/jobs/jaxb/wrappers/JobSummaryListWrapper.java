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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobSummary;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
/**
 * @deprecated (use server adapter).
 */
@XmlRootElement(name = "jobs")
public class JobSummaryListWrapper {
    private List<JobSummary> jobsummary;

    public JobSummaryListWrapper(){}

    public JobSummaryListWrapper(List<JobSummary> jobSummaries){
        jobsummary = new ArrayList<JobSummary>(jobSummaries.size());
        for (JobSummary r : jobSummaries){
            jobsummary.add(r);
        }
    }

    @XmlElement(name = "jobsummary")
    public List<JobSummary> getJobsummary() {
        return jobsummary;
    }

    public void setJobsummary(List<JobSummary> jobSummaries) {
        this.jobsummary = jobSummaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobSummaryListWrapper that = (JobSummaryListWrapper) o;

        if (jobsummary != null ? !jobsummary.equals(that.jobsummary) : that.jobsummary != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return jobsummary != null ? jobsummary.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "JobSummaryListWrapper{" +
                "jobsummary=" + jobsummary +
                '}';
    }
}
