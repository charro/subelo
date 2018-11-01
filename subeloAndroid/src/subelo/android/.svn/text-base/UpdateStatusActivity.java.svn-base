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
import java.util.Map;

import subelo.android.utils.Constants;
import subelo.android.utils.DialogHelper;
import subelo.android.utils.SubeloClientHelper;
import subelo.android.utils.TwitterHelper;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateStatusActivity extends TwitterGestureActivity {

	private ArrayList<String> statusStringList = new ArrayList<String>();

	private TextView statusTextView;
	private ImageView subeloImageView;
	private ImageView discardImageView;

	private String initialStatusContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.update_layout);

		statusTextView = (TextView) findViewById(R.id.statusTextView);
		
//		// Control the copy/pasting
//		statusTextView.addTextChangedListener(
//                new TextWatcher()
//                {
//                    public void afterTextChanged(Editable s) 
//                    {
//                    	// Do Nothing
//                    }
//
//                    public void beforeTextChanged(CharSequence s, int start, int count,
//                            int after) 
//                    {
//                        // Do Nothing
//
//                    }
//
//                    public void onTextChanged(CharSequence s, int start, int before,
//                            int count) 
//                    {	
//                    	String newText = s.toString().substring(start, start+count);
//                    	if(count>0 && 
//                    		(statusStringList.isEmpty() || (! newText.equals(statusStringList.get(statusStringList.size()-1))) )){
//                    		statusStringList.add(newText);
//                    	}
//                    	Toast.makeText(UpdateStatusActivity.this, "Added: " + count + " -> " + s.toString().substring(start, start+count), Toast.LENGTH_SHORT).show();
//                    	
//                    }
//                });
//		
//		
//		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(statusTextView.getWindowToken(), 0);
		
		subeloImageView = (ImageView) findViewById(R.id.subeloImageView);
		discardImageView = (ImageView) findViewById(R.id.discardImageView);

		initialStatusContent = statusTextView.getText().toString();
		
		speak(getString(R.string.update_status_title));
	}
	
	private void speakStatus(){
		speak(statusTextView.getText().toString());
	}
	
	@Override
	protected void upGestureAction() {

		if(statusTextView.getText().toString().equals(initialStatusContent) ||
				statusTextView.getText().length()<1){
			Toast.makeText(this, getString(R.string.please_introduce_text), Toast.LENGTH_LONG).show();
			speak(getString(R.string.please_introduce_text));
			return;
		}

		Map<String, String> params = SubeloClientHelper.getTwitterParams(loadAccessToken());
		params.put("status", statusTextView.getText().toString());
		RequestMessage request = 
			new RequestMessage(AvailableServiceProviders.TWITTER, AvailableServices.ADD_ITEM, params);

		new UpdateStatusTask().execute(request);
	}

	@Override
	protected void downGestureAction() {
		if(! statusStringList.isEmpty()){
			statusStringList.remove(statusStringList.size()-1);
			updateStatusText();
			speakStatus();
		}
		else{
			if(statusTextView.getText().toString().equals(initialStatusContent)){
				statusTextView.setText("");
			}
			else{
				Toast.makeText(this, getString(R.string.please_introduce_text), Toast.LENGTH_LONG).show();
				speak(getString(R.string.please_introduce_text));
			}
		}
		
	}

	@Override
	protected void leftGestureAction() {
//		if(! statusStringList.isEmpty()){
//			ArrayList<String> newStatusList = new ArrayList<String>(); 
//			for(String statusString : statusStringList){
//				newStatusList.add(TwitterHelper.shortURLs(statusString));
//			}
//			statusStringList = newStatusList;
//			updateStatusText();
//			speakStatus();
//		}else 
			if(statusTextView.getText().toString().length()>0){
			statusTextView.setText(TwitterHelper.shortURLs(statusTextView.getText().toString()));
			speakStatus();			
		}
		else{
			Toast.makeText(this, getString(R.string.please_introduce_text), Toast.LENGTH_LONG).show();
			speak(getString(R.string.please_introduce_text));
		}
	}

	@Override
	protected void rightGestureAction() {
		Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
		startActivity(mainMenuIntent);
	    
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	@Override
	protected void longPressAction() {
		speakStatus();
	}
	
	@Override
	protected void singleTapAction() {

	}

	@Override
	protected void doubleTapAction() {
		startVoiceRecognitionActivity();
	}

	@Override
	protected void onVoiceRecognition(String recognizedText){
		// Improve the Google Voice Search result with Twitter-knowledge based parsing
		String parsedRecognizedText = TwitterHelper.parseTwitterTerminology(recognizedText);
		parsedRecognizedText = TwitterHelper.parseURLWithBlanks(parsedRecognizedText);
		
		statusStringList.add(parsedRecognizedText);
		updateStatusText();
		speakStatus();
	}
	
	private void updateStatusText(){
		String statusText = "";
		for(String nextString : statusStringList){
			statusText += nextString + " ";
		}

		statusTextView.setText(statusText);
	}
	
	private class UpdateStatusTask extends AsyncTask<RequestMessage, Integer, ResponseMessage>{

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(UpdateStatusActivity.this,
					"", getString(R.string.wait_connecting_twitter), true);
			speak(getString(R.string.wait_connecting_twitter));
		}

		@Override
		protected ResponseMessage doInBackground(RequestMessage... messages) {
			SubeloClient subeloClient = new SubeloClient(Constants.serverURL, Constants.servicePath);
			return subeloClient.sendPostJSON(messages[0]);                                 
		}

		@Override
		protected void onPostExecute(ResponseMessage result) {
			progressDialog.dismiss();

			if(result.getResponseCode() == ResponseCodes.OK){
				initialStatusContent = statusTextView.getText().toString();
				Toast.makeText(UpdateStatusActivity.this, getString(R.string.status_updated_ok), Toast.LENGTH_LONG).show();
				speak(getString(R.string.status_updated_ok));
			}
			else{	
				showDialog(DialogHelper.DIALOG_ERROR_TWITTER_OP);
			}

			subeloImageView.setImageResource(R.drawable.whitearrowup);
		}
	}
	
}

