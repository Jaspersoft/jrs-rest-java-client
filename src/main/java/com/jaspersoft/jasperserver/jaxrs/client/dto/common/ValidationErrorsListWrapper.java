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

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ValidationErrorsListWrapper.java 21908 2012-01-20 13:56:21Z ykovalchyk $
 */
@XmlRootElement(name = "errors")
public class ValidationErrorsListWrapper {
	
    private List<ValidationError> errors;

	public ValidationErrorsListWrapper() {
		errors = new ArrayList<ValidationError>();
	}

    @XmlElement(name =  "error")
	public List<ValidationError> getErrors() {
		return errors;
	}

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

	public void add(ValidationError error) {
		errors.add(error);
	}
	
	public void removeError(String code, String field) {
		for (Iterator it = errors.iterator(); it.hasNext();) {
			ValidationError error = (ValidationError) it.next();
			if (matches(error, code, field)) {
				it.remove();
			}
		}
	}

	protected boolean matches(ValidationError error, String code, String field) {
		return code.equals(error.getErrorCode())
				&& field.equals(error.getField());
	}

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(errors.size());
        sb.append(" error(s)\n");
        for (Iterator it = errors.iterator(); it.hasNext();) {
            ValidationError error = (ValidationError) it.next();
            sb.append(error.toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}
