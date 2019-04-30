package no.kristiania.pgr200.http.server;

import java.io.IOException;
import java.sql.SQLException;

import no.kristiania.pgr200.core.http.HttpServer;

public class RunServer {
	
	public static void main(String[] args) throws IOException, SQLException {
		HttpServer server = new HttpServer(10080);
		server.start();
		System.out.println("server started, ctrl + c to exit");
	}
}
