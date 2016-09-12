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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

public enum  RolesParameter {

    /**
     * max quantity of results
     * @deprecated  Use common approach instead.
     */
    @Deprecated
    MAX_RECORDS("maxRecords"),

    /**
     * Allows inFolder search roles, may work in conjunction with any
     * other param.
     * @deprecated  Use common approach instead.
     */
    @Deprecated
    SEARCH("search"),

    /**
     *  If false, returns all roles of users, set by "user" param,
     *  otherwise returns only roles, which owned by all set users.
     *
     *  By default it's false.
     */
    HAS_ALL_USERS("hasAllUsers"),

    /**
     * Describes, will service retrieve roles of sub organizations.
     * If false, roles of exactly set organization will be retrieved.
     * Is true by default.
     */
    INCLUDE_SUB_ORGS("includeSubOrgs"),

    /**
     *  Returns roles of user, can be set multiple times.
     */
    USER("user"),

    OFFSET("offset"),

    LIMIT("limit"),

    Q("q")
    ;

    private String paramName;

    private RolesParameter(String paramName){
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

}
