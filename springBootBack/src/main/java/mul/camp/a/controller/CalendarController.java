package mul.camp.a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		date = date.substring(1, 8);
		System.out.println(date);
		List<CalendarDto> result = service.offList(date);
		System.out.println(result.toString());
		return result;
	}
}
