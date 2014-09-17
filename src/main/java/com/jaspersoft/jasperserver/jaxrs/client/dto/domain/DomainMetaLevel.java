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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Lysak
 */
public class DomainMetaLevel extends AbstractDomainMetaEntity {

    private List<DomainMetaItem> items;
    private List<DomainMetaLevel> subLevels;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item", type = DomainMetaItem.class)
    public List<DomainMetaItem> getItems() {
        return items;
    }

    public DomainMetaLevel setItems(List<DomainMetaItem> items) {
        this.items = items;
        return this;
    }

    public DomainMetaLevel addItem(DomainMetaItem item) {
        if(items == null) {
            items = new ArrayList<DomainMetaItem>();
        }
        items.add(item);
        return this;
    }

    @XmlElementWrapper(name = "subLevels")
    @XmlElement(name = "subLevel", type = DomainMetaLevel.class)
    public List<DomainMetaLevel> getSubLevels() {
        return subLevels;
    }

    public DomainMetaLevel setSubLevels(List<DomainMetaLevel> subLevels) {
        this.subLevels = subLevels;
        return this;
    }

    public DomainMetaLevel addSubLevel(DomainMetaLevel subLevel) {
        if(subLevels == null) {
            subLevels = new ArrayList<DomainMetaLevel>();
        }
        subLevels.add(subLevel);
        return this;
    }

    @Override
    public String toString() {
        return "DomainMetaLevel{" +
                "id='" + getId() + '\'' +
                ", label='" + getLabel() + '\'' +
                ", items=" + items +
                ", subLevels=" + subLevels +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DomainMetaLevel that = (DomainMetaLevel) o;

        if (items != null ? !items.equals(that.items) : that.items != null) return false;
        if (subLevels != null ? !subLevels.equals(that.subLevels) : that.subLevels != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + (subLevels != null ? subLevels.hashCode() : 0);
        return result;
    }
}
