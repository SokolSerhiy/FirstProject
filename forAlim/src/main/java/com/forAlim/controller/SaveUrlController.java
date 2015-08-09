package com.forAlim.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.forAlim.domain.SaveUrl;
import com.forAlim.service.SaveUrlService;

@Controller
public class SaveUrlController {
	private String massage = null;

	@Autowired
	private SaveUrlService saveUrlService;
	
	//переадресовує на сторінку вибору файлу
	//модель вперше нічого не передає потім передає повідомлення про результат upload файлу
	//можна було зробити через @ResponseBody в методі uploadFileHandler але мені не сподобалось
	@RequestMapping(value = "/showChoisePage")
	public String showChoicePage(Model model) {
		model.addAttribute("massage", massage);
		massage = null;
		return "save-url-choice";
	}
	//переадресовує на сторінку завантаження
	//показує всі файли які є на сервері
	@RequestMapping(value = "/showDownloadPage")
	public String showDownloadPage(Model model) {
		List<SaveUrl> list = saveUrlService.getAll();
		model.addAttribute("allFiles", list);
		
		return "save-url-download";
	}
	
	@RequestMapping(value = "/download/{id}")
	public void doDownload(@PathVariable(value = "id") String id, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
		String fileName = saveUrlService.findById(id).getSavedUrlPath();
		File downloadFile = new File(System.getProperty("catalina.home")+File.separator+"tmpFiles"+File.separator+fileName);
		FileInputStream inputStream = new FileInputStream(downloadFile);
		ServletContext context = request.getServletContext(); // подивитись сюди
		String mimeType = context.getMimeType(System.getProperty("catalina.home")+File.separator+"tmpFiles"+File.separator+fileName);
		if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
		// set content attributes for the response
		response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
     // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outStream.close();
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFileHandler(@RequestParam("file") MultipartFile file,
			Model model) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + file.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				saveUrlService.create(file.getOriginalFilename());
				massage = "You successfully upload file";
			} catch (Exception e) {
				massage = "You failed to upload file" + e.getMessage();
			}
		} else {
			massage = "You failed to upload file because the file is empty";
		}
		return "redirect:/showChoisePage";
	}
}
