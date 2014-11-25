package cn.com.gudashi.domain;

import java.io.Serializable;

public class Stock implements Serializable {
	private static final long serialVersionUID = 8996714573460506811L;

	private String code;
	private String name;

	public Stock(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name + "（" + code + "）";
	}
}
