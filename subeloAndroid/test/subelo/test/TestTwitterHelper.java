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

package subelo.test;

import org.junit.Test;

import subelo.android.utils.TwitterHelper;

public class TestTwitterHelper {

	@Test
	public void testTextParsing(){
		String text = "ffjfjfjf . rjrjrjr. pepe / krrr This is a twitter with a @  mention    "+
		"and a R  T retweet and a #  trending topic " +
		"and URL h  tt  p  :/ / www.ueeyy. oeoeeii / doddhdh manolo . es/ paquito   http://bit.ly/snxfqw " +
		" www.paquito.com";
		
		String twitterParsed = TwitterHelper.parseTwitterTerminology(text);
		System.out.println("Twitter terms  blanks removed: " + twitterParsed);
		String urlsWithoutBlank = TwitterHelper.parseURLWithBlanks(twitterParsed);
		System.out.println("Blanks removed: " + urlsWithoutBlank);
		System.out.println("Then short URLS: " + TwitterHelper.shortURLs(urlsWithoutBlank));
		
//		System.out.println(TwitterHelper.shortURLs(text));
	}
	
}