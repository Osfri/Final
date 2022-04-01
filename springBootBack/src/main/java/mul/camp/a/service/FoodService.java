package mul.camp.a.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mul.camp.a.dao.FoodDao;
import mul.camp.a.dto.FoodDto;
import mul.camp.a.dto.MemberDto;

@Service
@Transactional
public class FoodService {

	@Autowired
	FoodDao dao;
	public List<FoodDto> getSameCodeFoodInfo(MemberDto dto){
		return dao.getSameCodeFoodInfo(dto);
	}
	
	public int foodItemAdd(FoodDto dto) {
		return dao.foodItemAdd(dto);
	}
}
