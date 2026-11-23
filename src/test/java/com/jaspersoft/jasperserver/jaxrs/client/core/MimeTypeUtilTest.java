package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link MimeTypeUtil}
 */
public class MimeTypeUtilTest {

    @Mock
    private RestClientConfiguration configurationMock;

    private final String collectionMime = "application/collection+{mime}";
    private final String jobMime = "application/job+{mime}";

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() throws IllegalAccessException {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_invoke_toCorrectContentMime_method_only_once() {
        // Given
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
        // When
        String result = MimeTypeUtil.toCorrectContentMime(configurationMock, "abc");
        // Then
        assertNotNull(result);
    }

    @Test
    public void should_return_correct_content_mime() {
        // Given
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
        // When
        String result = MimeTypeUtil.toCorrectContentMime(configurationMock, collectionMime);
        // Then
        assertEquals(result, "application/collection+json");
    }

    @Test
    public void should_return_proper_content_mime_type() {
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
        String retrieved = MimeTypeUtil.toCorrectContentMime(configurationMock, collectionMime);
        assertEquals(retrieved, "application/collection+json");
    }

    @Test
    public void should_return_default_content_type() {
        String retrieved = MimeTypeUtil.toCorrectContentMime(configurationMock, "application/json");
        assertEquals(retrieved, "application/json");
    }

    @Test
    public void should_return_proper_accept_mime_type() {
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.XML);
        String retrieved = MimeTypeUtil.toCorrectAcceptMime(configurationMock, jobMime);
        assertEquals(retrieved, "application/job+xml");
    }

    @Test
    public void should_return_default_accept_type() {
        String retrieved = MimeTypeUtil.toCorrectAcceptMime(configurationMock, "application/xml");
        assertEquals(retrieved, "application/xml");
    }

    @Test
    public void should_create_instance() {
        MimeTypeUtil util = new MimeTypeUtil();
        assertNotNull(util);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(configurationMock);
        mockitoSession.close();
    }
}
