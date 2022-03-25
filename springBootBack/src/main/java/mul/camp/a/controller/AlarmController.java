package mul.camp.a.controller;


import mul.camp.a.dto.ParttimeDto;
import mul.camp.a.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlarmController {

    @Autowired
    AlarmService service;

    @RequestMapping(value = "/alarmList",method = RequestMethod.POST)
    public List<ParttimeDto> alarmList(String id){
        System.out.println("알람리스트 컨트롤러 =="+id);
        List<ParttimeDto> dto = service.alarmList(id);
        System.out.println("알람리스트 컨트롤러 리턴 =="+dto.toString());
        return dto;
    }
}
