package mul.camp.a.controller;


import mul.camp.a.dto.CalendarDto;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.ParttimeDto;
import mul.camp.a.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AlarmController {

    @Autowired
    AlarmService service;

    @RequestMapping(value = "/alarmList",method = RequestMethod.POST)
    public List<ParttimeDto> alarmList(@RequestBody String code){
        System.out.println("알람리스트 컨트롤러 =="+code);
        List<ParttimeDto> dto = service.alarmList(code.replaceAll("\"",""));
        System.out.println("알람리스트 컨트롤러 리턴 =="+dto.toString());
        return dto;
    }
    @RequestMapping(value = "/calList",method = RequestMethod.POST)
    public List<CalendarDto> calList(@RequestBody String id){
        System.out.println("캘린더리스트 컨트롤러 =="+id);
        List<CalendarDto> dto = service.calList(id.replaceAll("\"",""));
        System.out.println("캘린더리스트 컨트롤러 리턴 =="+dto.toString());
        return dto;
    }
}
