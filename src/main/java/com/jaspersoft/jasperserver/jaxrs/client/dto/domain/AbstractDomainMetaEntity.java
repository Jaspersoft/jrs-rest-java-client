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

package com.jaspersoft.jasperserver.jaxrs.client.dto.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Paul Lysak
 */
abstract public class AbstractDomainMetaEntity {

    private String id;
    private String label;
    private Map<String, String> properties;

    public String getId() {
        return id;
    }

    public AbstractDomainMetaEntity setId(String id) {
        this.id = id;
        return this;
    }

    @XmlElement(name = "label", required = false)
    public String getLabel() {
        return label;
    }

    public AbstractDomainMetaEntity setLabel(String label) {
        this.label = label;
        return this;
    }

    @XmlElementWrapper(name = "properties")
    public Map<String, String> getProperties() {
        return properties;
    }

    public AbstractDomainMetaEntity setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public AbstractDomainMetaEntity addProperty(String key, String value) {
        if(properties == null) {
           properties = new HashMap<String, String>();
        }
        properties.put(key, value);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDomainMetaEntity that = (AbstractDomainMetaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (properties != null ? !properties.equals(that.properties) : that.properties != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }
}
