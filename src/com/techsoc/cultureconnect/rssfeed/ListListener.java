package com.techsoc.cultureconnect.rssfeed;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class ListListener implements OnItemClickListener{

		// List item's reference
		List<RssItem> listItems;
		// Calling activity reference
		Activity activity;
		
		public ListListener(List<RssItem> aListItems, Activity anActivity) {
			listItems = aListItems;
			activity  = anActivity;
		}
		public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(listItems.get(pos).getLink()));
			activity.startActivity(intent);
			
		}
}
