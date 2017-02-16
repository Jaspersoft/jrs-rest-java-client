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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;


import java.util.LinkedHashMap;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;
/**
 * @deprecated (use server DTO).
 */
@XmlRootElement(name = "outputFTPInfo")
public class FtpInfo {

    private String userName;
    private String password;
    private String folderPath;
    private String serverName;
    private FtpType type;
    private String protocol;
    private Integer port;
    private Boolean implicit;
    private Long pbsz;
    private String prot;
    private Map<String, String> propertiesMap;

    public FtpInfo() {
    }

    public FtpInfo(FtpInfo other) {
        this.folderPath = other.folderPath;
        this.implicit = other.implicit;
        this.password = other.password;
        this.pbsz = other.pbsz;
        this.port = other.port;
        this.propertiesMap = (other.propertiesMap != null) ? new LinkedHashMap<String, String>(other.propertiesMap) : null;
        this.prot = other.prot;
        this.protocol = other.protocol;
        this.serverName = other.serverName;
        this.type = other.type;
        this.userName = other.userName;
    }

    public String getUserName() {
        return userName;
    }

    public FtpInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Boolean getImplicit() {
        return implicit;
    }

    public String getPassword() {
        return password;
    }

    public FtpInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public FtpInfo setFolderPath(String folderPath) {
        this.folderPath = folderPath;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public FtpInfo setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public FtpType getType() {
        return type;
    }

    public FtpInfo setType(FtpType type) {
        this.type = type;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public FtpInfo setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public FtpInfo setPort(Integer port) {
        this.port = port;
        return this;
    }

    public Boolean isImplicit() {
        return implicit;
    }

    public FtpInfo setImplicit(Boolean implicit) {
        this.implicit = implicit;
        return this;
    }

    public Long getPbsz() {
        return pbsz;
    }

    public FtpInfo setPbsz(Long pbsz) {
        this.pbsz = pbsz;
        return this;
    }

    public String getProt() {
        return prot;
    }

    public FtpInfo setProt(String prot) {
        this.prot = prot;
        return this;
    }

    public Map<String, String> getPropertiesMap() {
        return propertiesMap;
    }

    public FtpInfo setPropertiesMap(Map<String, String> propertiesMap) {
        this.propertiesMap = propertiesMap;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FtpInfo ftpInfo = (FtpInfo) o;

        if (folderPath != null ? !folderPath.equals(ftpInfo.folderPath) : ftpInfo.folderPath != null) return false;
        if (implicit != null ? !implicit.equals(ftpInfo.implicit) : ftpInfo.implicit != null) return false;
        if (password != null ? !password.equals(ftpInfo.password) : ftpInfo.password != null) return false;
        if (pbsz != null ? !pbsz.equals(ftpInfo.pbsz) : ftpInfo.pbsz != null) return false;
        if (port != null ? !port.equals(ftpInfo.port) : ftpInfo.port != null) return false;
        if (propertiesMap != null ? !propertiesMap.equals(ftpInfo.propertiesMap) : ftpInfo.propertiesMap != null)
            return false;
        if (prot != null ? !prot.equals(ftpInfo.prot) : ftpInfo.prot != null) return false;
        if (protocol != null ? !protocol.equals(ftpInfo.protocol) : ftpInfo.protocol != null) return false;
        if (serverName != null ? !serverName.equals(ftpInfo.serverName) : ftpInfo.serverName != null) return false;
        if (type != null ? !type.equals(ftpInfo.type) : ftpInfo.type != null) return false;
        if (userName != null ? !userName.equals(ftpInfo.userName) : ftpInfo.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (folderPath != null ? folderPath.hashCode() : 0);
        result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (implicit != null ? implicit.hashCode() : 0);
        result = 31 * result + (pbsz != null ? pbsz.hashCode() : 0);
        result = 31 * result + (prot != null ? prot.hashCode() : 0);
        result = 31 * result + (propertiesMap != null ? propertiesMap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FtpInfo{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", folderPath='" + folderPath + '\'' +
                ", serverName='" + serverName + '\'' +
                ", type='" + type + '\'' +
                ", protocol='" + protocol + '\'' +
                ", port=" + port +
                ", implicit=" + implicit +
                ", pbsz=" + pbsz +
                ", prot='" + prot + '\'' +
                ", propertiesMap=" + propertiesMap +
                '}';
    }
}
