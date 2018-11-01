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

/**
 * The strings below must correspond with the ones on the REST service
 * They will be used when calling each method on the remote service.
 * 
 * @author Miguel Rivero
 *
 */
public enum AvailableServices {

	HELLO ("hello"), 
	ADD_ITEM ("addItem"), 
	TEST_JSON("testJSON"), 
	TEST_POST_JSON("testPostJSON"), 
	LIST_MY_ITEMS("listMyItems") ;
	
	private String path;
	
	private AvailableServices(String path){
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
