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

package subelo.server.xml;

public class XMLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -742417300120215489L;

	public XMLException() {
		super();
	}

	public XMLException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public XMLException(String arg0) {
		super(arg0);
	}

	public XMLException(Throwable arg0) {
		super(arg0);
	}
	
}
