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

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.OutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.ReportJobOutputFormatsWrapper;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashSet;
import java.util.Set;

/**
 * This adapter is used for ReportJob.outputFormats serialization.
 * ReportJobOutputFormatsWrapper is used for serialization because of no ability inFolder use @XmlElementWrapper together with @XmlJavaTypeAdapter.
 * See http://java.net/jira/browse/JAXB-787
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: OutputFormatXmlAdapter.java 38764 2013-10-10 18:42:07Z carbiv $
 * @deprecated (use server adapter).
 */

public class OutputFormatXmlAdapter extends XmlAdapter<ReportJobOutputFormatsWrapper, Set<OutputFormat>> {

    @Override
    public Set<OutputFormat> unmarshal(ReportJobOutputFormatsWrapper v) throws Exception {
        Set<OutputFormat> result = null;
        if (v != null && v.getFormats() != null && !v.getFormats().isEmpty()) {
            result = new HashSet<OutputFormat>();
            for (String currentValue : v.getFormats()) {
                OutputFormat outputFormat = OutputFormat.valueOf(currentValue);
                if (outputFormat != null)
                    result.add(outputFormat);
            }
        }
        return result;
    }

    @Override
    public ReportJobOutputFormatsWrapper marshal(Set<OutputFormat> v) throws Exception {
        ReportJobOutputFormatsWrapper result = null;
        if (v != null && !v.isEmpty()) {
            Set<String> set = new HashSet<String>();
            for (OutputFormat currentValue : v) {
                String currentStringValue = currentValue.name();
                if (currentStringValue != null)
                    set.add(currentStringValue);
            }
            if(!set.isEmpty()){
                result = new ReportJobOutputFormatsWrapper();
                result.setFormats(set);
            }
        }
        return result;
    }

}
