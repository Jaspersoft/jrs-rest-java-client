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

package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "export")
public class ExportExecutionOptions {

    private String outputFormat;
    private String attachmentsPrefix;
    private String pages;
    private String baseUrl;
    private String anchor;
    private boolean allowInlineScripts = true;
    private String markupType;
    private Boolean ignorePagination;

    public ExportExecutionOptions() {
    }

    public ExportExecutionOptions(ExportExecutionOptions other) {
        this.outputFormat = other.outputFormat;
        this.attachmentsPrefix = other.attachmentsPrefix;
        this.pages = other.pages;
        this.baseUrl = other.baseUrl;
        this.anchor = other.anchor;
        this.allowInlineScripts = other.allowInlineScripts;
        this.markupType = other.markupType;
        this.ignorePagination = other.ignorePagination;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public ExportExecutionOptions setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
        return this;
    }

    public String getAttachmentsPrefix() {
        return attachmentsPrefix;
    }

    public ExportExecutionOptions setAttachmentsPrefix(String attachmentsPrefix) {
        this.attachmentsPrefix = attachmentsPrefix;
        return this;
    }

    public String getPages() {
        return pages;
    }

    public ExportExecutionOptions setPages(String pages) {
        this.pages = pages;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public ExportExecutionOptions setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getAnchor() {
        return anchor;
    }

    public ExportExecutionOptions setAnchor(String anchor) {
        this.anchor = anchor;
        return this;
    }

    public boolean isAllowInlineScripts() {
        return allowInlineScripts;
    }

    public ExportExecutionOptions setAllowInlineScripts(boolean allowInlineScripts) {
        this.allowInlineScripts = allowInlineScripts;
        return this;
    }

    public String getMarkupType() {
        return markupType;
    }

    public ExportExecutionOptions setMarkupType(String markupType) {
        this.markupType = markupType;
        return this;
    }

    public Boolean getIgnorePagination() {
        return ignorePagination;
    }

    public ExportExecutionOptions setIgnorePagination(Boolean ignorePagination) {
        this.ignorePagination = ignorePagination;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExportExecutionOptions)) return false;

        ExportExecutionOptions that = (ExportExecutionOptions) o;

        if (allowInlineScripts != that.allowInlineScripts) return false;
        if (outputFormat != null ? !outputFormat.equals(that.outputFormat) : that.outputFormat != null) return false;
        if (attachmentsPrefix != null ? !attachmentsPrefix.equals(that.attachmentsPrefix) : that.attachmentsPrefix != null)
            return false;
        if (pages != null ? !pages.equals(that.pages) : that.pages != null) return false;
        if (baseUrl != null ? !baseUrl.equals(that.baseUrl) : that.baseUrl != null) return false;
        if (anchor != null ? !anchor.equals(that.anchor) : that.anchor != null) return false;
        if (markupType != null ? !markupType.equals(that.markupType) : that.markupType != null) return false;
        return ignorePagination != null ? ignorePagination.equals(that.ignorePagination) : that.ignorePagination == null;
    }

    @Override
    public int hashCode() {
        int result = outputFormat != null ? outputFormat.hashCode() : 0;
        result = 31 * result + (attachmentsPrefix != null ? attachmentsPrefix.hashCode() : 0);
        result = 31 * result + (pages != null ? pages.hashCode() : 0);
        result = 31 * result + (baseUrl != null ? baseUrl.hashCode() : 0);
        result = 31 * result + (anchor != null ? anchor.hashCode() : 0);
        result = 31 * result + (allowInlineScripts ? 1 : 0);
        result = 31 * result + (markupType != null ? markupType.hashCode() : 0);
        result = 31 * result + (ignorePagination != null ? ignorePagination.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExportExecutionOptions{" +
                "outputFormat='" + outputFormat + '\'' +
                ", attachmentsPrefix='" + attachmentsPrefix + '\'' +
                ", pages='" + pages + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", anchor='" + anchor + '\'' +
                ", allowInlineScripts=" + allowInlineScripts +
                ", markupType='" + markupType + '\'' +
                ", ignorePagination=" + ignorePagination +
                '}';
    }
}
