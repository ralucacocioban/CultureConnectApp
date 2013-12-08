package com.techsoc.cultureconnect.singletalk;

import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.techsoc.cultureconnect.R;
import com.techsoc.cultureconnect.navigation.NavigationActivity;
import com.techsoc.cultureconnect.talk.Translator;

/**
 * 
 * @author Michael Single device chat supporting conversation within one
 *         Activity without the need for Bluetooth.
 */

public class SingleDeviceChatActivity extends Activity implements
		ChatFragment.OnMessageReceivedListener {
	// TODO: Going to need to rename this, this doesn't even use Bluetooth

	private final static String TAG = SingleDeviceChatActivity.class
			.getName();
	private final static int FIRST_FRAG = 0;
	private final static int SECOND_FRAG = 1;

	private int mCurrentFragment = FIRST_FRAG;

	private ChatFragment mFirstFrag;
	private ChatFragment mSecondFrag;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singletalk_main_single_device);
		
		
		FragmentManager fm = this.getFragmentManager();
		mFirstFrag = (ChatFragment) fm
				.findFragmentById(R.id.talk_messaging_unit_1);
		mSecondFrag = (ChatFragment) fm
				.findFragmentById(R.id.talk_messaging_unit_2);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.CultureConnect)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Single Device Talk");

	}
	
	   @Override
	    public void onBackPressed(){
	    	super.onBackPressed();
	    	Intent intent = new Intent(this, NavigationActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	startActivity(intent);
	    }
	    
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case android.R.id.home:
			Intent intentHome = new Intent(this, NavigationActivity.class);
	        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       	startActivity(intentHome);
	        return true;
	    }
	    return false;
    }
	
	private void setCurrentFragment(int fragmentNumber) {
	  mCurrentFragment = fragmentNumber;
    }
	
	@Override
	public void onMessageReceived(ChatMessage chatMessage) {
	// Checks which fragment
	Log.d(TAG, chatMessage.getMessage());
	String receivedMessage = chatMessage.getMessage();
	if (chatMessage.getFromId() == R.id.talk_messaging_unit_1) {
		String receivedLanguage = chatMessage.getLanguage();
		String toLanguage = mSecondFrag.getLanguage();
		this.setCurrentFragment(SECOND_FRAG);
		new TranslateText().execute(receivedMessage, receivedLanguage,
				toLanguage);
	} else {
		String receivedLanguage = chatMessage.getLanguage();
		String toLanguage = mFirstFrag.getLanguage();
		this.setCurrentFragment(FIRST_FRAG);
		new TranslateText().execute(receivedMessage, receivedLanguage,
				toLanguage);
	}
}

	class TranslateText extends AsyncTask<String, Void, String> {

		// Takes three parameters the text that needs to be translated, the
		// input and output languages

		@Override
		protected String doInBackground(String... languageParams) {
			Translator translator = new Translator(languageParams[1],
					languageParams[2]);
			Log.d("Language 0", languageParams[1]);
			Log.d("Language 1", languageParams[2]);

			String translatedText = "";

			try {
				translatedText = translator.Translate(languageParams[0]);
			} catch (IOException e) {
				Log.e("Translate", "IOException");
				e.printStackTrace();
			}
			return translatedText;
		}

		@Override
		protected void onPostExecute(String translatedText) {
			// TODO Things to do with translated Text
			// Below an example for text to speech using the translated Text
			
			if (mCurrentFragment == SingleDeviceChatActivity.SECOND_FRAG) {
				SingleDeviceChatActivity.this.mSecondFrag
						.addMessage(translatedText);
			} else {
				SingleDeviceChatActivity.this.mFirstFrag
						.addMessage(translatedText);
			}
		}

	
		
	}
	

	 
}