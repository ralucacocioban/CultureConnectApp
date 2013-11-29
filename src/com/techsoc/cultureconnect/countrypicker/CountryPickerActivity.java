package com.techsoc.cultureconnect.countrypicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import com.techsoc.cultureconnect.R;
import com.techsoc.cultureconnect.navigation.NavigationActivity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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


public class CountryPickerActivity extends Activity {
	

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_picker);
        
        Locale[] locales = Locale.getAvailableLocales(); // get all locales available
        ArrayList<String> countries = new ArrayList<String>(); // new array list to store countries
        for (Locale l : locales) {
        	String country = l.getDisplayCountry(); // get country name
        	if (country.trim().length() > 0 && !countries.contains(country)) { // country is not already in the array
        		countries.add(country); // add country to list
        	}
        }
        
        if(!countries.contains("Armenia")) { // just because // LOL
        	countries.add("Armenia");
        }
        
        Collections.sort(countries); // sort countries
        
        AutoCompleteTextView mCountryPickerAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.countryPickerTextView); // get reference to AutoComleteTextView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        mCountryPickerAutoCompleteTextView.setAdapter(arrayAdapter);
        
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.culture_connect_white);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9b00c2")));
        actionBar.setTitle(null);
        
        
        //TextView mTextView = (TextView)findViewById(R.id.textView1); // get reference to TextView
        //mTextView.setText("There are " + countries.size() + " countries."); // set text of TextView
        
        Button mConfirmButton = (Button)findViewById(R.id.confirmButton);
        final Intent intent = new Intent(this, NavigationActivity.class);
        
        mConfirmButton.setOnClickListener(new OnClickListener(){

        	
			@Override
			public void onClick(View v) {
				 
				// TODO Auto-generated method stub
				//intent.putExtra("big_string", finalData1);
				startActivity(intent);
			}
        	
        });
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.country_picker, menu);
        return true;
    }
    
}
