package mul.camp.a.dao;


import mul.camp.a.dto.HospitalDto;
import mul.camp.a.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HospitalDao {
	
    HospitalDto codeCheck(String code);
    int insertHospital(HospitalDto dto);
    int insertHospitalAf(MemberDto dto);

}
