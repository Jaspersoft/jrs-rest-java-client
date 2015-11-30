package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice;

/**
 * @author Tetiana Iefimenko
 * @version
 * @see
 */
public enum BrokenDependenciesParameter {

    /**
     * Server will give an error (errorCode=import.broken.dependencies) if import archive contain broken dependent resources.
     */
    FAIL("fail"),

    /**
     * Import will skip from import broken resources.
     */
    SKIP("skip"),

    /**
     *  Import will proceed with broken dependencies.
     */
    INCLUDE("include");

    private String valueName;

    private BrokenDependenciesParameter(String valueName){
        this.valueName = valueName;
    }

    public String getValueName() {
        return valueName;
    }
}
