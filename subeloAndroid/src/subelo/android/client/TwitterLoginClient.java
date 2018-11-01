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

package subelo.android.client;

import subelo.android.utils.Constants;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public enum TwitterLoginClient {

	INSTANCE;
	
    public RequestToken getLoginURL() throws TwitterException{
		// The factory instance is re-useable and thread safe.
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.consumerKey, Constants.consumerSecret);
		RequestToken requestToken = twitter.getOAuthRequestToken();

		return requestToken;
    }
    
    public AccessToken getAccessToken(RequestToken requestToken, String pin) throws TwitterException{
    	Twitter twitter = new TwitterFactory().getInstance();
    	twitter.setOAuthConsumer(Constants.consumerKey, Constants.consumerSecret);
    	return twitter.getOAuthAccessToken(requestToken, pin);
    }
    
//    public void updateStatus(String newStatus, AccessToken accessToken) throws TwitterException{
//    	Twitter twitter = new TwitterFactory().getInstance();
//    	twitter.setOAuthConsumer(consumerKey, consumerSecret);
//    	twitter.setOAuthAccessToken(accessToken);
//    	twitter.updateStatus(newStatus);
//    }
	
}
