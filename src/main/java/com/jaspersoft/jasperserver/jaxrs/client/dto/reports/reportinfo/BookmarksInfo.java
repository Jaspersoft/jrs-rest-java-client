package com.jaspersoft.jasperserver.jaxrs.client.dto.reports.reportinfo;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Tatyana Matveyeva
 */
public class BookmarksInfo {
    private String id;
    private String type;
    private List<Bookmark> bookmarks;

    public BookmarksInfo() {}

    public BookmarksInfo(BookmarksInfo other) {
        this.id = other.id;
        this.type = other.type;
        this.bookmarks = new ArrayList<>(bookmarks);
    }

    @XmlElement
    public String getId() {
        return id;
    }

    @XmlElement
    public String getType() {
        return type;
    }

    @XmlElementWrapper(name = "bookmarks")
    @XmlElement(name = "bookmark")
    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public BookmarksInfo setId(String id) {
        this.id = id;
        return this;
    }

    public BookmarksInfo setType(String type) {
        this.type = type;
        return this;
    }

    public BookmarksInfo setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookmarksInfo that = (BookmarksInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(bookmarks, that.bookmarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, bookmarks);
    }

    @Override
    public String toString() {
        return "BookmarksInfo{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", bookmarks=" + bookmarks +
                '}';
    }
}
