package thresholdset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	
	private String savePath = "";
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public ExcelWriter() {
		// TODO Auto-generated constructor stub
	}
	
	public ExcelWriter( String path){
		// TODO Auto-generated constructor stub
		savePath = path;
		
		try {
			
			workbook = new XSSFWorkbook();
//			workbook = new XSSFWorkbook (fis);
			sheet = workbook.createSheet("Java Books");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
	public void writeData(String[] data , int row){
		Row xssfRow = sheet.createRow(row);
		for(int i = 0; i < data.length; i++){
			Cell cell = xssfRow.createCell(i);
			cell.setCellValue(data[i]);
		}
		
	}
	
	public void save(){
		try {
			FileOutputStream fos =new FileOutputStream(new File(savePath));
		    workbook.write(fos);
		    fos.close();
			System.out.println("Done");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
