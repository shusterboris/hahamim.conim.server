package controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import proxies.AppImage;

public class ImageController {
	@GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getImage(@PathVariable(name = "id") String id) {
		String fileName = "";
		return readImage(fileName);
	}
	
	private static byte[] readImage(String fileName) {
		try {
			BufferedImage bImage = ImageIO.read(new File(fileName));
			ByteArrayOutputStream baOut = new ByteArrayOutputStream();
			ImageIO.write(bImage, "png", baOut);
			return baOut.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
}
