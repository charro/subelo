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

package subelo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import subelo.protocol.AvailableServiceProviders;
import subelo.protocol.AvailableServices;
import subelo.protocol.RequestMessage;
import subelo.protocol.ResponseCodes;
import subelo.protocol.ResponseMessage;

public class SubeloClient {

	private String serverURL;
	private String servicePath;
	private DefaultHttpClient httpClient = new DefaultHttpClient();
	
	public SubeloClient(String serverURL, String servicePath) {
		super();
		this.serverURL = serverURL;
		this.servicePath = servicePath;
	}
	
	public ResponseMessage hello(){
		return sendGetReceiveString(AvailableServices.HELLO);
	}

	public ResponseMessage testJson() {
		return sendGetReceiveString(AvailableServices.TEST_JSON);
	}
	
	public ResponseMessage sendPostJSON(RequestMessage request) {
		return sendPostJsonToServer(request.getServiceCode(), request.getServiceOperation(), request.getParams());
	}
	
	public ResponseMessage sendPostXML(RequestMessage request) {
		return sendPostXmlToServer(request.getServiceCode(), request.getServiceOperation(), request.getParams());
	}
	
	
	private ResponseMessage sendGetReceiveString(AvailableServices service){
		HttpGet getRequest = new HttpGet(getServiceURL(service));
		
		ResponseMessage response = new ResponseMessage();
		
		try {
			HttpResponse httpResponse = httpClient.execute(getRequest);
			response.setValue(convertStreamToString(httpResponse.getEntity().getContent()));
			response.setResponseCode(ResponseCodes.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ResponseCodes.CONNECTION_ERROR);
			response.setValue(e.getMessage());
		}
		
		return response;
	}
	
//	private ResponseMessage sendGetReceiveJson(AvailableServices service){
//		HttpGet getRequest = new HttpGet(getServiceURL(service));
//		
//		ResponseMessage response = new ResponseMessage();
//		
//		try {
//			HttpResponse httpResponse = httpClient.execute(getRequest);
//			JSONObject json = new JSONObject(convertStreamToString(httpResponse.getEntity().getContent()));
//			response.setResponseCode(ResponseCodes.valueOf(json.getString("responseCode")));
//			response.setValue(json.getString("value"));
//			
//			response.setResponseCode(ResponseCodes.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			response.setResponseCode(ResponseCodes.CONNECTION_ERROR);
//			response.setValue(e.getMessage());
//		}
//		
//		return response;
//	}	
	
	private ResponseMessage sendPostJsonToServer(AvailableServiceProviders idProvider, AvailableServices service, Map<String, String> params){
		HttpPost postRequest = new HttpPost(getServiceURL(service));
		
		ResponseMessage response = new ResponseMessage();
		try {
			JSONObject jsonRequest = new JSONObject();
			jsonRequest.put("serviceCode", idProvider.name());
			jsonRequest.put("serviceOperation", service.name());
			
			JSONObject jsonParams = new JSONObject(params);
			jsonRequest.put("params", jsonParams);
			
			StringEntity entity = new StringEntity(jsonRequest.toString());

			entity.setContentType("application/json;charset=iso-8859-1");
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=iso-8859-1")); 
			                     
			postRequest.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(postRequest);
			
			// Get and parse the Response
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				JSONObject responseJson = new JSONObject(convertStreamToString(httpResponse.getEntity().getContent()));
				response.setResponseCode(ResponseCodes.valueOf(responseJson.getString("responseCode")));
				response.setValue(responseJson.getString("value"));
				
				if(responseJson.has("responseValues")){
					JSONObject responseValuesJSON = responseJson.getJSONObject("responseValues");
					if(responseValuesJSON != null && responseValuesJSON.length()>0){
						HashMap<String, Object> responseValues = new HashMap<String, Object>();
						// Depending of the type of the request, look for different values and map to the Java type
						switch(service){
							case LIST_MY_ITEMS:
								ArrayList<String> tweetsArray = new ArrayList<String>();
								if(responseValuesJSON.has("tweets")){
									try{
										JSONArray tweetsArrayJson = responseValuesJSON.getJSONArray("tweets");
										for(int i=0; i<tweetsArrayJson.length(); i++){
											tweetsArray.add(tweetsArrayJson.getString(i));
										}
									}
									catch(JSONException ex){
										tweetsArray.add(responseValuesJSON.getString("tweets"));
									}
								}
								responseValues.put("tweets", tweetsArray);
								break;
						}
						
						response.setResponseValues(responseValues);
					}
				}
			}
			else{
				response.setResponseCode(ResponseCodes.CONNECTION_ERROR);
				response.setValue(String.valueOf(httpResponse.getStatusLine().getStatusCode()) +
						" : " + httpResponse.getStatusLine().getReasonPhrase());	
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ResponseCodes.CONNECTION_ERROR);
			response.setValue(e.getMessage());
		}			

		return response;
	}
	
	private ResponseMessage sendPostXmlToServer(AvailableServiceProviders idProvider, AvailableServices service, Map<String, String> params){
		HttpPost postRequest = new HttpPost(getServiceURL(service));
		
		ResponseMessage response = new ResponseMessage();
		try {
			DocumentBuilder xmlDocBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document xmlDoc = xmlDocBuilder.newDocument();
			Element root = xmlDoc.createElement("requestMessage");
			Element serviceCode = xmlDoc.createElement("serviceCode");
			serviceCode.setNodeValue(idProvider.name());
			root.appendChild(serviceCode);
			
			Element serviceOp = xmlDoc.createElement("serviceOperation");
			serviceOp.setNodeValue(service.name());
			root.appendChild(serviceOp);
			
			xmlDoc.appendChild(root);
			
		
			StringEntity entity = new StringEntity(xmlDoc.getTextContent());
			entity.setContentType("text/xml;charset=UTF-8");//text/plain;charset=UTF-8
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"text/xml;charset=UTF-8")); 
			                     
			postRequest.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(postRequest);
			JSONObject responseJson = new JSONObject(convertStreamToString(httpResponse.getEntity().getContent()));
			response.setResponseCode(ResponseCodes.valueOf(responseJson.getString("responseCode")));
			response.setValue(responseJson.getString("value"));
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(ResponseCodes.CONNECTION_ERROR);
			response.setValue(e.getMessage());
		}			

		return response;
	}
	
	private String getServiceURL(AvailableServices service) {
		return serverURL + servicePath + "/" + service.getPath();
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
