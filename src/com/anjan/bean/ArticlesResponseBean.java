package com.anjan.bean;

import java.util.List;

/**
 * This is response class to return all the articles and description
 * @author Anjan Arun Bhowmick
 *
 */
public class ArticlesResponseBean {
	
	private List<ArticleBean> listArticle;
	private String status;
	private int code;
	
	public List<ArticleBean> getListArticle() {
		return listArticle;
	}
	public void setListArticle(List<ArticleBean> listArticle) {
		this.listArticle = listArticle;
	}
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
	
	
}
