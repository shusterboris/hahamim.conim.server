package controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


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

	@PostMapping(value = "/image/product/put")
	public ResponseEntity<String> saveImage(@RequestBody String encodedMap){
		String result = writeImage(encodedMap);
		if (!"".equals(result)) {
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}else
			return new ResponseEntity<String>("File did not save", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public String writeImage(String fileData) {
		String fileName = "";
		
		String encodedImage = fileData.substring(4);
		String encodedStringImage = URLDecoder.decode(encodedImage);
		byte[] data = Base64.getMimeDecoder().decode(encodedStringImage);
		try {
			fileName = UUID.randomUUID().toString()+".png";
			URL fileURL = ImageController.class.getClassLoader().getResource("imgStore");
			Path filePath = Paths.get(fileURL.toURI());
			FileOutputStream fos = new FileOutputStream(filePath.toString().concat(File.separator).concat(fileName));
			fos.write(data); 
			fos.close();
			return fileName;
		} catch (Exception e) {
			System.out.println("Не могу записать файл "+fileName);
		}
		return "";
	}
	
	private byte[] readImage(String fileName) throws Exception {
		URL fileURL = ImageController.class.getClassLoader().getResource(fileName);
		BufferedImage bImage = ImageIO.read(fileURL); 
		ByteArrayOutputStream baOut = new ByteArrayOutputStream();
		String extention = fileName.substring(fileName.lastIndexOf("."));
		if (!(".png".equalsIgnoreCase(extention) || ".jpg".equalsIgnoreCase(extention) || ".jpeg".equalsIgnoreCase(extention)))
			throw new Exception("ErrMsg.invalidFileExtention");
		else
			extention = extention.substring(1);
		ImageIO.write(bImage, extention, baOut);
		return baOut.toByteArray();
	}	
}
