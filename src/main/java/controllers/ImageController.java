package controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import application.ApplicationSettings;

@RestController
public class ImageController {
	
	@GetMapping(value = "/image/id/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody ResponseEntity<Object> getImageById(@PathVariable(value = "id") Long id) {
		try {
			String fileName = "";
			return ResponseEntity.status(HttpStatus.OK)
	        .body(readImage(fileName));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("file not found");
		}
	}
	

	@GetMapping(value = "image/file/{fileName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<Object> getImage(@PathVariable(value = "fileName") String fileName) {
		try {
			//String fullPath = ApplicationSettings.getProperty("imgStore");
			String fullPath = "imgStore";
			fullPath = ("".equals(fullPath)) ? fileName : fullPath.concat("/").concat(fileName);
			return ResponseEntity.status(HttpStatus.OK)
	        .body(readImage(fullPath));
		}catch (Exception e) {
			if (e.getClass().getSimpleName().endsWith("IOException"))
				return new ResponseEntity<>("ErrMsg.fileNotFound", HttpStatus.NOT_FOUND);
			else 
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);				
		}
	}

	private static byte[] readImage(String fileName) throws Exception {
		URL fileURL = ImageController.class.getClassLoader().getResource(fileName);
		BufferedImage bImage = ImageIO.read(fileURL); 
		ByteArrayOutputStream baOut = new ByteArrayOutputStream();
		String extention = fileName.substring(fileName.lastIndexOf("."));
		if (!(".png".equalsIgnoreCase(extention) || ".jpg".equalsIgnoreCase(extention) || ".jpeg".equalsIgnoreCase(extention)))
			throw new Exception("ErrMsg.invalidFileExtention");
		ImageIO.write(bImage, "jpg", baOut);
		return baOut.toByteArray();
	}
}
