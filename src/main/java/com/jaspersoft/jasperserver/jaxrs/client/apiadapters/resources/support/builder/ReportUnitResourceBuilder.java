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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReference;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.decorator.ReportUnitResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Krasnyanskiy
 */
public class ReportUnitResourceBuilder extends ReportUnitResourceOperationProcessorDecorator {
    public ReportUnitResourceBuilder(ClientReportUnit entity, SessionStorage sessionStorage) {
        super(entity, sessionStorage);
    }

    @Deprecated
    public ReportUnitResourceBuilder withJrxml(InputStream jrxml) {
        multipart.field("jrxml", jrxml, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    public ReportUnitResourceBuilder withJrxml(InputStream jrxml, ClientFile jrxmlDescriptor) {
        multipart.field("jrxml", jrxml, MediaType.APPLICATION_XML_TYPE);
        super.reportUnit.setJrxml(jrxmlDescriptor);
        return this;
    }

    @Deprecated
    public ReportUnitResourceBuilder withJrxml(String jrxml) {
        multipart.field("jrxml", jrxml, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    public ReportUnitResourceBuilder withJrxml(String jrxml, ClientFile jrxmlDescriptor) {
        multipart.field("jrxml", jrxml, MediaType.APPLICATION_XML_TYPE);
        super.reportUnit.setJrxml(jrxmlDescriptor);
        return this;
    }

    @Deprecated
    public ReportUnitResourceBuilder withFile(String fileName, String file) {
        multipart.field("files." + fileName, file, MediaType.TEXT_PLAIN_TYPE);
        return this;
    }

    @Deprecated
    public ReportUnitResourceBuilder withFiles(Map<String, InputStream> files) {
        for (Map.Entry<String, InputStream> entry : files.entrySet()) {
            String fileName = entry.getKey();
            InputStream fileContent = entry.getValue();
            this.multipart.field("files." + fileName, fileContent, MediaType.TEXT_PLAIN_TYPE);
        }
        return this;
    }

    @Deprecated
    public ReportUnitResourceBuilder withFile(String fileName, InputStream file) {
        multipart.field("files." + fileName, file, MediaType.TEXT_PLAIN_TYPE);
        return this;
    }

    public ReportUnitResourceBuilder withNewFile(String content, String fileName, ClientFile fileDescriptor) {
        multipart.field("files." + fileName, content, MediaType.TEXT_PLAIN_TYPE);
        Map<String, ClientReferenceableFile> files = super.reportUnit.getFiles();
        if (files != null) {
            super.reportUnit.getFiles().put(fileName, fileDescriptor);
        } else {
            super.reportUnit.setFiles(new HashMap<String, ClientReferenceableFile>());
            super.reportUnit.getFiles().put(fileName, fileDescriptor);
        }
        return this;
    }

    public ReportUnitResourceBuilder withNewFile(InputStream content, String fileName, ClientFile fileDescriptor) {
        multipart.field("files." + fileName, content, MediaType.TEXT_PLAIN_TYPE);
        Map<String, ClientReferenceableFile> files = super.reportUnit.getFiles();
        if (files != null) {
            super.reportUnit.getFiles().put(fileName, fileDescriptor);
        } else {
            super.reportUnit.setFiles(new HashMap<String, ClientReferenceableFile>());
            super.reportUnit.getFiles().put(fileName, fileDescriptor);
        }
        return this;
    }

    public ReportUnitResourceBuilder withNewFileReference(String fileName, ClientReference fileDescriptor) {
        Map<String, ClientReferenceableFile> files = super.reportUnit.getFiles();
        if (files != null) {
            super.reportUnit.getFiles().put(fileName, fileDescriptor);
        } else {
            super.reportUnit.setFiles(new HashMap<String, ClientReferenceableFile>());
            super.reportUnit.getFiles().put(fileName, fileDescriptor);
        }
        return this;
    }
}
