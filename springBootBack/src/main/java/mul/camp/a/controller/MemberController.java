package mul.camp.a.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mul.camp.a.dao.MemberDao;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import mul.camp.a.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.auth.FirebaseAuthException;

import mul.camp.a.dto.CalendarDto;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.service.MemberService;
import mul.camp.a.controller.FirebaseService;
import mul.camp.a.util.MediatypeUtils;
import mul.camp.a.util.readExcel;

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
	
	@RequestMapping(value ="/webLogin",method= RequestMethod.POST)
	public MemberDto webLogin(MemberDto dto) {
		MemberDto mem = service.login(dto);
		//System.out.println("MEM"+mem.toString());
		
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
