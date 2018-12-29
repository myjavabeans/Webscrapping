package com.anjan.bo;

import java.util.List;

import com.anjan.bean.ArticleBean;

public interface WebscrapperBO {

	public List<String> getAllAuthors();
	public List<ArticleBean> getArticlesByAuthorName(String authorName);
	public List<ArticleBean> getArticlesByTitleDescription(String title, String description);
	
}
