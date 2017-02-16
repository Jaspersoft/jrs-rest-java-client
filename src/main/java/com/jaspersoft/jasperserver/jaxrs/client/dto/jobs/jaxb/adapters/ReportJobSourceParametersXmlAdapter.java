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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.ReportParametersMapWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.ValuesCollection;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportJobSourceParametersXmlAdapter.java 28947 2013-02-26 15:02:08Z vsabadosh $
 * @deprecated (use server adapter).
 */
public class ReportJobSourceParametersXmlAdapter extends XmlAdapter<ReportParametersMapWrapper, Map<String, Object>> {

    public static final String REPORT_TIME_ZONE = "REPORT_TIME_ZONE";

    @Override
    public ReportParametersMapWrapper marshal(Map<String, Object> v) throws Exception {
        HashMap<String, Object> result = null;
        if (v != null && !v.isEmpty()) {
            result = new HashMap<String, Object>();
            for (String currentKey : v.keySet()) {
                Object currentValue = v.get(currentKey);
                if (REPORT_TIME_ZONE.equals(currentKey) && currentValue instanceof TimeZone) {
                    currentValue = ((TimeZone) currentValue).getID();
                }/*else if(currentValue instanceof Collection){
                    final ValuesCollection collectionWrapper = new ValuesCollection();
                    collectionWrapper.setCollection((Collection<Object>) currentValue);
                    currentValue = collectionWrapper;
                }*/
                result.put(currentKey, currentValue);
            }
        }
        return result != null ? new ReportParametersMapWrapper(result) : null;
    }

    @Override
    public Map<String, Object> unmarshal(ReportParametersMapWrapper v) throws Exception {
        Map<String, Object> result = null;
        if (v != null) {
            final HashMap<String, Object> parameterValues = v.getParameterValues();
            if (parameterValues != null && !parameterValues.isEmpty()) {
                result = new HashMap<String, Object>();
                for (String currentKey : parameterValues.keySet()) {
                    Object currentValue = parameterValues.get(currentKey);
                    if (REPORT_TIME_ZONE.equals(currentKey) && currentValue instanceof String){
                        currentValue = TimeZone.getTimeZone((String) currentValue);
                    }else if(currentValue instanceof ValuesCollection){
                        currentValue = ((ValuesCollection)currentValue).getCollection();
                    } else if(currentValue instanceof XMLGregorianCalendar){
                        currentValue = ((XMLGregorianCalendar)currentValue).toGregorianCalendar().getTime();
                    }
                    result.put(currentKey, currentValue);
                }
            }
        }
        return result;
    }
}
