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

package com.jaspersoft.jasperserver.jaxrs.client.dto.reports.inputcontrols;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlState;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author akasych
 * @version $Id$
 */
@XmlRootElement(name = "inputControlStateList")
public class InputControlStateListWrapper {

    private List<InputControlState> inputControlStateList;

    public InputControlStateListWrapper(){}
    public InputControlStateListWrapper(List<InputControlState> inputControlStateList){
        this.inputControlStateList = inputControlStateList;
    }
    @XmlElement(name = "inputControlState")
    public List<InputControlState> getInputControlStateList() {
        return inputControlStateList;
    }

    public void setInputControlStateList(List<InputControlState> inputControlStateList) {
        this.inputControlStateList = inputControlStateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InputControlStateListWrapper that = (InputControlStateListWrapper) o;

        if (inputControlStateList != null ? !inputControlStateList.equals(that.inputControlStateList) : that.inputControlStateList != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return inputControlStateList != null ? inputControlStateList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "InputControlStateListWrapper{" +
                "inputControlStateList=" + inputControlStateList +
                '}';
    }
}
