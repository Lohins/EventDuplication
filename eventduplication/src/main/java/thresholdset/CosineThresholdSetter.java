package thresholdset;

import java.io.File;
import java.util.ArrayList;

import org.openxmlformats.schemas.drawingml.x2006.chart.STXstring;

import text.eventduplication.xmlparse.*;
import simAlgorithm.*;

public class CosineThresholdSetter {
	
	private String SaveBasePath = "";

	public CosineThresholdSetter() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * DESC: depends on the given beginning threshold and step to filter events 
	 * of which the similarity lower than threshold.
	 * */
	public void generateThreshold( ArrayList<String[]> events, int index, String tag, double start_threshold, double step ) {
		CosineSimilarity cosineSimilarity = new CosineSimilarity();
		
		// init excel writer
		String saveFolder = System.getProperty("user.dir") + "\\excel";
		String excelName = saveFolder +  "\\Cosine-" + tag + ".xlsx"; 
		File file = new File(excelName);
		if (file.exists() && file.isDirectory()){
			file.delete();
		}
		try {
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println(excelName);
		
		ExcelWriter excelWriter = new ExcelWriter(excelName);
		
		int rowCount = 0;
		
		String[] labels = {"Name" , "StartDate", "EndDate" , "Url" , "Location" , "Description"};
		excelWriter.writeData(labels, rowCount);
		rowCount++;
		
		for (int i = 0; i < events.size(); i++){
			for (int k = 1; k < events.size(); k++){
				
				if (i <= k) {
					continue;
				}
				
				String[] event1 = events.get(i);
				String[] event2 = events.get(k);
				
				String text1 = event1[index];
				String text2 = event2[index];
				
				double similarity = cosineSimilarity.similarity(text1, text2);
				
				if (similarity >= start_threshold && similarity < (start_threshold + step)) {
					// save data
					excelWriter.writeData(event1, rowCount);
					rowCount++;
					excelWriter.writeData(event2, rowCount);
					rowCount++;
					
					String[] tmp = {String.valueOf(similarity), "","","","",""};
					excelWriter.writeData(tmp, rowCount);
					rowCount++;
				}
			}
		}
		
		String[] sublabels = {"start threshold" , "step", "index" , "number" , "" , ""};
		excelWriter.writeData(sublabels, rowCount);
		rowCount++;
		String[] sum = {String.valueOf(start_threshold) , String.valueOf(step),  String.valueOf(index) , String.valueOf(rowCount / 3) , "" , ""};
		excelWriter.writeData(sum, rowCount);
		rowCount++;
		
		excelWriter.save();
	}

	public static void main(String[] args) {
		
		System.out.println(System.getProperty("user.dir"));
		
		String saveFolder = System.getProperty("user.dir") + "\\excel";
		
		File saveFile = new File(saveFolder);
		if (saveFile.exists() && saveFile.isDirectory()) {
		}
		else{
			saveFile.mkdir();
		}
		
		// TODO Auto-generated method stub
		String folderPath = "C:\\Users\\Yajing\\Desktop\\tmp";
		
		double start_threshold = 0.5;
		double step = 0.05;
		
		ArrayList<String[]> events = EventXmlParser.getXmlFileContent(folderPath);
		System.out.println("There are " + events.size() + " pieces of events");
		CosineThresholdSetter setter = new CosineThresholdSetter();
		setter.SaveBasePath = saveFolder;
		
		
		// for index 0 - Name
		for (double i = start_threshold; i <= 1.0; i=i+step){
			String tag = "NAME-th-" + String.valueOf(i).replaceAll("0.","" ) + "-step-"  +
					String.valueOf(step).replaceAll("0.","" );
			setter.generateThreshold(events, 0 , tag, i, step);
		}
		
		// for index 4 - Location
//		for (double i = start_threshold; i <= 1.0; i=i+step){
//			String tag = "Location-th-" + String.valueOf(i).replaceAll("0.","" ) + "-step-"  +
//			String.valueOf(step).replaceAll("0.","" );
//			setter.generateThreshold(events, 4 , tag, i, step);
//		}
		
		// for index 5 - Decsription
//		for (double i = start_threshold; i <= 1.0; i=i+step){
//			String tag = "Description-th-" + String.valueOf(i).replaceAll("0.","" ) + "-step-"  +
//			String.valueOf(step).replaceAll("0.","" );
//			setter.generateThreshold(events, 5 , tag, i, step);
//		}
		
		
		
		
	}

}
