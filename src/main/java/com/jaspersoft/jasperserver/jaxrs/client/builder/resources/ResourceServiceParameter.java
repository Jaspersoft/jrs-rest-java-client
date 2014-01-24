package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

public enum ResourceServiceParameter {

    /**
     * When true, all nested resources will be given as full descriptors. The default
     * behavior, false, has all nested resources given as references. For more
     * information, see Local Resources.
     *
     * Type/Value: true|false
     */
    EXPANDED("expanded"),

    /**
     * By default, this is true, and the service will create all parent folders if they
     * don't already exist. When set to false, the folders specified in the URL must
     * all exist, otherwise the service returns an error.
     *
     * Type/Value: true|false
     */
    CREATE_FOLDERS("createFolders"),

    /**
     * When true, the resource given in the URL is overwritten even if it is a
     * different type than the resource descriptor in the content. The default is false.
     *
     * Type/Value: true|false
     */
    OVERWRITE("overwrite"),
    ;

    private String name;

    private ResourceServiceParameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
