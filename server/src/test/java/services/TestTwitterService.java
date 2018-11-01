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

package services;

import org.junit.Test;

import subelo.protocol.AvailableServiceProviders;
import subelo.protocol.AvailableServices;
import subelo.protocol.RequestMessage;

//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.net.URI;
import java.util.HashMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class TestTwitterService {

	private static final String serverURL = "http://localhost:8888";
	
//	@Test
//	public void testChangeStatus(){
//		String newStatus = "Hello Twitter";
//		
//		ClientConfig config = new DefaultClientConfig();
//		Client client = Client.create(config);
//		WebResource service = client.resource(getBaseURI());
//		// Get XML
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("accessToken", "INVENTAO");
//		params.put("accessTokenSecret" , "INVENTAO2");
//		
//		System.out.println(service.path("addItem").entity(
//				new RequestMessage(AvailableServiceProviders.TWITTER,AvailableServices.ADD_ITEM, params)).accept(
//				MediaType.TEXT_XML).post(String.class));
//		
//		// Get XML for application
//		System.out.println(service.path("subelo").path("todo").accept(
//				MediaType.APPLICATION_JSON).get(String.class));
//		// Get JSON for application
//		System.out.println(service.path("rest").path("todo").accept(
//				MediaType.APPLICATION_XML).get(String.class));
//	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				serverURL + "/rest/subelo").build();
	}
}
