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
import java.util.Date;
import java.util.Map;

import subelo.android.utils.ConfigFileHelper;
import subelo.android.utils.Constants;
import subelo.android.utils.DialogHelper;
import subelo.android.utils.SubeloClientHelper;
import subelo.client.SubeloClient;
import subelo.protocol.AvailableServiceProviders;
import subelo.protocol.AvailableServices;
import subelo.protocol.RequestMessage;
import subelo.protocol.ResponseCodes;
import subelo.protocol.ResponseMessage;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ListTweetsActivity extends TwitterGestureActivity {

	private ArrayList<String> tweetList;
	private Date lastRefresh; 
	private int currentTweet=0;
	private TextView tweetTextView;
	private ImageView leftImageView;
	private ImageView rightImageView;
	
	private boolean runningGetTweetTask = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_tweets);
		
		tweetTextView = (TextView)findViewById(R.id.tweetTextView);
		leftImageView = (ImageView)findViewById(R.id.leftImageView);
		rightImageView = (ImageView)findViewById(R.id.rightImageView);
		
		tweetList = loadTweetList();
		
		// Load the last time the list was refreshed
		lastRefresh = loadLastRefreshDate();
		if(tweetList == null || 
		   tweetList.size()<1 || 
		   lastRefresh==null ||
		   Math.abs((new Date()).getTime() - lastRefresh.getTime()) > Constants.MIN_TWEET_REFRESH_TIME){
			refreshTweets();
		}
		else{
			showFirstTweet();
		}

	}

	/**
	 * This method is called when the Text-to-Speech is ready, so read the current tweet shown
	 */
	@Override
	protected void onTextToSpeechReady() {
		super.onTextToSpeechReady();
		
		if(! runningGetTweetTask){
			speakCurrentTweet();
		}
	}
	
	private Date loadLastRefreshDate() {
		try {
			return (Date) 
				ConfigFileHelper.loadConfigObject(this, ConfigFileHelper.LAST_REFRESH_MY_TWEET_LIST);
		} catch (Exception e) {
			Log.e("Loading error", "Error loading last tweets refresh from the config file");
		}
		
		return null;
	}
	
	private void saveLastRefreshDate() {
		try {
			ConfigFileHelper.saveConfigObject(this, ConfigFileHelper.LAST_REFRESH_MY_TWEET_LIST, lastRefresh);
		} catch (Exception e) {
			Log.e("Saving error", "Error saving the my tweet list to the config file");
		}
	}
	
	private ArrayList<String> loadTweetList() {
		try {
			return (ArrayList<String>) 
				ConfigFileHelper.loadConfigObject(this, ConfigFileHelper.MY_TWEET_LIST);
		} catch (Exception e) {
			Log.e("Loading error", "Error loading the my tweet list from the config file");
		}
		
		return null;
	}

	private void saveTweetList() {
		try {
			ConfigFileHelper.saveConfigObject(this, ConfigFileHelper.MY_TWEET_LIST, tweetList);
		} catch (Exception e) {
			Log.e("Saving error", "Error saving the my tweet list to the config file");
		}
	}
	
	private void showFirstTweet(){
		if(tweetList != null && tweetList.size()>0){
			tweetTextView.setText(tweetList.get(0));
			if(tweetList.size()>1){
				rightImageView.setVisibility(View.VISIBLE);
			}
		}
	}
	
	private void refreshTweets(){
		Map<String, String> params = SubeloClientHelper.getTwitterParams(loadAccessToken());

		RequestMessage request = 
			new RequestMessage(AvailableServiceProviders.TWITTER, AvailableServices.LIST_MY_ITEMS, params);
		
		new GetTweetsTask().execute(request);
	}
	
	private void speakCurrentTweet(){
		if(textToSpeechEngine != null){
			if(isTextToSpeechEnabled){
				textToSpeechEngine.speak(tweetTextView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
			}
		}
		else{
			// Not ready, try again
			startTextToSpeechEngine();
		}
	}
	
	@Override
	protected void upGestureAction() {

	}

	@Override
	protected void downGestureAction() {
		Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
		startActivity(mainMenuIntent);
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
		finish();
	}

	@Override
	protected void leftGestureAction() {
		
		if(currentTweet<(tweetList.size()-1)){
			if(currentTweet==0){
				leftImageView.setVisibility(View.VISIBLE);
			}
			
			currentTweet++;
			tweetTextView.setText(tweetList.get(currentTweet));
			
			if(currentTweet==(tweetList.size()-1)){
				rightImageView.setVisibility(View.INVISIBLE);
			}
			
			speakCurrentTweet();
		}
	}

	@Override
	protected void rightGestureAction() {
		
		if(currentTweet>0){
			if(currentTweet==(tweetList.size()-1)){
				rightImageView.setVisibility(View.VISIBLE);
			}
			
			currentTweet--;
			tweetTextView.setText(tweetList.get(currentTweet));
			
			if(currentTweet==0){
				leftImageView.setVisibility(View.INVISIBLE);
			}
			
			speakCurrentTweet();
		}
	}

	@Override
	protected void singleTapAction() {

	}

	@Override
	protected void longPressAction() {
		speakCurrentTweet();
	}
	
	@Override
	protected void doubleTapAction() {
		refreshTweets();

	}

	private class GetTweetsTask extends AsyncTask<RequestMessage, Integer, ResponseMessage>{

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			runningGetTweetTask = true;
			progressDialog = ProgressDialog.show(ListTweetsActivity.this,
					"", getString(R.string.wait_connecting_twitter), true);
			speak(getString(R.string.wait_connecting_twitter));
		}

		@Override
		protected ResponseMessage doInBackground(RequestMessage... messages) {
			SubeloClient subeloClient = new SubeloClient(Constants.serverURL, Constants.servicePath);
			return subeloClient.sendPostJSON(messages[0]);                        
		}

		@Override
		protected void onPostExecute(ResponseMessage response) {
			progressDialog.dismiss();

			if(response.getResponseCode() == ResponseCodes.OK){
				if(response.getResponseValues() == null || 
				   response.getResponseValues().get("tweets")==null){
					tweetTextView.setText(R.string.no_tweets_found);
					leftImageView.setVisibility(View.INVISIBLE);
					rightImageView.setVisibility(View.INVISIBLE);
				}
				else{
					tweetList = ((ArrayList<String>) response.getResponseValues().get("tweets"));
					showFirstTweet();
					// Save the tweet list on the config file
					saveTweetList();
					
					lastRefresh = new Date();
					saveLastRefreshDate();
					
					speakCurrentTweet();
				}
			}
			else{	
				showDialog(DialogHelper.DIALOG_ERROR_TWITTER_OP);
			}

			runningGetTweetTask = false;
		}

	}
	
}
