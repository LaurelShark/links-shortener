//package com.saveme.go.integration.config;
//
//
//import jakarta.inject.Singleton;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//
//@Singleton
//public class IntegrationTestConfig extends PostgreSQLContainer<IntegrationTestConfig> {
//
//    private static IntegrationTestConfig INSTANCE = null;
//
//    private boolean started = false;
//
//    private IntegrationTestConfig() {
//    }
//
//    @Container
//    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
//            .withDatabaseName("integration-tests-db")
//            .withUsername("postgres")
//            .withPassword("postgres");
//
//    public static IntegrationTestConfig getInstance() {
//        if (INSTANCE == null) {
//            INSTANCE = new IntegrationTestConfig();
//            INSTANCE.start();
//        }
//        return INSTANCE;
//    }
//
//    @Override
//    public void start() {
//        if (!started) {
//            postgreSQLContainer.start();
//            started = true;
//        }
//    }
//}
