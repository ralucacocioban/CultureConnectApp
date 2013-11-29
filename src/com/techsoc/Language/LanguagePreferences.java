package com.techsoc.Language;

import android.content.Context;
import android.content.SharedPreferences;

public class LanguagePreferences {

 Context context;
 SharedPreferences langaugePreferences;
 private static final String HOME_LANGUAGE_ID = "home_language";
 private static String defaultLanguage = "en";

 public LanguagePreferences(Context context){
  
  this.context = context;
  langaugePreferences = context.getSharedPreferences("USER_LANGUAGE_PREFERENCES", 0);

 }
 
 public String getHomeLanguage(){
  
  String homeLanguage = "";
  homeLanguage = langaugePreferences.getString(HOME_LANGUAGE_ID,defaultLanguage);
  return homeLanguage;
 }
 
 public void setHomeLanguage(String homeLanguage){
  
  SharedPreferences.Editor PrefEditor = langaugePreferences.edit();
  PrefEditor.putString(HOME_LANGUAGE_ID, homeLanguage);
  PrefEditor.commit();
 }
  
}
