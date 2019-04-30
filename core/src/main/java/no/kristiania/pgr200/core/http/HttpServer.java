package no.kristiania.pgr200.core.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	private int port;
	private ServerSocket serverSocket;

	public HttpServer(int port) throws IOException {
		this.port = port;
	}

	public void start() {

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this::serverThread).start();

	}

	public void serverThread() {

		while (true) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
				InputStream input = clientSocket.getInputStream();
				OutputStream output = clientSocket.getOutputStream();
				RequestHandler handler = new RequestHandler(input, output);
				handler.handleRequest();
			} catch (RuntimeException e) {
				if (clientSocket != null) {
					try {
						clientSocket.close();
					} catch (IOException ioEx) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getPort() {
		return port;
	}

}
