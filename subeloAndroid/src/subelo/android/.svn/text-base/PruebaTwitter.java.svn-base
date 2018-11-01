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

import subelo.android.R;
import subelo.android.R.id;
import subelo.android.R.layout;
import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.conf.ConfigurationContext;
import twitter4j.conf.PropertyConfiguration;
import twitter4j.internal.http.HttpClient;
import twitter4j.internal.http.HttpClientImpl;
import twitter4j.internal.http.HttpParameter;
import twitter4j.internal.http.HttpRequest;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.http.RequestMethod;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressWarnings({"unused" })
public class PruebaTwitter extends Activity {

	private TextView txtOut;
	private EditText etUsername;
	private EditText etPassword;
    private Button btnGo;
    private TextView lblResult;
    public String pin = "";
    public Twitter twitter;
    public RequestToken requestToken;
    public String urlapp;
    public String twitterconsumerkey;
    public String twittersecretkey;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	try
    	{
	    	super.onCreate(savedInstanceState);

	    	callRestService();
	    	
	        // Set Activity Layout
	        setContentView(R.layout.test_twitter);

	        txtOut = (TextView)findViewById(R.id.txtOut);
	        etUsername = (EditText)findViewById(R.id.username);
	        etPassword = (EditText)findViewById(R.id.password);
	        btnGo = (Button)findViewById(R.id.go_button);
	        lblResult = (TextView)findViewById(R.id.result);

	        // Set Click Listener
	        btnGo.setOnClickListener(new OnClickListener() {

	            public void onClick(View v) {
//	                    TestingTwitter();
	            	callRestService();
	            }
	        });
    	}
    	catch(Exception ex)
    	{
    		txtOut.setText(ex.getMessage());
    	}

    }

    public void TestingTwitter()
    {
    	// TODO Auto-generated method stub
        RequestToken rt;
        Twitter twitter = new TwitterFactory().getInstance();
        HttpClientImpl http;
        HttpResponse response;
        String resStr;
        String authorizeURL;
        HttpParameter[] params;
        AccessToken at;
        String cookie;
        http = new HttpClientImpl();

        try
        {

	        // browser client - not requiring pin / overriding callback url

	       twitterconsumerkey = "5MGPuFw3zE9vlCSXTQCQg";
	       twittersecretkey = "MiArOv0Ee1rQi57fQpLgD1ER0IBGcVMLeeqo9LCVqw";
	       String username = etUsername.getText().toString();
	       String password = etPassword.getText().toString();

	       twitter = new TwitterFactory().getInstance();
	       twitter.setOAuthConsumer(twitterconsumerkey, twittersecretkey);
	       rt = twitter.getOAuthRequestToken();

	       http = new HttpClientImpl();

	       txtOut.setText("AuthorizationURL: " + rt.getAuthorizationURL());
	       response = http.get(rt.getAuthorizationURL());
	       Map<String,String> props = new HashMap<String,String>();
	       cookie = response.getResponseHeader("Set-Cookie");
	       props.put("Cookie", cookie);
	       resStr = response.asString();
	       authorizeURL = catchPattern(resStr, "<form action=\"", "\" id=\"login_form\"");

	       params = new HttpParameter[4];
	       params[0] = new HttpParameter("authenticity_token" , catchPattern(resStr, "\"authenticity_token\" type=\"hidden\" value=\"", "\" />"));
	       params[1] = new HttpParameter("oauth_token", catchPattern(resStr, "name=\"oauth_token\" type=\"hidden\" value=\"", "\" />"));
	       params[2] = new HttpParameter("session[username_or_email]", username);
	       params[3] = new HttpParameter("session[password]", password);

	       txtOut.setText(username);

	       response = http.request(new HttpRequest(RequestMethod.POST, authorizeURL, params, null, props));
	       resStr = response.asString();
	       String pin = catchPattern(resStr, "<div id=\"oauth_pin\">\n  ", "\n</div>");
	       at = twitter.getOAuthAccessToken(rt, pin);   

	       ResponseList<Status> responseList = twitter.getFavorites();
	       for(int i=0;i<responseList.size();i++)
	       {
	    	   txtOut.setText(txtOut.getText().toString() + "\n"  + responseList.get(i).getText()+ "\n");
	       }

	    	   txtOut.setText(txtOut.getText().toString() + "\n My ID: "  + twitter.getId() + "\n");
        }
        catch(Exception ex)
        {
        	txtOut.setText(ex.getMessage());
        }

    }

    private static String catchPattern(String body, String before, String after) {
        int beforeIndex = body.indexOf(before);
        int afterIndex = body.indexOf(after, beforeIndex);
        return body.substring(beforeIndex + before.length(), afterIndex);
    }
    
    private void callRestService(){
    	
    	try{
    		DefaultHttpClient httpClient = new DefaultHttpClient();
    		HttpPost httpPost = new HttpPost("http://subelo-server.appspot.com/rest/subelo/hello");

        	// Make sure the server knows what kind of a response we will accept
        	//httpPost.addHeader("Accept", "text/xml");
    		//httpPost.setHeader("content-type", "application/xml");
    
//    		StringEntity entity = new StringEntity();
//    		httpPost.setEntity(entity);
    
    		org.apache.http.HttpResponse response = httpClient.execute(httpPost);
    		
    		String respuesta = convertStreamToString(response.getEntity().getContent());
    		//System.out.println(response.getEntity().getContentLength());
    		System.out.println(respuesta);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    private void testRest(){
    	String urlToSendRequest = "http://localhost:8888/subelo/hello";
    	String targetDomain = "localhost";
    	String xmlContentToSend = "hello this is a test";

    	DefaultHttpClient httpClient = new DefaultHttpClient();

    	HttpHost targetHost = new HttpHost(targetDomain, 8888, "http");
    	// Using POST here
    	HttpPost httpPost = new HttpPost(urlToSendRequest);
    	// Make sure the server knows what kind of a response we will accept
    	httpPost.addHeader("Accept", "text/xml");
    	// Also be sure to tell the server what kind of content we are sending
    	httpPost.addHeader("Content-Type", "application/xml");

    	try
    	{
    	StringEntity entity = new StringEntity(xmlContentToSend, "UTF-8");
    	entity.setContentType("application/xml");
    	httpPost.setEntity(entity);

    	// execute is a blocking call, it's best to call this code in a thread separate from the ui's
    	org.apache.http.HttpResponse response = httpClient.execute(targetHost, httpPost);

    	// Have your way with the response
    	}
    	catch (Exception ex)
    	{
    	ex.printStackTrace();
    	}
    }

    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
}
