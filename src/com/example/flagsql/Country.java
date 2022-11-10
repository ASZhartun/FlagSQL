package com.example.flagsql;

public class Country {

	private String code;
	private String name;
	private byte[] flag;

	public Country(String code, String name, byte[] flag) {
		this.code = code;
		this.name = name;
		this.flag = flag;
	}

	public Country() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getFlag() {
		return flag;
	}

	public void setFlag(byte[] flag) {
		this.flag = flag;
	}

}
