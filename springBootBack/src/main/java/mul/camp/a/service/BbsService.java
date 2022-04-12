package mul.camp.a.service;

import mul.camp.a.dao.BbsDao;
import mul.camp.a.dto.BoardDto;
import mul.camp.a.dto.BoardTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BbsService {

    @Autowired
    BbsDao dao;

    public int bbsAdd(BoardTypeDto dto){
        return dao.bbsAdd(dto);
    }

    public BoardTypeDto bbsRandomCheck(int type){
        return dao.bbsRandomCheck(type);
    }

    public List<BoardTypeDto> getBoardTypeList(String code){
        return dao.getBoardTypeList(code);
    }

    public int bbswrite(BoardDto dto){
        return dao.bbswrite(dto);
    }

    public ArrayList<BoardDto> getBbsList(Map<String,Object> map){
        return dao.getBbsList(map);
    }

    public ArrayList<BoardDto> getCommentList(int gr){
        return dao.getCommentList(gr);
    }

    public int deleteBbs(int seq){
        return dao.deleteBbs(seq);
    }

    public int updateBbs(BoardDto dto){
        return dao.updateBbs(dto);
    }

    public int commentwrite(BoardDto dto) { return dao.commentwrite(dto); }

    public int updatecomment(BoardDto dto) { return dao.updatecomment(dto); }

    public int deleteBoardTypeDto(BoardTypeDto dto) { return dao.deleteBoardTypeDto(dto); }
}
