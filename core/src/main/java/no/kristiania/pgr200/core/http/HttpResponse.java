package no.kristiania.pgr200.core.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

	private String body;
	private int statusCode;
	private InputStream input;
	private Map<String, String> headers = new HashMap<>();

	public HttpResponse(Socket socket) throws IOException {
		input = socket.getInputStream();
		readStatusCode();
		readHeaders();
		if (headers.containsKey("content-length")) {
			readBody();

		}
	}

	public void readStatusCode() throws IOException {
		String statusLine = readNextLine();
		String[] parts = statusLine.split(" ");
		statusCode = Integer.parseInt(parts[1]);
	}

	public void readBody() throws IOException {
		StringBuilder body = new StringBuilder();
		int contentLength = getContentLength();
		for (int i = 0; i < contentLength; i++) {
			int c = input.read();
			body.append((char) c);
		}
		this.body = body.toString();

	}

	public void readHeaders() throws IOException {
		String header;
		while ((header = readNextLine()) != null) {
			if (header.isEmpty()) {
				break;
			}
			int colonPos = header.indexOf(':');
			String headerName = header.substring(0, colonPos);
			String headerValue = header.substring(colonPos + 1).trim();

			headers.put(headerName.toLowerCase(), headerValue);
		}
	}

	public String readNextLine() throws IOException {
		StringBuilder currentLine = new StringBuilder();
		int c;
		while ((c = input.read()) != -1) {
			if (c == '\r') {
				input.mark(1);
				c = input.read();
				if (c != '\n') {
					input.reset();
				}
				break;
			}
			currentLine.append((char) c);
		}
		return currentLine.toString();
	}

	public int getContentLength() {
		return Integer.parseInt(getHeader("content-length"));
	}

	public String getHeader(String headerName) {
		return headers.get(headerName);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getBody() {
		return body;
	}
}
