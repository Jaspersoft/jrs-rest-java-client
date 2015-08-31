package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertNotNull;

/**
 * @author Tetiana Iefimenko
 */
@PrepareForTest({JerseyRequest.class})
public class SettingsServiceTest extends PowerMockTestCase {

    private SettingsService service =  new SettingsService(mock(SessionStorage.class));

    @Test
    public void should_return_proper_settings_adapter() throws Exception {
        //when
        SingleSettingsAdapter retrieved = service.settings();
        //then
        assertNotNull(retrieved);

    }

}