package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public enum AttributesGroupParameter {

    /**
     * Custom attributes(doesn't affect on server).
     */
    CUSTOM("custom"),

    /**
     * Logger specific attributes.
     */
    LOG4J("log4j"),

    /**
     * Server attributes that make affect on Mondrian engine.
     *
     */
    MONDRIAN("mondrian"),

    /**
     * AWS specific server attributes.
     */
    AWS("aws"),

    /**
     * JDBC drivers specific attributes.
     */
    JDBC("jdbc"),

    /**
     * 	AdHoc specific attributes.
     */
    ADHOC("adhoc"),

    /**
     * 	Profiling attributes.
     */
    JI("ji"),

    /**
     * 	Updated server settings(changed log4j, mondrian, aws, jdbc, adhoc, ji server setting).
     */
    CUSTOM_SERVER_SETTINGS("customServerSettings");

    private String name;

    AttributesGroupParameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
