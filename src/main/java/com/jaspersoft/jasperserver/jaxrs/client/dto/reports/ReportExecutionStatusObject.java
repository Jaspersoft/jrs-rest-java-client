package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.executions.ExecutionStatus;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */

@XmlRootElement(name = "status")
public class ReportExecutionStatusObject {

    private ExecutionStatus value;
    private ErrorDescriptor errorDescriptor;

    public ReportExecutionStatusObject(){}

    public ReportExecutionStatusObject(ReportExecutionStatusObject source){
        this.value = source.getValue();
        this.errorDescriptor = new ErrorDescriptor(source.getErrorDescriptor());
    }

    public ExecutionStatus getValue() {
        return value;
    }

    public ReportExecutionStatusObject setValue(ExecutionStatus value) {
        this.value = value;
        return this;
    }

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public ReportExecutionStatusObject setErrorDescriptor(ErrorDescriptor errorDescriptor) {
        this.errorDescriptor = errorDescriptor;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportExecutionStatusObject that = (ReportExecutionStatusObject) o;

        if (errorDescriptor != null ? !errorDescriptor.equals(that.errorDescriptor) : that.errorDescriptor != null)
            return false;
        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (errorDescriptor != null ? errorDescriptor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportExecutionStatusObject{" +
                "value=" + value +
                ", errorDescriptor=" + errorDescriptor +
                '}';
    }
}
