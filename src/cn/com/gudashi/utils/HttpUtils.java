package cn.com.gudashi.utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class HttpUtils {
	public static String getForString(String url) throws IOException {
		return getStringAndClose(openConnection(url));
	}

	public static <Resp> Resp getForJson(String url, Class<Resp> respClass) throws Exception {
		String json = getStringAndClose(openConnection(url));
		Resp resp = OjmUtils.fromJson(json, respClass);
		return resp;
	}
	
	public static <Req, Resp> Resp postJsonForJson(String url, Req req, Class<Resp> respClass) throws Exception {
		String json = OjmUtils.toJson(req);
		HttpURLConnection conn = openConnection(url);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		IOUtils.copy(new StringReader(json), out);
		out.close();
		json = getStringAndClose(conn);
		return OjmUtils.fromJson(json, respClass);
	}

	private static HttpURLConnection openConnection(String url) throws IOException {
		return (HttpURLConnection) new URL(url).openConnection();
	}

	private static String getStringAndClose(HttpURLConnection conn) throws IOException {
		try{
			String charset = null;
			String contentType = conn.getContentType();
			if(contentType != null){
				int idx = contentType.indexOf("charset=");
				if(idx > 0){
					charset = contentType.substring(idx + "charset=".length());
				}
			}
			return IOUtils.toString(conn.getInputStream(), charset);
			
		}finally{
			conn.disconnect();
		}
	}
}
