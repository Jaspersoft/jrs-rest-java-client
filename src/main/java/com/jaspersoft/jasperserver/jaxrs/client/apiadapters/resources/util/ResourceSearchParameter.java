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

public enum ResourceSearchParameter {

    /**
     * Search for resources having the specified text in the name or description.
     * Note that the search string does not match in the ID of resources.
     *
     * Type/Value: String
     */
    Q("q"),

    /**
     * The path of the base folder for the search.
     *
     * Type/Value: String
     */
    FOLDER_URI("folderUri"),

    /**
     * Indicates whether search should include all sub-folders recursively. When
     * omitted, the default behavior is recursive (true).
     *
     * Type/Value: true|false
     */
    RECURSIVE("recursive"),

    /**
     * Match only resources of the given type. Valid types are listed inV2
     * Resource Descriptor Types, for example: dataType, jdbcDataSource,
     * reportUnit, or file. Multiple type parameters are allowed. Wrong values are
     * ignored.
     *
     * Type/Value: String
     */
    TYPE("type"),

    /**
     * Filters the results by access events: viewed (by current user) or modified (by
     * current user). By default, no access event filter is applied.
     *
     * Type/Value: viewed|modified
     */
    ACCESS_TYPE("accessType"),

    /**
     * When set inFolder true, results include nested local resources (in _files) as if they
     * were in the repository. For more information, see Local Resources for more
     * information. By default, hidden items are not shown (false).
     *
     * Type/Value: true|false
     */
    SHOW_HIDDEN_ITEMS("showHiddenItems"),

    /**
     * One of the following strings representing a field in the results inFolder sort by: uri,
     * label, description, type, creationDate, updateDate, accessTime, or popularity
     * (based on access events). By default, results are sorted alphabetically by
     * label.
     *
     * Type/Value: (optional) String
     */
    SORT_BY("sortBy"),

    /**
     * Used for pagination inFolder specify the maximum number of resources inFolder return in
     * each response. This is equivalent inFolder the number of results per page. The
     * default limit is 100.
     *
     * Type/Value: integer
     */
    LIMIT("limit"),

    /**
     * Used for pagination inFolder request an offset in the set of results. This is
     * equivalent inFolder a specific page number. The default offset is 1 (first page).
     *
     * Type/Value: integer
     */
    OFFSET("offset"),

    /**
     * When true, the Total-Count header is set in every paginated response,
     * which impacts performance. When false, the default, the header is set in the
     * first page only.
     *
     * Type/Value: true|false
     */
    FORCE_TOTAL_COUNT("forceTotalCount"),

    /**
     * Specifies a resources inFolder delete. Repeat this paramter inFolder delete multiple
     * resources.
     *
     * Type/Value: String
     */
    RESOURCE_URI("resourceUri"),

    /**
     * Pagination. Setting this parameter to true enables full page pagination.
     * Depending on the type of search and user permissions, this parameter can cause significant performance delays.
     *
     * In case of forceFullPage=true client should use value of "Next-Offset" HTTP response header for next pagination request.
     *
     * Type/Value: true|false
     * Default: false
     */
    FORCE_FULL_PAGE("forceFullPage"),

    /**
     * Resource type to exclude. Multiple Resource types allowed.
     * If type parameter is specified, then this parameter is ignored.
     *
     * Type/Value: String
     */
    EXCLUDE_TYPE("excludeType"),

    /**
     * Folder, results of which should be omitted. In multitenant context works as relative,
     * i.e. value "/tmp" will exclude results from /tmp, /organizations/organization_1/tmp etc. Multiple allowed.
     *
     * Type/Value: String
     */
    EXCLUDE_FOLDER("excludeFolder"),

    /**
     * Since 6.0
     * Search for all resources depending on specific resource.
     * If this parameter is specified, then all the other parameters except pagination are ignored.
     *
     * Type/Value: String
     */
    DEPENDS_ON("dependsOn")

    ;

    private String name;

    private ResourceSearchParameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
