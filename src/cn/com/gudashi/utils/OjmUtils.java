package cn.com.gudashi.utils;

import org.codehaus.jackson.map.ObjectMapper;

public class OjmUtils {
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T fromJson(String json, Class<T> clazz) throws Exception {
		return objectMapper.readValue(json, clazz);
	}

	public static String toJson(Object bean) throws Exception {
		return objectMapper.writeValueAsString(bean);
	}
}
