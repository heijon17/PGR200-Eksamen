package no.kristiania.pgr200.http.client;

import no.kristiania.pgr200.core.http.HttpPath;
import no.kristiania.pgr200.core.http.HttpQuery;

public class CommandLineParser {

	private String[] args;
	private String method;
	private DateAndTimeParser timeParser = new DateAndTimeParser();

//	this class takes a String Array and creates a HTTP path with URL encoding
	public CommandLineParser(String[] args) {
		this.args = args;
	}

	public String getParsedPath() {
		HttpPath path = parse();
		if (path.getPath().startsWith("-invalid:")) {
			return parse().getPath();
		}
		return path.getPath() + "?" + path.getQuery().toString();
	}

	public String getParsedQuery() {
		return parse().getQuery().toString();
	}

	public HttpQuery getQuery() {
		return parse().getQuery();
	}

	public HttpPath parse() {

		if (args.length > 0) {
			switch (args[0]) {
			case "insert":
				setMethod("POST");
				return multiArg(args);
			case "get":
				setMethod("GET");
				return singleArg(args);
			case "delete":
				setMethod("POST");
				return singleArg(args);
			case "update":
				setMethod("POST");
				return multiArg(args);
			case "listall":
				setMethod("GET");
				return zeroArg(args);
			case "reset":
				setMethod("GET");
				return zeroArg(args);
			default:
				setMethod(null);
				return incorrectCommand();
			}
		}
		return incorrectCommand();
	}

	private HttpPath multiArg(String[] inputArray) {
		if ((inputArray.length < 4 || inputArray.length > 10) || !((inputArray.length % 2) == 0)) {
			return incorrectCommand();
		} else {
			return new HttpPath(getMultiArgPathString(inputArray));
		}
	}

	private HttpPath singleArg(String[] inputArray) {
		if (inputArray.length != 4)
			return incorrectCommand();
		else {
			return new HttpPath(getSingleArgPathString(args[0], args[1], args[2], args[3]));
		}
	}

	private HttpPath zeroArg(String[] inputArray) {
		if (inputArray.length == 2)
			return new HttpPath(getZeroArgPathString(args[0], args[1]));
		else if (inputArray[0].equals("reset"))
			return new HttpPath("/api?method=reset");
		else {
			return incorrectCommand();
		}
	}

	private String getMultiArgPathString(String[] inputArray) {
		String result = "/api?method=" + args[0] + "&" + "table=" + args[1] + "&";

		for (int i = 3; i < inputArray.length; i += 2) {

			if (args[i - 1] == "-date") {
				result += args[i - 1].substring(1) + "=" + timeParser.formatDateString(args[i]);

			} else if (args[i - 1] == "-timeFrom" || args[i - 1] == "-timeTo") {
				result += args[i - 1].substring(1) + "=" + timeParser.formatTimeString(args[i]);

			} else {
				result += args[i - 1].substring(1) + "=" + args[i];
			}

			if (i < args.length - 1)
				result += "&";
		}

		return new HttpPath(result).getFullPath();
	}

	private String getSingleArgPathString(String method, String table, String key, String value) {
		return "/api?method=" + method + "&table=" + table + "&" + key.substring(1) + "=" + value;
	}

	private String getZeroArgPathString(String method, String table) {
		return "/api?method=" + method + "&table=" + table;
	}

	public String getPathWithOnlyMethodAndTable() {
		String path = getParsedPath();
		return path.substring(0, path.indexOf("&", path.indexOf("&") + 1));
	}

	private HttpPath incorrectCommand() {
		HttpPath path = new HttpPath("-invalid:invalid command, write 'help' to see valid commands");
		path.setValidPath(false);
		return path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
