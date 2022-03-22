package mul.camp.a.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.CalendarDto;
import mul.camp.a.dto.MemberDto;

@Mapper
@Repository
public interface MemberDao {

	
	ArrayList<MemberDto> getMember();
    MemberDto login(MemberDto dto);
    int register(MemberDto dto);
    String emailCheck(MemberDto dto);
    String idCheck(MemberDto dto);
    
    int calendarInsert(List<CalendarDto> list);
    int calendarUpdate(List<CalendarDto> list);
}




