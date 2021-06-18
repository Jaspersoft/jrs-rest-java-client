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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice;

public enum ImportParameter {
    /**
     *  Access events (date, time, and user name of last modification) are
     *  exported
     */
    INCLUDE_ACCESS_EVENTS("includeAccessEvents", "include-access-events"),

    /**
     *  Include audit data for all resources and users in the export
     */
    INCLUDE_AUDIT_EVENTS("includeAuditEvents", "include-audit-events"),

    /**
     *  Resources in the catalog replace those in the repository if their URIs
     *  and types match
     */
    UPDATE("update", "update"),

    /**
     *  When used with --update, users in the catalog are not imported or
     *  updated. Use this
     *  option inFolder import catalogs without overwriting currently defined user
     */
    SKIP_USER_UPDATE("skipUserUpdate", "skip-user-update"),

    /**
     *  Include monitoring events
     */
    INCLUDE_MONITORING_EVENTS("includeMonitoringEvents", "include-monitoring-events"),

    /**
     *  Include server settings
     */
    INCLUDE_SERVER_SETTINGS("includeServerSettings", "include-server-settings"),

    /**
     *  Allows merging of exported organization/resource into organization with different identifier.
     */
    MERGE_ORGANIZATION("mergeOrganization", "merge-organization"),

    /**
     * Skips custom (not default themes) to be exported
     */
    SKIP_THEMES("skipThemes", "skip-themes"),

    /**
     * Organization identifier we import into
     */
    ORGANIZATION("organization", "organization"),

    /**
     * Defines strategy with broken dependencies
     */
    BROKEN_DEPENDENCIES("brokenDependencies", "broken-dependencies"),
    /**
     * Pagination. Start index for requested pate.
     */
    OFFSET("offset", "offset"),

    /**
     * 	Pagination. Resources count per page
     */
    LIMIT("limit", "limit"),

    /**
     * Secret key bytes in hex
     */
    SECRET_KEY("secret-key", "secret-key"),

    /**
     * Secret File repository location
     */
    SECRET_URI("secretUri", "secret-uri");

    private String paramName;
    private String multiPartParamName;

    private ImportParameter(String paramName, String multiPartParamName){
        this.paramName = paramName;
        this.multiPartParamName = multiPartParamName;
    }

    public String getParamName() {
        return paramName;
    }

    public String getMultiPartParamName() {
        return multiPartParamName;
    }
}
