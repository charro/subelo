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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.rosaloves.bitlyj.Bitly;
import com.rosaloves.bitlyj.BitlyException;
import com.rosaloves.bitlyj.Url;

/**
 * Class that provides support for reusable features related with the Twitter terminology 
 * 
 * @author Miguel Rivero
 *
 */

public class TwitterHelper {
	
	// Twitter mention that include spaces between the @ and the username
	// or the R and T of a Retweet 
	// or the hashtag and the name of the trending topic
	// or URLs
	public static final String TWITTER_BLANK_REGULAR_EX = "(@\\s+[^\\s]+)|(R\\s+T)|(#\\s+[^\\s]+)";
	public static final String CORRECT_URL_REGEX = 
		"((http|ftp|https)://)?[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
	
	public static final String URLS_WITH_BLANKS = "(((h\\s*t\\s*t\\s*p(\\s*s)?)|f\\s*t\\s*p)\\s*:\\s*/\\s*/\\s*)?([\\w\\-_]+\\s*(\\.|/)\\s*)+[\\w\\-_]+"; 

	/**
	 * Check for user mention @username, #topic or RT
	 * 
	 * @param textBeforeParsing
	 * @return
	 */
	public static String parseTwitterTerminology(String textBeforeParsing){
		
		return substituteRegexInText(textBeforeParsing, TWITTER_BLANK_REGULAR_EX);
	}
	
	/**
	 * Remove blank spaces into URLs
	 * 
	 * @param textBeforeParsing
	 * @return
	 */
	public static String parseURLWithBlanks(String textBeforeParsing){
		
		return substituteRegexInText(textBeforeParsing, URLS_WITH_BLANKS);
	}
	
	/**
	 * Short all the URLs on the text
	 * 
	 * @param textBeforeParsing
	 * @return
	 */
	public static String shortURLs(String textBeforeParsing){
		return substituteRegexInText(textBeforeParsing, CORRECT_URL_REGEX);
	}
	
	
	private static String substituteRegexInText(String textBeforeParsing, String regex){
		
		String textAfterParsing = "";
		
		Matcher matcher = Pattern.compile(regex).matcher(textBeforeParsing);
		
		int initialIndex = 0; 
		while(matcher.find()){
			System.out.println("Match: " + matcher.group() + "fin");
			// Text between the previous and the next match
			textAfterParsing += textBeforeParsing.substring(initialIndex, matcher.start());
			
			if(regex.equals(TWITTER_BLANK_REGULAR_EX) || regex.equals(URLS_WITH_BLANKS)){
				// Remove blank spaces (except the last one )		
				textAfterParsing += matcher.group().replaceAll("\\s", "");
			}
			
			if(regex.equals(CORRECT_URL_REGEX)){
				textAfterParsing += shortURL(matcher.group());
			}
			
			// Set the initial index at the end
			initialIndex = matcher.end();
			
		}
		
		// Text from the last match to the end
		textAfterParsing += textBeforeParsing.substring(initialIndex);
		
		return textAfterParsing;
	}
	
	private static String shortURL(String urlToShort){
		
		try{
			String checkedUrl = urlToShort;
			if(!urlToShort.contains("http") && !urlToShort.contains("https") && !urlToShort.contains("ftp")){
				checkedUrl = "http://"+urlToShort;
			}
			Url url = Bitly.as(Constants.bitlyUser, Constants.bitlyApiKey).call(
					Bitly.shorten(checkedUrl));
			
			return url.getShortUrl();
		}
		// If there is an invalid URL, just return the long one
		catch(BitlyException ex){
//			Log.e(TwitterHelper.class.toString(), ex.getMessage());
			return urlToShort;
		}
				
	}
}
