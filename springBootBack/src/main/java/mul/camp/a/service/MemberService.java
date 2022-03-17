package mul.camp.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.MemberDao;
import mul.camp.a.dto.MemberDto;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	MemberDao dao;
	

	public MemberDto login(MemberDto dto){
		MemberDto mem = dao.login(dto);
		if (mem != null){
			return mem;
		}else {
			return null;
		}
	}

}







