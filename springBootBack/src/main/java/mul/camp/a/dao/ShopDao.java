package mul.camp.a.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.ShopDto;

@Mapper
@Repository
public interface ShopDao {
	public int shopItemAdd(ShopDto dto);
	public List<ShopDto> getShopItemAll();
	public ShopDto getShopItemInfo(ShopDto dto);
	public int buyShopItemPoint(HashMap<String, Object> map);
	public int orderShopItem(HashMap<String, Object> map);
	public int buyShopItemCnt(HashMap<String, Object> map);

}
