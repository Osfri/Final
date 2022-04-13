package mul.camp.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.WebMainDao;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.ParttimeDto;
import mul.camp.a.dto.CalendarDto;
import mul.camp.a.dto.ChatDto.HospitalDto;

@Service
@Transactional
public class WebMainService {

	@Autowired
	WebMainDao dao;
	
	public MemberDto login(MemberDto dto){
		MemberDto mem = dao.login(dto);
		if (mem != null){
			return mem;
		}else {
			return null;
		}
	}
	
	public List<MemberDto> getMemberList(int start, int end, String code, String hospital){
		return dao.getMemberList(start, end, code, hospital);
	}
	
	public List<HospitalDto> getHospitalList(String code){
		return dao.getHospitalList(code);
	}
	
	public List<HospitalDto> getHospitalListPage(int start, int end, String code){
		return dao.getHospitalListPage(start, end, code);
	}
	
	public int addHospital(String curCode,String code, String name) {
		return dao.addHospital(curCode, code, name);
	}
	
	public int hospitalChk(String code) {
		return dao.hospitalChk(code);
	}
	
	public int delHospital(String code) {
		return dao.delHospital(code);
	}
	
	public int toStaff(String id) {
		return dao.toStaff(id);
	}
	
	public int toManager(String id) {
		return dao.toManager(id);
	}
	
	public int managerChk(String code) {
		return dao.managerChk(code);
	}
	
	public int toYes(String id) {
		return dao.toYes(id);
	}
	
	public int toNo(String id) {
		return dao.toNo(id);
	}
	
	public int changeHospital(String id, String code) {
		return dao.changeHospital(id, code);
	}
	
	public int authChk(String id) {
		return dao.authChk(id);
	}
	
	public List<ParttimeDto> getParttime(String code) {
		return dao.getParttime(code);
	}
	
	public int saveTime(String name, String st, String et, String code) {
		return dao.saveTime(name, st, et, code);
	}
	
	public int point(String code, String point) {
		return dao.point(code, point);
	}
	
	public int getStaffCount(String hospital, String code) {
		return dao.getStaffCount(hospital, code);
	}
	
	public int getHospitalCount(String code) {
		return dao.getHospitalCount(code);
	}
	
	public List<CalendarDto> getScheduleList(String code, String date) {
		return dao.getScheduleList(code, date);
	}
}
