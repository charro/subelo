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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class ConfigFileHelper {

	public final static String ACCESS_TOKEN = "accesToken";
	public final static String TWEETS_REFRESH_DATE = "tweetRefreshDate";
	public final static String CURRENT_TWEET_LIST = "currentTweetList";
	public static final String MY_TWEET_LIST = "myTweetList";
	public static final String LAST_REFRESH_MY_TWEET_LIST = "lastRefreshMyTweetListDate";
	public static final String TEXT_TO_SPEECH_FLAG = "textToSpeechFlag";
	
	public static Map<String, Object> configMap = new HashMap<String, Object>();
	
	public static Object loadConfigObject(Context appContext, String objectName) throws Exception{
		loadConfigFile(appContext);
		return configMap.get(objectName);
	}
	
	public static void saveConfigObject(Context appContext, String objectName, Object object) throws Exception{
		configMap.put(objectName, object);
		writeConfigFile(appContext);
	}	
	
	private static void writeConfigFile(Context appContext) throws Exception{
		FileOutputStream fos;
		fos = appContext.openFileOutput(Constants.DATA_FILE_NAME,
				Context.MODE_PRIVATE);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(configMap);
	}
	
	private static void loadConfigFile(Context appContext) throws Exception{
		FileInputStream fis;
		fis = appContext.openFileInput(Constants.DATA_FILE_NAME);

		ObjectInputStream ois = new ObjectInputStream(fis);
		configMap = (Map<String, Object>) ois.readObject();
	}
	
}
