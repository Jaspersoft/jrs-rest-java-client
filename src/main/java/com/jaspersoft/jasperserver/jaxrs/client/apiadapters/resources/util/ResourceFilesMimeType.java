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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util;

public enum  ResourceFilesMimeType {

    PDF("application/pdf"),
    HTML("text/html"),
    XLS("application/xls"),
    RTF("application/rtf"),
    CSV("text/csv"),
    ODS("application/vnd.oasis.opendocument.spreadsheet"),
    ODT("application/vnd.oasis.opendocument.text"),
    TXT("text/plain"),
    DOCX("application/vnd.openxmlformatsofficedocument.wordprocessingml.document"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    FONT("font/*"),
    IMG("image/*"),
    JRXML("application/jrxml"),
    JAR("application/zip"),
    PROP("application/properties"),
    JRTX("application/jrtx"),
    XML("application/xml"),
    CSS("text/css"),
    ACCES_GRANT_SCHEMA("application/accessGrantSchema"),
    OLAP_MONDRIAN_SCHEMA("application/olapMondrianSchema");

    private String type;

    private ResourceFilesMimeType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
