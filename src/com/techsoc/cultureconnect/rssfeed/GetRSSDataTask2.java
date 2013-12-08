package com.techsoc.cultureconnect.rssfeed;

import java.io.IOException;
import java.util.List;

import com.techsoc.Language.LanguagePreferences;
import com.techsoc.cultureconnect.R;
import com.techsoc.cultureconnect.talk.Translator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class GetRSSDataTask2 extends AsyncTask<String, Void, List<RssItem> > {
	
	private ProgressDialog dialog;
	
	private Activity activity;
	public GetRSSDataTask2(Activity activity)
	{
		this.activity=activity;
	}
	@Override
	protected void onPreExecute() {
		dialog=new ProgressDialog(activity);
		dialog.setCancelable(true);
		dialog.setMessage("loading...");
		dialog.show();
	}

	@Override
	protected List<RssItem> doInBackground(String... urls) {
		
		try {
			// Create RSS reader
			RssReader rssReader = new RssReader(urls[0]);
		
			// Parse RSS, get items
			List<RssItem> result = rssReader.getItems();
			
			//Taking every RSS item and translating it
			for (int i = 0; i < result.size();i++){
				
				RssItem rssItem = result.get(i);
				
				LanguagePreferences languagePreferences = new LanguagePreferences(this.activity);
				String inputLanguage = languagePreferences.getHomeLanguage();
				String currentCountry= languagePreferences.getHomeCountry();
				
				String outputLanguage;
				
				
				if (currentCountry.equals("United Kingdom"))
				    outputLanguage = "en";
				else if (currentCountry.equals("Germany"))
					outputLanguage = "de"; //de
				else if (currentCountry.equals("Russia"))
					outputLanguage = "ru"; //ru
				else if (currentCountry.equals("Italy"))
					outputLanguage = "it"; //it
				
				else outputLanguage = "en";
				
				Translator translator = new Translator(outputLanguage, inputLanguage);
				
				Log.v("RSSTask", "Before: " + rssItem.getPubDate());
				try {
		   		 rssItem.setTitle(translator.Translate(rssItem.getTitle()));
		   		 rssItem.setPubDate(translator.Translate(rssItem.getPubDate()));
		   		 
		   		Log.v("RSSTask", "After: " +rssItem.getPubDate());
		   		 
				} catch (IOException e) {
					Log.e("Translate", "IOException");
					e.printStackTrace();
				}
				
				result.set(i, rssItem);
				
			}
			
			return result;
			
		} catch (Exception e) {
			Log.e("ITCRssReader", e.getMessage());
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(List<RssItem> result) {
		
		dialog.dismiss();
		// Get a ListView from main view
		ListView listItems = (ListView) this.activity.findViewById(R.id.listMainView);
		
		
		RssListViewAdapter adapter = new RssListViewAdapter(activity, result);
		// Create a list adapter
		//ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(local,android.R.layout.simple_list_item_1, result);
		// Set list adapter for the ListView
		listItems.setAdapter(adapter);
					
		// Set list view item click listener
		listItems.setOnItemClickListener(new ListListener(result, activity));
	}
}
