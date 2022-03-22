package mul.camp.a.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import mul.camp.a.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.auth.FirebaseAuthException;

import mul.camp.a.dto.CalendarDto;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.service.MemberService;
import mul.camp.a.controller.FirebaseService;
import mul.camp.a.util.MediatypeUtils;
import mul.camp.a.util.readExcel;

@RestController
public class MemberController {
	@Autowired
    private ServletContext servletContext;

	@Autowired
	MemberService service;
	
	
	//@RequestBody
	@RequestMapping(value ="/login",method= RequestMethod.POST)
	public MemberDto login(@RequestBody MemberDto dto) {
		System.out.println("DTO"+dto.toString());
		MemberDto mem = service.login(dto);
		System.out.println("MEM"+mem.toString());
		return mem;

	}
	@RequestMapping(value = "/register",method= RequestMethod.POST)
	public String register(@RequestBody MemberDto dto){
		System.out.println("DTO"+dto.toString());
		int result = service.register(dto);
		System.out.println("result"+result);
		return "NO";
	}
	@RequestMapping(value = "/emailCheck",method = RequestMethod.POST)
	public String emailCheck(@RequestBody MemberDto dto){
		String mem = service.emailCheck(dto);
		System.out.println(mem);
		return mem;
	}
	@RequestMapping(value = "/idCheck",method = RequestMethod.POST)
	public String idCheck(@RequestBody MemberDto dto){
		String mem = service.idCheck(dto);
		System.out.println(mem);
		return mem;
	}
	
	@RequestMapping(value ="/webLogin",method= RequestMethod.POST)
	public MemberDto webLogin(MemberDto dto) {
		MemberDto mem = service.login(dto);
		System.out.println("MEM"+mem.toString());
		
		return mem;

	}
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    public String fileUpload(HttpServletRequest req, @RequestParam("uploadFile") MultipartFile uploadfile, String date, String code){
		System.out.println("fileUpload()");
		System.out.println(date.split("-"));

		String UPLOADPATH = req.getServletContext().getRealPath("/upload");
		
        try {
        	String path = UPLOADPATH + File.separator + code;
        	File FolderChk = new File(path);
        	//해당 코드의 폴더 유무 확인후, 폴더에 저장
        	if(!FolderChk.exists()) {
        		try {
        			FolderChk.mkdir();
        		}catch(Exception e){
        			System.out.println(e);
        		}
        	}
    		String fileName = code+"_"+date+".xls";
            String filePath = path + File.separator + fileName;
            File fileChk = new File(filePath);
            boolean fileTf = false;
            //해당 폴더에 파일 유무 확인
            if(fileChk.exists()) {
            	fileTf = true;
            	System.out.println("이미 파일 있음");
            }
            System.out.println("filePath:" + filePath);
            
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(uploadfile.getBytes());
            
            stream.close();
            try {
				List<CalendarDto> list = readExcel.readExcelFile(fileName, req, date, code);
				System.out.println(list.toString());
				
				//CalendarDto test = new CalendarDto("a", "a", "2022-03-02", "n", "");
				if(!fileTf) {
					service.calendarInsert(list);
				}else {
					System.out.println("file ok update");
					service.calendarUpdate(list);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        } catch (Exception e) {     
        	e.printStackTrace();
            return "file upload fail";
        }

        return "file upload success";
    }
	
	@RequestMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(String fileName, HttpServletRequest req, HttpServletResponse response) throws IOException {
		
		System.out.println("downloadFile()");
		
		//TODO 해당 병원 코드인 사람만 불러오기
		ArrayList<MemberDto> member = service.getMember();
		System.out.println(member.get(3).getId());
		
		// server
		String UPLOADPATH = req.getServletContext().getRealPath("/download");
		
        MediaType mediaType = MediatypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);
        
        FileInputStream fis = new FileInputStream(UPLOADPATH + File.separator + fileName);
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);

		//엑셀 index는 0부터 시작
		int rowindex= member.size();
			
		for(int i=5; i < rowindex + 5; i++) {
			sheet.getRow(i).createCell(0).setCellValue(member.get(i-5).getId());
			sheet.getRow(i).createCell(1).setCellValue(member.get(i-5).getName());
		}
		
		//FileOutputStream fos = new FileOutputStream(UPLOADPATH + File.separator + fileName);
		String fn = "sample.xls";
        response.setContentType("ms-vnd/excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fn);

        wb.write(response.getOutputStream());
        wb.close();
        //fos.close();
        
        File file = new File(UPLOADPATH + File.separator + fileName);
		
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
 
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) 
                .body(resource);
    }
	
	@PostMapping("/files")
	public String uploadFile(@RequestParam("uploadFile") MultipartFile file, String nameFile)
	    throws IOException, FirebaseAuthException {
		System.out.println("file====================="+file);
		System.out.println("name====================="+nameFile);
	    if (file.isEmpty()) {
	        return "is empty";
	    }
	    return FirebaseService.uploadFiles(file, nameFile);
	}
		 
}
