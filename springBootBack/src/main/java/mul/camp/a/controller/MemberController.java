package mul.camp.a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mul.camp.a.service.MemberService;

@RestController
public class MemberController {
	
	@Autowired
	MemberService aaa;
	
	@RequestMapping("/api")
	public String test() {
		
		String a = "";
		try {
			a = aaa.kim();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return a;
		
	}
}
