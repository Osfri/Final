package mul.camp.a.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import mul.camp.a.dto.CalendarDto;

public class readExcel {

	public static ArrayList<CalendarDto> readExcelFile(String fileName, HttpServletRequest req, String date) {
		ArrayList<CalendarDto> list = new ArrayList<CalendarDto>();
		String UPLOADPATH = req.getServletContext().getRealPath("/upload");
			
		try{
			String filePath = UPLOADPATH + File.separator + fileName;
			File file = new File(filePath);
	        
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = null;

			if(fileName.endsWith("xlsx")){ workbook = new XSSFWorkbook(fis); } 
			else if(fileName.endsWith("xls")){ workbook = new HSSFWorkbook(fis); }
			
			//엑셀 index는 0부터 시작
			int rowindex=0;
				
			//시트 수
			Sheet sheet = workbook.getSheetAt(0);
			//행의 수
			for(rowindex=5; rowindex <7 ; rowindex++) {
					
				//행 읽기
				Row row = sheet.getRow(rowindex);
					
				for(int i=2; i<10; i++) {
					CalendarDto ed = new CalendarDto();
					
					ed.setId(Integer.toString(i));
					ed.setName(String.valueOf(row.getCell(0)));
					ed.setWdate(date+"-"+(i-1));
					ed.setTime(String.valueOf(row.getCell(i)));
					list.add(ed);
				}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
