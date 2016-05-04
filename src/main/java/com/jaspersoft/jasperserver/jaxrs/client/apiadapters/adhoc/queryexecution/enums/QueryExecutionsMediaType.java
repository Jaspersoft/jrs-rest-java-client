package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums;

public class QueryExecutionsMediaType {
    public final static String EXECUTION_MULTI_LEVEL_QUERY_TYPE = "application/execution.multiLevelQuery+{mime}";
    public final static String EXECUTION_MULTI_AXES_QUERY_TYPE = "application/execution.multiAxesQuery+{mime}";
    public final static String EXECUTION_PROVIDED_QUERY_TYPE = "application/execution.providedQuery+{mime}";

    public final static String EXECUTION_MULTI_LEVEL_QUERY_JSON = "application/execution.multiLevelQuery+json";
    public final static String EXECUTION_MULTI_AXES_QUERY_JSON = "application/execution.multiAxesQuery+json";
    public final static String EXECUTION_PROVIDED_QUERY_JSON = "application/execution.providedQuery+json";

    public final static String EXECUTION_MULTI_LEVEL_QUERY_XML = "application/execution.multiLevelQuery+xml";
    public final static String EXECUTION_MULTI_AXES_QUERY_XML = "application/execution.multiAxesQuery+xml";
    public final static String EXECUTION_PROVIDED_QUERY_XML = "application/execution.providedQuery+xml";
}
