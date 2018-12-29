package com.anjan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anjan.bean.ArticleBean;
import com.anjan.bean.ArticlesResponseBean;
import com.anjan.bean.AuthorsResponseBean;
import com.anjan.bo.WebscrapperBO;

@RestController
public class WebscrapperController {
	
	private final String sharedKey = "SHARED_KEY";
	
	private static final int CODE_SUCCESS = 100;
	private static final int CODE_ERROR = 102;
	private static final String SUCCESS_STATUS = "success";
	private static final String ERROR_STATUS = "error";
	
	private WebscrapperBO webscrapperBO;
	
	@Autowired(required = true)
	@Qualifier(value = "webscrapperBO")
	public void setWebscrapperBO(WebscrapperBO webscrapperBO) {
		this.webscrapperBO = webscrapperBO;
	}

	/**
	 * This method will be invoked to get all authors
	 * @param key - API Key
	 * @return - JSON Response
	 */
	@RequestMapping(value = "/getAllAuthors", method = RequestMethod.POST)
	public AuthorsResponseBean getAllAuthors(@RequestParam(value = "key") String key) {

		AuthorsResponseBean response = new AuthorsResponseBean();

		if (sharedKey.equals(key)) {

			List<String> authors = webscrapperBO.getAllAuthors();
			response.setAuthorsName(authors);
			response.setStatus(SUCCESS_STATUS);
			response.setCode(CODE_SUCCESS);

		} else {

			response.setStatus(ERROR_STATUS);
			response.setCode(CODE_ERROR);
		}

		return response;
	}
	
	/**
	 * This method will be invoked to get all articles by Author
	 * @param key - API Key
	 * @param bean - Request Bean
	 * @return - JSON Response
	 */
	@RequestMapping(value = "/getArticleByAuthor", method = RequestMethod.POST)
	public ArticlesResponseBean getArticlesByAuthor(@RequestParam(value = "key") String key, @RequestBody ArticleBean bean) {

		ArticlesResponseBean response = new ArticlesResponseBean();

		if (sharedKey.equals(key)) {

			List<ArticleBean> articles = webscrapperBO.getArticlesByAuthorName(bean.getAuthor());
			response.setListArticle(articles);
			response.setStatus(SUCCESS_STATUS);
			response.setCode(CODE_SUCCESS);

		} else {

			response.setStatus(ERROR_STATUS);
			response.setCode(CODE_ERROR);
		}

		return response;
	}
	
	/**
	 * This method will be invoked to get all articles by Title/Description
	 * @param key - API Key
	 * @param bean - Request Bean
	 * @return - JSON Response
	 */
	@RequestMapping(value = "/getArticleByTitle", method = RequestMethod.POST)
	public ArticlesResponseBean getArticlesByTitle(@RequestParam(value = "key") String key, @RequestBody ArticleBean bean) {

		ArticlesResponseBean response = new ArticlesResponseBean();

		if (sharedKey.equals(key)) {

			List<ArticleBean> articles = webscrapperBO.getArticlesByTitleDescription(bean.getArticleTitle(), bean.getArticleDesc());
			response.setListArticle(articles);
			response.setStatus(SUCCESS_STATUS);
			response.setCode(CODE_SUCCESS);

		} else {

			response.setStatus(ERROR_STATUS);
			response.setCode(CODE_ERROR);
		}

		return response;
	}

}
