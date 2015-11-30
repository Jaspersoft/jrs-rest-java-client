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

package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;

import java.util.List;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ResourceAlreadyExistsException.java 24125 2012-06-11 16:53:46Z inesterenko $
 */
public class ResourceAlreadyExistsException extends JSClientWebException {

    public static final String RESOURCE_ALREADY_EXISTS = "resources.already.exists";

    public ResourceAlreadyExistsException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ResourceAlreadyExistsException(String message, List<ErrorDescriptor> errorDescriptors) {
        super(message, errorDescriptors);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
