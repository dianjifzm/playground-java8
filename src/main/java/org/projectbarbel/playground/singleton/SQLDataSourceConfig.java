package org.projectbarbel.playground.singleton;

import java.io.File;
import java.util.Optional;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class SQLDataSourceConfig {

    public static final SQLDataSourceConfig INSTANCE = new SQLDataSourceConfig();
    private Configurations configs = new Configurations();
    private Configuration config;
    
    private SQLDataSourceConfig() {
    }
    
    public Configuration configuration(String filename) {
        return Optional.ofNullable(config).orElseGet(()->createConfiguration(filename));
    }
    
    private synchronized Configuration createConfiguration(String filename) {
        if (config == null) {
            try {
                config = configs.properties(new File(filename));
            } catch (ConfigurationException e) {
                throw new IllegalStateException("unable to load configuration database.properties", e);
            }
        }
        return config;
    }

}
