package mul.camp.a.controller;

import mul.camp.a.dto.BoardDto;
import mul.camp.a.dto.BoardTypeDto;
import mul.camp.a.service.BbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BbsController {

    @Autowired
    BbsService service;

    //게시판 생성
    @RequestMapping(value = "/bbsAdd",method = RequestMethod.POST)
    public int bbsAdd(@RequestBody BoardTypeDto dto){
        System.out.println("게시판 생성 컨트롤러"+dto.toString());
        return service.bbsAdd(dto);
    }
    //BoardType 생성시 type 중복 확인
    @RequestMapping(value = "/bbsRandomCheck",method = RequestMethod.POST)
    public BoardTypeDto bbsRandomCheck(@RequestBody int type){
        System.out.println("랜덤 체크 컨트롤러"+type);
        return service.bbsRandomCheck(type);
    }
    //게시판 불러오기
    @RequestMapping(value = "/getBoardTypeList",method = RequestMethod.POST)
    public ArrayList<BoardTypeDto> getBoardTypeList(@RequestBody String code){
        System.out.println("게시판 불러오기 컨트롤러"+code);
        return service.getBoardTypeList(code);
    }
    //게시물 작성
    @RequestMapping(value = "/bbswrite",method = RequestMethod.POST)
    public int bbswrite(@RequestBody BoardDto dto){
        System.out.println("게시물 작성 컨트롤러"+dto.toString());
        return service.bbswrite(dto);
    }
    //게시물 불러오기
    @RequestMapping(value = "/getBbsList",method = RequestMethod.POST)
    public ArrayList<BoardDto> getBbsList(@RequestBody String code,int type){
        System.out.println("게시물 작성 컨트롤러"+code+type);
        Map<String,Object> map = new HashMap<String,Object>();
        String typeString = String.valueOf(type);
        map.put("code",code);
        map.put("type",typeString);
        return service.getBbsList(map);
    }
    //댓글 불러오기
    @RequestMapping(value = "/getCommentList",method = RequestMethod.POST)
    public ArrayList<BoardDto> getCommentList(@RequestBody int gr){
        System.out.println("댓글 불러오기 컨트롤러"+gr);
        return service.getCommentList(gr);
    }
    //게시물,댓글 삭제
    @RequestMapping(value = "/deleteBbs",method = RequestMethod.POST)
    public int deleteBbs(@RequestBody int seq){
        System.out.println("게시물,댓글 삭제 컨트롤러"+seq);
        return service.deleteBbs(seq);
    }
    //게시물,댓글 수정
    @RequestMapping(value = "/updateBbs",method = RequestMethod.POST)
    public int updateBbs(@RequestBody BoardDto dto){
        System.out.println("게시물,댓글 수정 컨트롤러"+dto.toString());
        return service.updateBbs(dto);
    }
}
