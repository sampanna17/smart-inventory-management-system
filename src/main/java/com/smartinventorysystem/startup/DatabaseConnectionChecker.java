package com.smartinventorysystem.startup;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseConnectionChecker implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseConnectionChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String @NonNull[] args) {
        System.out.println("========== RUNNER STARTED ==========");
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Database Connected Successfully!");
        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
        }
    }
}