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

package subelo.server.services;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import subelo.protocol.AvailableServiceProviders;
import subelo.protocol.RequestMessage;
import subelo.protocol.ResponseCodes;
import subelo.protocol.ResponseMessage;
import subelo.server.services.external.twitter.TwitterServiceProvider;

@Path(value = "/subelo")
public class RestService {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path (value="/hello")
	public String getHello(){
		return "Hello there. This is the Hello REST Web Service!!";
	}	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path (value="/sendMap")
	@Consumes(MediaType.APPLICATION_JSON)
	public HashMap<String, String> sendMap(HashMap<String, String> map){
		return map;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path (value="/testJSON")
	public ResponseMessage testJson(){

		return new ResponseMessage(ResponseCodes.OK, "json test from subelo REST server");
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path (value="/testPostJSON")
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseMessage testPostJson(RequestMessage request){

		return new ResponseMessage(ResponseCodes.OK, 
				"Sucessfully received you request to the ServiceProvider: " + request);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path (value="/addItem")
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseMessage addNewItem(RequestMessage request){
		
		return getServiceImpl(request.getServiceCode()).addItem(request);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path (value="/listMyItems")
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseMessage listMyItems(RequestMessage request){
		
		return getServiceImpl(request.getServiceCode()).listMyItems(request);
	}
	
	
	private ServiceProvider getServiceImpl(AvailableServiceProviders provider){
		
		ServiceProvider serviceProvider = null;
		
		switch(provider){
			case TWITTER:
				serviceProvider = new TwitterServiceProvider();
				break;
		}
		
		return serviceProvider;
	}

}
