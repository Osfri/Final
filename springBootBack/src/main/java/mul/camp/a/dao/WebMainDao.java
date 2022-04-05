package mul.camp.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.ChatDto.HospitalDto;
import mul.camp.a.dto.MemberDto;

@Mapper
@Repository
public interface WebMainDao {

	//직원 리스트 불러오기
	public List<MemberDto> getMemberList(MemberDto login);
	//병동 추가하기
	public int addHospital(String curCode, String code, String name);
	//병동 리스트 불러오기
	public List<HospitalDto> getHospitalList(String code);
	//병동 이미 존재하는지 확인하기
	public int hospitalChk(String code);
}
