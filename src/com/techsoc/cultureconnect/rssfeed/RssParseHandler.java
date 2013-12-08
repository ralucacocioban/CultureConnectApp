package com.techsoc.cultureconnect.rssfeed;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RssParseHandler extends DefaultHandler{
	private List<RssItem> rssItems;
	private RssItem currentItem;
	private boolean parsingTitle;
	private boolean parsingLink;
	private boolean parsingDate;
	private int nbNews = 0;
	
	public RssParseHandler() {
		nbNews = 0;
		rssItems = new ArrayList<RssItem>();
	}
	
	public List<RssItem> getItems() {
		return rssItems;
	}
	
	//reads start tag
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(nbNews < 15)
		{
		if("item".equals(qName))
		{
			currentItem=new RssItem();
		}
		else if("title".equals(qName))
		{
			parsingTitle=true;
		}
		else if("link".equals(qName))
		{
			parsingLink=true;
		}
		else if("pubDate".equals(qName))
		{
			parsingDate=true;
		}
	}
	}
	
//reads an end tag
@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
	
	if(nbNews < 15)
	{
		if("item".equals(qName))
		{
			nbNews++;
			rssItems.add(currentItem);
		}
		else if("title".equals(qName))
		{
			parsingTitle=false;
		}
		else if("link".equals(qName))
		{
			parsingLink=false;
		}
		else if("pubDate".equals(qName))
		{
			parsingDate=false;
		}
	}
	}

//reads content between tags
@Override
public void characters(char[] ch, int start, int length) throws SAXException 
	{
		if(parsingTitle)
		{
			if(currentItem!=null)
			{
				currentItem.setTitle(new String(ch,start,length));
				parsingTitle=false;
			}
		}
		if(parsingLink)
		{
			if(currentItem!=null)
			{
				
				currentItem.setLink(new String(ch,start,length));
				parsingLink=false;
			}
		}
		if(parsingDate)
		{
			if(currentItem!=null)
			{
			
				currentItem.setPubDate(new String(ch,start,length));
				parsingDate=false;
			}
		}
	}
}
