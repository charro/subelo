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

package subelo.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.json.JSONObject;

@SuppressWarnings("restriction")
@XmlRootElement (name="response")
public class ResponseMessage implements Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7849584743251544181L;
	private ResponseCodes responseCode;
	private String value;

	private Map<String, Object> responseValues;
	
	public ResponseMessage(ResponseCodes responseCode, String value) {
		super();
		this.responseCode = responseCode;
		this.value = value;
	}
	
	public ResponseMessage() {
		super();
	}

	public ResponseCodes getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(ResponseCodes responseCode) {
		this.responseCode = responseCode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public Map<String, Object> getResponseValues() {
		return responseValues;
	}

	// Using customized Map adapter for Maps to Json
	@XmlJavaTypeAdapter(MapAdapter.class)
	public void setResponseValues(Map<String, Object> responseValues) {
		this.responseValues = responseValues;
	}
	
	@Override
	public String toString() {
		return "Code:" + responseCode + " Value:" + value;
	}
}
