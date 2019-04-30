package no.kristiania.pgr200.core.database;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGPoolingDataSource;


public class ProgramDataSource {
	
	private Flyway flyway;
	private DataSource dataSource;
	
	public ProgramDataSource() {
		this.dataSource = createDataSource();
	}

	public  DataSource createDataSource() {
		JdbcProperties prop = new JdbcProperties("innlevering.properties");

		PGPoolingDataSource dataSource = new PGPoolingDataSource();
		dataSource.setUrl(prop.getUrl());
		dataSource.setUser(prop.getUsername());
		dataSource.setPassword(prop.getPassword());

		flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.migrate();
		

		return dataSource;
	}
	
	public void cleanDb() {
		flyway.clean();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

}
