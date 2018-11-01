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

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import subelo.protocol.AvailableServiceProviders;

@SuppressWarnings("restriction")
@XmlRootElement
public class RequestMessage implements Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6900521242349483560L;
	private AvailableServiceProviders serviceCode;
	private AvailableServices serviceOperation;
	private Map<String, String> params;
	
	public RequestMessage(AvailableServiceProviders serviceCode, AvailableServices serviceOperation,
			Map<String, String> params) {
		super();
		this.serviceCode = serviceCode;
		this.serviceOperation = serviceOperation;
		this.params = params;
	}
	public RequestMessage() {

	}
	
	public AvailableServiceProviders getServiceCode() {
		return serviceCode;
	}
	
	public void setServiceCode(AvailableServiceProviders serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public AvailableServices getServiceOperation() {
		return serviceOperation;
	}
	
	public void setServiceOperation(AvailableServices serviceOperation) {
		this.serviceOperation = serviceOperation;
	}
	
	public Map<String, String> getParams() {
		return params;
	}
	
	// Using customized Map adapter for Maps to Json
	@XmlJavaTypeAdapter(MapAdapter.class)
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	@Override
	public String toString() {
		String paramsString = "| params[] = ";
		for(Entry<String, String> param : params.entrySet()){
			paramsString = "(" + param.getKey() + ":" + param.getValue() + ") ";
		}
		return (serviceCode + " | " + serviceOperation + paramsString);
	}
	
}
