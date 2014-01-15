package com.jaspersoft.jasperserver.jaxrs.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RestClientConfiguration {

    private String restServerUrl;

    public String getRestServerUrl() {
        return restServerUrl;
    }

    public void setRestServerUrl(String restServerUrl) {
        this.restServerUrl = restServerUrl;
    }

    public static RestClientConfiguration loadConfiguration(String path){
        InputStream is = RestClientConfiguration.class.getClassLoader().getResourceAsStream(path);
        RestClientConfiguration configuration = new RestClientConfiguration();
        configuration.setRestServerUrl(getProperty(is, "url"));
        return configuration;
    }

    private static String getProperty(InputStream is, String name) {

        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(name);
    }
}
