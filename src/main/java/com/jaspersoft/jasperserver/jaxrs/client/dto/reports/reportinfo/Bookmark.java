package com.jaspersoft.jasperserver.jaxrs.client.dto.reports.reportinfo;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Tatyana Matveyeva
 */
public class Bookmark {
    private String label;
    private Integer pageIndex;
    private String elementAddress;
    private List<Bookmark> bookmarks;

    public Bookmark() {
    }

    public Bookmark(Bookmark other) {
        label = other.label;
        pageIndex = other.pageIndex;
        elementAddress = other.elementAddress;
        bookmarks = new ArrayList<>(other.bookmarks);
    }

    @XmlElement
    public String getLabel() {
        return label;
    }

    @XmlElement
    public Integer getPageIndex() {
        return pageIndex;
    }

    @XmlElement
    public String getElementAddress() {
        return elementAddress;
    }

    @XmlElementWrapper(name = "bookmarks")
    @XmlElement(name = "bookmark")
    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public Bookmark setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
        return this;
    }

    public Bookmark setLabel(String label) {
        this.label = label;
        return this;
    }

    public Bookmark setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public Bookmark setElementAddress(String elementAddress) {
        this.elementAddress = elementAddress;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return Objects.equals(label, bookmark.label) &&
                Objects.equals(pageIndex, bookmark.pageIndex) &&
                Objects.equals(elementAddress, bookmark.elementAddress) &&
                Objects.equals(bookmarks, bookmark.bookmarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, pageIndex, elementAddress, bookmarks);
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "label='" + label + '\'' +
                ", pageIndex=" + pageIndex +
                ", elementAddress='" + elementAddress + '\'' +
                ", bookmarks=" + bookmarks +
                '}';
    }
}
