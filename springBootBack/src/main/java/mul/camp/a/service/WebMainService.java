package mul.camp.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.WebMainDao;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.ChatDto.HospitalDto;

@Service
@Transactional
public class WebMainService {

	@Autowired
	WebMainDao dao;
	
	public List<MemberDto> getMemberList(MemberDto login){
		return dao.getMemberList(login);
	}
	
	public List<HospitalDto> getHospitalList(String code){
		return dao.getHospitalList(code);
	}
	
	public int addHospital(String curCode,String code, String name) {
		return dao.addHospital(curCode, code, name);
	}
	
	public int hospitalChk(String code) {
		return dao.hospitalChk(code);
	}
}
