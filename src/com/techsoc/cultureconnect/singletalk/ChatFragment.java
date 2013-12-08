package com.techsoc.cultureconnect.singletalk;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.techsoc.Language.Language;
import com.techsoc.cultureconnect.R;

public class ChatFragment extends Fragment {
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 0;

	protected static final String TAG = ChatFragment.class.getName();

	private ListView mConversationView;
	private ArrayAdapter<String> mConversationArrayAdapter;

	private View mChatView;
	private OnMessageReceivedListener mCallback;

	private Spinner mSpinner;
	private TextToSpeech textToSpeech;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mChatView = inflater.inflate(R.layout.singletalk_messaging_unit, container,
				false);
		setup();
		return mChatView;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnMessageReceivedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString());
		}
	}

	public void addMessage(String inputMessage) {
		String message = "";
		String language = "";
		for (int counter = 0; counter<inputMessage.length();counter++) {
			if (inputMessage.charAt(counter)!=')') language+=inputMessage.charAt(counter);
			else {
				language+=inputMessage.charAt(counter);
				message=inputMessage.substring(counter+1);
				counter=inputMessage.length();
			}
		}
		
		mConversationArrayAdapter.add(language+message);
		Log.d("Conversation", message);
		convertToSpeech(message);
	}

	private void setup() {
		setupSpinner();
		setupConversationArrayAdapter();
		setupSendButton();
		setupTextToSpeech();
	}

	private void setupConversationArrayAdapter() {
		mConversationView = (ListView) mChatView.findViewById(R.id.in);
		mConversationArrayAdapter = new ArrayAdapter<String>(
				this.getActivity(), R.layout.talk_message);
		mConversationView.setAdapter(mConversationArrayAdapter);
	}

	private void setupSendButton() {
		Button sendButton = (Button) mChatView.findViewById(R.id.button_send);
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO: Get from speech
				String messageText = ((EditText) mChatView
						.findViewById(R.id.edit_text_out)).getText().toString();
				
				if (messageText.isEmpty()) {
					Log.d(TAG, "Getting Message");
					startVoiceRecognitionActivity();
				} else {
					sendMessageFromEditText();
				}
				// TODO: Clear message
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == Activity.RESULT_OK) {
			// Returns the arraylist of strings sorted by confidence
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String message = matches.get(0);
			
			
			ChatMessage chatMessage = new ChatMessage();
			
			Language accessLanguage = new Language();
			String languageSpinner = mSpinner.getSelectedItem().toString();
			String language = accessLanguage.getLanguageCode(languageSpinner);
			
			mConversationArrayAdapter.add(message);
			
			chatMessage.setFromId(ChatFragment.this.getId());
			chatMessage.setMessage("("+languageSpinner+")" + " " +message);
			chatMessage.setLanguage(language);
			sendMessage(chatMessage);
		}
	}
	

	private void sendMessage(ChatMessage chatMessage) {
		mCallback.onMessageReceived(chatMessage);
	}
	

	private void setupSpinner() {
		mSpinner = (Spinner) mChatView.findViewById(R.id.languages)
				.findViewById(R.id.languages);
		ArrayAdapter<CharSequence> secondAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.languages,
						android.R.layout.simple_spinner_item);
		secondAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(secondAdapter);
	}
	

	private void setupTextToSpeech() {
		textToSpeech = new TextToSpeech(this.getActivity(),
				new TextToSpeech.OnInitListener() {
					public void onInit(int status) {
						if (status == TextToSpeech.SUCCESS) {
						} else {
							Log.e(TAG, "Failed to intialize Text to Speech");
						}
					}
				});
	}

	
	private void sendMessageFromEditText() {
		
		String messageText = ((EditText) mChatView
				.findViewById(R.id.edit_text_out)).getText().toString();
		
		((EditText) mChatView.findViewById(R.id.edit_text_out)).setText("");
		
		
		Language accessLanguage = new Language();
		String languageSpinner = mSpinner.getSelectedItem().toString();
		String language = accessLanguage.getLanguageCode(languageSpinner);
		
		mConversationArrayAdapter.add(messageText);
		
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setFromId(ChatFragment.this.getId());
		chatMessage.setMessage("("+languageSpinner+")" + " " +messageText);
		chatMessage.setLanguage(language);
		sendMessage(chatMessage);
	}

	private void convertToSpeech(String text) {
		Log.d("Input Language", getLanguage());
		String inputLanguage = getLanguage();
		if (textToSpeech.isLanguageAvailable(new Locale(inputLanguage)) == TextToSpeech.LANG_AVAILABLE) {
			textToSpeech.setLanguage(new Locale(inputLanguage));
			Log.v("Translate", "Language Available: " + inputLanguage);
			textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
		} else {
			Log.e("Translate", "Language Not Available: " + inputLanguage);
			Intent installIntent = new Intent();
			installIntent
					.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
			this.getActivity().startActivity(installIntent);
		}
	}

	public String getLanguage() {
		Spinner spinner = (Spinner) mChatView.findViewById(R.id.languages);
		Language accessLanguage = new Language();
		//String language = 
		return accessLanguage.getLanguageCode(spinner.getSelectedItem().toString());
	}

	private void startVoiceRecognitionActivity() {
		Language accessLanguage = new Language();
		String languageSpinner = mSpinner.getSelectedItem().toString();
		String language = accessLanguage.getLanguageCode(languageSpinner);
		
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now");
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	public interface OnMessageReceivedListener {
		public void onMessageReceived(ChatMessage chatMessage);
	}
}
