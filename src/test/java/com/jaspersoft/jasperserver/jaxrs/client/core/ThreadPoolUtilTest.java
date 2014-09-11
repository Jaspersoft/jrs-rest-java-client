package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class ThreadPoolUtilTest {

    @Test
    public void should_return_not_null_executor() {
        assertNotNull(ThreadPoolUtil.getExecutorService());
    }

}