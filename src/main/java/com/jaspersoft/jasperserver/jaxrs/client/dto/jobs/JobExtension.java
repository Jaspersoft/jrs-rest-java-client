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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "job")
public class JobExtension extends Job {

    private String outputTimeZone;

    public String getOutputTimeZone() {
        return outputTimeZone;
    }

    public void setOutputTimeZone(String outputTimeZone) {
        this.outputTimeZone = outputTimeZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobExtension)) return false;
        if (!super.equals(o)) return false;

        JobExtension that = (JobExtension) o;

        if (outputTimeZone != null ? !outputTimeZone.equals(that.outputTimeZone) : that.outputTimeZone != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (outputTimeZone != null ? outputTimeZone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobExtension{" +
                "outputTimeZone='" + outputTimeZone + '\'' +
                "} " + super.toString();
    }
}
