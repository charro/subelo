/** Copyright 2011 UNED
*
*  Miguel Rivero
*  Juan Manuel Cigarran
*
* Licensed under the EUPL, Version 1.1 or – as soon they will be
approved by the European Commission – subsequent versions of the
EUPL (the "Licence");* you may not use this work except in
compliance with the Licence.
* You may obtain a copy of the Licence at:
*
* http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
*
* Unless required by applicable law or agreed to in writing,
software distributed under the Licence is distributed on an "AS
IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
* See the Licence for the specific language governing permissions
and limitations under the Licence.
*/

package subelo.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import subelo.android.utils.ConfigFileHelper;
import subelo.android.utils.DialogHelper;
import twitter4j.auth.AccessToken;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Base class for the Activities of the application. Includes the gesture, Twitter login, 
 * voice recognition and text-to-speech functionalities that may so be used on every Activity.
 * 
 * @author Miguel Rivero
 *
 */

public abstract class TwitterGestureActivity extends GestureActivity implements OnInitListener{

	protected static final int VOICE_RECOGNITION_REQUEST_CODE = 666;
	protected static final int CHECK_FOR_TEXT_TO_SPEECH_INTENT_CODE = 777;
	
	protected TextToSpeech textToSpeechEngine;
	
	protected boolean isTextToSpeechEnabled;
	
	protected String pendingTextToSpeech = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// If there is not internet connection, the application can not continue
		if(!isOnline()){
			showDialog(DialogHelper.DIALOG_ERROR_NO_NETWORK_CONNECTION);
		}
		
		// Check if there is a Text to Speech Engine available and install it otherwise
		startTextToSpeechEngine();
		
		isTextToSpeechEnabled = loadTextToSpeechEnabled();
	}
	
	protected void saveAccessToken(AccessToken accesstoken){
		try {
			ConfigFileHelper.saveConfigObject(this, ConfigFileHelper.ACCESS_TOKEN, accesstoken);
		}
		catch(Exception ex){
			ex.printStackTrace();
			showDialog(DialogHelper.DIALOG_ERROR_SAVING_ACCESS_TOKEN);
		}
	}
	
	protected AccessToken loadAccessToken(){
		try {
			return (AccessToken) ConfigFileHelper.loadConfigObject(this, ConfigFileHelper.ACCESS_TOKEN);
		}
		catch(Exception ex){
			return null;
		}
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {

		return DialogHelper.getNewDialog(id, this);
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		// Close the application if there is not Network connection
		// after the user dismiss the dialog
		if(id == DialogHelper.DIALOG_ERROR_NO_NETWORK_CONNECTION){
			dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {				
				public void onDismiss(DialogInterface dialog) {
					finish();
				}
			});
		}
		super.onPrepareDialog(id, dialog);
	}
	/**
	 * This method is called when recognizing Text from Google Voice Search.
	 * Override it to use the recognized text in your Activity.  
	 * 
	 * @param recognizedText
	 */
	protected void onVoiceRecognition(String recognizedText){
		Log.d("Recognized voice text", recognizedText);
	}

	/**
	 * This method is called after Text2Speech is successfully installed
	 * in case it wasn't previously 
	 */
	protected void onTextToSpeechReady(){
		Log.d("Text2Speech ready", "Text To Speech Engine ready");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		if(isTextToSpeechEnabled){
			menu.findItem(R.id.toggleToSpeechMenuItem).setTitle(R.string.disableTextToSpeech);
		}
		else{
			menu.findItem(R.id.toggleToSpeechMenuItem).setTitle(R.string.enableTextToSpeech);
		}

		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch(item.getItemId()){
			case R.id.toggleToSpeechMenuItem:				
				isTextToSpeechEnabled = ! isTextToSpeechEnabled;
				saveTextToSpeechEnabled(isTextToSpeechEnabled);
				break;
		}
		
		return true;
	};
	
	protected void speak(String textToSpeak){
		if(textToSpeechEngine != null){
			if(isTextToSpeechEnabled){
				textToSpeechEngine.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
			}
		}
		else{
			pendingTextToSpeech += (" " + textToSpeak);
			startTextToSpeechEngine();
		}
	}
	
	protected boolean loadTextToSpeechEnabled(){
		try {
			return (Boolean) ConfigFileHelper.loadConfigObject(this, ConfigFileHelper.TEXT_TO_SPEECH_FLAG);
		}
		catch(Exception ex){
			// Enabled by default
			return true;
		}	
	}
	
	protected void saveTextToSpeechEnabled(boolean isEnabled){
		try {
			ConfigFileHelper.saveConfigObject(this, ConfigFileHelper.TEXT_TO_SPEECH_FLAG, new Boolean(isEnabled));
		}
		catch(Exception ex){
			ex.printStackTrace();
			showDialog(DialogHelper.DIALOG_ERROR_SAVING_CONFIG);
		}	
	}
	
	/**
	 * Checks if the Text2Speech Engine is available and try to install it otherwise
	 */
	protected void startTextToSpeechEngine(){
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, CHECK_FOR_TEXT_TO_SPEECH_INTENT_CODE);
	}
	
	/**
	 *  The Text-to-Speech is ready, configure it and speak any pending text
	 */
	public void onInit(int arg0) {
		textToSpeechEngine.setLanguage(Locale.getDefault());
		speak(pendingTextToSpeech);
		pendingTextToSpeech = "";
		// Gives the opportunity to do something specific to the children classes
		onTextToSpeechReady();
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	/**
	 * Fire an intent to start the speech recognition activity.
	 */
	protected void startVoiceRecognitionActivity() {
		// Check to see if a recognition activity is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(
				new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

		if(activities.size()>0){
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.please_talk_now);
			startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
		}
		else{
			showDialog(DialogHelper.DIALOG_ERROR_NO_VOICE_RECOGNITION);
		}
		
	}
	
	/**
	 * Handle the results from the recognition activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
			// Fill the list view with the strings the recognizer thought it could have heard
			ArrayList<String> matches = data.getStringArrayListExtra(
					RecognizerIntent.EXTRA_RESULTS);
			if(matches.size()>0){
				String bestTry = matches.get(0);
				onVoiceRecognition(bestTry);
			}
		}

		if (requestCode == CHECK_FOR_TEXT_TO_SPEECH_INTENT_CODE){
			if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
			// There is Text-to-Speech available, test it 
	            // success, create the TTS instance
	            textToSpeechEngine = new TextToSpeech(this, this);
	        } else {
	            // missing data, install it
	            Intent installIntent = new Intent();
	            installIntent.setAction(
	                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
	            startActivity(installIntent);
	        }
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
}
