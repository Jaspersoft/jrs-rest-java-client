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

package com.jaspersoft.jasperserver.jaxrs.client.dto.authority;

import javax.xml.bind.annotation.XmlRootElement;
@Deprecated
@XmlRootElement(name = "organization")
public class Organization {

    private String id = null;
    private String alias = null;
    private String parentId = null;
    private String tenantName = null;
    private String tenantDesc = null;
    private String tenantNote = null;
    private String tenantUri = null;
    private String tenantFolderUri = null;
    private String theme = null;

    public String getTenantDesc() {
        return tenantDesc;
    }

    public Organization setTenantDesc(String tenantDesc) {
        this.tenantDesc = tenantDesc;
        return this;
    }

    public String getTenantNote() {
        return tenantNote;
    }

    public Organization setTenantNote(String tenantNote) {
        this.tenantNote = tenantNote;
        return this;
    }

    public String getId() {
        return id;
    }
    public Organization setId(String pid) {
        id = pid;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public Organization setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public Organization setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getTenantName() {
        return tenantName;
    }

    public Organization setTenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    public String getTenantUri() {
        return tenantUri;
    }

    public Organization setTenantUri(String tenantUri) {
        this.tenantUri = tenantUri;
        return this;
    }

    public String getTenantFolderUri() {
        return tenantFolderUri;
    }
    public Organization setTenantFolderUri(String tenantFolderUri) {
        this.tenantFolderUri = tenantFolderUri;
        return this;
    }

    public String getTheme() {
        return theme;
    }

    public Organization setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (tenantDesc != null ? !tenantDesc.equals(that.tenantDesc) : that.tenantDesc != null) return false;
        if (tenantFolderUri != null ? !tenantFolderUri.equals(that.tenantFolderUri) : that.tenantFolderUri != null)
            return false;
        if (tenantName != null ? !tenantName.equals(that.tenantName) : that.tenantName != null) return false;
        if (tenantNote != null ? !tenantNote.equals(that.tenantNote) : that.tenantNote != null) return false;
        if (tenantUri != null ? !tenantUri.equals(that.tenantUri) : that.tenantUri != null) return false;
        if (theme != null ? !theme.equals(that.theme) : that.theme != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (tenantName != null ? tenantName.hashCode() : 0);
        result = 31 * result + (tenantDesc != null ? tenantDesc.hashCode() : 0);
        result = 31 * result + (tenantNote != null ? tenantNote.hashCode() : 0);
        result = 31 * result + (tenantUri != null ? tenantUri.hashCode() : 0);
        result = 31 * result + (tenantFolderUri != null ? tenantFolderUri.hashCode() : 0);
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id='" + id + '\'' +
                ", alias='" + alias + '\'' +
                ", parentId='" + parentId + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", tenantDesc='" + tenantDesc + '\'' +
                ", tenantNote='" + tenantNote + '\'' +
                ", tenantUri='" + tenantUri + '\'' +
                ", tenantFolderUri='" + tenantFolderUri + '\'' +
                ", theme='" + theme + '\'' +
                '}';
    }
}
