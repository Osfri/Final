package mul.camp.a.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.MemberDto;

@Mapper
@Repository
public interface MemberDao {

	
    MemberDto login(MemberDto dto);
    int register(MemberDto dto);
    String emailCheck(MemberDto dto);
    String idCheck(MemberDto dto);
    
}




