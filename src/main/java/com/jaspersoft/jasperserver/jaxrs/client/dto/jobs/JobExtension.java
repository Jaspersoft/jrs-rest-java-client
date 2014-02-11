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

}
