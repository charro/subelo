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

package subelo.android.utils;

import subelo.android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

public class DialogHelper {

	public static final int DIALOG_ERROR_TRYING_LOGIN = 1001;
	public static final int DIALOG_ERROR_GETTING_TOKEN_REQUEST = 1002;
	public static final int DIALOG_ERROR_SENDING_PIN = 1003;
	public static final int DIALOG_ERROR_SAVING_ACCESS_TOKEN = 1004;
	public static final int DIALOG_ERROR_LOADING_ACCESS_TOKEN = 1005;
	public static final int DIALOG_ERROR_TWITTER_OP = 1006;
	public static final int DIALOG_ERROR_NO_VOICE_RECOGNITION = 1007;
	public static final int DIALOG_ERROR_NO_NETWORK_CONNECTION = 1008;
	public static final int DIALOG_ERROR_SAVING_CONFIG = 1009;
	
	public static final int DIALOG_AUTHORIZATION_OK = 2001;
	public static final int DIALOG_OPERATION_OK = 2002;
	public static final int DIALOG_STATUS_UPDATED_OK = 2003;
	
	public static final int DIALOG_PLEASE_INTRODUCE_TEXT_FIRST = 3001;
	
	
	public static Dialog getNewDialog(int messageId, Context context){
		Dialog dialog = null;
		
		switch(messageId){
			// 	Error messages
			case DIALOG_ERROR_TRYING_LOGIN :
				dialog = createErrorMessage(R.string.error_connecting_final_app, context);
				break;
			case DIALOG_ERROR_GETTING_TOKEN_REQUEST :
				dialog = createErrorMessage(R.string.error_getting_token_request, context);
				break;
			case DIALOG_ERROR_SENDING_PIN :
				dialog = createErrorMessage(R.string.error_sending_pin, context);
				break;
			case DIALOG_ERROR_SAVING_ACCESS_TOKEN :
				dialog = createErrorMessage(R.string.error_saving_access_token, context);
				break;
			case DIALOG_ERROR_LOADING_ACCESS_TOKEN :
				dialog = createErrorMessage(R.string.error_loading_access_token, context);
				break;
			case DIALOG_ERROR_TWITTER_OP :
				dialog = createErrorMessage(R.string.error_twitter_operation, context);
				break;
			case DIALOG_ERROR_NO_NETWORK_CONNECTION:
				dialog = createErrorMessage(R.string.error_no_network, context);
				break;
			case DIALOG_ERROR_SAVING_CONFIG:
				dialog = createErrorMessage(R.string.error_saving_config, context);
				break;
				
			// Info Messages 
			case DIALOG_AUTHORIZATION_OK :
				dialog = createInfoMessage(R.string.message_auth_ok, context);
				break;
			case DIALOG_OPERATION_OK :
				dialog = createInfoMessage(R.string.message_operation_ok, context);
				break;
			case DIALOG_ERROR_NO_VOICE_RECOGNITION :
				dialog = createInfoMessage(R.string.error_no_voice_recon, context);
				break;
			case DIALOG_PLEASE_INTRODUCE_TEXT_FIRST :
				dialog = createInfoMessage(R.string.please_introduce_text, context);
				break;
			case DIALOG_STATUS_UPDATED_OK :
				dialog = createInfoMessage(R.string.status_updated_ok, context);
				break;
			default:
				dialog = null;
		}
		
		return dialog;
	}
	
	
	private static Dialog createErrorMessage(int errorMessage, Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false)
	       .setMessage(errorMessage)
		   .setNeutralButton("OK", null)
		   .setTitle("Error");
		return builder.create();
	}
	
	private static Dialog createInfoMessage(int message, Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false)
	       .setMessage(message)
		   .setNeutralButton("OK", null)
		   .setTitle("Info");
		return builder.create();
	}
	
}
