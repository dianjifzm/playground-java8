package org.projectbarbel.playground.singleton;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class SQLDataSourceConfigSynchronized {

    public static final SQLDataSourceConfigSynchronized INSTANCE = new SQLDataSourceConfigSynchronized();
    private Configurations configs = new Configurations();
    private Configuration config;

    private SQLDataSourceConfigSynchronized() {
    }

    public Configuration configuration(String filename) {
        try {
            if (config == null) {
                synchronized (INSTANCE) {
                    if (config == null) {
                        System.out.println("created");
                        config = configs.properties(new File(filename));
                    }
                }
            }
            return config;
        } catch (ConfigurationException e) {
            throw new IllegalStateException("unable to load configuration database.properties", e);
        }
    }

}
