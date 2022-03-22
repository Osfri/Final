package mul.camp.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.ShopDto;

@Mapper
@Repository
public interface ShopDao {
	public int shopItemAdd(ShopDto dto);
	public List<ShopDto> getShopItemAll();

}
