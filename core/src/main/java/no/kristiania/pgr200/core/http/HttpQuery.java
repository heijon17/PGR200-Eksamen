package no.kristiania.pgr200.core.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpQuery {

	private String fullQuery;
	private Map<String, String> parameters = new LinkedHashMap<>();

	public HttpQuery(String query) {
		setFullQuery(query);
		parseQuery(query);
	}

	private void parseQuery(String query) {
		for (String parameter : query.split("&")) {
			if (parameter.contains("=")) {
				int ePos = parameter.indexOf("=");
				String paramName = parameter.substring(0, ePos);
				String paramValue = parameter.substring(ePos + 1);

				paramName = urlDecode(paramName);
				paramValue = urlDecode(paramValue);

				getParameters().put(paramName.toLowerCase(), paramValue);
			}
		}
	}

	public String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String urlDecode(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String getParameter(String query) {
		return getParameters().get(query);
	}

	@Override
	public String toString() {
		if (getParameters().isEmpty()) {
			return null;
		}
		StringBuilder result = new StringBuilder();

		for (String key : getParameters().keySet()) {
			if (result.length() > 0) {
				result.append("&");
			}
			result.append(urlEncode(key)).append("=").append(urlEncode(getParameters().get(key)));
		}
		return result.toString();
	}

	public String getFullQuery() {
		return fullQuery;
	}

	public void setFullQuery(String fullQuery) {
		this.fullQuery = fullQuery;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
