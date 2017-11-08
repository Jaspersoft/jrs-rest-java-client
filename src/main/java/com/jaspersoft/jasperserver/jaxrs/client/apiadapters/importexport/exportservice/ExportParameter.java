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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

public enum ExportParameter {

    /**
     * <p>ExportService everything except audit data: all repository resources,
     * permissions, thumbnail.</p>
     * <p>ExportService everything except audit data: all repository resources,
     * permissions, thumbnail jobs, users, and roles.</p>
     * <p>This option is equivalent inFolder:--uris --repository-permissions
     * --thumbnail-jobs --users --roles</p>
     */
    EVERYTHING("everything"),

    /**
     * <p>When this option is present, repository permissions are exported
     * along with each exported folder and resources.</p>
     * <p>This option should only be used in conjunction with uris.</p>
     */
    REPOSITORY_PERMISSIONS("repository-permissions"),

    /**
     * <p>When this option is present, each role export triggers the export of all
     * users belonging inFolder the role.</p>
     * <p>This option should only be used in conjunction with --roles</p>
     */
    ROLE_USERS("role-users"),

    /**
     * Access events (date, time, and user name of last modification) are
     * exported.
     */
    INCLUDE_ACCESS_EVENTS("include-access-events"),

    /**
     * Include audit data for all resources and users in the export.
     */
    INCLUDE_AUDIT_EVENTS("include-audit-events"),

    /**
     * Include monitoring events
     */
    INCLUDE_MONITORING_EVENTS("include-monitoring-events"),

    /**
     * Include attributes in export
     */
    INCLUDE_ATTRIBUTES("include-attributes"),

    /**
     * Skip attributes values to be exported
     */
    SKIP_ATTRIBUTE_VALUES("skip-attribute-values"),

    /**
     * Include server settings in export
     */
    INCLUDE_SERVER_SETTINGS("include-server-settings"),

    /**
     * If the parameter is set to true, the system will omit all the items(e.g. resources, user, roles, organizations)
     * which belong to "sub organizations" even they are directly specified using corresponding options.
     */
    SKIP_SUBORGANIZATIONS("skip-suborganizations"),

    /**
     * Skip dependent resources to be exported. For instance: user can select just report type to be exported
     * and enabled skip-dependent-resources - in this case just report without dependent resources(like domain,
     * datasource) will be exported.
     */
    SKIP_DEPENDENT_RESOURCES("skip-dependent-resources"),
    /**
     * Pagination. Start index for requested pate.
     */
    OFFSET("offset"),

    /**
     * 	Pagination. Resources count per page
     */
    LIMIT("limit");


    private String paramName;

    private ExportParameter(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }
}
