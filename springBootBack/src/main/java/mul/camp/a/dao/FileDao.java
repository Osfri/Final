package mul.camp.a.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.CalendarDto;
import mul.camp.a.dto.MemberDto;

@Mapper
@Repository
public interface FileDao {
    
	ArrayList<MemberDto> getMember(String code);
    int calendarInsert(List<CalendarDto> list);
    int calendarUpdate(List<CalendarDto> list);
    List<CalendarDto> offChk(CalendarDto cd);
}




