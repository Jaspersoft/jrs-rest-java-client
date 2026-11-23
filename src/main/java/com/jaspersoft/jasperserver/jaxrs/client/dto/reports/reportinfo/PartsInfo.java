package com.jaspersoft.jasperserver.jaxrs.client.dto.reports.reportinfo;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Tatyana Matveyeva
 */
public class PartsInfo {
    private String id;
    private String type;
    private List<Part> parts;

    public PartsInfo() {}

    public PartsInfo(PartsInfo other) {
        this.id = other.id;
        this.type = other.type;
        this.parts = new ArrayList<>(other.parts);
    }

    @XmlElement
    public String getId() {
        return id;
    }

    @XmlElement
    public String getType() {
        return type;
    }

    @XmlElementWrapper(name = "parts")
    @XmlElement(name = "part")
    public List<Part> getParts() {
        return parts;
    }

    public PartsInfo setId(String id) {
        this.id = id;
        return this;
    }

    public PartsInfo setType(String type) {
        this.type = type;
        return this;
    }

    public PartsInfo setParts(List<Part> parts) {
        this.parts = parts;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartsInfo partsInfo = (PartsInfo) o;
        return Objects.equals(id, partsInfo.id) &&
                Objects.equals(type, partsInfo.type) &&
                Objects.equals(parts, partsInfo.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, parts);
    }

    @Override
    public String toString() {
        return "PartsInfo{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", parts=" + parts +
                '}';
    }
}
