package mul.camp.a.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.ShopDao;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.ShopDto;

@Service
@Transactional
public class ShopService {
	
	@Autowired
	ShopDao dao;
	
	public int shopItemAdd(ShopDto dto) {
		return dao.shopItemAdd(dto);
	}
	
	public List<ShopDto> getShopItemAll(){
		return dao.getShopItemAll();
	}
	
	public ShopDto getShopItemInfo(ShopDto dto) {
		return dao.getShopItemInfo(dto);
	}
	
	public int buyShopItemPoint(HashMap<String, Object> map) {
		return dao.buyShopItemPoint(map);
	}
	
	public int orderShopItem(HashMap<String, Object> map) {
		return dao.orderShopItem(map);
	}
	
	public int buyShopItemCnt(HashMap<String, Object> map){
		return dao.buyShopItemCnt(map);
	}

}
