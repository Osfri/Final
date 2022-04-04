package mul.camp.a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mul.camp.a.dto.MemberDto;
import mul.camp.a.service.WebMainService;


@RestController
public class WebMainController {
	
	@Autowired
	WebMainService service;

	@RequestMapping(value ="/getMemberList", method = RequestMethod.POST)
	public List<MemberDto> getMemberList(MemberDto login) {
		System.out.println("getMemberList "+login);
		List<MemberDto> result = service.getMemberList(login);
		System.out.println(result.toString());
		return result;
	}
}
