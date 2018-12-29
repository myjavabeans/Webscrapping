package com.anjan.bo;

import java.util.List;

import com.anjan.bean.ArticleBean;
import com.anjan.util.WebscrapperUtil;

public class WebscrapperBOImpl implements WebscrapperBO {

	@Override
	public List<String> getAllAuthors() {
		return WebscrapperUtil.getAllAuthors();
	}

	@Override
	public List<ArticleBean> getArticlesByAuthorName(String authorName) {
		return WebscrapperUtil.searchByAuthorName(authorName);
	}

	@Override
	public List<ArticleBean> getArticlesByTitleDescription(String title, String description) {
		return WebscrapperUtil.searchByArticleDesc(title, description);
	}

}
