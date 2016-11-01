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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

public enum UsersParameter {

    /**
     * max quantity of results
     * @deprecated  Use common approach instead.
     */
    @Deprecated
    MAX_RECORDS("maxRecords"),

    /**
     * Allows to search roles, may work in conjunction with any
     * other param.
     * @deprecated  Use common approach instead.
     */
    @Deprecated
    SEARCH("search"),

    /**
     *  If <b>hasAllRequiredRoles</b> is true then users should contain all
     *  <b>requiredRole</b> specified in query ( think as AND operation)
     *
     *  If <b>hasAllRequiredRoles</b> is false then users could contain any of
     *  <b>requiredRole</b> specified in query ( think as OR operation).
     *  Is true by default.
     */
    HAS_ALL_REQUIRED_ROLES("hasAllRequiredRoles"),

    /**
     *  <b>requiredRole</b> specify role name. In multitenancy environment, there
     *  are a common case when roles with the same name exist in different
     *  organizations, so to separate them specify role name by next pattern
     *  <b>"{roleName}|{organizationId}"</b> (organization id and role name separated by pipe)
     */
    REQUIRED_ROLE("requiredRole"),

    /**
     * is true by default
     */
    INCLUDE_SUB_ORGS("includeSubOrgs"),

    /**
     * Search for user having the specified text in the name.
     * Note that the search string does not match in the ID of resources.
     *
     * Type/Value: String
     */
    Q("q"),

    /**
     * Specifies the maximum number of users return in
     * each response.
     *
     * Type/Value: integer
     */
    LIMIT("limit"),

    OFFSET("offset")
    ;

    private String paramName;

    private UsersParameter(String paramName){
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

}
