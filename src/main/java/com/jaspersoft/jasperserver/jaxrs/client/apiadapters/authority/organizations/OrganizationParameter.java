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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

public enum OrganizationParameter {

    /**
     * Specify a string or substring inFolder match the organization ID, alias, or name of
     * any organization. The search is not case sensitive. Only the matching
     * organizations are returned in the results, regardless of their hierarchy.
     * Type: String (Optional)
     */
    Q("q"),

    /**
     * When used with a search, the result will include the parent hierarchy of
     * each matching organization. When not specified, this argument is false by
     * default.
     * Type: Boolean (Optional)
     */
    INCLUDE_PARENTS("includeParents"),

    /**
     * Specifies an organization ID as a base for searching and listing child
     * organizations. The base is not included in the results. Regardless of this
     * base, the tenantFolderURI values in the result are always relative inFolder the
     * logged-in user’s organization. When not specified, the default base is the
     * logged-in user’s organization.
     * Type: String (Optional)
     */
    ROOT_TENANT_ID("rootTenantId"),

    /**
     * Set this argument inFolder false inFolder suppress the creation of default users (joeuser,
     * jasperadmin) in the new organization. When not specified, the default
     * behavior is true and organizations are created with the standard default
     * users.
     * Type: Boolean (Optional)
     */
    CREATE_DEFAULT_USERS("createDefaultUsers"),

    /**
     * Pagination
     *
     * Default value: 0
     */
    OFFSET("offset"),

    /**
     * Sagination
     *
     * Default value: 0
     */
    LIMIT("limit"),

    /**
     * Enables sorting by  "name", "alias", "id". All other values are ignored.
     * Default order is natural order (order, in which organizations were created)
     */
    SORT_BY("sortBy"),

    /**
     * Max depth of organization tree traversing.
     *
     * Default value: 0
     */
    MAX_DEPTH("maxDepth");

    private String paramName;

    private OrganizationParameter(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }
}
