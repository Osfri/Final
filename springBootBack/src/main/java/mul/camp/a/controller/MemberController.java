package mul.camp.a.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.service.MemberService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

	@Autowired
	MemberService service;
	
	
	//@RequestBody
	@RequestMapping(value ="/login",method= RequestMethod.POST)
	public MemberDto login(@RequestBody MemberDto dto) {
		System.out.println("DTO"+dto.toString());
		MemberDto mem = service.login(dto);
		System.out.println("MEM"+mem.toString());
		return mem;

	}
	@RequestMapping(value = "/register",method= RequestMethod.POST)
	public String register(@RequestBody MemberDto dto){
		System.out.println("DTO"+dto.toString());
		int result = service.register(dto);
		System.out.println("result"+result);
		return "NO";
	}
	@RequestMapping(value = "/emailCheck",method = RequestMethod.POST)
	public String emailCheck(@RequestBody MemberDto dto){
		String mem = service.emailCheck(dto);
		System.out.println(mem);
		return mem;
	}
	@RequestMapping(value = "/idCheck",method = RequestMethod.POST)
	public String idCheck(@RequestBody MemberDto dto){
		String mem = service.idCheck(dto);
		System.out.println(mem);
		return mem;
	}
	

	@RequestMapping(value = "/allmember",method = RequestMethod.POST)
	public ArrayList<MemberDto> allmember(@RequestBody String code) {
		System.out.println("올멤버 컨트롤러"+code);
		ArrayList<MemberDto> mem = service.allmember(code.replaceAll("\"", ""));
		System.out.println(mem.toString());
		return mem;
	}
	@RequestMapping(value = "/yesjoin",method = RequestMethod.POST)
	public int yesjoin(@RequestBody MemberDto dto) {
		System.out.println("예스 컨트롤러"+dto.toString());
		return service.yesjoin(dto);
	}
	@RequestMapping(value = "/nojoin",method = RequestMethod.POST)
	public int nojoin(@RequestBody MemberDto dto) {
		System.out.println("no 컨트롤러"+dto.toString());
		return service.nojoin(dto);
	}
	@RequestMapping(value = "/waitmember",method = RequestMethod.POST)
	public ArrayList<MemberDto> waitmember(@RequestBody String code) {
		System.out.println("웨잇멤버 컨트롤러"+code);
		ArrayList<MemberDto> mem = service.waitmember(code.replaceAll("\"", ""));
		return mem;
	}
}
