package com.techsoc.cultureconnect.countrypicker;

import java.util.ArrayList;
import java.util.Collections;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.techsoc.Language.Language;
import com.techsoc.Language.LanguagePreferences;
import com.techsoc.cultureconnect.R;
import com.techsoc.cultureconnect.navigation.NavigationActivity;


public class CountryPickerActivity extends Activity {
	
	LanguagePreferences language;
	Language languageLibrary;
	
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_picker);
        
        // access to language preferences
        language = new LanguagePreferences(getApplicationContext());
        languageLibrary = new Language();
                
        ArrayList<String> countries = languageLibrary.getCountries(); // new array list to store countries
         	
     
        Collections.sort(countries); // sort countries
        
        final AutoCompleteTextView mCountryPickerAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.countryPickerTextView); // get reference to AutoComleteTextView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        mCountryPickerAutoCompleteTextView.setAdapter(arrayAdapter);
        
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.culture_connect_white);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.CultureConnect)));
        actionBar.setTitle(null);
        
        
        
        TextView mTextView = (TextView) findViewById(R.id.textView1); 
        mTextView.setText("At this point only United Kingdom, Germany, Russia and Italy are availbale"); 
     
        // can be used to show number of countries available:
        //mTextView.setText("There are " + countries.size() + " countries."); 
        
        Button mConfirmButton = (Button)findViewById(R.id.confirmButton);
        final Intent intent = new Intent(this, NavigationActivity.class);
        
        
        mConfirmButton.setOnClickListener(new OnClickListener(){
	
			@Override
			public void onClick(View v) { 
				 
				// TODO Auto-generated method stub
				String inputCountry = mCountryPickerAutoCompleteTextView.getText().toString();
				if (languageLibrary.doWeHaveThisCountry(inputCountry)) {
					language.setHomeCountry(inputCountry);
					startActivity(intent);
				}
				else {
					returnMessage();
				}
			}        	
        });
        
    }
    
    private void returnMessage() {
    	Toast.makeText(this, "Sorry, at this point only United Kingdom, Germany, Russia and Italy are availbale", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.country_picker, menu);
        return true;
    }
    
}
