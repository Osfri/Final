package mul.camp.a.dao;

import mul.camp.a.dto.BoardDto;
import mul.camp.a.dto.BoardTypeDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BbsDao {

    int bbsAdd(BoardTypeDto dto);

    BoardTypeDto bbsRandomCheck(int type);

    public List<BoardTypeDto> getBoardTypeList(String code);

    int bbswrite(BoardDto dto);

    ArrayList<BoardDto> getBbsList(Map<String,Object> map);

    ArrayList<BoardDto> getCommentList(int gr);

    int deleteBbs(int gr);

    int updateBbs(BoardDto dto);

    int commentwrite(BoardDto dto);

    int updatecomment(BoardDto dto);

    int deleteBoardTypeDto(BoardTypeDto dto);
}
