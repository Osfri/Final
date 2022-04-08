package mul.camp.a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mul.camp.a.dto.ChatDto.HospitalDto;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.ParttimeDto;
import mul.camp.a.service.WebMainService;


@RestController
public class WebMainController {
	
	@Autowired
	WebMainService service;

	@RequestMapping(value ="/getMemberList", method = RequestMethod.POST)
	public List<MemberDto> getMemberList(String code, String hospital) {
		System.out.println("getMemberList "+code+" "+hospital);
		List<MemberDto> result = service.getMemberList(code, hospital);
		System.out.println("getMemberList result=="+result.toString());
		return result;
	}
	
	@RequestMapping(value ="/addHospital", method = RequestMethod.POST)
	public String addHospital(String name, String curCode) {
		System.out.println("addHospital "+name+curCode);
		int cnt = service.hospitalChk(curCode+"_"+name);
		System.out.println("cnt============"+cnt);
		if(cnt != 0) {
			return "already";
		}
		int result = service.addHospital(curCode, curCode+"_"+name, name);
		if(result>0) {
			return "success";
		}else {
			return "error";
		}
	}
	
	@RequestMapping(value ="/getHospitalList", method = RequestMethod.POST)
	public List<HospitalDto> getHospitalList(String code) {
		System.out.println("getHospitalList "+code);
		List<HospitalDto> result = service.getHospitalList(code);
		System.out.println("getHospitalList result=="+result.toString());
		return result;
	}
	
	@RequestMapping(value ="/delHospital", method = RequestMethod.POST)
	public String delHospital(String code) {
		System.out.println("delHospital "+code);
		int result = service.delHospital(code);
		if(result>0) {
			return "success";
		}else {
			return "error";
		}
	}
	
	@RequestMapping(value ="/toStaff", method = RequestMethod.POST)
	public String toStaff(String id) {
		System.out.println("toStaff "+id);
		int result = service.toStaff(id);
		if(result>0) {
			return "success";
		}else {
			return "error";
		}
	}
	
	@RequestMapping(value ="/toManager", method = RequestMethod.POST)
	public String toManager(String id, String code) {
		System.out.println("toManager "+id+" "+code);
		int cnt = service.managerChk(code);
		System.out.println("cnt======="+cnt);
		if(cnt != 0) {
			return "already";
		}
		int result = service.toManager(id);
		if(result>0) {
			return "success";
		}else {
			return "error";
		}
	}
	
	@RequestMapping(value ="/toYes", method = RequestMethod.POST)
	public String toYes(String id) {
		System.out.println("toYes "+id);
		int result = service.toYes(id);
		if(result>0) {
			return "success";
		}else {
			return "error";
		}
	}
	
	@RequestMapping(value ="/toNo", method = RequestMethod.POST)
	public String toNo(String id) {
		System.out.println("toNo "+id);
		int result = service.toNo(id);
		if(result>0) {
			return "success";
		}else {
			return "error";
		}
	}

	@RequestMapping(value ="/changeHospital", method = RequestMethod.POST)
	public String changeHospital(String id, String code) {
		System.out.println("changeHospital "+id+" "+code);
		int auth = service.authChk(id);
		System.out.println(auth);
		if(auth == 3) {
			return "manager";
		}
		int result = service.changeHospital(id, code.substring(2));
		if(result>0) {
			return "success";
		}else {
			return "error";
		}
	}
	
	@RequestMapping(value ="/getParttime", method = RequestMethod.POST)
	public List<ParttimeDto> getParttime(String code) {
		System.out.println("getParttime "+code);
		List<ParttimeDto> result = service.getParttime(code.split("_")[0]);
		System.out.println(result.toString());
		
		return result;
	}
	
	@RequestMapping(value ="/saveTime", method = RequestMethod.POST)
	public String saveTime(String name, String st, String et, String code) {
		System.out.println("saveTime "+name + " " +code+" "+st+" "+et);
		int result = service.saveTime(name, st, et, code.split("_")[0]);
		if(result>0) {
			return "success";
		}else {
			return "error";
		}
	}
	
	@RequestMapping(value ="/point", method = RequestMethod.POST)
	public String point(String point, String code) {
		System.out.println("point "+code+" "+point);
		int result = service.point(code, point);
		if(result>0) {
			return "success";
		}else {
			return "error";
		}
	}
}
