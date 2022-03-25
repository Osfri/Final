package mul.camp.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.CalendarDto;


@Mapper
@Repository
public interface CalendarDao {
	public int offApply(List<CalendarDto> dto);
	public List<CalendarDto> offList(String date);
}
