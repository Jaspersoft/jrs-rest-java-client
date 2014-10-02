package com.jaspersoft.jasperserver.jaxrs.client.dto.query;

import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link Query}
 */
public class QueryTest {

    @Test
    public void should_set_field_with_proper_param() throws IllegalAccessException {

        // Given
        final List<QueryField> expected = new ArrayList<QueryField>();
        final Query query = new Query();

        // When
        query.setQueryFields(expected);

        // Than
        final Field field = field(Query.class, "queryFields");
        final List<QueryField> retrieved = (List<QueryField>) field.get(query);
        assertSame(retrieved, expected);
    }

    @Test
    public void should_get_a_proper_queryFields() throws IllegalAccessException {

        // Given
        final List<QueryField> expected = new ArrayList<QueryField>();
        final Query query = new Query();
        final Field field = field(Query.class, "queryFields");
        field.set(query, expected);

        // When
        List<QueryField> retrieved = query.getQueryFields();

        // Than
        assertSame(retrieved, expected);
    }

    @Test
    public void should_return_hash_of_field() {

        // Given
        final List<QueryField> queryFields = new ArrayList<QueryField>();
        final Query query = new Query();
        final int hash = queryFields.hashCode();

        assertTrue(query.hashCode() == 0);
        query.setQueryFields(queryFields);
        assertTrue(query.hashCode() == hash);
    }
}