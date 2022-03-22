package mul.camp.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.ChatDao;
import mul.camp.a.dto.ChatDto;

@Service
@Transactional
public class ChatService {
	
	@Autowired
	ChatDao dao;
	public List<ChatDto> getAllUserInfo(){
		return dao.getAllUserInfo();
	}
	
	public List<ChatDto> getSameCodeUsers(ChatDto dto){
		return dao.getSameCodeUsers(dto);
	}
	
	public ChatDto getLoginUserInfo(String id) {
		return dao.getLoginUserInfo(id);
	}
	
	public ChatDto.HospitalDto getLoginUserHospitalInfo (ChatDto dto){
		return dao.getLoginUserHospitalInfo(dto);
	}

}
