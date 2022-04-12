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
import mul.camp.a.dto.FoodParam;
import mul.camp.a.dto.MemberDto;
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
	
	// (3) 로그인한 유저의 병원 식단 가져오기 (Web)
	@RequestMapping(value = "/getSameCodeFoodList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<FoodDto> getSameCodeFoodList(FoodParam param) {
		System.out.println("FoodController getSameCodeFoodList() : " + new Date());
		// 페이지 설정
		int sn = param.getPageNumber();	// 0 1 2 3 ~
		int start = sn * 3 +1;			// 1	11	21
		int end = (sn + 1) * 3;		// 10	20	30
		
		param.setStart(start);
		param.setEnd(end);
		return service.getSameCodeFoodList(param);
	}
	
	// (4) 로그인한 유저의 병원 식단 게시글 수 (Web)
	@RequestMapping(value = "/getSameCodeFoodListCnt", method = {RequestMethod.GET, RequestMethod.POST})
	public int getSameCodeFoodListCnt(FoodParam param) {
		System.out.println("FoodController getSameCodeFoodListCnt() : " + new Date());
		return service.getSameCodeFoodListCnt(param);
	}
	
	// (5) 로그인한 유저의 병원 식단 추가하기 (Web)
	@RequestMapping(value = "/foodItemAdd", method = {RequestMethod.GET, RequestMethod.POST})
	public int foodItemAdd(FoodDto dto) {
		System.out.println("FoodController foodItemAdd() : " + new Date());
		return service.foodItemAdd(dto);
	}
	
	// (6) 병원식단 삭제하기 (Web)
	@RequestMapping(value = "/foodItemModify", method = {RequestMethod.GET, RequestMethod.POST})
	public int foodItemModify(String seq) {
		System.out.println("FoodController foodItemModify() : " + new Date());
		return service.foodItemModify(seq);
	}
}
