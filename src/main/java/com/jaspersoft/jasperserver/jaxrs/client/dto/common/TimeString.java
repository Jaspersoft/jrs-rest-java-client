package com.jaspersoft.jasperserver.jaxrs.client.dto.common;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(name = "time", namespace = "http://www.w3.org/2001/XMLSchema")
public class TimeString {
    private String time;

    public TimeString() {
    }

    public TimeString(String time) {
        this.time = time;
    }

    @XmlValue
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
