package mul.camp.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.CalendarDto;


@Mapper
@Repository

public interface CalendarDao {
	// 신청
	public int offApply(List<CalendarDto> dto);
	// 리스트 ex- 4월 기준 전체 리스트
	public List<CalendarDto> offList(String date);
	// 취소
	public int offCancel(CalendarDto dto);
	public List<CalendarDto> dutyList(CalendarDto dto);
	//메모 추가
	public int memoInsert(CalendarDto dto);
	//본인 off 개수 확인
	public int offCount(String date, String id);
}
