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

import subelo.android.client.TwitterLoginClient;
import subelo.android.utils.DialogHelper;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Subelo extends TwitterGestureActivity {
	
	private RequestToken requestToken;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final AccessToken accessToken = loadAccessToken();

		if (accessToken != null) {
			setContentView(R.layout.new_login);
			Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
			startActivity(mainMenuIntent);
			
			finish();
		} else {
			// Its first login, get the AccessToken from Twitter
			try {
				setContentView(R.layout.new_login);
				final Button loginButton = (Button) findViewById(R.id.loginButton);

				loginButton
						.setOnClickListener(new Button.OnClickListener() {

							public void onClick(View v) {
								try {
									requestToken = TwitterLoginClient.INSTANCE.getLoginURL();
									// Save requestToken and timestamp
									Uri uri = Uri
											.parse(requestToken.getAuthorizationURL());
									Intent intent = new Intent(
											Intent.ACTION_VIEW, uri);
									startActivity(intent);
								} catch (Exception e) {
									showDialog(DialogHelper.DIALOG_ERROR_TRYING_LOGIN);
									Log.e(this.toString(), e.getMessage());
								}
							}

						});
				
				final EditText pinTextBox = (EditText) findViewById(R.id.pinTextBox);
				final Button sendPinButton = (Button) findViewById(R.id.sendPinButton);

				sendPinButton.setOnClickListener(new Button.OnClickListener() {
					
					public void onClick(View v) {
						if(pinTextBox.getText() != null && pinTextBox.getText().length()>0){
							if(requestToken == null){
								showDialog(DialogHelper.DIALOG_ERROR_GETTING_TOKEN_REQUEST);
								return;
							}
							try {
								AccessToken accessToken = 
									TwitterLoginClient.INSTANCE.getAccessToken(requestToken, pinTextBox.getText().toString());
									saveAccessToken(accessToken);
									showDialog(DialogHelper.DIALOG_AUTHORIZATION_OK);
									reload();
							} catch (TwitterException e) {
								showDialog(DialogHelper.DIALOG_ERROR_SENDING_PIN);
							}
							
						}
						else{
							
						}
					}
				});
					
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
    private void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

	@Override
	protected void upGestureAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void downGestureAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void leftGestureAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void rightGestureAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void singleTapAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doubleTapAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void longPressAction() {
		// TODO Auto-generated method stub
		
	}
}