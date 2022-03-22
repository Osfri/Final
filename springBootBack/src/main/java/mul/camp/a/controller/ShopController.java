package mul.camp.a.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mul.camp.a.dto.ShopDto;
import mul.camp.a.service.ShopService;

// CORS 설정
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ShopController {
	@Autowired
	ShopService service;
	
	// (1) 상품 추가하기
	@RequestMapping(value = "/shopItemAdd", method = {RequestMethod.GET, RequestMethod.POST})
	public boolean shopItemAdd(ShopDto dto) {
		System.out.println("ShopController shopItemAdd() : " + new Date());
		return (service.shopItemAdd(dto) > 0) ? true : false;
	}
	
	// (2) 모든 상품가져오기
	
	@RequestMapping(value = "/getShopItemAll", method = {RequestMethod.GET, RequestMethod.POST})
	public List<ShopDto> getShopItemAll() {
		System.out.println("ShopController getShopItemAll() : " + new Date());
		return service.getShopItemAll();
	}

}
