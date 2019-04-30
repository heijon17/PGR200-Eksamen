package no.kristiania.pgr200.core.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JdbcProperties {

	private String url, username, password;
	private Map<String,String> properties = new HashMap<>();



	public JdbcProperties (String filename) {
		readPropFile(filename);
	}

	private void readPropFile(String filename) {
	
		try (BufferedReader br = new BufferedReader(new FileReader("../" + filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] splitLine = line.split(": ");
				properties.put(splitLine[0].trim(), splitLine[1].trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setUrl(properties.get("dataSource.url"));
		setUsername(properties.get("dataSource.username"));
		setPassword(properties.get("dataSource.password"));

	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}


}

