package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement
public class GlobalConfigurationSettings {

    private Integer paginatorItemsPerPage;
    private Integer paginatorPagesRange;
    private Boolean reportLevelConfigurable;
    private Boolean paginationForSinglePageReport;
    private String calendarInputJsp;
    private Integer userItemsPerPage;
    private Integer roleItemsPerPage;
    private Integer tenantItemsPerPage;

    private Collection<String> userNameNotSupportedSymbols;
    private Collection<String> roleNameNotSupportedSymbols;

    private String userNameSeparator;

    private String defaultRole;
    private String passwordMask;
    private Collection<String> viewReportsFilterList;
    private Collection<String> outputFolderFilterList;
    private Collection<String> outputFolderFilterPatterns;

//            "tenantNameNotSupportedSymbols": "[\\|&+*?\\<\\>/\\\\]",
//            "tenantIdNotSupportedSymbols": "[~!\\+\\-#\\$%\\^\\|\\s]",

    private Collection<String> resourceIdNotSupportedSymbols;


//            "publicFolderUri": "/public",
//            "themeDefaultName": "default",
//            "themeFolderName": "/themes",
//            "themeServletPrefix": "_themes",
//            "dateFormat": "M/d/yyyy",
//            "currentYearDateFormat": "MMMMM d",
//            "timestampFormat": "M/d/yyyy hh:mmaaa",
//            "timeFormat": "hh:mmaaa",
//            "entitiesPerPage": 20,
//            "tempFolderUri": "/temp",
//            "organizationsFolderUri": "/organizations",
//            "jdbcDriversFolderUri": "/jdbc",
//            "emailRegExpPattern":
//            "^([\\p{L}\\p{M}\\p{N}!#$%&'*+-/=?^_`{}|~]+|\"[\\p{L}\\p{M}\\p{N}_%'-+\\s\\(\\)<>\\[\\
//            ]:,;@!#$%&'*+-/=?^_`{}|\\s~\\.]+\")(\\.([\\p{L}\\p{M}\\p{N}_%'-+]+|\"[\\p{L}\\p{M}\\p{
//    N}_%'-+\\s\\(\\)<>\\[\\]:,;@!#$%&'*+-/=?^_`{}|\\s~\\.]+]*\"))*@((\\[(\\d{1,3}\\.){3,3}
//        \\d{1,3}\\])|(([\\p{L}\\p{M}\\p{N}-]+\\.)*[\\p{L}\\p{M}\\p{N}-]+))$",

//        "enableSaveToHostFS": false,
//        "allFileResourceTypes": {
//        "accessGrantSchema": "Access Grant Schema",
//        "contentResource": "Content Resource",
//        "css": "CSS",
//        "font": "Font",
//        "img": "Image",
//        "jar": "JAR",
//        "jrxml": "JRXML",
//        "olapMondrianSchema": "OLAP Schema",
//        "prop": "Resource Bundle",
//        "jrtx": "Style Template",
//        "xml": "XML"
//        },
//        "dataSourceTypes": [{
//        "type":
//        "com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.JdbcReportDataSource",
//        "typeValue": "JDBC Data Source",
//        "labelMessage": "dataSource.jdbc"
//        }, {
//        "type":
//        "com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.JndiJdbcReportDataSourc
//        e",
//        "typeValue": "JNDI Data Source",
//        "labelMessage": "dataSource.jndi"
//        },
//        { "type":
//        "com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.VirtualReportDataSource
//        ",
//        "typeValue": "Virtual Data Source",
//        "labelMessage": "dataSource.virtual"
//        },
}
