package com.forAlim.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.forAlim.service.SavedFileService;

@Controller
public class SaveUrlController {
	// тут подивись, мені здається що фігня получилась
	// це поле для того що б перенести повідомлення з одного метода контролера
	// на інший
	private String massage = null;

	@Autowired
	private SavedFileService savedFileService;

	// переадресовує на сторінку вибору файлу
	// модель вперше нічого не передає потім передає повідомлення про результат
	// upload файлу
	// можна було зробити через @ResponseBody в методі uploadFileHandler але
	// мені не сподобалось
	@RequestMapping(value = "/showChoisePage")
	public String showChoicePage(Model model) {
		model.addAttribute("massage", massage);
		massage = null;
		return "save-url-choice";
	}

	// переадресовує на сторінку завантаження
	// показує всі файли які є на сервері
	// бере данні з БД
	@RequestMapping(value = "/showDownloadPage")
	public String showDownloadPage(Model model) {
		model.addAttribute("allFiles", savedFileService.getAll());
		return "save-url-download";
	}

	// видаляє файл
	@RequestMapping(value = "/delete/{id}")
	public String deleteFile(@PathVariable(value = "id") String id) {
		savedFileService.delete(id);
		return "redirect:/showDownloadPage";
	}

	// відображає пдф у вікні браузера
	// використовує можливості браузера
	@RequestMapping(value = "/view/{id}")
	public void view(@PathVariable(value = "id") String id,
			HttpServletResponse response) throws IOException {
		// id приходить як параметер в метод через анотацію @PathVariable
		// з jsp яка формується з ссилками де перша частина ссилки завжди
		// download а друга id
		// встановлюємо контент для відповіді
		// тип файлу пдф
		response.setContentType("application/pdf");
		// отримуємо з БД збережений байтовий масив
		byte[] dbValue = savedFileService.findByIdSavedFile(id).getBais();
		// встановлюємо ромір файлу
		response.setContentLength((int) dbValue.length);
		// отримуемо вихідний потік з відповіді сервлета
		try {
			OutputStream outStream = response.getOutputStream();
			// вигружаємо в браузер для відображення на сторінці
			outStream.write(dbValue);
			outStream.flush();
			// закриваємо стрім
			outStream.close();
		} catch (IOException e) {
			// не судьба :)
		}
	}

	// метод завантаження файлів
	@RequestMapping(value = "/download/{id}")
	public void doDownload(@PathVariable(value = "id") String id,
			HttpServletResponse response) {
		// отримуємо імя файлу з бази
		String fileName = savedFileService.findById(id).getName();
		// встановлюємо контент для відповіді
		// тип файлу пдф
		response.setContentType("application/pdf");
		// встановлюємо заголовки відповіді
		// назва
		String headerKey = "Content-Disposition";
		// значення
		String headerValue = String.format("attachment; filename=\"%s\"",
				fileName);
		// отримуємо з БД збережений байтовий масив
		byte[] dbValue = savedFileService.findByIdSavedFile(id).getBais();
		// ця стрічка каже браузеру що потрібно зберегти файл
		response.setHeader(headerKey, headerValue);
		// встановлюємо розмір файлу
		response.setContentLength((int) dbValue.length);
		// отримуемо вихідний потік з відповіді
		try {
			OutputStream outStream = response.getOutputStream();
			// записуємо юзеру
			outStream.write(dbValue);
			outStream.flush();
			// закриваємо стрім
			outStream.close();
		} catch (IOException e) {
			// не судьба :)
		}
	}

	// upload файла
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFileHandler(@RequestParam("file") MultipartFile file) {
		// перевірка чи файл не пустий
		if (!file.isEmpty()) {
			try {
				// передаємо на сервіс для подальшого парсінгу і збереження
				savedFileService.create(file);
				massage = "You successfully upload file";
			} catch (IOException e) {
				massage = "You failed to upload file" + e.getMessage();
			}
		} else {
			massage = "You failed to upload file because the file is empty";
		}

		return "redirect:/showChoisePage";
	}
}
