/*
* Copyright (C) 2005 - 2009 Jaspersoft Corporation. All rights  reserved.
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
package com.jaspersoft.jasperserver.jaxrs.client.dto.common;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Volodya Sabadosh (vsabadosh@jaspersoft.com)
 * @version $Id $
 */
 @XmlRootElement
public class ServerInfo {

    private String dateFormatPattern;

    private String datetimeFormatPattern;

    private String version;

    private ServerEdition edition;

    private String editionName;

    private String licenseType;

    private String build;

    private String expiration;

    private String features;

    public String getDateFormatPattern() {
        return dateFormatPattern;
    }

    public ServerInfo setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
        return this;
    }

    public String getDatetimeFormatPattern() {
        return datetimeFormatPattern;
    }

    public ServerInfo setDatetimeFormatPattern(String datetimeFormatPattern) {
        this.datetimeFormatPattern = datetimeFormatPattern;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ServerInfo setVersion(String version) {
        this.version = version;
        return this;
    }

    public ServerInfo setEdition(ServerEdition edition) {
        this.edition = edition;
        return this;
    }

    public ServerEdition getEdition() {
        return edition;
    }

    public String getEditionName() {
        return editionName;
    }

    public ServerInfo setEditionName(String editionName) {
        this.editionName = editionName;
        return this;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public ServerInfo setLicenseType(String licenseType) {
        this.licenseType = licenseType;
        return this;
    }

    public String getBuild() {
        return build;
    }

    public ServerInfo setBuild(String build) {
        this.build = build;
        return this;
    }

    public String getExpiration() {
        return expiration;
    }

    public ServerInfo setExpiration(String expiration) {
        this.expiration = expiration;
        return this;
    }

    public String getFeatures() {
        return features;
    }

    public ServerInfo setFeatures(String features) {
        this.features = features;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerInfo that = (ServerInfo) o;

        if (build != null ? !build.equals(that.build) : that.build != null) return false;
        if (dateFormatPattern != null ? !dateFormatPattern.equals(that.dateFormatPattern) : that.dateFormatPattern != null)
            return false;
        if (datetimeFormatPattern != null ? !datetimeFormatPattern.equals(that.datetimeFormatPattern) : that.datetimeFormatPattern != null)
            return false;
        if (edition != that.edition) return false;
        if (editionName != null ? !editionName.equals(that.editionName) : that.editionName != null) return false;
        if (expiration != null ? !expiration.equals(that.expiration) : that.expiration != null) return false;
        if (features != null ? !features.equals(that.features) : that.features != null) return false;
        if (licenseType != null ? !licenseType.equals(that.licenseType) : that.licenseType != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dateFormatPattern != null ? dateFormatPattern.hashCode() : 0;
        result = 31 * result + (datetimeFormatPattern != null ? datetimeFormatPattern.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (edition != null ? edition.hashCode() : 0);
        result = 31 * result + (editionName != null ? editionName.hashCode() : 0);
        result = 31 * result + (licenseType != null ? licenseType.hashCode() : 0);
        result = 31 * result + (build != null ? build.hashCode() : 0);
        result = 31 * result + (expiration != null ? expiration.hashCode() : 0);
        result = 31 * result + (features != null ? features.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "dateFormatPattern='" + dateFormatPattern + '\'' +
                ", datetimeFormatPattern='" + datetimeFormatPattern + '\'' +
                ", version='" + version + '\'' +
                ", edition=" + edition +
                ", editionName='" + editionName + '\'' +
                ", licenseType='" + licenseType + '\'' +
                ", build='" + build + '\'' +
                ", expiration='" + expiration + '\'' +
                ", features='" + features + '\'' +
                '}';
    }

    public static enum ServerEdition {
        CE,
        PRO;
    }

}
