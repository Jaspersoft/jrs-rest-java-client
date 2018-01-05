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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@Deprecated
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "export")
public class ExportDescriptor {

    private String id;
    private String status;
    private String pages;
    private String attachmentsPrefix;
    private OutputResourceDescriptor outputResource;
    private ErrorDescriptor errorDescriptor;
    private List<AttachmentDescriptor> attachments;

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public ExportDescriptor setErrorDescriptor(ErrorDescriptor errorDescriptor) {
        this.errorDescriptor = errorDescriptor;
        return this;
    }

    public String getId() {
        return id;
    }

    public ExportDescriptor setId(String id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ExportDescriptor setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPages() {
        return pages;
    }

    public ExportDescriptor setPages(String pages) {
        this.pages = pages;
        return this;
    }

    public String getAttachmentsPrefix() {
        return attachmentsPrefix;
    }

    public ExportDescriptor setAttachmentsPrefix(String attachmentsPrefix) {
        this.attachmentsPrefix = attachmentsPrefix;
        return this;
    }

    public OutputResourceDescriptor getOutputResource() {
        return outputResource;
    }

    public ExportDescriptor setOutputResource(OutputResourceDescriptor outputResource) {
        this.outputResource = outputResource;
        return this;
    }

    @XmlElementWrapper(name = "attachments")
    @XmlElement(name = "attachment", type = AttachmentDescriptor.class)
    public List<AttachmentDescriptor> getAttachments() {
        return attachments;
    }

    public ExportDescriptor setAttachments(List<AttachmentDescriptor> attachments) {
        this.attachments = attachments;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportDescriptor that = (ExportDescriptor) o;

        if (attachments != null ? !attachments.equals(that.attachments) : that.attachments != null) return false;
        if (attachmentsPrefix != null ? !attachmentsPrefix.equals(that.attachmentsPrefix) : that.attachmentsPrefix != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (outputResource != null ? !outputResource.equals(that.outputResource) : that.outputResource != null)
            return false;
        if (pages != null ? !pages.equals(that.pages) : that.pages != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (pages != null ? pages.hashCode() : 0);
        result = 31 * result + (attachmentsPrefix != null ? attachmentsPrefix.hashCode() : 0);
        result = 31 * result + (outputResource != null ? outputResource.hashCode() : 0);
        result = 31 * result + (attachments != null ? attachments.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExportDescriptor{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", pages='" + pages + '\'' +
                ", attachmentsPrefix='" + attachmentsPrefix + '\'' +
                ", outputResource=" + outputResource +
                ", attachments=" + attachments +
                '}';
    }

}
