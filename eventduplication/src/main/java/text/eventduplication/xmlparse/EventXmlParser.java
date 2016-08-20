package text.eventduplication.xmlparse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;   
import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.NamedNodeMap;  
import org.w3c.dom.Node;  
import org.w3c.dom.NodeList; 

public class EventXmlParser {

	public EventXmlParser() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * func to read all xml files in the folder.
	 * */
	
	static public ArrayList<String[]> getXmlFileContent(String folderPath) {
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		try{
			File file = new File(folderPath);
			File[] childs = file.listFiles();
			
			for (File child : childs) {
				String absolutePath = child.getAbsolutePath();
				if(child.isFile() && absolutePath.endsWith("xml")){
					ArrayList<String []> smallList = readXML(child);
					dataList.addAll(smallList);
				}
			}
		}catch(NullPointerException e){
			System.out.println("Fail to read file.");
			e.printStackTrace();
		}

		return dataList;
	}
	
	/*
	 * func to read single xml file.
	 * */
	
	static public ArrayList<String[]> getXmlContent(String filePath) {
		
		File file = new File(filePath);
		if (file.isFile() && file.getAbsolutePath().endsWith("xml")) {
			ArrayList<String []> result = readXML(file);
			return result;
		}
		else{
			return null;
		}
	}
	
	/*
	 * Function to read XML file and return back a array of string.
	 * */
	static private ArrayList<String[]> readXML(File xmlFile){
		
		ArrayList<String[]> dataList = new ArrayList<String[]>(); 
		
		try{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			
			Document document = documentBuilder.parse(xmlFile);
			
			// Get root element, should be TagName = DATA.
			Element root = document.getDocumentElement();
			NodeList children = root.getChildNodes();
			
			System.out.println("--DATA  " + root.getNodeName() + children.getLength());
			
			
			for(int i = 0; i < children.getLength(); i++){
				Node node = children.item(i);
				
				if (node.getNodeName().contains("Item") == false){
					continue;
				}
				
				HashMap<String, String> dataMap = new HashMap<String, String>();
				System.out.println(node.getNodeName() );
				NamedNodeMap map = node.getAttributes();
				for (int k = 0; k < map.getLength(); k++){
					
					Attr attr = (Attr)map.item(k);
					//System.out.println("----" + attr.getName()+ "  " + attr.getValue());
					dataMap.put(attr.getName(), attr.getValue());
				}
				String[] tmp = new String[map.getLength()];
				
				
				
				tmp[0] = dataMap.get("NAME").replaceAll("'", "''");
				tmp[1] = dataMap.get("STARTDATE").replaceAll("'", "''");
				tmp[2] = dataMap.get("ENDDATE").replaceAll("'", "''");
				tmp[3] = dataMap.get("LOCATION").replaceAll("'", "''");
				tmp[4] = dataMap.get("LINK").replaceAll("'", "''");
				tmp[5] = dataMap.get("DESCRIPTION").replaceAll("'", "''");
				
				dataList.add(tmp);
				
			}
		}
		catch(ParserConfigurationException exception){
			exception.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return dataList;
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
