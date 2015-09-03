package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.testng.Assert.assertNotNull;

/**
 * @author Tetiana Iefimenko
 */

public class SettingsServiceTest extends PowerMockTestCase {

    @Test
    public void should_return_proper_settings_adapter() throws Exception {
        //given
        SettingsService service =  new SettingsService(mock(SessionStorage.class));
        //when
        SingleSettingsAdapter retrieved = service.settings();
        //then
        assertNotNull(retrieved);

    }

}