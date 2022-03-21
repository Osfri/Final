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
import mul.camp.a.dto.MemberDto;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	MemberDao dao;
	
	public String kim() throws Exception {
		String a = dao.kim();
		return a;
	}
	
	public ArrayList<MemberDto> getMember(){
		
		return dao.getMember();
	}
	
}







