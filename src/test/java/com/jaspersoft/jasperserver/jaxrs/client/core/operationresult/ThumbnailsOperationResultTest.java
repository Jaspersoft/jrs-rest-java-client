package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnail;
import com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails.ResourceThumbnailListWrapper;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link ThumbnailsOperationResult}
 */
public class ThumbnailsOperationResultTest extends PowerMockTestCase {

    @Mock
    private Response responseMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    /**
     * for {@link ThumbnailsOperationResult#ThumbnailsOperationResult(Response, Class)}
     */
    public void should_pass_response_and_proper_class_to_parent_operation_result() {
        ThumbnailsOperationResult operationResult = new ThumbnailsOperationResult(responseMock, ResourceThumbnailListWrapper.class);
        Response retrievedResponse = operationResult.getResponse();
        Class<? extends ResourceThumbnailListWrapper> retrievedEntityClass = operationResult.getEntityClass();
        Assert.assertSame(retrievedResponse, responseMock);
        Assert.assertSame(retrievedEntityClass, ResourceThumbnailListWrapper.class);
    }

    @Test
    public void should_return_wrapper_entity() {

        /** Given **/
        List<ResourceThumbnail> fakeThumbnails = new ArrayList<ResourceThumbnail>() {{
            add(new ResourceThumbnail());
            add(new ResourceThumbnail());
        }};

        Mockito.doReturn(fakeThumbnails).when(responseMock).readEntity(any(GenericType.class));

        ThumbnailsOperationResult operationResult = new ThumbnailsOperationResult(responseMock, ResourceThumbnailListWrapper.class);

        /** When **/
        ResourceThumbnailListWrapper retrieved = operationResult.getEntity();

        /** Than **/
        Mockito.verify(responseMock, times(1)).readEntity(any(GenericType.class));
        Mockito.verify(responseMock, times(1)).bufferEntity();
        Mockito.verifyNoMoreInteractions(responseMock);
        List<ResourceThumbnail> thumbnails = retrieved.getThumbnails();
        Assert.assertTrue(thumbnails.size() == 2);
    }

    @Test
    public void should_return_null_if_content_of_the_message_cannot_be_mapped_to_an_entity() {

        /** Given **/
        doThrow(new ProcessingException("O_o")).when(responseMock).readEntity(any(GenericType.class));

        /** When **/
        ThumbnailsOperationResult operationResult = new ThumbnailsOperationResult(responseMock, ResourceThumbnailListWrapper.class);
        ResourceThumbnailListWrapper entity = operationResult.getEntity();

        /** Than **/
        Assert.assertNull(entity);
    }

    @AfterMethod
    public void after() {
        reset(responseMock);
    }
}