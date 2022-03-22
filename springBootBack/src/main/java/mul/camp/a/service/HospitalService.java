package mul.camp.a.service;


import mul.camp.a.dao.HospitalDao;
import mul.camp.a.dto.HospitalDto;
import mul.camp.a.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HospitalService {

    @Autowired
    HospitalDao dao;

    public HospitalDto codeCheck(String code){
        return dao.codeCheck(code);
    }
    public int insertHospital(HospitalDto dto){
        return dao.insertHospital(dto);
    }
    public int insertHospitalAf(MemberDto dto){
        return dao.insertHospitalAf(dto);
    }
}
