package uk.co.kyleharrison.ehealth.service.xml.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class URLReader {

	private String url;
	private String response;
	
	public URLReader() {
		super();
	}
	
	public URLReader(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResponse() {
		return response;
	}
	
	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public String readxmlFromUrl() throws IOException {
		
		InputStream is = new URL(this.url).openStream();

		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
			this.response = readAll(rd);

			return this.response;
		} finally {
			is.close();
		}
	}
	
	public void resetURLReader(){
		this.url=null;
		this.response=null;
	}
	
}
