package mul.camp.a.service;


import mul.camp.a.dao.AlarmDao;
import mul.camp.a.dao.CalendarDao;
import mul.camp.a.dto.CalendarDto;
import mul.camp.a.dto.ParttimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CalendarService {

    @Autowired
    CalendarDao dao;


    public int offApply(List<CalendarDto> dto){
        return dao.offApply(dto);
    }
    
    public List<CalendarDto> offList(String date){
    	return dao.offList(date);
    }
    
    public int offCancel(CalendarDto dto){
    	return dao.offCancel(dto);
    }
    
    public List<CalendarDto> dutyList(CalendarDto dto){
    	return dao.dutyList(dto);
    }
    public int memoInsert(CalendarDto dto){
    	return dao.memoInsert(dto);
    }
    public int offCount(String date, String id){
    	return dao.offCount(date, id);
    }
}
