package no.kristiania.pgr200.http.server.database.dao;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

public class TestDataSource {

    public static DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("test");
    
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    
        return dataSource;
    }
}