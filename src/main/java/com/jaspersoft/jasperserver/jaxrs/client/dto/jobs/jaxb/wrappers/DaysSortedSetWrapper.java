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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.SortedSet;

/**
 * This class is needed because of bug in JAXB.
 * @XmlElementWrapper doesn't support @XmlJavaTypeAdapter
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: DaysSortedSetWrapper.java 22756 2012-03-23 10:39:15Z sergey.prilukin $
 * @deprecated (use server adapter).
 */
@XmlRootElement
public class DaysSortedSetWrapper {

    private SortedSet<String> days;

    public DaysSortedSetWrapper(){}

    public DaysSortedSetWrapper(SortedSet<String> days){
        this.days = days;
    }
    @XmlElement(name = "day")
    public SortedSet<String> getDays() {
        return days;
    }

    public void setDays(SortedSet<String> days) {
        this.days = days;
    }

}
