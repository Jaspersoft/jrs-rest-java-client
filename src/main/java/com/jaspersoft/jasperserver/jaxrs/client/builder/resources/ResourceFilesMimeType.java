package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

public enum  ResourceFilesMimeType {

    PDF("application/pdf"),
    HTML("text/html"),
    XLS("application/xls"),
    RTF("application/rtf"),
    CSV("text/csv"),
    ODS("application/vnd.oasis.opendocument.spreadsheet"),
    ODT("application/vnd.oasis.opendocument.text"),
    TXT("text/plain"),
    DOCX("application/vnd.openxmlformatsofficedocument.wordprocessingml.document"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    FONT("font/*"),
    IMG("image/*"),
    JRXML("application/jrxml"),
    JAR("application/zip"),
    PROP("application/properties"),
    JRTX("application/jrtx"),
    XML("application/xml"),
    CSS("text/css"),
    ACCES_GRANT_SCHEMA("application/accessGrantSchema"),
    OLAP_MONDRIAN_SCHEMA("application/olapMondrianSchema")
    ;

    private String type;

    private ResourceFilesMimeType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
