package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportExecutionStatusEntity.java 26599 2012-12-10 13:04:23Z ykovalchyk $
 */
@XmlRootElement(name = "status")
public class ReportExecutionStatusEntity {
    public static final String VALUE_CANCELLED = "cancelled";

    private String value = VALUE_CANCELLED;

    @XmlValue
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

        ReportExecutionStatusEntity that = (ReportExecutionStatusEntity) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ReportExecutionStatusEntity{" +
                "value='" + value + '\'' +
                '}';
    }
}
