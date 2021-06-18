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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters.ReportJobSourceParametersXmlAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.ValuesCollection;

import java.util.LinkedHashMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;
/**
 * @deprecated (use server DTO).
 */
@XmlRootElement(name = "source")
@XmlSeeAlso(ValuesCollection.class)
public class JobSource {

    private String reportUnitURI;
    private Map<String, Object> parameters;

    public JobSource() {
    }

    public JobSource(JobSource other) {
        this.parameters = (other.parameters != null) ? new LinkedHashMap<String, Object>(other.parameters) : null;
        this.reportUnitURI = other.reportUnitURI;
    }

    public String getReportUnitURI() {
        return reportUnitURI;
    }

    public JobSource setReportUnitURI(String reportUnitURI) {
        this.reportUnitURI = reportUnitURI;
        return this;
    }

    @XmlJavaTypeAdapter(ReportJobSourceParametersXmlAdapter.class)
    public Map<String, Object> getParameters() {
        return parameters;
    }

    public JobSource setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobSource)) return false;

        JobSource source = (JobSource) o;

        if (parameters != null ? !parameters.equals(source.parameters) : source.parameters != null) return false;
        if (reportUnitURI != null ? !reportUnitURI.equals(source.reportUnitURI) : source.reportUnitURI != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reportUnitURI != null ? reportUnitURI.hashCode() : 0;
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobSource{" +
                "reportUnitURI='" + reportUnitURI + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
