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
	public List<MemberDto> getMemberList(String code, String hospital);
	//병동 추가하기
	public int addHospital(String curCode, String code, String name);
	//병동 리스트 불러오기
	public List<HospitalDto> getHospitalList(String code);
	//병동 이미 존재하는지 확인하기
	public int hospitalChk(String code);
	//병동 삭제하기
	public int delHospital(String code);
	//관리자에서 직원으로 변경하기
	public int toStaff(String id);
	//직원에서 관리자로 변경하기
	public int toManager(String id);
	//해당 병동에 관리자 존재하는지 확인
	public int managerChk(String code);
	//병동에서 직원으로 승인
	public int toYes(String id);
	//병동에서 직원으로 승인 거부
	public int toNo(String id);
	//직원의 병동코드 변경
	public int changeHospital(String id, String code);
	//권한 확인
	public int authChk(String id);
}
