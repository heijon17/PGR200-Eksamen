package no.kristiania.pgr200.core.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import no.kristiania.pgr200.core.database.Commands;

/**
 * This class handles the HttpRequest and writes response from server.
 */

public class RequestHandler {

	private OutputStream output;
	private InputStream input;
	private HttpPath path;
	private String method;
	private int statusCode;
	private String body, returnBody;
	private Map<String, String> headers = new LinkedHashMap<>();
	private List<String> headerList = new ArrayList<>();

	public RequestHandler(InputStream input, OutputStream output) throws IOException {
		this.output = output;
		this.input = input;
	}

	public void handleRequest() throws IOException {

		String line = readNextLine();
		String[] lineSplit = line.split(" ");

		path = new HttpPath(lineSplit[1]);
		method = lineSplit[0];
		readHeaders();

		if (headers.containsKey("content-length")) {
			this.body = headerList.get(headerList.size() - 1);
		}

		setStatusCode();

		runMethod().get(path.getQuery().getParameter("method")).run();
		writeResponse();
	}

	public void readHeaders() throws IOException {
		String header;
		while ((header = readNextLine()) != null) {
			if (header.equals("\r\n")) {
				this.body = readNextLine();
			}
			if (header.isEmpty()) {
				break;
			}
			headerList.add(header);
			int colonPos = header.indexOf(':');
			String headerName = header.substring(0, colonPos);
			String headerValue = header.substring(colonPos + 1).trim();

			headers.put(headerName.toLowerCase(), headerValue);
		}
	}

	public int getContentLength() {
		return Integer.parseInt(getHeader("content-length"));
	}

	public String getHeader(String headerName) {
		return headers.get(headerName);
	}

	public Map<String, Runnable> runMethod() {
		Commands commands = new Commands(path.getQuery(), body);
		Map<String, Runnable> runMethod = new HashMap<>();

		runMethod.put("insert", () -> {
			try {
				commands.insert();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		runMethod.put("get", () -> {
			try {
				setReturnBody(commands.get());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		runMethod.put("delete", () -> {
			try {
				commands.delete();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		runMethod.put("update", () -> {
			try {
				commands.update();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		runMethod.put("listall", () -> {
			try {
				setReturnBody(commands.listAll().toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		runMethod.put("reset", () -> commands.resetDb());
		return runMethod;
	}

	private void setReturnBody(String body) {
		this.returnBody = body;
	}

	private void setStatusCode() {
		if (path.getFullPath().equals("/"))
			statusCode = 200;
		else if (path.getValidPath()) {
			statusCode = 200;
		} else
			statusCode = 404;
	}

	public void writeResponse() throws IOException {

		writeLine(output, "HTTP/1.1 " + statusCode + " " + getStatusText(statusCode));
		writeLine(output, "X-Server-Name: Conference Talk Web Server");
		writeLine(output, "Connection: close");
		if (method.equals("POST")) {
			writeLine(output, "Content-Type: application/x-www-form-urlencoded");
		}
		if (method.equals("GET")) {
			body = returnBody;
		}
		writeLine(output, "Content-Length: " + body.length());
		writeLine(output, "");
		writeLine(output, body);
	}

	public String readNextLine() throws IOException {
		StringBuilder nextLine = new StringBuilder();
		int c;
		while ((c = input.read()) != -1) {
			if (c == '\r') {
				input.read();
				break;
			}
			nextLine.append((char) c);
		}
		return nextLine.toString();
	}

	public void writeLine(OutputStream output, String line) throws IOException {
		output.write((line + "\r\n").getBytes());
	}

	public String getStatusText(int statusCode) {
		Map<String, String> statusMessage = new HashMap<>();

		statusMessage.put("200", "OK");
		statusMessage.put("202", "Accepted");
		statusMessage.put("307", "Temporary redirect");
		statusMessage.put("404", "Not Found");
		statusMessage.put("500", "Internal Server Error");

		return statusMessage.get(Integer.toString(statusCode));
	}

}