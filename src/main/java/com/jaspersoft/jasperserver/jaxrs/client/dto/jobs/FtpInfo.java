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
}
