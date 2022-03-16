package mul.camp.a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mul.camp.a.service.MemberService;

@RestController
public class MemberController {
	
	/*static {
	    System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
	}*/
	// 아무거나주석123 
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
		System.out.println(a);

		System.out.println("ddd");
		System.out.println("dssss");
		return a;
		
	}
}
