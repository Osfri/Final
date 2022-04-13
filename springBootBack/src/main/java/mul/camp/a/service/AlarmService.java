package mul.camp.a.service;


import mul.camp.a.dao.AlarmDao;
import mul.camp.a.dto.CalendarDto;
import mul.camp.a.dto.ParttimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlarmService {

    @Autowired
    AlarmDao dao;


    public List<ParttimeDto> alarmList(String code){
        return dao.alarmList(code);
    }
    public List<CalendarDto> calList(String id){
        return dao.calList(id);
    }
}
