package mul.camp.a.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.FileDao;
import mul.camp.a.dto.CalendarDto;
import mul.camp.a.dto.MemberDto;

@Service
@Transactional
public class FileService {
	@Autowired
	FileDao dao;
	
	public ArrayList<MemberDto> getMember(String code){
		
		return dao.getMember(code);
	}
	
	public int calendarInsert(List<CalendarDto> list) {
		return dao.calendarInsert(list);
	}
	
	public int calendarUpdate(List<CalendarDto> list) {
		return dao.calendarUpdate(list);
	}

	public List<CalendarDto> offChk(CalendarDto cd){
		return dao.offChk(cd);
	}
}
