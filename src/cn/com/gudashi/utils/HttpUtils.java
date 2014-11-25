package cn.com.gudashi.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	public static String getForString(String url) throws IOException {
		return getStringAndClose(openConnection(url));
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
			return IoUtils.toString(conn.getInputStream(), charset);
		}finally{
			conn.disconnect();
		}
	}
}
