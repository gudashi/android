package cn.com.gudashi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class IoUtils {
	public static String toString(InputStream inputStream, String charset) throws IOException {
		InputStreamReader reader = charset != null ? new InputStreamReader(inputStream, charset) : new InputStreamReader(inputStream);
		String str = null;
		CharBuffer buf = CharBuffer.allocate(1024);
		while(reader.read(buf) != -1){
			buf.flip();
			str = str == null ? buf.toString() : (str + buf.toString());
			buf.clear();
		}
		reader.close();
		return str;
	}

	public static String toString(InputStream inputStream) throws IOException {
		return toString(inputStream, null);
	}
}
