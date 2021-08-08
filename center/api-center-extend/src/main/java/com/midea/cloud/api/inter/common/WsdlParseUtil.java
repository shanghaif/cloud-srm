package com.midea.cloud.api.inter.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WsdlParseUtil {
	static void resolve(String wsdl) throws Exception {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    DocumentBuilder builder = factory.newDocumentBuilder();

	    InputStream in = new ByteArrayInputStream(wsdl.getBytes("utf-8"));
	    InputStreamReader isr = new InputStreamReader(in, "utf-8");

	    InputSource is = new InputSource(isr);
	    Document doc = builder.parse(is);

	    System.out.println("root:" + doc.getFirstChild().getNodeName());

	    NodeList nodeList = doc.getElementsByTagNameNS("http://schemas.xmlsoap.org/wsdl/", "definitions");
	    Node root = nodeList.item(0);
	    NamedNodeMap map = root.getAttributes();
	    Node targetNs = map.getNamedItem("targetNamespace");
	    System.out.println("名称空间:" + targetNs.getNodeValue());

	    String wsdlNsUri = "http://schemas.xmlsoap.org/wsdl/";

	    NodeList bindingNodes = doc.getElementsByTagNameNS(wsdlNsUri, "binding");
	    Node bindingNode = bindingNodes.item(0);
	    String serviceName = bindingNode.getAttributes().getNamedItem("name").getNodeValue();
	    System.out.println("服务名:" + serviceName);

	    NodeList serviceNodes = doc.getElementsByTagNameNS(wsdlNsUri, "service");
	    Node serviceNode = serviceNodes.item(0);
	    NodeList portNodes = serviceNode.getChildNodes();
	    
	    String bindingAddress = null;
	    Node addressNode = portNodes.item(1).getChildNodes().item(1);
	    bindingAddress = addressNode.getAttributes().getNamedItem("location").getNodeValue();
	    System.out.println("调用地址:" + bindingAddress);

	    NodeList portTypeNodes = doc.getElementsByTagNameNS(wsdlNsUri, "portType");
	    Node portType = portTypeNodes.item(0);
	    NodeList opNodes = portType.getChildNodes();
	    for (int i = 0; i < opNodes.getLength(); i++) {
	        Node node = opNodes.item(i);
	        if (node.getNodeType() == Node.ELEMENT_NODE && node.getLocalName().equalsIgnoreCase("operation")) {
	            String methodName = node.getAttributes().getNamedItem("name").getNodeValue();
	            System.out.println("方法名:" + methodName);
	            resoleElement(doc, node);
	        }
	    }
	}
	
	
	public static void resoleElement(Document doc,Node method ) {
		if (method.hasChildNodes()) {
			NodeList nodeList = method.getChildNodes();
			for (int i =0;i<nodeList.getLength();i++) {
				Node node = nodeList.item(i);
				if ("input".equals(node.getLocalName())) { //入参
					System.out.println(node.getAttributes().getNamedItem("name"));
					
					NodeList bindingNodes = doc.getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema", "element");
					if (bindingNodes.getLength()>0) {
						for (int j=0;j<bindingNodes.getLength();j++) {
							String name = bindingNodes.item(j).getAttributes().getNamedItem("name").getNodeValue();
							String type = bindingNodes.item(j).getAttributes().getNamedItem("type").getNodeValue();
							if (node.getAttributes().getNamedItem("name").getNodeValue().equals(name)) {
								if (isComplexType(type)) {
									System.out.println("复杂类型："+name);
									resoleComplexType(doc, name);
								} else {
									System.out.println("简单类型："+name);
								}
							}
							
						}
					}
				} else if ("output".equals(node.getLocalName())) { //出参
					System.out.println(node.getAttributes().getNamedItem("name"));
					NodeList bindingNodes = doc.getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema", "element");
					if (bindingNodes.getLength()>0) {
						for (int j=0;j<bindingNodes.getLength();j++) {
							String name = bindingNodes.item(j).getAttributes().getNamedItem("name").getNodeValue();
							String type = bindingNodes.item(j).getAttributes().getNamedItem("type").getNodeValue();
							if (node.getAttributes().getNamedItem("name").getNodeValue().equals(name)) {
								if (isComplexType(type)) {
									resoleComplexType(doc, name);
								}
							}
							
						}
					}
				}
			}
		}
	}
	
	
	
	public static Boolean isComplexType(String type) {
		if ("xs:string".equals(type)) {
			return false;
		} else if ("xs:long".equals(type)) {
			return false;
		} else if ("xs:anyType".equals(type)) {
			return false;
		}
		return true;
	}
	
	public static void resoleComplexType(Document doc,String name) {
		NodeList bindingNodes = doc.getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema", "complexType");
		if (bindingNodes.getLength()>0) {
			for (int i=0;i<bindingNodes.getLength();i++) {
				String nameTemp = bindingNodes.item(i).getAttributes().getNamedItem("name").getNodeValue();
				if (name.equals(nameTemp)) {
					NodeList childs = bindingNodes.item(i).getChildNodes().item(1).getChildNodes();
					if (childs.getLength() > 0) {
						for (int j=0;j<childs.getLength();j++) {
							if (null == childs.item(j).getLocalName()) {
								continue;
							}
							String nameC = childs.item(j).getAttributes().getNamedItem("name").getNodeValue();
							String typeC = childs.item(j).getAttributes().getNamedItem("type").getNodeValue();
							if (isComplexType(typeC)) {
								System.out.println("复杂类型："+nameC);
								resoleComplexType(doc, typeC.replace("tns:", ""));
							} else {
								System.out.print("简单类型："+nameC);
								System.out.println(" 类型："+typeC);
							}
						}
					}
					
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			String xml = WsdlParseUtil.getResult("http://10.16.87.99:8809/registerService/categoryService?wsdl");
			WsdlParseUtil.resolve(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static String getResult(String url)
            throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>("", header);
		ResponseEntity<String> result = new RestTemplate().exchange(url, HttpMethod.GET, httpEntity, String.class);
		return result.getBody();
    }
}
