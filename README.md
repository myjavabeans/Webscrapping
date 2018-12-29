# Webscrapping
Webscrapping

--------------------------
Steps to Install -
--------------------------

1. Build the project using maven build
2. Deploy the war using tomcat
3. To Search available Authors -
	<HOSTNAME>:<PORT_NUMBER>/Webscrapping/getAllAuthors?key=SHARED_KEY
	Request method - POST
	Body Content Type - application/json
	
4. To Search articles based on Author Name -
	<HOSTNAME>:<PORT_NUMBER>/Webscrapping/getArticleByAuthor?key=SHARED_KEY
	
	Request method - POST
	Body Content Type - application/json
	Property needs to be set - 
	author - Author Name 

5. To Search articles based on Article Title and Description
	<HOSTNAME>:<PORT_NUMBER>/Webscrapping/getArticleByTitle?key=SHARED_KEY
	
	Request method - POST
	Body Content Type - application/json
	Property needs to be set - 
	articleTitle - Title of the Article
	articleDesc - Description of the Article