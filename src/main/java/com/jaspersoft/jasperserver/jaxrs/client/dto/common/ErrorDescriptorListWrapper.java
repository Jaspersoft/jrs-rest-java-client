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

package com.jaspersoft.jasperserver.jaxrs.client.dto.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@XmlRootElement(name = "errorDescriptors")
public class ErrorDescriptorListWrapper {

    private List<ErrorDescriptor> errorDescriptors;

    public ErrorDescriptorListWrapper() {
        errorDescriptors = new ArrayList<ErrorDescriptor>();
    }

    @XmlElement(name =  "errorDescriptor")
    public List<ErrorDescriptor> getErrorDescriptors() {
        return errorDescriptors;
    }

    public void setErrorDescriptors(List<ErrorDescriptor> errorDescriptors) {
        this.errorDescriptors = errorDescriptors;
    }

    public void add(ErrorDescriptor error) {
        errorDescriptors.add(error);
    }

    public void removeError(String code) {
        for (Iterator it = errorDescriptors.iterator(); it.hasNext();) {
            ErrorDescriptor error = (ErrorDescriptor) it.next();
            if (matches(error, code)) {
                it.remove();
            }
        }
    }

    protected boolean matches(ErrorDescriptor error, String code) {
        return code.equals(error.getErrorCode());
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(errorDescriptors.size());
        sb.append(" error(s)\n");
        for (Iterator it = errorDescriptors.iterator(); it.hasNext();) {
            ErrorDescriptor error = (ErrorDescriptor) it.next();
            sb.append(error.toString());
            sb.append('\n');
        }
        return sb.toString();
    }

}
