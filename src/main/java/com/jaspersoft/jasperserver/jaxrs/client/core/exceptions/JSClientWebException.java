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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: JSClientWebException.java 30161 2013-03-22 19:20:15Z inesterenko $
 */
public class JSClientWebException extends JSClientException {

    private List<ErrorDescriptor> errorDescriptors;

    private void init(){
        errorDescriptors = new ArrayList<ErrorDescriptor>();
    }

    public JSClientWebException() {
        super();
        init();
    }

    public JSClientWebException(String message) {
        super(message);
        init();
    }

    public JSClientWebException(String message, List<ErrorDescriptor> errorDescriptors) {
        super(message);
        this.errorDescriptors = errorDescriptors;
    }

    public List<ErrorDescriptor> getErrorDescriptors() {
        return errorDescriptors;
    }

    public void setErrorDescriptors(List<ErrorDescriptor> errorDescriptors) {
        this.errorDescriptors = errorDescriptors;
    }

    public void addErrorDescriptor(ErrorDescriptor errorDescriptor){
        errorDescriptors.add(errorDescriptor);
    }
}
