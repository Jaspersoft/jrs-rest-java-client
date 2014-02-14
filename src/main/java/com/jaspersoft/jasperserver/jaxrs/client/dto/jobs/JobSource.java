package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.adapters.ReportJobSourceParametersXmlAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.adapters.ValuesCollection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

@XmlRootElement(name = "source")
@XmlSeeAlso(ValuesCollection.class)
public class JobSource {

    private String reportUnitURI;
    private Map<String, Object> parameters;

    public String getReportUnitURI() {
        return reportUnitURI;
    }

    public void setReportUnitURI(String reportUnitURI) {
        this.reportUnitURI = reportUnitURI;
    }

    @XmlJavaTypeAdapter(ReportJobSourceParametersXmlAdapter.class)
    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
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
