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

package xml;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import subelo.protocol.AvailableServiceProviders;
import subelo.protocol.AvailableServices;
import subelo.protocol.RequestMessage;
import subelo.server.xml.XMLEncoder;
import subelo.server.xml.XMLException;

public class XMLEncoderTest {

	@Test
	public void testGetXMLStringFromBean() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("param1", "value1");
		
		RequestMessage testMessage = new RequestMessage(AvailableServiceProviders.TWITTER, AvailableServices.HELLO, params);
		
		try{
			String xmlCode = XMLEncoder.getXMLStringFromBean(testMessage);
			assertNotNull(xmlCode);
			
			RequestMessage requestUnmarshalled = 
				(RequestMessage) XMLEncoder.getBeanFromXMLString(xmlCode, RequestMessage.class);
			System.out.println("\n Request: " + requestUnmarshalled);
			assertNotNull(requestUnmarshalled);
		}
		catch(XMLException ex){
			ex.printStackTrace();
			fail("Error when marshalling: " + ex.getMessage());
		}
	}

}
