package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public enum AttributesSearchParameter {
    /**
     * Represent the target holder, attributes should be fetched from.
     */
    HOLDER("holder"),

    /**
     * name of the attribute
     */
    NAME("name"),

    /**
     * Attribute group:
     *
     * custom - custom attributes(doesn't affect on server)
     * log4j - logger specific attributes
     * mondrian - server attributes that make affect on Mondrian engine
     * aws - aws specific server attributes
     * jdbc - jdbc drivers specific attributes
     * adhoc - adhoc specific attributes
     * ji - profiling attributes
     * customServerSettings - updated server settings(changed log4j, mondrian, aws, jdbc, adhoc, ji server setting)

     * default value is null
     */
    GROUP("group"),

    /**
     * Flag indicates if attributes will be fetched also from lower level.
     *
     * Default value is true
     */
    RECURSIVE("recursive"),

    /**
     * Flag indicates if search should include also higher level attributes, relatively to target holder.
     */
    INCLUDE_INHERITED("includeInherited"),

    /**
     * Pagination. Start index for requested pate.
     */
    OFFSET("offset"),

    /**
     * 	Pagination. Resources count per page
     */
    LIMIT("limit");// @QueryParam("_embedded") String embedded,

    private String name;

    AttributesSearchParameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
