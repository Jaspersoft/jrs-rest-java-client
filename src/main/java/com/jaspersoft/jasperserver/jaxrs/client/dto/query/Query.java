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

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "query")
public class Query {

    protected List<QueryField> queryFields;

    public String getQueryFilterString() {
        return queryFilterString;
    }

    public Query setQueryFilterString(String queryFilterString) {
        this.queryFilterString = queryFilterString;
        return this;
    }

    protected String queryFilterString;

    @XmlElementWrapper(name = "queryFields")
    @XmlElement(name = "queryField")
    public List<QueryField> getQueryFields() {
        return queryFields;
    }

    public Query setQueryFields(List<QueryField> queryFields) {
        this.queryFields = queryFields;
        return this;
    }

    @Override
    public String toString() {
        return "Query{" +
                "queryFields=" + queryFields +
                ", queryFilterString='" + queryFilterString + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Query)) return false;

        Query query = (Query) o;

        if (!getQueryFields().equals(query.getQueryFields())) return false;
        return getQueryFilterString().equals(query.getQueryFilterString());

    }

    @Override
    public int hashCode() {
        int result = getQueryFields().hashCode();
        result = 31 * result + getQueryFilterString().hashCode();
        return result;
    }
}
