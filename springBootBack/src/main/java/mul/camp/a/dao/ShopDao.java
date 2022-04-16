package mul.camp.a.dao;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import mul.camp.a.dto.ShopDto;
import mul.camp.a.dto.ShopOrderParam;
import mul.camp.a.dto.ShopParam;

@Mapper
@Repository
public interface ShopDao {
	public int shopItemAdd(ShopDto dto);
	public List<ShopDto> getShopItemAll(String code);
	public ShopDto getShopItemInfo(ShopDto dto);
	public int buyShopItemPoint(HashMap<String, Object> map);
	public int orderShopItem(HashMap<String, Object> map);
	public int buyShopItemCnt(HashMap<String, Object> map);
	public List<ShopDto> getShopItemList(ShopParam param);
	public int getShopItemListCnt(ShopParam param);
	public int shopItemModify(String seq);
	public List<ShopOrderParam> getOrderList (ShopOrderParam param);
	public int getOrderListCnt(ShopOrderParam param);

}
