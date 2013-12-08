package com.techsoc.cultureconnect.rssfeed;

public class RssItem {
	private String title;
	private String link;
	private String pubDate;
	
	public String getTitle() {
		return title;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return title;
	}
	

}
