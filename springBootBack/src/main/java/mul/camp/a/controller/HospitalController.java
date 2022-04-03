package mul.camp.a.controller;


import mul.camp.a.dto.HospitalDto;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {

    @Autowired
    HospitalService service;

    /*HospitalDto codeCheck(String code);
    int insertHospital(HospitalDto dto);
    int insertHospitalAf(MemberDto dto);*/
    @RequestMapping(value = "/codeCheck",method = RequestMethod.POST)
    public HospitalDto codeCheck(@RequestBody String code){
        System.out.println("코드 체크"+code);
        return service.codeCheck(code);
    }
    @RequestMapping(value = "/insertHospital",method = RequestMethod.POST)
    public int insertHospital(@RequestBody HospitalDto dto){
        System.out.println("인서트병원"+dto.toString());
        return service.insertHospital(dto);
    }
    
    @RequestMapping(value = "/insertHospitalAf",method = RequestMethod.POST)
    public int insertHospitalAf(@RequestBody MemberDto dto){
        System.out.println("인서트병원AF"+dto.toString());
        return service.insertHospitalAf(dto);
    }
}
