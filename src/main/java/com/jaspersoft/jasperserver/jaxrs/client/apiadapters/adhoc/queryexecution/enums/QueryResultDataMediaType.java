/*
 * Copyright (C) 2005 - 2015 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums;

/**
 * @author Vasyl Spachynskyi
 * @version $Id: QueryResultDataMediaType.java 58175 2016-03-25 19:57:33Z nthapa $
 * @since 11.02.2016
 */
public class QueryResultDataMediaType {

    public final static String FLAT_DATA_TYPE = "application/flatData+{mime}";
    public final static String FLAT_DATA_JSON = "application/flatData+json";
    public final static String FLAT_DATA_XML = "application/flatData+xml";

    public final static String MULTI_LEVEL_DATA_TYPE = "application/multiLevelData+{mime}";
    public final static String MULTI_LEVEL_DATA_JSON = "application/multiLevelData+json";
    public final static String MULTI_LEVEL_DATA_XML = "application/multiLevelData+xml";

    public final static String MULTI_AXES_DATA_TYPE = "application/multiAxesData+{mime}";
    public final static String MULTI_AXES_DATA_JSON = "application/multiAxesData+json";
    public final static String MULTI_AXES_DATA_XML = "application/multiAxesData+xml";

}