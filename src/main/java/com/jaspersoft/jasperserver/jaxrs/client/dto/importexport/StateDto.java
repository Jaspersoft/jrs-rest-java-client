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

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;

import com.jaspersoft.jasperserver.dto.common.WarningDescriptor;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author: Zakhar.Tomchenco
 *
 * @deprecated should be replaced with com.jaspersoft.jasperserver.dto.importexport.ExportTask when it is fixed
 */
@XmlRootElement(name = "state")
public class StateDto {

    private String id;
    private String message;
    private String phase;
    private ErrorDescriptor errorDescriptor;
    private List<WarningDescriptor> warnings;


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

    @XmlElement(name = "error")
    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public void setErrorDescriptor(ErrorDescriptor errorDescriptor) {
        this.errorDescriptor = errorDescriptor;
    }

    @XmlElement(name = "warnings")
    public List<WarningDescriptor> getWarnings() {
        return this.warnings != null && this.warnings.size() > 0?this.warnings:null;
    }

    public void setWarnings(List<WarningDescriptor> warnings) {
        this.warnings = warnings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateDto)) return false;

        StateDto stateDto = (StateDto) o;

        if (getId() != null ? !getId().equals(stateDto.getId()) : stateDto.getId() != null) return false;
        if (getMessage() != null ? !getMessage().equals(stateDto.getMessage()) : stateDto.getMessage() != null)
            return false;
        if (getPhase() != null ? !getPhase().equals(stateDto.getPhase()) : stateDto.getPhase() != null) return false;
        if (getErrorDescriptor() != null ? !getErrorDescriptor().equals(stateDto.getErrorDescriptor()) : stateDto.getErrorDescriptor() != null)
            return false;
        return !(getWarnings() != null ? !getWarnings().equals(stateDto.getWarnings()) : stateDto.getWarnings() != null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        result = 31 * result + (getPhase() != null ? getPhase().hashCode() : 0);
        result = 31 * result + (getErrorDescriptor() != null ? getErrorDescriptor().hashCode() : 0);
        result = 31 * result + (getWarnings() != null ? getWarnings().hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        return "StateDto{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", phase='" + phase + '\'' +
                ", errorDescriptor=" + errorDescriptor +
                ", warnings=" + warnings +
                '}';
    }
}