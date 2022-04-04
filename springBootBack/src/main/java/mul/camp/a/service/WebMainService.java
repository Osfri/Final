package mul.camp.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.WebMainDao;
import mul.camp.a.dto.MemberDto;

@Service
@Transactional
public class WebMainService {

	@Autowired
	WebMainDao dao;
	
	public List<MemberDto> getMemberList(MemberDto login){
		return dao.getMemberList(login);
	}
}
