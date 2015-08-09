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


//від себе: чисто з пдф працювати було б легше
//не потрібно паритись з маймом, він завжди однаковий
//@PathVariable вважає що після крапки це не те що потрібно повернути,
//але це не проблема тому що дальше .pdf
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
	//бере з БД
	@RequestMapping(value = "/showDownloadPage")
	public String showDownloadPage(Model model) {
		List<SaveUrl> list = saveUrlService.getAll();
		model.addAttribute("allFiles", list);
		return "save-url-download";
	}
	//сторінка завантаження файлів
	@RequestMapping(value = "/download/{id}")
	public void doDownload(@PathVariable(value = "id") String id, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
		//пошук в БД по id назви файлу, id приходить як параметер в метод через анотацію @PathVariable
		//з jsp яка формується з ссилками де перша частина ссилки завжди download а друга id
		String fileName = saveUrlService.findById(id).getSavedUrlPath();
		//створюєм повний шлях до файлу з компонентів:
		//шлях до серверу + назва папки + назва файлу
		File downloadFile = new File(System.getProperty("catalina.home")+File.separator+"tmpFiles"+File.separator+fileName);
		//створюєм вхідний потік данних з файлу (перетворюєм файл на вхідний потік)
		FileInputStream inputStream = new FileInputStream(downloadFile);
		//створюєм сервлет контекст для отримання МІМ типу файлу
		ServletContext context = request.getServletContext();
		//отримуємо тип файлу, схоже з розширенням але дещо по іншому виглядає :)
		String mimeType = context.getMimeType(System.getProperty("catalina.home")+File.separator+"tmpFiles"+File.separator+fileName);
		//якщо не має типу присвоюєм двійковий тип
		if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
		//встановлюємо контент для відповіді
		//тип файлу
		response.setContentType(mimeType);
		//повний шлях до файлу
        response.setContentLength((int) downloadFile.length());
        //встановлюємо заголовки відповіді
        //назва
        String headerKey = "Content-Disposition";
        //значення
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
        //отримуемо вихідний потік з відповіді
        OutputStream outStream = response.getOutputStream();
        //створюємо буфер у вигляді 4м-го байтового масиву
        byte[] buffer = new byte[4096];
        //змінна для роботи циклу
        int bytesRead = -1;
 
        //записуємо з буфера юзеру на компютер
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        //закриваємо всі стріми
        inputStream.close();
        outStream.close();
	}
	//upload файла
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFileHandler(@RequestParam("file") MultipartFile file) {
		//перевірка чи файл не пустий
		if (!file.isEmpty()) {
			try {
				//формуєм масив батів з файла
				byte[] bytes = file.getBytes();
				//отримуєм шлях до розташування сервера(томкат в данному прикладі)
				String rootPath = System.getProperty("catalina.home");
				//створюєм обєкт файл з шляхом до папки в якій будуть зберігатись файли
				File dir = new File(rootPath + File.separator + "tmpFiles");
				//перевірка чи є така папка, якщо ні створюєм
				if (!dir.exists()) {
					dir.mkdirs();
				}
				//створюєм шлях до файлу
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + file.getOriginalFilename());
				//зберігаєм файл за допомогою буферизованого вихідного потоку
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				//зберігаєм в БД назву файла з розширенням
				saveUrlService.create(file.getOriginalFilename());
				//передаєм повідомрення для методу showChoicePage()
				massage = "You successfully upload file";
			} catch (Exception e) {
				massage = "You failed to upload file" + e.getMessage();
			}
		} else {
			massage = "You failed to upload file because the file is empty";
		}
		//залишаємся на сторінці вибору файлу
		return "redirect:/showChoisePage";
	}
}
