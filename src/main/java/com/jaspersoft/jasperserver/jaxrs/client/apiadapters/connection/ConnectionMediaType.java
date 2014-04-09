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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connection;

public final class ConnectionMediaType {

    public static final String FTP_JSON = "application/connections.ftp+json";
    public static final String FTP_XML = "application/connections.ftp+xml";

    public static final String LOCAL_FILE_SYSTEM_JSON = "application/connections.lfs+json";
    public static final String LOCAL_FILE_SYSTEM_XML = "application/connections.lfs+xml";

    public static final String TXT_FILE_JSON = "application/connections.txtFile+json";
    public static final String TXT_FILE_XML = "application/connections.txtFile+xml";

    public static final String XLS_FILE_JSON = "application/connections.xlsFile+json";
    public static final String XLS_FILE_XML = "application/connections.xlsFile+xml";

    private ConnectionMediaType(){}
}
