package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class ExportsContainer {
    private Map<String, ExportExecution> executions = new HashMap<String, ExportExecution>();

    public ExportsContainer() {
    }

    public ExportsContainer(ExportsContainer other) {
        this.executions = new HashMap<>();
        final Map<String, ExportExecution> executions = other.getExecutions();
        if (executions != null) {
            for (String execution : executions.keySet()) {
                this.executions.put(execution, new ExportExecution(executions.get(execution)));
            }
        }
    }

    public Map<String, ExportExecution> getExecutions() {
        return executions;
    }

    public ExportsContainer setExecutions(Map<String, ExportExecution> executions) {
        this.executions = executions;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExportsContainer)) return false;

        ExportsContainer that = (ExportsContainer) o;

        return executions != null ? executions.equals(that.executions) : that.executions == null;
    }

    @Override
    public int hashCode() {
        return executions != null ? executions.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ExportsContainer{" +
                "executions=" + executions +
                '}';
    }
}
