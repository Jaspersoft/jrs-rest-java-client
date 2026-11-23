package com.jaspersoft.jasperserver.jaxrs.client.dto.reports.reportinfo;

import jakarta.xml.bind.annotation.XmlElement;
import java.util.Objects;

/**
 * @author Tatyana Matveyeva
 */
public class Part {
    private Integer idx;
    private String name;

    public Part() {
    }

    public Part(Part other) {
        idx = other.idx;
        name = other.name;
    }

    @XmlElement
    public Integer getIdx() {
        return idx;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public Part setIdx(Integer idx) {
        this.idx = idx;
        return this;
    }

    public Part setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Objects.equals(idx, part.idx) &&
                Objects.equals(name, part.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idx, name);
    }

    @Override
    public String toString() {
        return "Part{" +
                "idx=" + idx +
                ", name='" + name + '\'' +
                '}';
    }
}
