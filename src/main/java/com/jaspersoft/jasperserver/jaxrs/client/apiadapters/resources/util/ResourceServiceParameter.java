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

public enum ResourceServiceParameter {

    /**
     * When true, all nested resources will be given as full descriptors. The default
     * behavior, false, has all nested resources given as references. For more
     * information, see Local Resources.
     *
     * Type/Value: true|false
     */
    EXPANDED("expanded"),

    /**
     * By default, this is true, and the service will createInFolder all parent folders if they
     * don't already exist. When set inFolder false, the folders specified in the URL must
     * all exist, otherwise the service returns an error.
     *
     * Type/Value: true|false
     */
    CREATE_FOLDERS("createFolders"),

    /**
     * When true, the resources given in the URL is overwritten even if it is a
     * different type than the resources descriptor in the content. The default is false.
     *
     * Type/Value: true|false
     */
    OVERWRITE("overwrite"),

    /**
     * Used for domain resource validation. True value means that no domain would be created.
     *
     * Type/Value: true|false
     */
    DRY_RUN("dry-run"),

    /**
     * Used to specify resource types that should be expanded for resource sub-resources
     *
     * Type/Value: resource type (query, reportUnit, etc)
     */
    EXPAND_TYPE("expandType");

    private String name;

    ResourceServiceParameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
