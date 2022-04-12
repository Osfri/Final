package mul.camp.a.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import mul.camp.a.dto.FoodDto;
import mul.camp.a.dto.FoodParam;
import mul.camp.a.dto.MemberDto;

@Mapper
@Repository
public interface FoodDao {
	
	public List<FoodDto> getSameCodeFoodInfo(MemberDto dto);
	public int foodItemAdd(FoodDto dto);
	public List<FoodDto> getSameCodeFoodList(FoodParam param);
	public int getSameCodeFoodListCnt(FoodParam param);
	public int foodItemModify(String seq);

}
