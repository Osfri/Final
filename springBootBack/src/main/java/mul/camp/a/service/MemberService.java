package mul.camp.a.service;

import java.util.ArrayList;
import java.util.List;

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
	

	public MemberDto login(MemberDto dto){
		MemberDto mem = dao.login(dto);
		if (mem != null){
			return mem;
		}else {
			return null;
		}
	}
	public int register(MemberDto dto){
		return dao.register(dto);
	}
	public String emailCheck(MemberDto dto){
		return dao.emailCheck(dto);
	}
	public String idCheck(MemberDto dto){
		return dao.idCheck(dto);
	}
	
	public ArrayList<MemberDto> getMember(){
		
		return dao.getMember();
	}
	
	public int calendar(List<CalendarDto> list) {
		//dao.delCalendar(list);
		return dao.upCalendar(list);
	}
	
//	public int calendar(CalendarDto list) {
//		//dao.delCalendar(list);
//		return dao.upCalendar(list);
//	}
	
}

