package org.projectbarbel.playground.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class SQLDataSourceConfigHashMap {

    public static final SQLDataSourceConfigHashMap INSTANCE = new SQLDataSourceConfigHashMap();
    private Configurations configs = new Configurations();
    private Map<String, ConfigurationBuilder<?>> map = new ConcurrentHashMap<>();

    private SQLDataSourceConfigHashMap() {
    }

    public ImmutableConfiguration configuration(String filename) throws ConfigurationException {
        return map.computeIfAbsent(filename, configs::propertiesBuilder).getConfiguration();
    }

}
