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

import java.util.LinkedList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: JobIdListWrapper.java 38348 2013-09-30 04:57:18Z carbiv $
 * @deprecated (use server DTO).
 */
@XmlRootElement(name = "jobIdList")
public class JobIdListWrapper {

    private List<Long> ids;

    public JobIdListWrapper(){
    }

    public JobIdListWrapper(List<Long> ids){
        this.ids = ids;
    }

    public JobIdListWrapper(JobIdListWrapper other){
        this.ids = new LinkedList<Long>();
        for (Long id : other.ids) {
            this.ids.add(id);
        }
    }

    @XmlElement(name = "jobId")
    public List<Long> getIds() {
        return ids;
    }

    public JobIdListWrapper setIds(List<Long> ids) {
        this.ids = ids;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobIdListWrapper that = (JobIdListWrapper) o;

        if (ids != null ? !ids.equals(that.ids) : that.ids != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ids != null ? ids.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "JobIdListWrapper{" +
                "ids=" + ids +
                '}';
    }
}
