package com.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	public String uploadImage(String url, MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		String imageName = UUID.randomUUID().toString();
		String randomName = imageName.concat(fileName.substring(fileName.lastIndexOf(".")));
		String path = url + File.separator + randomName;

		System.out.println("fileName :" + fileName);
		System.out.println("imageName :" + imageName);
		System.out.println("randomName :" + randomName);
		System.out.println("path :" + path);
		File folderFile = new File(url);

		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}

		Files.copy(file.getInputStream(), Paths.get(path));
		return randomName;
	}
}
