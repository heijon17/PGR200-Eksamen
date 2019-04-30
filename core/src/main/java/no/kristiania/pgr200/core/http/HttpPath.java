package no.kristiania.pgr200.core.http;

public class HttpPath {

	private String path, fullPath;
	private HttpQuery query;
	private boolean validPath = true;

	public HttpPath(String path) {
		this.fullPath = path;
		checkPath(path);
	}

	public void checkPath(String path) {
		if (path.startsWith("-invalid:")) {
			this.path = path;
			this.fullPath = path;
		} else if (path.startsWith("/api")) {
			int qPos = path.indexOf('?');
			if (qPos > 0) {
				this.path = path.substring(0, qPos);
				query = new HttpQuery(path.substring(qPos + 1));
			} else {
				this.path = path;
				setValidPath(false);
			}
		} else {
			setValidPath(false);
		}

	}

	public String getFullPath() {
		return fullPath;
	}

	public HttpQuery getQuery() {
		return query;
	}

	public String getPath() {
		return path;
	}

	public void setValidPath(boolean valid) {
		this.validPath = valid;
	}

	public boolean getValidPath() {
		return validPath;
	}

	public void setParameter(String paramName, String paramValue) {
		if (query == null) {
			query = new HttpQuery(path);
			getQuery().getParameters().put(paramName, paramValue);
		}
		if (!fullPath.contains("?")) {
			fullPath += "?";
		}
		fullPath += paramName + "=" + paramValue;
	}

	@Override
	public String toString() {
		return fullPath;
	}

}
