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


import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement(name = "outputFTPInfo")
public class FtpInfo {

    private String userName;
    private String password;
    private String folderPath;
    private String serverName;
    private String type;
    private String protocol;
    private Integer port;
    private Boolean implicit;
    private Long pbsz;
    private String prot;
    private volatile Map<String, String> propertiesMap;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean isImplicit() {
        return implicit;
    }

    public void setImplicit(Boolean implicit) {
        this.implicit = implicit;
    }

    public Long getPbsz() {
        return pbsz;
    }

    public void setPbsz(Long pbsz) {
        this.pbsz = pbsz;
    }

    public String getProt() {
        return prot;
    }

    public void setProt(String prot) {
        this.prot = prot;
    }

    public Map<String, String> getPropertiesMap() {
        return propertiesMap;
    }

    public void setPropertiesMap(Map<String, String> propertiesMap) {
        this.propertiesMap = propertiesMap;
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
