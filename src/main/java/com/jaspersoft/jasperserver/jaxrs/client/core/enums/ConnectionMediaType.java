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

package com.jaspersoft.jasperserver.jaxrs.client.core.enums;

public interface ConnectionMediaType {

    String FTP_TYPE = "application/connections.ftp";
    String FTP_JSON = "application/connections.ftp+json";
    String FTP_XML = "application/connections.ftp+xml";

    String LOCAL_FILE_SYSTEM_TYPE = "application/connections.lfs";
    String LOCAL_FILE_SYSTEM_JSON = "application/connections.lfs+json";
    String LOCAL_FILE_SYSTEM_XML = "application/connections.lfs+xml";

    String CUSTOM_DATA_SOURCE_TYPE = "application/repository.customDataSource";
    String CUSTOM_DATA_SOURCE_JSON = "application/repository.customDataSource+json";
    String CUSTOM_DATA_SOURCE_XML = "application/repository.customDataSource+xml";

    String CUSTOM_DATA_SOURCE_METADATA_TYPE = "application/table.metadata";
    String CUSTOM_DATA_SOURCE_METADATA_JSON = "application/table.metadata+json";
    String CUSTOM_DATA_SOURCE_METADATA_XML = "application/table.metadata+xml";

    String JNDI_JDBC_DATA_SOURCE_TYPE = "application/repository.jndiJdbcDataSource";
    String JNDI_JDBC_DATA_SOURCE_JSON = "application/repository.jndiJdbcDataSource+json";
    String JNDI_JDBC_DATA_SOURCE_XML = "application/repository.jndiJdbcDataSource+xml";

    String JDBC_DATA_SOURCE_TYPE = "application/repository.jdbcDataSource";
    String JDBC_DATA_SOURCE_JSON = "application/repository.jdbcDataSource+json";
    String JDBC_DATA_SOURCE_XML = "application/repository.jdbcDataSource+xml";

    String JDBC_DATA_SOURCE_METADATA_TYPE = "application/repository.customDataSource.metadata";
    String JDBC_DATA_SOURCE_METADATA_JSON = "application/repository.customDataSource.metadata+json";
    String JDBC_DATA_SOURCE_METADATA_XML = "application/repository.customDataSource.metadata+xml";

    String TXT_FILE_TYPE = "application/connections.txtFile";
    String TXT_FILE_JSON = "application/connections.txtFile+json";
    String TXT_FILE_XML = "application/connections.txtFile+xml";

    String XLS_FILE_TYPE = "application/connections.xlsFile";
    String XLS_FILE_JSON = "application/connections.xlsFile+json";
    String XLS_FILE_XML = "application/connections.xlsFile+xml";

}
