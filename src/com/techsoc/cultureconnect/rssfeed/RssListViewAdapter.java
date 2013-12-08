package com.techsoc.cultureconnect.rssfeed;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.techsoc.Language.LanguagePreferences;
import com.techsoc.cultureconnect.R;

public class RssListViewAdapter extends BaseAdapter {

	private Context context;

	private List<RssItem> listItems;

	private TextView tvTitle;
	private TextView tvDatePosted;

	LanguagePreferences languagePreferences;

	public RssListViewAdapter(Context context, List<RssItem> listItems) {
		this.context = context;
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		RssItem currentItem = listItems.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(
					R.layout.nav_myfragment_layout_rss_row, null);
		}

		tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

		tvDatePosted = (TextView) convertView.findViewById(R.id.tvDatePosted);

		tvTitle.setText(currentItem.toString());
		tvDatePosted.setText(currentItem.getPubDate());
		//Log.v("ListViewAdapter", "PubDate: " + currentItem.getPubDate());
		return convertView;
	}

}
