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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import android.sax.ElementListener;

public class MapAdapter extends XmlAdapter<Object, Map<?, ?>> {

	private static Map<String, Class<?>> rootElementName2class = new ConcurrentHashMap<String, Class<?>>();

	@Override
	public Map<?, ?> unmarshal(Object domTree) {
		// NOTE: LinkedHashMap is used here for predictability of JUnit results.
		// If this is not important, any other Hash implementation can be used.
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		Element content = (Element) domTree;
		NodeList childNodes = content.getChildNodes();
		if (childNodes.getLength() > 0) {
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node keyValueNode = childNodes.item(i);
				String key = keyValueNode.getNodeName();

				// Skip text nodes
				if (key.startsWith("#"))
					continue;

				Node valueSubTree = keyValueNode.getChildNodes().item(0);

				// Place-holder to keep the unmarshalled JAXBObject
				Object value = null;

				if (valueSubTree instanceof Text) {
					// This is a simple type
					value = ((Text) keyValueNode.getChildNodes().item(0))
							.getWholeText();
				} else if (valueSubTree instanceof Element) {
					// This is something more complex
					String nodeName = valueSubTree.getLocalName();
					String nodeNamespace = valueSubTree.getNamespaceURI();

					try {
						// Try to obtain a proper JAXBContext
						JAXBContext jaxbContext;
						
						// Build up a fully qualified name
						String qualName;
						if (nodeNamespace == null) {
							qualName = nodeName;
						} else {
							qualName = nodeNamespace + ":" + nodeName;
						}
						
						// Look-up a matching JAXBContext
						jaxbContext = getJAXBContextForElementName(qualName);

						// Use the obtain context for unmarshalling
						value = jaxbContext.createUnmarshaller().unmarshal(
								valueSubTree);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				map.put(key, value);

			}
		}
		return map;
	}

	@Override
	public Object marshal(Map<?, ?> map) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			Element customXml = doc.createElement("Map");

			for (Map.Entry<?, ?> entry : map.entrySet()) {
				Element keyValueElement = doc.createElement(entry.getKey()
						.toString());
				Object value = entry.getValue();

				JAXBContext jaxbContext = null;
				if (value != null)
					jaxbContext = getJAXBContextForClass(value.getClass());
				else
					value = "";

				if (jaxbContext != null) {
					// Use this context to serialize value
					Marshaller marshaller = jaxbContext.createMarshaller();
					Node node = keyValueElement;
					String valueNamespace = getRequiredObjectNamespace(value);
					// Let JAXB marshal it now
					marshaller.marshal(value, node);
					
					Node valueChild = node.getFirstChild();
					// valueChild.setPrefix(null);
					NodeList valueChildren = valueChild.getChildNodes();

					Node newValueChild = null;
					String qualifiedName;

					// If there is no specific namespace or only the default
					// one, no need to provide this namespace in the XML element
					if (valueNamespace == null
							|| valueNamespace.equals("##default")) {
						qualifiedName = valueChild.getLocalName();
						newValueChild = doc.createElement(qualifiedName);
					} else {
						// Check if this namespace is already known and
						// was assigned a prefix
						String prefix = customXml.lookupPrefix(valueNamespace);
						if (prefix == null)
							// If not assigned a prefix yet, generate a new one
							prefix = "nsValue";
						newValueChild = doc.createElementNS(valueNamespace,
								prefix + ":" + valueChild.getLocalName());
						qualifiedName = valueNamespace + ":"
								+ valueChild.getLocalName();
					}

					// We do it here, as only after JAXB has marshalled 
					// the object, the JAXB-specific qualified name of 
					// the element can can derived.
					
					rootElementName2class.put(qualifiedName, value.getClass());


					// Copy all children into the new node
					for (int i = 0; i < valueChildren.getLength(); ++i) {
						newValueChild.appendChild(valueChildren.item(i));
					}

					// Replace the generated child with our own
					node.removeChild(valueChild);
					node.appendChild(newValueChild);
				} else {
					
					// If the value is a List, create a list of child values
					if(value instanceof List<?>){
						for(Object arrayElement : (List<?>) value){
							Element newValueElement = doc.createElement(keyValueElement.getTagName());
							newValueElement.setTextContent(arrayElement.toString());
							customXml.appendChild(newValueElement);
						}
						// Go to the next element of the Map
						continue;
					}
					else{
						// value can be only marshaled to a simple String
						keyValueElement.appendChild(doc.createTextNode(value
								.toString()));
					}
				}
				customXml.appendChild(keyValueElement);
			}
			return customXml;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private String getRequiredObjectNamespace(Object value) {
		XmlRootElement annotation = value.getClass().getAnnotation(
				XmlRootElement.class);
		if (annotation != null)
			return annotation.namespace();
		return null;
	}

	/***
	 * Analyze a JAXB-annotated class, build a proper JAXBContext. Side-effect:
	 * Remember the mapping from the fully qualified element name to this class.
	 * 
	 * @param clz
	 * @return
	 * @throws JAXBException
	 */
	private static JAXBContext getJAXBContextForClass(Class<?> clz)
			throws JAXBException {
		if (clz == null)
			return null;

		if (!isJAXBAnnotated(clz))
			return null;

		JAXBContext jaxbContext = JAXBContext.newInstance(clz);
		return jaxbContext;
	}

	private static boolean isJAXBAnnotated(Class<?> clz) {
		XmlRootElement annXmlRootElement = clz
				.getAnnotation(XmlRootElement.class);
		return annXmlRootElement != null;
	}


	/***
	 * Given a fully qualified XML element name try to create the JAXBContext
	 * related to it.
	 * 
	 * @param elementName
	 * @return
	 * @throws JAXBException
	 */
	private JAXBContext getJAXBContextForElementName(String elementName)
			throws JAXBException {
		Class<?> clazz = rootElementName2class.get(elementName);
		return getJAXBContextForClass(clazz);
	}
}