package com.server.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@Component
public class DataInitializer {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void loadData() {
        try {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.addScript(new ClassPathResource("data.sql"));
            resourceDatabasePopulator.setSqlScriptEncoding(StandardCharsets.UTF_8.name());

            DatabasePopulatorUtils.execute(resourceDatabasePopulator, dataSource);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
