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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.ExcludeDaysWrapper;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ExcludeDaysXmlAdapter.java 23844 2012-05-22 06:23:41Z ykovalchyk $
 * @deprecated (use server adapter).
 */
public class ExcludeDaysXmlAdapter extends XmlAdapter<ExcludeDaysWrapper, ArrayList<Calendar>> {
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ArrayList<Calendar> unmarshal(ExcludeDaysWrapper v) throws Exception {
        ArrayList<Calendar> result = null;
        if(v != null && v.getExcludeDays() != null && !v.getExcludeDays().isEmpty()){
            result = new ArrayList<Calendar>();
            for (String currentCalendarString : v.getExcludeDays()){
                final Date date = format.parse(currentCalendarString);
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTime(date);
                result.add(currentCalendar);
            }
        }
        return result;
    }

    @Override
    public ExcludeDaysWrapper marshal(ArrayList<Calendar> v) throws Exception {
        ExcludeDaysWrapper result = null;
        if(v != null && !v.isEmpty()){
            List<String> dayStrings = new ArrayList<String>();
            for (Calendar currentCalendar : v){
                dayStrings.add(format.format(currentCalendar.getTime()));
            }
            result = new ExcludeDaysWrapper(dayStrings);
        }
        return result;
    }
}
