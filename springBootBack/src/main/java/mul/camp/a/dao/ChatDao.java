package mul.camp.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.ChatDto;

@Mapper
@Repository
public interface ChatDao {
	public List<ChatDto> getAllUserInfo();
	public List<ChatDto> getSameCodeUsers(ChatDto dto);
	public ChatDto getLoginUserInfo (String id);
	public ChatDto.HospitalDto getLoginUserHospitalInfo (ChatDto dto);
}
