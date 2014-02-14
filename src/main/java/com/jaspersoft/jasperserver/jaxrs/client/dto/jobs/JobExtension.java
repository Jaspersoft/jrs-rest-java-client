package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "job")
public class JobExtension extends Job {

    private String outputTimeZone;

    public String getOutputTimeZone() {
        return outputTimeZone;
    }

    public void setOutputTimeZone(String outputTimeZone) {
        this.outputTimeZone = outputTimeZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobExtension)) return false;
        if (!super.equals(o)) return false;

        JobExtension that = (JobExtension) o;

        if (outputTimeZone != null ? !outputTimeZone.equals(that.outputTimeZone) : that.outputTimeZone != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (outputTimeZone != null ? outputTimeZone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobExtension{" +
                "outputTimeZone='" + outputTimeZone + '\'' +
                "} " + super.toString();
    }
}
