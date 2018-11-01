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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import subelo.protocol.Message;


public class XMLEncoder {

	public static String getXMLStringFromBean(Object bean) throws XMLException{
		try {
		// Create JAXB context and instantiate marshaller
		JAXBContext context = JAXBContext.newInstance(bean.getClass());
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		m.marshal(bean, System.out);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			m.marshal(bean, baos);
		} finally {
			try {
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new XMLException(e);
			}
		}
		
		return baos.toString();
		
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new XMLException(e);
		}
	}
	
	public static <T extends Message> Object getBeanFromXMLString(String xmlString, Class<T> clazz) throws XMLException{
		try{
			// Create JAXB context and instantiate unmarshaller
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			ByteArrayInputStream bios = new ByteArrayInputStream(xmlString.getBytes());
	
			try {
				return unmarshaller.unmarshal(bios);
			} finally {
				try {
					bios.close();
				} catch (Exception e) {
					e.printStackTrace();
					throw new XMLException(e);
				}
			}
		
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new XMLException(e);
		}
	}
}
