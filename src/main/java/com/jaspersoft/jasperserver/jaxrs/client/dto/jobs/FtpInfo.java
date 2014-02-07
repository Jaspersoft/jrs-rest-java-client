package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "outputFTPInfo")
public class FtpInfo {

    private String userName;
    private String password;
    private String folderPath;
    private String serverName;
    private String type;
    private String protocol;
    private int port;
    private boolean implicit;
    private long pbsz;
    private String prot;


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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isImplicit() {
        return implicit;
    }

    public void setImplicit(boolean implicit) {
        this.implicit = implicit;
    }

    public long getPbsz() {
        return pbsz;
    }

    public void setPbsz(long pbsz) {
        this.pbsz = pbsz;
    }

    public String getProt() {
        return prot;
    }

    public void setProt(String prot) {
        this.prot = prot;
    }
}
