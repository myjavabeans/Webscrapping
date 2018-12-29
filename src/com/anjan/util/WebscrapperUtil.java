package com.anjan.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.anjan.bean.ArticleBean;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * This is Util Class for Webscrapper
 * @author Anjan Arun Bhowmick
 *
 */
public class WebscrapperUtil {

	private static final String baseUrl = "https://www.thehindu.com/archive/";

	private static WebClient getWebClient() {
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		return client;
	}

	/**
	 * This method will extract the Year
	 */
	private static Set<String> getAllYear() {

		Set<String> yearSet = new HashSet<String>();

		WebClient client = getWebClient();

		try {
			String url = baseUrl + "web/";

			HtmlPage page = client.getPage(url);

			List<Object> items = page.getByXPath("//h2[@class='archiveH2']");

			if (items.isEmpty()) {
				System.out.println("No Year found !");
			} else {
				for (Object item : items) {
					HtmlElement tempItem = (HtmlElement) item;
					String year = tempItem.asText();
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			client.close();
		}

		return yearSet;

	}

	private static int numberOfDays(String year, int month) {

		int tYear = Integer.parseInt(year);

		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		} else if (month == 2) {
			if (tYear % 400 == 0) {
				return 29;
			} else {
				return 28;
			}
		} else {
			return 30;
		}

	}

	private static String formatInt(int i) {
		if (i > 0 && i < 10) {
			return "0" + i;
		} else {
			return i + "";
		}
	}

	/**
	 * This method will create Month wise link
	 * 
	 * @return
	 */
	private static List<String> getMonthWiseLink() {

		String url = baseUrl + "web/";

		Set<String> year = getAllYear();

		List<String> allMonthWiseUrl = new ArrayList<String>();

		for (String tempYear : year) {

			for (int i = 1; i <= 12; i++) {

				int numberOfDays = numberOfDays(tempYear, i);

				for (int j = 1; j <= numberOfDays; j++) {
					String tUrl = url + tempYear + "/" + formatInt(i) + "/" + formatInt(j);

					allMonthWiseUrl.add(tUrl);
				}

			}

		}

		return allMonthWiseUrl;

	}

	
	private static List<String> getArticleUrls() {

		List<String> urls = getMonthWiseLink();

		List<String> articleUrl = new ArrayList<String>();

		for (String url : urls) {

			System.out.println("Processing URL : " + url);

			WebClient client = getWebClient();

			try {

				HtmlPage page = client.getPage(url);

				List<Object> items = page.getByXPath("//ul[@class='archive-list']");

				if (items.isEmpty()) {
					System.out.println("No Article found !");
				} else {
					for (Object item : items) {
						HtmlElement tempItem = (HtmlElement) item;
						List<Object> itemsUrls = tempItem.getByXPath(".//a");

						for (Object itemUrl : itemsUrls) {
							HtmlAnchor itemAnchor = (HtmlAnchor) itemUrl;
							String tUrl = itemAnchor.getHrefAttribute();

							articleUrl.add(tUrl);
						}
					}
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				client.close();
			}

		}

		return articleUrl;

	}

	private static ArticleBean getArticleDetails(String url, String authorName, String titleArticle,
			String descArticle) {

		WebClient client = getWebClient();

		ArticleBean bean = new ArticleBean();

		try {

			HtmlPage page = client.getPage(url);

			HtmlElement title = ((HtmlElement) page.getFirstByXPath(".//h1[@class='title']"));

			bean.setArticleTitle(title.asText());

			HtmlAnchor author = ((HtmlAnchor) page.getFirstByXPath(".//a[@class='auth-nm lnk']"));
			if (author != null) {
				bean.setAuthor(author.asText());
			} else {
				bean.setAuthor("");
			}

			HtmlElement description = (HtmlElement) page.getFirstByXPath(".//div[@class='article']");

			Iterable<DomElement> elements = description.getChildElements();

			int count = 1;

			for (DomElement element : elements) {
				if (count == 3) {
					bean.setArticleDesc(element.asText());
				}
				++count;
			}

			if (authorName != null && !authorName.isEmpty() && authorName.equals(bean.getAuthor())) {
				return bean;
			} else if (authorName != null && !authorName.isEmpty() && !authorName.equals(bean.getAuthor())) {
				return null;
			}

			if (((titleArticle != null && !titleArticle.isEmpty()) || (descArticle != null && !descArticle.isEmpty()))
					&& (titleArticle.contains(bean.getArticleTitle()) || descArticle.contains(bean.getArticleDesc()))) {
				return bean;
			} else if (((titleArticle != null && !titleArticle.isEmpty())
					|| (descArticle != null && !descArticle.isEmpty()))
					&& (!titleArticle.contains(bean.getArticleTitle())
							&& !descArticle.contains(bean.getArticleDesc()))) {
				return null;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			client.close();
		}

		return bean;

	}

	private static String getAuthor(String url) {

		WebClient client = getWebClient();

		String author = "";

		try {

			HtmlPage page = client.getPage(url);

			HtmlAnchor authors = ((HtmlAnchor) page.getFirstByXPath(".//a[@class='auth-nm lnk']"));
			if (author != null) {
				author = authors.asText();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			client.close();
		}

		return author;

	}

	/**
	 * This method is to get All Author Name
	 * @return - List of Strings
	 */
	public static List<String> getAllAuthors() {

		List<String> lists = getArticleUrls();

		Set<String> setAuthors = new HashSet<String>();

		for (String list : lists) {
			setAuthors.add(getAuthor(list));
		}

		List<String> tempList = new ArrayList<String>();
		tempList.addAll(setAuthors);

		return tempList;

	}

	/**
	 * This method is to search articles based on Author Name
	 * @param authorName - Author Name
	 * @return - Return list of Articles
	 */
	public static List<ArticleBean> searchByAuthorName(String authorName) {

		List<String> lists = getArticleUrls();

		List<ArticleBean> listArticle = new ArrayList<ArticleBean>();

		for (String list : lists) {
			listArticle.add(getArticleDetails(list, authorName, null, null));
		}

		return listArticle;

	}

	/**
	 * This method is to search articles based on title and description
	 * @param title - Title of Article
	 * @param desc - Description of Article
	 * @return - Return list of Articles
	 */
	public static List<ArticleBean> searchByArticleDesc(String title, String desc) {

		List<String> lists = getArticleUrls();

		List<ArticleBean> listArticle = new ArrayList<ArticleBean>();

		for (String list : lists) {
			listArticle.add(getArticleDetails(list, null, title, desc));
		}

		return listArticle;

	}

}
