package mul.camp.a.dao;

import mul.camp.a.dto.CalendarDto;
import mul.camp.a.dto.ParttimeDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AlarmDao {
    public List<ParttimeDto> alarmList(String code);
    public List<CalendarDto> calList(String id);
}
