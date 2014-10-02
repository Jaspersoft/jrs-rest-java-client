package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

@PrepareForTest(PageRange.class)
public class PageRangeTest extends PowerMockTestCase {

    @Test
    public void should_set_fields_with_proper_params() throws Exception {

        /* Given */
        PageRange rangeSpy = PowerMockito.spy(new PageRange(10L, 20L));
        PowerMockito.whenNew(PageRange.class).withArguments(10L, 20L).thenReturn(rangeSpy);

        /* When */
        PageRange retrieved = new PageRange(10L, 20L);

        /* Than */
        long startIndex = (Long) Whitebox.getInternalState(retrieved, "startIndex");
        long endIndex = (Long) Whitebox.getInternalState(retrieved, "endIndex");

        assertSame(retrieved, rangeSpy);
        assertTrue(startIndex == 10L);
        assertTrue(endIndex == 20L);
    }

    @Test
    public void should_return_proper_string_when_startIndex_is_diff_than_endIndex() {
        PageRange range = new PageRange(10L, 20L);
        String retrieved = range.getRange();
        assertEquals(retrieved, "10-20");
    }

    @Test
    public void should_return_proper_string_when_startIndex_is_the_same_as_endIndex() {
        PageRange range = new PageRange(250L, 250L);
        String retrieved = range.getRange();
        assertEquals(retrieved, "250");
    }
}