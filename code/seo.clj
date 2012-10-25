(ns seo)
(use 'jsoup.soup)
(use 'clojure.pprint)
(require '[clj-http.client :as client])
(import 'java.net.URLDecoder)

; http://www.blueglass.com/blog/google-search-url-parameters-query-string-anatomy/

(def user-agent 
	"Mozilla/5.0 (Windows; U; WindowsNT 5.1; ja-JP; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")

(def targets 
	{
	  :google
		{
		:base "https://www.google.co.jp/search?"
		:selector "cite"
		:query (fn[k] {"num" 20 "ie" "utf-8" "q" k})
		:clean (fn[e] (.replaceAll (.html e) "<b>|</b>" ""))
		}	
	  :yahoo
	  	{
	  	:base  "http://search.yahoo.co.jp/search?"
	  	:selector "li em"
	  	:query (fn[k] {"search.x" 1 "fr" "top_ga1_sa" "tid" "top_ga1_sa" "ei" "UTF-8" "p" k})
	  	:clean (fn[e] (.replaceAll (.html e) "<b>|</b>" ""))
	  	}
	   :bing
	    {
	    :base "http://www.bing.com/search?"
	    :query (fn[k]  {"x" 0 "y" 0 "form" "MSNH69" "qs" "n" "mkt" "ja-jp" "q" k})
	    :selector "h3 a"
	    :clean (fn[e] (.attr e "href"))
	    }
	    :goo 
	    {
	    :base "http://search.goo.ne.jp/web.jsp?"
	    :query (fn[k] {"MT" k "bt_search.x" 0 "bt_search.y" 0 "STYPE" "web" "SH" 1 "IE" "UTF-8" "OE" "UTF-8" "from" "gootop"})
	    :selector "a.g[target]"
	    :clean (fn[e] (.attr e "href"))
	    }
	    :fresheye  ; naver ;)
	    {
	    :base "http://search.fresheye.com/?"
	    :query (fn[k] {"kw" k "x" 0 "y" 0 "type" 1 "ord" "s" "rt" "web" "cs" "utf8"})
	    :selector "div.rslt p.title a"
	    :clean (fn[e] (URLDecoder/decode (last (re-find #"go=(.*)\&srch=" (.attr e "href"))) "UTF-8")) ; the href is encoded in a search query :(
		}
	}
)

; prepare the query
(defn query-string[target words]
	(str (target :base) (client/generate-query-string ((target :query) words))))

; run the query
(defn run[target keywords]
 (client/get (query-string target keywords)
  {:client-params {"http.useragent" user-agent }}))

; suppose we have an answer with no error
(defn fetch[target keywords]
	((run target keywords) :body))
; turn answer into something ready to be parsed
(defn fetch-doc[target keywords]
	(parse (fetch target keywords)))
; apply selector and clean up each element found
(defn lookup[target docme]
	(pmap (target :clean) (select (target :selector) docme)))

; top method to execute a query with given keywords
(defn query[target keywords]
	(lookup target (fetch-doc target keywords)))

; testing
(def target (ref (targets :fresheye)))
(defn q[keywords] (query @target keywords))
(defn p[keywords] (pprint (q keywords)))
(defn d[keywords] (fetch-doc @target keywords))
(defn p[keywords t] (pprint (query (targets t) keywords)))