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

package com.jaspersoft.jasperserver.jaxrs.client.dto.importexport;

import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author: Zakhar.Tomchenco
 */
@XmlRootElement(name = "state")
public class StateDto {

    private String id;
    private String message;
    private String phase;
    private ErrorDescriptor errorDescriptor;


    public StateDto() {
        super();
    }

    @XmlElement(name = "id")
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "phase")
    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    @XmlElement(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public void setErrorDescriptor(ErrorDescriptor errorDescriptor) {
        this.errorDescriptor = errorDescriptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateDto)) return false;

        StateDto stateDto = (StateDto) o;

        if (errorDescriptor != null ? !errorDescriptor.equals(stateDto.errorDescriptor) : stateDto.errorDescriptor != null)
            return false;
        if (id != null ? !id.equals(stateDto.id) : stateDto.id != null) return false;
        if (message != null ? !message.equals(stateDto.message) : stateDto.message != null) return false;
        if (phase != null ? !phase.equals(stateDto.phase) : stateDto.phase != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (phase != null ? phase.hashCode() : 0);
        result = 31 * result + (errorDescriptor != null ? errorDescriptor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StateDto{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", phase='" + phase + '\'' +
                ", errorDescriptor=" + errorDescriptor +
                '}';
    }
}
