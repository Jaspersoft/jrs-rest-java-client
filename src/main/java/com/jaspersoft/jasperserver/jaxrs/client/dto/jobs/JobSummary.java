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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * @deprecated (use server DTO).
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "jobsummary")
public class JobSummary {

    private Long id;
    private String label;
    private String reportUnitURI;
    private String owner;
    private JobState state;
    private Long version;

    public JobSummary() {
    }

    public JobSummary(JobSummary other) {
        this.id = other.id;
        this.label = other.label;
        this.owner = other.owner;
        this.reportUnitURI = other.reportUnitURI;
        this.state = (other.state != null) ? new JobState(other.state) : null;
        this.version = other.version;
    }

    public Long getId() {
        return id;
    }

    public JobSummary setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public JobSummary setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getReportUnitURI() {
        return reportUnitURI;
    }

    public JobSummary setReportUnitURI(String reportUnitURI) {
        this.reportUnitURI = reportUnitURI;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public JobSummary setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public JobState getState() {
        return state;
    }

    public JobSummary setState(JobState state) {
        this.state = state;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public JobSummary setVersion(Long version) {
        this.version = version;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobSummary)) return false;

        JobSummary that = (JobSummary) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (reportUnitURI != null ? !reportUnitURI.equals(that.reportUnitURI) : that.reportUnitURI != null)
            return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (reportUnitURI != null ? reportUnitURI.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobSummary{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", reportUnitURI='" + reportUnitURI + '\'' +
                ", owner='" + owner + '\'' +
                ", state=" + state +
                ", version=" + version +
                '}';
    }
}
