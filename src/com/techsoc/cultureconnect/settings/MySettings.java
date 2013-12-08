package com.techsoc.cultureconnect.settings;

import java.util.ArrayList;
import java.util.Collections;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.techsoc.Language.Language;
import com.techsoc.Language.LanguagePreferences;
import com.techsoc.cultureconnect.R;
import com.techsoc.cultureconnect.navigation.NavigationActivity;
import com.techsoc.cultureconnect.talk.BluetoothChat;

public class MySettings extends Activity {

	 LanguagePreferences language;
	 String previousClass="";
	 Spinner settingsLanguageSpinner;
	 
	 Language languageLibrary;
	 
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.settings);
	        
	        // access to language preferences
	        language = new LanguagePreferences(getApplicationContext());
	        String myCountry = language.getHomeCountry();
	        String myLanguage = language.getHomeLanguage();
	        
	        
	        settingsLanguageSpinner = (Spinner) findViewById(R.id.settingsLanguages);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter
					.createFromResource(this, R.array.languages,
							android.R.layout.simple_spinner_item);
			adapter
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			settingsLanguageSpinner.setAdapter(adapter);
			
			Language languageCodeLibrary = new Language();
			settingsLanguageSpinner
			   .setSelection(adapter.getPosition(languageCodeLibrary.getLanguageFromCode(myLanguage)));
			
	       			
	        Intent prev_intent = getIntent();
	        previousClass = prev_intent.getStringExtra("previous_class");
	        	        
	        
	        languageLibrary = new Language();
	        ArrayList<String> countries = languageLibrary.getCountries(); // new array list to store countries
	        Collections.sort(countries); // sort countries
	       
	    	
	        final AutoCompleteTextView countryFinder = (AutoCompleteTextView) findViewById(R.id.countryPickerSettings);
	        countryFinder.setText(myCountry);
	        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
	        countryFinder.setAdapter(arrayAdapter);
	        countryFinder.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					countryFinder.setText("");
				}
	        	
	        });
	        
	        
	        Button saveSettings = (Button) findViewById(R.id.saveButton);
	        
	        final Intent intent;
	        if (previousClass.equals("Navigation")) {
	        	intent = new Intent(this, NavigationActivity.class);
	        }
	        else {
	        	intent = new Intent(this, BluetoothChat.class);
	        }
	        
	        saveSettings.setOnClickListener(new OnClickListener(){
	        	
				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					String inputCountry = countryFinder.getText().toString();
					if (languageLibrary.doWeHaveThisCountry(inputCountry)) {
						language.setHomeCountry(inputCountry);
						language
						   .setHomeLanguage(languageLibrary.getLanguageCode(settingsLanguageSpinner.getSelectedItem().toString()));
						
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        	startActivity(intent);
					}
					else {
						returnMessage();
					}
					
		        }
	        	
	        });
	        
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.setIcon(R.drawable.culture_connect_white);
	        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.CultureConnect)));
	        actionBar.setTitle("Settings");
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setHomeButtonEnabled(true);
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
	 
	 
	 private void returnMessage() {
		 Toast.makeText(this, "Sorry, at this point only United Kingdom, Germany, Russia and Italy are availbale", Toast.LENGTH_SHORT).show();

	 }
	 
	 

}
