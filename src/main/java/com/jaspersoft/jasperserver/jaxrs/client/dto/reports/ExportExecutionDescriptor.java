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

package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;

import javax.xml.bind.annotation.XmlRootElement;
@Deprecated
@XmlRootElement(name = "exportExecution")
public class ExportExecutionDescriptor {

    private String id;
    private String status;
    private OutputResourceDescriptor outputResource;
    private ErrorDescriptor errorDescriptor;

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public void setErrorDescriptor(ErrorDescriptor errorDescriptor) {
        this.errorDescriptor = errorDescriptor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OutputResourceDescriptor getOutputResource() {
        return outputResource;
    }

    public void setOutputResource(OutputResourceDescriptor outputResource) {
        this.outputResource = outputResource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportExecutionDescriptor that = (ExportExecutionDescriptor) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (outputResource != null ? !outputResource.equals(that.outputResource) : that.outputResource != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (outputResource != null ? outputResource.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExportExecutionDescriptor{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", outputResource=" + outputResource +
                '}';
    }
}
