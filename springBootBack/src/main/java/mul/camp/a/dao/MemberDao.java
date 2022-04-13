package mul.camp.a.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.MemberDto;

import java.util.ArrayList;

@Mapper
@Repository
public interface MemberDao {

	
    MemberDto login(MemberDto dto);
    int register(MemberDto dto);
    String emailCheck(MemberDto dto);
    String idCheck(MemberDto dto);

    ArrayList<MemberDto> allmember(String code);
    int yesjoin(MemberDto dto);
    int nojoin(MemberDto dto);
    ArrayList<MemberDto> waitmember(String code);
}




