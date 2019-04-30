package no.kristiania.pgr200.core.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpRequest {

	private String host, path;
	private int port;
	private String method;
	private String body = "";

	public HttpRequest(String host, int port, String path, String method) {
		this.host = host;
		this.port = port;
		this.path = path;
		this.method = method;
	}

	public HttpResponse execute() {

		try (Socket socket = new Socket(host, port)) {
			OutputStream output = socket.getOutputStream();
			output.write((method + " " + path + " HTTP/1.1\r\n").getBytes());
			output.write(("Host: " + host + "\r\n").getBytes());
			output.write("Connection: Close\r\n".getBytes());
			if (method.equals("POST")) {
				output.write("Content-type: application/x-www-form-urlencoded\r\n".getBytes());
				output.write(("Content-Length: " + body.length()).getBytes());
				output.write("\r\n".getBytes());
				output.write((body + "\r\n").getBytes());
			}
			if (method.equals("GET") && !body.isEmpty()) {
				output.write("Content-type: application/x-www-form-urlencoded\r\n".getBytes());
				output.write(("Content-Length: " + body.length()).getBytes());
				output.write("\r\n".getBytes());
				output.write((body + "\r\n").getBytes());
			}
			output.write("\r\n".getBytes());
			return new HttpResponse(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
