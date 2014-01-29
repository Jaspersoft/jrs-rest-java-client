package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "state")
public class JobState {

    private String nextFireTime;
    private String value;

    public String getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobState jobState = (JobState) o;

        if (nextFireTime != null ? !nextFireTime.equals(jobState.nextFireTime) : jobState.nextFireTime != null)
            return false;
        if (value != null ? !value.equals(jobState.value) : jobState.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nextFireTime != null ? nextFireTime.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
