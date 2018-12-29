package com.anjan.bo;

import java.util.List;

import com.anjan.bean.ArticleBean;
import com.anjan.util.WebscrapperUtil;

/**
 * This is BO Class for Webscrapping
 * @author Anjan Arun Bhowmick
 *
 */
public class WebscrapperBOImpl implements WebscrapperBO {

	/**
	 * This method is to get All Authors
	 */
	@Override
	public List<String> getAllAuthors() {
		return WebscrapperUtil.getAllAuthors();
	}

	/**
	 * This method is to get all Articles by Author Name
	 */
	@Override
	public List<ArticleBean> getArticlesByAuthorName(String authorName) {
		return WebscrapperUtil.searchByAuthorName(authorName);
	}

	/**
	 * This method is to get all Articles by Title / Description
	 */
	@Override
	public List<ArticleBean> getArticlesByTitleDescription(String title, String description) {
		return WebscrapperUtil.searchByArticleDesc(title, description);
	}

}
