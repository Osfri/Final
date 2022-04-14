package mul.camp.a.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import mul.camp.a.dto.ShopDto;
import mul.camp.a.dto.ShopParam;
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
	@RequestMapping(value = "/getShopItemAllApp", method = {RequestMethod.GET, RequestMethod.POST})
	public List<ShopDto> getShopItemAllApp(@RequestBody String code) {
		System.out.println("ShopController getShopItemAllApp() : " + new Date());
		String codeModify = code.replaceAll("\"", "");
		return service.getShopItemAll(codeModify);
	}
	
	// (3) 선택한 상품정보 가져오기
	@RequestMapping(value = "/getShopItemInfoApp", method = {RequestMethod.GET, RequestMethod.POST})
	public ShopDto getShopItemInfoApp(@RequestBody ShopDto dto) {
		System.out.println("ShopController getShopItemInfoApp() : " + new Date());
		return service.getShopItemInfo(dto);
	}
	
	// (4) 상품 구매하기
	@RequestMapping(value = "/buyShopItemPointApp", method = {RequestMethod.GET, RequestMethod.POST})
	public int buyShopItemPointApp(@RequestBody HashMap<String, Object> map) {
		System.out.println("ShopController buyShopItemPointApp() : " + new Date());
		return service.buyShopItemPoint(map);
	}
	
	// (5) 구매한 상품 주문내역에 추가하기
	@RequestMapping(value = "/orderShopItemApp", method = {RequestMethod.GET, RequestMethod.POST})
	public int orderShopItemApp(@RequestBody HashMap<String, Object> map) {
		System.out.println("ShopController orderShopItemApp() : " + new Date());
		return service.orderShopItem(map);
	}
	
	// (6) 구매한 상품 재고변경
	@RequestMapping(value = "/buyShopItemCntApp", method = {RequestMethod.GET, RequestMethod.POST})
	public int buyShopItemCntApp(@RequestBody HashMap<String, Object> map) {
		System.out.println("ShopController buyShopItemCntApp() : " + new Date());
		return service.buyShopItemCnt(map);
	}
	
	// (7) 상품목록 가져오기 (Web)
	@RequestMapping(value = "/getShopItemList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<ShopDto> getShopItemList(ShopParam param) {
		System.out.println("ShopController getShopItemList() : " + new Date());
		// 페이지 설정
		int sn = param.getPageNumber();	// 0 1 2 3 ~
		int start = sn * 3 +1;			// 1	11	21
		int end = (sn + 1) * 3;		// 10	20	30
		
		param.setStart(start);
		param.setEnd(end);
		return service.getShopItemList(param);
	}
	
	// (8) 상품목록 수 가져오기 (Web)
	@RequestMapping(value = "/getShopItemListCnt", method = {RequestMethod.GET, RequestMethod.POST})
	public int getShopItemListCnt(ShopParam param) {
		System.out.println("ShopController getShopItemListCnt() : " + new Date());
		return service.getShopItemListCnt(param);
	}
	
	// (9) 상품 삭제하기 (Web)
	@RequestMapping(value = "/shopItemModify", method = {RequestMethod.GET, RequestMethod.POST})
	public int shopItemModify(String seq) {
		System.out.println("ShopController shopItemModify() : " + new Date());
		return service.shopItemModify(seq);
	}
}
