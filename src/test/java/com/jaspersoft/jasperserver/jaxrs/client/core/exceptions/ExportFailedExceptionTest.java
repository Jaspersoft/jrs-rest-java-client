package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link ExportFailedException}
 */
public class ExportFailedExceptionTest {

    @Mock
    private ErrorDescriptor descriptorMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_invoke_parent_constructor() {
        ExportFailedException exception = new ExportFailedException();
        assertNotNull(exception.getErrorDescriptors());
    }

    @Test
    public void should_pass_message_to_parent_class() {
        ExportFailedException exception = new ExportFailedException("msg");
        assertEquals("msg", exception.getMessage());
    }

    @Test
    public void should_pass_message_and_descriptors_to_parent_class() {
        List<ErrorDescriptor> expected = new ArrayList<ErrorDescriptor>();
        ExportFailedException exception = new ExportFailedException("msg", expected);
        assertEquals(expected, exception.getErrorDescriptors());
        assertEquals("msg", exception.getMessage());
    }

    @Test
    public void should_pass_param_to_parent_class() {

        StateDto dto = Mockito.spy(new StateDto());
        Mockito.doReturn(descriptorMock).when(dto).getErrorDescriptor();
        Mockito.doReturn("_msg").when(descriptorMock).getMessage();

        ExportFailedException exception = new ExportFailedException(dto);

        assertEquals(exception.getMessage(), "_msg");
    }

    @AfterMethod
    public void after() {
        reset(descriptorMock);
    }
}