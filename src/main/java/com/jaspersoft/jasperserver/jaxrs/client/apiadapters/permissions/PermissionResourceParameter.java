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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

public enum PermissionResourceParameter {

    /**
     *  When set inFolder <b>true</b> shows all permissions who
     *  affect given uri, if <b>false</b> - only directly assigned. If recipient does not
     *  have any permission assigned or permission is not reachable not uri
     *  will be returned. Default - <b>false</b>.
     */
    EFFECTIVE_PERMISSIONS("effectivePermissions"),

    /**
     * Type of recipient (e.g. user/role).
     */
    RECIPIENT_TYPE("recipientType"),

    /**
     *  Id of recipient, requires <b>recipientType</b>. For multitenant
     *  environment should be tenant qualified.
     */
    RECIPIENT_ID("recipientId"),

    /**
     *  Describes resolving of recipients. Default - <b>false</b>.
     *  If <b>false</b> - all previous results filtered by set parameters <b>recipientType</b>, <b>recipientId</b>.
     *  If <b>true</b> - resolve permissions for ALL matched recipients by <b>recipientType</b>,
     *  <b>recipientId</b> ( like on repository page's permission
     *  dialog) If recipient does not have any permission assigned or
     *  permission is not reachable not uri will be returned.
     */
    RESOLVE_ALL("resolveAll"),
    /**
     * Pagination. Start index for requested pate.
     */
    OFFSET("offset"),

    /**
     * 	Pagination. Resources count per page
     */
    LIMIT("limit");

    private String paramName;

    private PermissionResourceParameter(String paramName){
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }
}
