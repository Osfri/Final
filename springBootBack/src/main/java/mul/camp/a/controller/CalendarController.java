package mul.camp.a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mul.camp.a.dto.CalendarDto;
import mul.camp.a.service.CalendarService;

@RestController
public class CalendarController {
	
	@Autowired
	CalendarService service;
	
	@RequestMapping(value ="/offApply",method= RequestMethod.POST)
	public String offApply(@RequestBody List<CalendarDto> offlist) {
		System.out.println("offApply"+offlist.toString());
		int i = service.offApply(offlist);
		if(i>0) {
			return "success"; 
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value ="/offList",method= RequestMethod.POST)
	public List<CalendarDto> offList(@RequestBody String date) {
		System.out.println("offList"+date);
		// "2022-04-14" 2022 04월까지 자름
 		date = date.substring(1, 8);
		System.out.println(date);
		List<CalendarDto> result = service.offList(date);
		System.out.println(result.toString());
		return result;
	}

	@RequestMapping(value ="/offCancel",method= RequestMethod.POST)
	public String offCancel(@RequestBody CalendarDto dto) {
		System.out.println("offCancel"+dto.toString());
		int i = service.offCancel(dto);
		if(i>0) {
			return "success"; 
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value ="/dutyList",method= RequestMethod.POST)
	public List<CalendarDto> dutyList(@RequestBody String id) {		
		System.out.println("dutyList"+id.replace("\"", ""));
		List<CalendarDto> result = service.dutyList(id.replace("\"", ""));
		System.out.println(result.toString());
		return result;
	}
}
