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
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.decorator.ReportUnitResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * @author Alexander Krasnyanskiy
 */
public class ReportUnitResourceBuilder extends ReportUnitResourceOperationProcessorDecorator {

    public ReportUnitResourceBuilder(ClientReportUnit entity, SessionStorage sessionStorage) {
        super(entity, sessionStorage);
    }

    public ReportUnitResourceBuilder withJrxml(InputStream jrxml) {
        multipart.field("jrxml", jrxml, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    public ReportUnitResourceBuilder withJrxml(InputStream jrxml, ClientFile jrxmlRef) {
        multipart.field("jrxml", jrxml, MediaType.APPLICATION_XML_TYPE);
        super.reportUnit.setJrxml(jrxmlRef);
        return this;
    }

    public ReportUnitResourceBuilder withJrxml(String jrxml) {
        multipart.field("jrxml", jrxml, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    public ReportUnitResourceBuilder withJrxml(String jrxml, ClientFile jrxmlRef) {
        multipart.field("jrxml", jrxml, MediaType.APPLICATION_XML_TYPE);
        super.reportUnit.setJrxml(jrxmlRef);
        return this;
    }

    public ReportUnitResourceBuilder withFile(InputStream file) {
        //multipart.field("jrxml", file, MediaType.WILDCARD_TYPE);
        return this;
    }

    public ReportUnitResourceBuilder withFile(String file) {
        //multipart.field("file", file, MediaType.WILDCARD_TYPE);
        //super.reportUnit.setFiles();
        return this;
    }

    public ReportUnitResourceBuilder withFile(String file, ClientFile fileRef) {
//        multipart.field("file", file, MediaType.WILDCARD_TYPE);
//        Map<String, ClientReferenceableFile> files = super.reportUnit.getFiles();
//        if (files != null) {
//            files.put();
//        }
        return this;
    }
}
