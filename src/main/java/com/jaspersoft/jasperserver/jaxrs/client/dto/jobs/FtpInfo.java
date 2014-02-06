package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "outputFTPInfo")
public class FtpInfo {

    private String userName;
    private String password;
    private String folderPath;
    private String serverName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FtpInfo ftpInfo = (FtpInfo) o;

        if (folderPath != null ? !folderPath.equals(ftpInfo.folderPath) : ftpInfo.folderPath != null) return false;
        if (password != null ? !password.equals(ftpInfo.password) : ftpInfo.password != null) return false;
        if (serverName != null ? !serverName.equals(ftpInfo.serverName) : ftpInfo.serverName != null) return false;
        if (userName != null ? !userName.equals(ftpInfo.userName) : ftpInfo.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (folderPath != null ? folderPath.hashCode() : 0);
        result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FtpInfo{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", folderPath='" + folderPath + '\'' +
                ", serverName='" + serverName + '\'' +
                '}';
    }
}
