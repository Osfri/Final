package mul.camp.a.service;


import mul.camp.a.dao.AlarmDao;
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


    public List<ParttimeDto> alarmList(String id){
        return dao.alarmList(id);
    }
}
