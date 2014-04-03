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

package com.jaspersoft.jasperserver.jaxrs.client.dto.importexport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zakhar.Tomchenco
 */
@XmlRootElement(name = "export")
public class ExportTaskDto {
    @XmlElementWrapper(name = "parameters")
    @XmlElement(name = "parameter")
    private List<String> exportParams;

    @XmlElementWrapper(name = "uris")
    @XmlElement(name = "uri")
    private List<String> urisOfResources;

    @XmlTransient
    private List<String> urisOfScheduledJobs;

    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    private List<String> rolesToExport;

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private List<String> usersToExport;

    public ExportTaskDto() {
    }

    public ExportTaskDto(ExportTaskDto other) {
        this.exportParams = new ArrayList<String>(other.exportParams);
        this.urisOfResources = new ArrayList<String>(other.urisOfResources);
        this.urisOfScheduledJobs = new ArrayList<String>(other.urisOfScheduledJobs);
        this.rolesToExport = new ArrayList<String>(other.rolesToExport);
        this.usersToExport = new ArrayList<String>(other.usersToExport);
    }


    public List<String> getParameters() {
        return exportParams;
    }

    public ExportTaskDto setParameters(List<String> exportParams) {
        this.exportParams = exportParams;
        return this;
    }

    public List<String> getUris() {
        return urisOfResources;
    }

    public ExportTaskDto setUris(List<String> urisOfResources) {
        this.urisOfResources = urisOfResources;
        return this;
    }

    public List<String> getScheduledJobs() {
        return urisOfScheduledJobs;
    }

    public ExportTaskDto setScheduledJobs(List<String> urisOfScheduledJobs) {
        this.urisOfScheduledJobs = urisOfScheduledJobs;
        return this;
    }

    public List<String> getRoles() {
        return rolesToExport;
    }

    public ExportTaskDto setRoles(List<String> rolesToExport) {
        this.rolesToExport = rolesToExport;
        return this;
    }

    public List<String> getUsers() {
        return usersToExport;
    }

    public ExportTaskDto setUsers(List<String> usersToExport) {
        this.usersToExport = usersToExport;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportTaskDto that = (ExportTaskDto) o;

        if (exportParams != null ? !exportParams.equals(that.exportParams) : that.exportParams != null) return false;
        if (rolesToExport != null ? !rolesToExport.equals(that.rolesToExport) : that.rolesToExport != null)
            return false;
        if (urisOfResources != null ? !urisOfResources.equals(that.urisOfResources) : that.urisOfResources != null)
            return false;
        if (urisOfScheduledJobs != null ? !urisOfScheduledJobs.equals(that.urisOfScheduledJobs) : that.urisOfScheduledJobs != null)
            return false;
        if (usersToExport != null ? !usersToExport.equals(that.usersToExport) : that.usersToExport != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = exportParams != null ? exportParams.hashCode() : 0;
        result = 31 * result + (urisOfResources != null ? urisOfResources.hashCode() : 0);
        result = 31 * result + (urisOfScheduledJobs != null ? urisOfScheduledJobs.hashCode() : 0);
        result = 31 * result + (rolesToExport != null ? rolesToExport.hashCode() : 0);
        result = 31 * result + (usersToExport != null ? usersToExport.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExportTaskDto{" +
                "exportParams=" + exportParams +
                ", urisOfResources=" + urisOfResources +
                ", urisOfScheduledJobs=" + urisOfScheduledJobs +
                ", rolesToExport=" + rolesToExport +
                ", usersToExport=" + usersToExport +
                '}';
    }
}
