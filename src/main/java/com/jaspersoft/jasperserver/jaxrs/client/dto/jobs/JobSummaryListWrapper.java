package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

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
