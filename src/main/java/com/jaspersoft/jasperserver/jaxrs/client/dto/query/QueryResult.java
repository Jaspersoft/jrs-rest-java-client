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

package com.jaspersoft.jasperserver.jaxrs.client.dto.query;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "queryResult")
@XmlType(propOrder = {"names", "rows"})
public class QueryResult {

    private List<String> names;
    private List<QueryResultRow> rows;

    public QueryResult() {

    }

    public QueryResult(List<String> names, QueryResultRow... rows) {
        this.names = names;
        this.rows = Arrays.asList(rows);
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    @XmlElement(name = "name")
    @XmlElementWrapper(name = "names")
    public List<String> getNames() {
        return names;
    }

    @XmlElement(name = "row")
    @XmlElementWrapper(name = "values")
    public List<QueryResultRow> getRows() {
        return rows;
    }

    public void setRows(List<QueryResultRow> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "names=" + names +
                ", rows=" + rows +
                '}';
    }
}