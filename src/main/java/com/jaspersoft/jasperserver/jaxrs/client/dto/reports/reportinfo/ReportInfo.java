package com.jaspersoft.jasperserver.jaxrs.client.dto.reports.reportinfo;

import jakarta.xml.bind.annotation.XmlAccessOrder;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * @author Tatyana Matveyeva
 */
@XmlRootElement
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class ReportInfo {
    private BookmarksInfo bookmarks;
    private PartsInfo parts;

    public ReportInfo() {
    }

    public ReportInfo(ReportInfo other) {
        this.bookmarks = other.bookmarks;
        this.parts = other.parts;
    }


    @XmlElement
    public BookmarksInfo getBookmarks() {
        return bookmarks;
    }


    @XmlElement
    public PartsInfo getParts() {
        return parts;
    }

    public ReportInfo setBookmarks(BookmarksInfo bookmarks) {
        this.bookmarks = bookmarks;
        return this;
    }

    public ReportInfo setParts(PartsInfo parts) {
        this.parts = parts;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportInfo that = (ReportInfo) o;
        return Objects.equals(bookmarks, that.bookmarks) &&
                Objects.equals(parts, that.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookmarks, parts);
    }

    @Override
    public String toString() {
        return "ReportInfo{" +
                "bookmarks=" + bookmarks +
                ", parts=" + parts +
                '}';
    }
}
