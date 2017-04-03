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

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: NoTimezoneDateToStringXmlAdapter.java 38348 2013-09-30 04:57:18Z carbiv $
 * @deprecated (use server adapter).
 */
public class NoTimezoneDateToStringXmlAdapter extends XmlAdapter<String, Date>{

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    @Override
    public Date unmarshal(String v) throws Exception {
        return new SimpleDateFormat(DATE_TIME_PATTERN).parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return new SimpleDateFormat(DATE_TIME_PATTERN).format(v);
    }
}
