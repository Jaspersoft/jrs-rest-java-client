package com.jaspersoft.jasperserver.jaxrs.client.core.config;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alexander Krasnyanskiy
 */
public class ConfigurationReaderAdapter {

    private ServerMetaData server;

    public ServerMetaData getServer() {
        return server;
    }

    public void setServer(ServerMetaData server) {
        this.server = server;
    }

    public ConfigurationReaderAdapter configure(InputStream configFile, ConfigType type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        switch (type){
            case YML: mapper = new ObjectMapper(new YAMLFactory()); break;
            case JSON: mapper = new ObjectMapper(new JsonFactory()); break;
            case XML:  mapper = new XmlMapper();                     break;
        }
        return mapper.readValue(configFile, ConfigurationReaderAdapter.class);
    }
}