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

public final class ContextMediaTypes {

    public static final String FTP_TYPE = "application/connections.ftp";
    public static final String FTP_JSON = "application/connections.ftp+json";
    public static final String FTP_XML = "application/connections.ftp+xml";

    public static final String LOCAL_FILE_SYSTEM_TYPE = "application/connections.lfs";
    public static final String LOCAL_FILE_SYSTEM_JSON = "application/connections.lfs+json";
    public static final String LOCAL_FILE_SYSTEM_XML = "application/connections.lfs+xml";

    public static final String CUSTOM_DATA_SOURCE_TYPE = "application/repository.customDataSource";
    public static final String CUSTOM_DATA_SOURCE_JSON = "application/repository.customDataSource+json";
    public static final String CUSTOM_DATA_SOURCE_XML = "application/repository.customDataSource+xml";

    public static final String CUSTOM_DATA_SOURCE_METADATA_TYPE = "application/table.metadata";
    public static final String CUSTOM_DATA_SOURCE_METADATA_JSON = "application/table.metadata+json";
    public static final String CUSTOM_DATA_SOURCE_METADATA_XML = "application/table.metadata+xml";

    public static final String JNDI_JDBC_DATA_SOURCE_TYPE = "application/repository.jndiJdbcDataSource";
    public static final String JNDI_JDBC_DATA_SOURCE_JSON = "application/repository.jndiJdbcDataSource+json";
    public static final String JNDI_JDBC_DATA_SOURCE_XML = "application/repository.jndiJdbcDataSource+xml";

    public static final String JDBC_DATA_SOURCE_TYPE = "application/repository.jdbcDataSource";
    public static final String JDBC_DATA_SOURCE_JSON = "application/repository.jdbcDataSource+json";
    public static final String JDBC_DATA_SOURCE_XML = "application/repository.jdbcDataSource+xml";

    public static final String JDBC_DATA_SOURCE_METADATA_TYPE = "application/repository.customDataSource.metadata";
    public static final String JDBC_DATA_SOURCE_METADATA_JSON = "application/repository.customDataSource.metadata+json";
    public static final String JDBC_DATA_SOURCE_METADATA_XML = "application/repository.customDataSource.metadata+xml";

    public static final String DOMAIN_DATA_SOURCE_TYPE = "application/repository.semanticLayerDataSource";
    public static final String DOMAIN_DATA_SOURCE_JSON = "application/repository.semanticLayerDataSource+json";
    public static final String DOMAIN_DATA_SOURCE_XML = "application/repository.semanticLayerDataSource+xml";

    public static final String RESOURCE_LOOKUP_TYPE = "application/repository.resourceLookup";
    public static final String RESOURCE_LOOKUP_JSON = "application/repository.resourceLookup+json";
    public static final String RESOURCE_LOOKUP_XML = "application/repository.resourceLookup+xml";

    public static final String RESOURCE_LOOKUP_METADATA_TYPE = "application/repository.resourceLookup";
    public static final String RESOURCE_LOOKUP_METADATA_JSON = "application/repository.resourceLookup+json";
    public static final String RESOURCE_LOOKUP_METADATA_XML = "application/repository.resourceLookup+xml";

    public static final String DOMAIN_TYPE = "application/repository.domain";
    public static final String DOMAIN_JSON = "application/repository.domain+json";
    public static final String DOMAIN_XML = "application/repository.domain+xml";

    public static final String DOMAIN_METADATA_TYPE = "application/repository.domain.metadata";
    public static final String DOMAIN_METADATA_JSON = "application/repository.domain.metadata+json";
    public static final String DOMAIN_METADATA_XML = "application/repository.domain.metadata+xml";

    public static final String DOMAIN_DATA_SOURCE_METADATA_TYPE = "application/repository.semanticLayerDataSource.metadata";
    public static final String DOMAIN_DATA_SOURCE_METADATA_JSON = "application/repository.semanticLayerDataSource.metadata+json";
    public static final String DOMAIN_DATA_SOURCE_METADATA_XML = "application/repository.semanticLayerDataSource.metadata+xml";

    public static final String REPORT_UNIT_TYPE = "application/repository.reportUnit";
    public static final String REPORT_UNIT_JSON = "application/repository.reportUnit+json";
    public static final String REPORT_UNIT_XML = "application/repository.reportUnit+xml";

    public static final String REPORT_UNIT_METADATA_TYPE = "application/repository.reportUnit.metadata";
    public static final String REPORT_UNIT_METADATA_JSON = "application/repository.reportUnit.metadata+json";
    public static final String REPORT_UNIT_METADATA_XML = "application/repository.reportUnit.metadata+xml";

    public static final String TXT_FILE_TYPE = "application/connections.txtFile";
    public static final String TXT_FILE_JSON = "application/connections.txtFile+json";
    public static final String TXT_FILE_XML = "application/connections.txtFile+xml";

    public static final String XLS_FILE_TYPE = "application/connections.xlsFile";
    public static final String XLS_FILE_JSON = "application/connections.xlsFile+json";
    public static final String XLS_FILE_XML = "application/connections.xlsFile+xml";

    public static final String DOM_EL_CONTEXT_TYPE= "application/contexts.domElExpressionContext";
    public static final String DOM_EL_CONTEXT_JSON= "application/contexts.domElExpressionContext+json";

    public static final String DOM_EL_COLLECTION_CONTEXT_TYPE= "application/contexts.domElExpressionCollectionContext";
    public static final String DOM_EL_COLLECTION_CONTEXT_JSON= "application/contexts.domElExpressionCollectionContext+json";

    public static final String SQL_EXECUTION_TYPE= "application/contexts.sqlExecutionRequest";
    public static final String SQL_EXECUTION_JSON= "application/contexts.sqlExecutionRequest+json";

    public static final String DATASET_METADATA_TYPE= "application/dataset.metadata";
    public static final String DATASET_METADATA_JSON= "application/dataset.metadata+json";

    private ContextMediaTypes(){}
}
