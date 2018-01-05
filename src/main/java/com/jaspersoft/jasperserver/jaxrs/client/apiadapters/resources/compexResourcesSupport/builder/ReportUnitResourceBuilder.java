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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReference;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.ReportUnitResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.MediaTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

/**
 * @author Alexander Krasnyanskiy
 */
public class ReportUnitResourceBuilder extends ReportUnitResourceOperationProcessorDecorator {
    public ReportUnitResourceBuilder(ClientReportUnit reportUnit, SessionStorage sessionStorage) {
        super(reportUnit, sessionStorage);
    }

@Deprecated
    public ReportUnitResourceBuilder withJrxml(InputStream jrxml, ClientFile jrxmlDescriptor) {
        multipart.field("jrxml", jrxml, MediaType.APPLICATION_XML_TYPE);
        super.reportUnit.setJrxml(jrxmlDescriptor);
        return this;
    }
    @Deprecated
    public ReportUnitResourceBuilder withJrxml(String jrxml, ClientFile jrxmlDescriptor) {
        multipart.field("jrxml", jrxml, MediaType.APPLICATION_XML_TYPE);
        super.reportUnit.setJrxml(jrxmlDescriptor);
        return this;
    }


    public ReportUnitResourceBuilder withJrxml(String jrxmlContent, String label, String description) {
        super.multipart.field("jrxml", jrxmlContent, new MediaType("application", "jrxml"));
        super.reportUnit.setJrxml(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.jrxml));
        return this;
    }

    public ReportUnitResourceBuilder withJrxml(InputStream jrxml, String label, String description) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("jrxml", jrxml, label, new MediaType("application", "jrxml"));
        super.multipart.bodyPart(streamDataBodyPart);
        super.reportUnit.setJrxml(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.jrxml));
        return this;
    }

    public ReportUnitResourceBuilder withJrxml(File jrxml, String label, String description) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("jrxml", jrxml, new MediaType("application", "jrxml"));
        super.multipart.bodyPart(fileDataBodyPart);
        super.reportUnit.setJrxml(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.jrxml));
        return this;
    }

    public ReportUnitResourceBuilder withJrxml(ClientReferenceableFile jrxml) {
        this.reportUnit.setJrxml(jrxml);
        return this;
    }

    @Deprecated
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
    @Deprecated
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
    @Deprecated
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

    public ReportUnitResourceBuilder withFile(InputStream fileData, String label, String description, ClientFile.FileType fileType) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("files." + label,
                fileData,
                label,
                MediaTypeUtil.stringToMediaType(fileType.getMimeType()));
        super.multipart.bodyPart(streamDataBodyPart);
        if (super.reportUnit.getFiles() == null) super.reportUnit.setFiles(new HashMap<String, ClientReferenceableFile>());
        super.reportUnit.getFiles().put(label, new ClientFile().setLabel(label).setDescription(description).setType(fileType));
        return this;
    }

    public ReportUnitResourceBuilder withFile(File fileData, String label, String description, ClientFile.FileType fileType) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("files." + label,
                fileData,
                MediaTypeUtil.stringToMediaType(fileType.getMimeType()));
        super.multipart.bodyPart(fileDataBodyPart);
        if (super.reportUnit.getFiles() == null) super.reportUnit.setFiles(new HashMap<String, ClientReferenceableFile>());
        super.reportUnit.getFiles().put(label, new ClientFile().setLabel(label).setDescription(description).setType(fileType));
        return this;
    }

    public ReportUnitResourceBuilder withFile(String fileData, String label, String description, ClientFile.FileType fileType) {
        super.multipart.field("file." + label,
                fileData,
                MediaTypeUtil.stringToMediaType(fileType.getMimeType()));
        if (super.reportUnit.getFiles() == null) super.reportUnit.setFiles(new HashMap<String, ClientReferenceableFile>());
        super.reportUnit.getFiles().put(label, new ClientFile().setLabel(label).setDescription(description).setType(fileType));
        return this;
    }


    public ReportUnitResourceBuilder withFile(ClientReferenceableFile fileUri, String name) {
        if (super.reportUnit.getFiles() == null) super.reportUnit.setFiles(new HashMap<String, ClientReferenceableFile>());
        this.reportUnit.getFiles().put(name, fileUri);
        return this;
    }

    public ReportUnitResourceBuilder withLabel(String label) {
        this.reportUnit.setLabel(label);
        return this;
    }

    public ReportUnitResourceBuilder withDataSource(String datasourceUri) {
        this.reportUnit.setDataSource(new ClientReference().setUri(datasourceUri));
        return this;
    }

    public ReportUnitResourceBuilder withDataSource(ClientReference datasource) {
        this.reportUnit.setDataSource(datasource);
        return this;
    }

    public ReportUnitResourceBuilder withDescription(String description) {
        this.reportUnit.setDescription(description);
        return this;
    }

}
