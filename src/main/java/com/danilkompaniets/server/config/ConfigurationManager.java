package com.danilkompaniets.server.config;

import com.danilkompaniets.server.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import javax.naming.ConfigurationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static  ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {}

    public static ConfigurationManager getInstance() {
    if (myConfigurationManager==null)
        myConfigurationManager = new ConfigurationManager();
        return myConfigurationManager;
    }

    public void loadConfigurationFile(String filepath)  {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        while (true) {
            try {
                if (!((i = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException(e);
            }
            sb.append((char) i);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing config file ", e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the config file, internal ", e);
        }
    }

    public Configuration getCurrentConfiguration() throws ConfigurationException {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No configuration set.");
        }
        return myCurrentConfiguration;
    }
}
