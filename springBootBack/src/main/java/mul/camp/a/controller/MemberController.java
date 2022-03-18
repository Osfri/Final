package mul.camp.a.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mul.camp.a.dto.CalendarDto;
import mul.camp.a.service.MemberService;
import mul.camp.a.util.MediatypeUtils;
import mul.camp.a.util.readExcel;

@RestController
public class MemberController {
	@Autowired
    private ServletContext servletContext;

	@Autowired
	MemberService ser;
	
	@RequestMapping("/api")
	public String test() {
		
		String a = "";
		try {
			a = ser.kim();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return a;
		
	}
	
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("uploadFile") MultipartFile uploadfile, HttpServletRequest req, String date){
		System.out.println("fileUpload()");
		System.out.println(date.split("-"));
		// server
		String UPLOADPATH = req.getServletContext().getRealPath("/upload");
		
        try {
            String fileName = uploadfile.getOriginalFilename();
            String filePath = UPLOADPATH + File.separator + fileName;
            System.out.println("filePath:" + filePath);
            
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(uploadfile.getBytes());
            
            
            
			/*
			 * Map<String, Object> map = new HashMap<String, Object>();
			 * 
			 * try { List<CalendarDto> fileList = ser.readExcelData(req, fileName, date);
			 * 
			 * map.put("list", fileList);
			 * 
			 * dao.addExcel(map); } catch (Exception e) { e.printStackTrace(); }
			 */

            stream.close();
            try {
				ArrayList<CalendarDto> list = readExcel.readExcelFile(fileName, req, date);
				System.out.println(list.toString());
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
    public ResponseEntity<InputStreamResource> downloadFile(String fileName, HttpServletRequest req) throws IOException {
		
		System.out.println("downloadFile()");
		
		// server
		String UPLOADPATH = req.getServletContext().getRealPath("/download");
		
	//	String UPLOADPATH = "D:\\tmp";
 
        MediaType mediaType = MediatypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);
 
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
	
}
