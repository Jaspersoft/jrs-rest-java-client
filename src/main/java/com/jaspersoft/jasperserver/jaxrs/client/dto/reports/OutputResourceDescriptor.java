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

import com.jaspersoft.jasperserver.dto.common.DeepCloneable;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

@XmlRootElement(name = "outputResource")
public class OutputResourceDescriptor implements DeepCloneable{

    private String contentType;
    private String fileName;
    private Boolean outputFinal;
    private String pages;
    private byte[] data;

    public OutputResourceDescriptor() {
    }

    public OutputResourceDescriptor(OutputResourceDescriptor other) {
        this.contentType = other.contentType;
        this.fileName = other.fileName;
        this.outputFinal = other.outputFinal;
        this.pages = other.pages;
        if (other.data != null) {
            this.data = new byte[other.data.length];
            for (int i=0; i < other.data.length; i++) {
                this.data[i] = other.data[i];
            }
        }
    }

    public String getContentType() {
        return contentType;
    }

    public OutputResourceDescriptor setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public OutputResourceDescriptor setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Boolean getOutputFinal() {
        return outputFinal;
    }

    public OutputResourceDescriptor setOutputFinal(Boolean outputFinal) {
        this.outputFinal = outputFinal;
        return this;
    }

    public String getPages() {
        return pages;
    }

    public OutputResourceDescriptor setPages(String pages) {
        this.pages = pages;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public OutputResourceDescriptor setData(byte[] data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutputResourceDescriptor)) return false;

        OutputResourceDescriptor that = (OutputResourceDescriptor) o;

        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (outputFinal != null ? !outputFinal.equals(that.outputFinal) : that.outputFinal != null) return false;
        if (pages != null ? !pages.equals(that.pages) : that.pages != null) return false;
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = contentType != null ? contentType.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (outputFinal != null ? outputFinal.hashCode() : 0);
        result = 31 * result + (pages != null ? pages.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "OutputResourceDescriptor{" +
                "contentType='" + contentType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", outputFinal=" + outputFinal +
                ", pages='" + pages + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public OutputResourceDescriptor deepClone() {
        return new OutputResourceDescriptor(this);
    }
}
