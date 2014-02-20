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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers;

import java.util.ArrayList;
import java.util.List;

public class StringListWrapper {

    protected List<String> strings;

    protected StringListWrapper(){}

    protected StringListWrapper(List<String> strings){
        this.strings = new ArrayList<String>(strings.size());
        for (String r : strings){
            this.strings.add(r);
        }
    }

    protected List<String> getStrings() {
        return strings;
    }

    protected void setStrings(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringListWrapper that = (StringListWrapper) o;

        if (strings != null ? !strings.equals(that.strings) : that.strings != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return strings != null ? strings.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StringListWrapper{" +
                "strings=" + strings +
                '}';
    }
}
