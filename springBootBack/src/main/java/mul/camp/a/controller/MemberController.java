package mul.camp.a.controller;

import mul.camp.a.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mul.camp.a.service.MemberService;

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
}
