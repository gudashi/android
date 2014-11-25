package cn.com.gudashi.utils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlUtils {
	public static String addParam(String url, String name, String value) throws UnsupportedEncodingException {
		int anchorStart = url.indexOf('#');
		if(anchorStart < 0){
			anchorStart = url.length();
		}

		int queryStart = url.indexOf('?');
		boolean hasQuery = queryStart >= 0 && queryStart < anchorStart;

		StringBuilder buf = new StringBuilder();
		buf.append(url, 0, anchorStart);
		if(hasQuery){
			buf.append('&');
		}else{
			buf.append('?');
		}
		buf.append(URLEncoder.encode(name, "UTF-8"));
		buf.append('=');
		buf.append(URLEncoder.encode(value, "UTF-8"));
		if(anchorStart < url.length()){
			buf.append(url, anchorStart, url.length());
		}
		return buf.toString();
	}

	public static String buildParam(String name, String value) throws UnsupportedEncodingException {
		StringBuilder buf = new StringBuilder();
		buf.append(URLEncoder.encode(name, "UTF-8"));
		buf.append('=');
		buf.append(URLEncoder.encode(value, "UTF-8"));
		return buf.toString();
	}
}
