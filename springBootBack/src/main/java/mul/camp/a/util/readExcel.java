package mul.camp.a.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import mul.camp.a.dto.CalendarDto;

public class readExcel {

	public static ArrayList<CalendarDto> readExcelFile(String fileName, HttpServletRequest req, String date, String code) {
		ArrayList<CalendarDto> list = new ArrayList<CalendarDto>();
		String UPLOADPATH = req.getServletContext().getRealPath("/upload");
		String path = UPLOADPATH + File.separator + code;
			
		try{
			String filePath = path + File.separator + fileName;
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
			for(rowindex=5; rowindex <35 ; rowindex++) {
					
				//행 읽기
				Row row = sheet.getRow(rowindex);
				if(String.valueOf(row.getCell((short)0))=="" || String.valueOf(row.getCell((short)0))==null) {
					break;
				}
				for(int i=3; i<34; i++) {
					if(String.valueOf(row.getCell(i))=="" || String.valueOf(row.getCell(i))==null) {
						break;
					}
					CalendarDto ed = new CalendarDto();
					ed.setId(String.valueOf(row.getCell(0)));
					ed.setName(String.valueOf(row.getCell(1)));
					String tmp = "0"+(i-2); 
					String d = tmp.substring(tmp.length()-2, tmp.length());
					ed.setWdate(date+"-"+d);
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
