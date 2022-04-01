package mul.camp.a.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mul.camp.a.dto.FoodDto;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.ShopDto;
import mul.camp.a.service.FoodService;

//CORS 설정
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FoodController {

	@Autowired
	FoodService service;
	
	// (1) 로그인한 유저의 병원 식단 가져오기
	@RequestMapping(value = "/getSameCodeFoodInfoApp", method = {RequestMethod.GET, RequestMethod.POST})
	public List<FoodDto> getSameCodeFoodInfoApp(@RequestBody MemberDto dto) {
		System.out.println("FoodController getSameCodeFoodInfoApp() : " + new Date());
		return service.getSameCodeFoodInfo(dto);
	}
	
	// (2) 로그인한 유저의 병원 식단 추가하기
	@RequestMapping(value = "/foodItemAddApp", method = {RequestMethod.GET, RequestMethod.POST})
	public int foodItemAddApp(@RequestBody FoodDto dto) {
		System.out.println("FoodController foodItemAddApp() : " + new Date());
		return service.foodItemAdd(dto);
	}
}
