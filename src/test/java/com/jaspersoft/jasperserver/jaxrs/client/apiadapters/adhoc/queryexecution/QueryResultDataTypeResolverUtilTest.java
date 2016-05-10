package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType;
import org.testng.annotations.Test;

import static org.testng.Assert.assertSame;

public class QueryResultDataTypeResolverUtilTest {

    @Test
    public void should_return_proper_client_resource_for_flat_data_content_type_plus_json() {
        Class<? extends ClientQueryResultData> retrieved = QueryResultDataTypeResolverUtil.
                getClassForMime(QueryResultDataMediaType.FLAT_DATA_JSON);

        assertSame(retrieved, ClientFlatQueryResultData.class);
    }

    @Test
    public void should_return_proper_client_resource_for_flat_data_content_type_plus_xml() {
        Class<? extends ClientQueryResultData> retrieved = QueryResultDataTypeResolverUtil.
                getClassForMime(QueryResultDataMediaType.FLAT_DATA_XML);

        assertSame(retrieved, ClientFlatQueryResultData.class);
    }

    @Test
    public void should_return_proper_client_resource_for_multi_level_data_content_type_plus_json() {
        Class<? extends ClientQueryResultData> retrieved = QueryResultDataTypeResolverUtil.
                getClassForMime(QueryResultDataMediaType.MULTI_LEVEL_DATA_JSON);

        assertSame(retrieved, ClientMultiLevelQueryResultData.class);
    }

    @Test
    public void should_return_proper_client_resource_for_multi_level_data_content_type_plus_xml() {
        Class<? extends ClientQueryResultData> retrieved = QueryResultDataTypeResolverUtil.
                getClassForMime(QueryResultDataMediaType.MULTI_LEVEL_DATA_XML);

        assertSame(retrieved, ClientMultiLevelQueryResultData.class);
    }

    @Test
    public void should_return_proper_client_resource_for_multi_axes_data_content_type_plus_json() {
        Class<? extends ClientQueryResultData> retrieved = QueryResultDataTypeResolverUtil.
                getClassForMime(QueryResultDataMediaType.MULTI_AXES_DATA_JSON);

        assertSame(retrieved, ClientMultiAxesQueryResultData.class);
    }

    @Test
    public void should_return_proper_client_resource_for_multi_axes_data_content_type_plus_xml() {
        Class<? extends ClientQueryResultData> retrieved = QueryResultDataTypeResolverUtil.
                getClassForMime(QueryResultDataMediaType.MULTI_AXES_DATA_XML);

        assertSame(retrieved, ClientMultiAxesQueryResultData.class);
    }

}