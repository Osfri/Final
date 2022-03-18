package mul.camp.a.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.MemberDao;
import mul.camp.a.dto.CalendarDto;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	MemberDao dao;
	
	public String kim() throws Exception {
		String a = dao.kim();
		return a;
	}
	
	/*
	 * public static List<CalendarDto> readExcelData(HttpServletRequest req, String
	 * fileName) {
	 * 
	 * List<CalendarDto> list = new ArrayList<CalendarDto>(); String UPLOADPATH =
	 * req.getServletContext().getRealPath("/upload");
	 * 
	 * try {
	 * 
	 * //02.한국수자원공사 String file = UPLOADPATH + File.separator + fileName;
	 * 
	 * FileInputStream fis = new FileInputStream(file);
	 * 
	 * Workbook workbook = null;
	 * 
	 * 
	 * if(fileName.endsWith("xlsx")){ workbook = new XSSFWorkbook(); } else
	 * if(fileName.endsWith("xls")){ workbook = new HSSFWorkbook(); }
	 * 
	 * 
	 * // 탐색에 사용할 Sheet, Row, Cell 객체 HSSFSheet curSheet; HSSFRow curRow; HSSFCell
	 * curCell; // 행 ExcelDTO dto;
	 * 
	 * String year = "2017"; String month = null; // 월 String date = null; // 일
	 * 
	 * int day = 0; int mon = 0;
	 * 
	 * int numberOfSheets = workbook.getNumberOfSheets(); // 시트의 갯수 추출
	 * 
	 * for (int i = 0; i < numberOfSheets; i++) {
	 * 
	 * // 현재 sheet 반환 curSheet = (HSSFSheet) workbook.getSheetAt(i);
	 * 
	 * curRow = curSheet.getRow(1); // 두번째 행 추출
	 * 
	 * // 두번째 행의 첫번째 Cell 값 가져오기 String wlobscd =
	 * curRow.getCell(0).getStringCellValue(); // code 분할 wlobscd =
	 * wlobscd.substring(wlobscd.length() - 8, wlobscd.length() - 1);
	 * 
	 * // Row 돌리기/* //for(int rowIndex = 5; rowIndex < 36; rowIndex++) { for (int
	 * rowIndex = 7; rowIndex < 38; rowIndex++) { //for (int rowIndex = 8; rowIndex
	 * < 39; rowIndex++) { //for (int rowIndex = 6; rowIndex < 37; rowIndex++) {
	 * 
	 * curRow = curSheet.getRow(rowIndex); // 현재 행 읽기 day++;
	 * 
	 * // 현재 행의 셀의 수 만큼 읽기 for (int cellIndex = 1; cellIndex < 25; cellIndex++) {
	 * 
	 * if (cellIndex % 2 != 0) { mon++;
	 * 
	 * if (mon == 13) { mon = 1; }
	 * 
	 * if (day == 32) { day = 1; }
	 * 
	 * if (mon <= 9) { month = "0" + mon; } else {
	 * 
	 * month = Integer.toString(mon); }
	 * 
	 * if (day <= 9) { date = "0" + day; } else { date = Integer.toString(day); }
	 * 
	 * curCell = curRow.getCell(cellIndex);
	 * 
	 * curCell.setCellType(HSSFCell.CELL_TYPE_STRING);
	 * 
	 * switch (curCell.getCellType()) {
	 * 
	 * case HSSFCell.CELL_TYPE_NUMERIC:
	 * 
	 * dto = new ExcelDTO();
	 * 
	 * dto.setFw((float) curCell.getNumericCellValue()); dto.setTgt_de(year + "-" +
	 * month + "-" + date); dto.setFwobscd(wlobscd);
	 * 
	 * curCell = curRow.getCell(cellIndex + 1);
	 * 
	 * if (curCell.getStringCellValue().trim().equals("*") ||
	 * curCell.getStringCellValue().trim() == "*") { dto.setRev_at("Y"); }else {
	 * dto.setRev_at("N"); }
	 * 
	 * list.add(dto);
	 * 
	 * break;
	 * 
	 * case HSSFCell.CELL_TYPE_STRING:
	 * 
	 * if("".equals(curCell.getStringCellValue().trim()) ||
	 * curCell.getStringCellValue().isEmpty() || curCell.equals(null)) { break; }
	 * 
	 * dto = new ExcelDTO();
	 * 
	 * dto.setFw(Float.valueOf(curCell.getStringCellValue())); dto.setTgt_de(year +
	 * "-" + month + "-" + date); dto.setFwobscd(wlobscd);
	 * 
	 * curCell = curRow.getCell(cellIndex + 1);
	 * 
	 * if (curCell.getStringCellValue().trim().equals("*") ||
	 * curCell.getStringCellValue().trim() == "*") { dto.setRev_at("Y");
	 * 
	 * }else { dto.setRev_at("N"); }
	 * 
	 * list.add(dto);
	 * 
	 * break; } } } } }
	 * 
	 * fis.close();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return list;
	 * 
	 * }
	 */
	

}







