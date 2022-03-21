package mul.camp.a.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mul.camp.a.dto.ChatDto;
import mul.camp.a.service.ChatService;

@RestController
public class ChatController {

	@Autowired
	ChatService service;
	
	// (0) 테스트용 (모든 유저정보 불러오기)
	@RequestMapping(value = "/getAllUserInfoApp", method = {RequestMethod.GET, RequestMethod.POST})
	public List<ChatDto> getAllUserInfoApp(){
		System.out.println("ChatController getAllUserInfoApp() : " + new Date());
		List<ChatDto> list = service.getAllUserInfo();
		return list;
	}	
	
	// (1) 로그인한 유저와 같은 회사코드의 회원 불러오기
	@RequestMapping(value = "/getSameCodeUsersApp", method = {RequestMethod.GET, RequestMethod.POST})
	public Map<String, ChatDto> getSameCodeUsersApp(@RequestBody ChatDto dto){
		System.out.println("ChatController getSameCodeUsersApp() : " + new Date());
		List<ChatDto> list = service.getSameCodeUsers(dto);
		Map<String, ChatDto> map = new HashMap<String, ChatDto>();
		for(ChatDto user : list) {
			map.put(user.getId(), user);
		}		
		return map;
	}
	
	// (2) 로그인한 유저의 정보 얻기
	@RequestMapping(value = "/getLoginUserInfoApp", method = {RequestMethod.GET, RequestMethod.POST})
	public ChatDto getLoginUserInfoApp(@RequestBody String id){
		System.out.println("ChatController getLoginUserInfoApp() : " + new Date());
		String idModify = id.replaceAll("\"", "");
		ChatDto dto = service.getLoginUserInfo(idModify);
		return dto;
	}
	
	// (3) 로그인한 유저의 회사 정보 얻기
	@RequestMapping(value = "/getLoginUserHospitalInfoApp", method = {RequestMethod.GET, RequestMethod.POST})
	public ChatDto.HospitalDto getLoginUserHospitalInfoApp(@RequestBody ChatDto dto){
		System.out.println("ChatController getLoginUserHospitalInfoApp() : " + new Date());
		ChatDto.HospitalDto hospitalDto = service.getLoginUserHospitalInfo(dto);
		return hospitalDto;
	}	
}
