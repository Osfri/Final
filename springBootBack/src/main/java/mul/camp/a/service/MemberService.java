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
	
	public ArrayList<MemberDto> allmember(String code) { return dao.allmember(code); }
	public int yesjoin(MemberDto dto) { return dao.yesjoin(dto); }
	public int nojoin(MemberDto dto) { return dao.nojoin(dto); }
	public ArrayList<MemberDto> waitmember(String code) { return dao.waitmember(code); }
	
}

