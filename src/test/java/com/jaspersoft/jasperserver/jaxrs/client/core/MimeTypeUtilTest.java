package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.expect;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link MimeTypeUtil}
 */
@PrepareForTest({MimeTypeUtil.class, MimeType.class, RestClientConfiguration.class})
public class MimeTypeUtilTest extends PowerMockTestCase {

    @Mock
    private RestClientConfiguration configurationMock;

    private final String collectionMime = "application/collection+{mime}";
    private final String jobMime = "application/job+{mime}";

    @BeforeMethod
    public void before() throws IllegalAccessException {
        initMocks(this);
    }

    @Test
    public void test_invoke_toCorrectContentMime_method_only_once (){
        PowerMockito.mockStatic(MimeTypeUtil.class);
        MimeTypeUtil.toCorrectContentMime(configurationMock, "abc");
        PowerMockito.verifyStatic(Mockito.times(1));
        MimeTypeUtil.toCorrectContentMime(configurationMock, "abc");
    }

    @Test
    public void should_return_correct_content_mime() {
        mockStatic(MimeTypeUtil.class);
        expect(MimeTypeUtil.toCorrectContentMime(configurationMock, collectionMime)).andReturn("application/collection+json");
        replay(MimeTypeUtil.class);

        MimeTypeUtil.toCorrectContentMime(configurationMock, collectionMime);
        verify(MimeTypeUtil.class);
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
    public void after() {
        reset(configurationMock);
    }
}