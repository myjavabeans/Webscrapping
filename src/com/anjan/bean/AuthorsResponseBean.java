package com.anjan.bean;

import java.util.List;

/**
 * This class will list all the authors
 * @author bhoan02
 *
 */
public class AuthorsResponseBean {
	
	private List<String> authorsName;
	private String status;
	private int code;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setAuthorsName(List<String> authorsName) {
		this.authorsName = authorsName;
	}

	public List<String> getAuthorsName() {
		return authorsName;
	}

}
