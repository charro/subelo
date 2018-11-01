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

package subelo.server.services.external.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import subelo.protocol.RequestMessage;
import subelo.protocol.ResponseCodes;
import subelo.protocol.ResponseMessage;
import subelo.server.Utils;
import subelo.server.services.ServiceProvider;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Implementation for subelo server of the Twitter provider
 * 
 * @author Miguel Rivero
 *
 */
public class TwitterServiceProvider implements ServiceProvider {

	// Set your own key and secret
	private static final String SUBELO_CONSUMER_KEY = "";
	private static final String SUBELO_CONSUMER_SECRET = "";
	
	@Autowired
	TwitterFactory twitterFactory;
	
	/**
	 * Update the status in Twitter. Needs the param "status"
	 */
	public ResponseMessage addItem(RequestMessage request) {
		
		ResponseMessage response = new ResponseMessage();

		try{
			Twitter twitter = login(request);
			twitter.updateStatus(request.getParams().get("status"));
			response.setResponseCode(ResponseCodes.OK);
			response.setValue("Twitter status updated successfully");
		}
		catch(Exception e){
			e.printStackTrace();
			response.setResponseCode(ResponseCodes.EXTERNAL_SERVICE_ERROR);
			response.setValue(e.getMessage());
		}
		
		return response;
	}

	/**
	 * Get the last "count" tweets of the user and the users he follows. If not "count" specified, it use constant MAX_ITEM
	 */
	public ResponseMessage listMyItems(RequestMessage request) {

		ResponseMessage response = new ResponseMessage();

		try{
			int maxItem = MAX_ITEM;
			if(request.getParams().containsKey("count")){
				String itemCount = request.getParams().get("count");
				try{
					maxItem = Integer.parseInt(itemCount);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}

			Twitter twitter = login(request);
			ArrayList<String> tweetList = new ArrayList<String>();
			for(Status status : twitter.getHomeTimeline(new Paging(1, maxItem))){
				String newTweet = status.getUser().getName()+":\n"+ status.getText() + ". \n\n" + Utils.getFormattedDate(status.getCreatedAt());
				tweetList.add(newTweet);
			}
			HashMap<String, Object> responseValues = new HashMap<String, Object>();
			responseValues.put("tweets", tweetList);
			response.setResponseValues(responseValues);
			response.setResponseCode(ResponseCodes.OK);
			response.setValue("My tweets recovered successfully");
		}
		catch(Exception e){
			e.printStackTrace();
			response.setResponseCode(ResponseCodes.EXTERNAL_SERVICE_ERROR);
			response.setValue(e.getMessage());
		}
		
		return response;

	}
	
	public Twitter login(RequestMessage request) throws TwitterException{
		Map<String, String> params = request.getParams();
		String accessToken = params.get("accessToken");
		String accessTokenSecret = params.get("accessTokenSecret");
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(SUBELO_CONSUMER_KEY)
		.setOAuthConsumerSecret(SUBELO_CONSUMER_SECRET)
		.setOAuthAccessToken(accessToken)
		.setOAuthAccessTokenSecret(accessTokenSecret);
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}

}
