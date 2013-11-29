/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.techsoc.cultureconnect.navigation;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.techsoc.cultureconnect.R;
import com.techsoc.cultureconnect.talk.BluetoothChat;



public class NavigationActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListLeft;
    private ActionBarDrawerToggle mDrawerToggleLeft;//nav button
    private CharSequence mDrawerTitle;//get the value from the drawer and set title to it
    private CharSequence mTitle;//title of the screen
    private String[] mOptions;//String-array with menu options
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_main_activity);

        mTitle = mDrawerTitle = "";//getTitle();
        mOptions = getResources().getStringArray(R.array.options);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListLeft = (ListView) findViewById(R.id.left_drawer);
       
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerListLeft.setAdapter(new ArrayAdapter<String>(this,
                R.layout.nav_drawer_list_item, mOptions));
        mDrawerListLeft.setOnItemClickListener(new DrawerItemClickListener());
        
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.culture_connect_white);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.CultureConnect)));
        actionBar.setTitle(null);
        // enable ActionBar app icon to behave as action to toggle nav drawer
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        
        mDrawerToggleLeft = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
               mDrawerLayout.setDrawerListener(mDrawerToggleLeft);
       

        if (savedInstanceState == null) {
            selectItem(0);
        }
        
        
        
        
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggleLeft.onOptionsItemSelected(item)) {
            return true;
        }
               
       return super.onOptionsItemSelected(item);

    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        
    	// update the main content by replacing fragments
    	int talkIntent = 0;
    	
        Fragment fragment = null;
    
        
        switch (position){
        
        case 0:
        	fragment = new FactsFragment();
        	break;
        case 1:
        	fragment = new PlacesFragment();
        	break;
        case 2:
        	break;
        case 3:
        	break;
        case 4:
        	break;
        case 5:
        	talkIntent=1;
        	break;
        
        }
        
        if (talkIntent==1) {
        	
        	Intent intent = new Intent(this,BluetoothChat.class);
        	startActivity(intent);
        	        
        }else{
        	
        	 FragmentManager fragmentManager = getFragmentManager();
 	        fragmentManager.beginTransaction()
 	                       .replace(R.id.content_frame, fragment)
 	                       .commit();
 	
 	        
 	        // update selected item and title, then close the drawer
 	        mDrawerListLeft.setItemChecked(position, true);
 	        setTitle(mOptions[position]);
 	        mDrawerLayout.closeDrawer(mDrawerListLeft);
         
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggleLeft.syncState();
       
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggleLeft.onConfigurationChanged(newConfig);
       
    }

    
    public static class PlacesFragment extends Fragment {
        public PlacesFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
        	View layoutView = inflater.inflate(R.layout.nav_places_layout, container, false);      	
            
        	Button helloButton = (Button) layoutView.findViewById(R.id.places_hello_button);
        	
        	helloButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					v.setBackgroundColor(Color.CYAN);		
				}
			});
        	
            getActivity().setTitle("Places");
            
            
            return layoutView;
        }
    
    }
    
    public static class FactsFragment extends Fragment {
        public FactsFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
        	View rootView = inflater.inflate(R.layout.nav_fragment_option, container, false);      	
            
            getActivity().setTitle("Facts");
            return rootView;
        }
    
    }
    
    
    
    
    
   
    
    
}