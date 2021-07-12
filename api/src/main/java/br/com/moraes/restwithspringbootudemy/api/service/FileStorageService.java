package br.com.moraes.restwithspringbootudemy.api.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	String storeFile(MultipartFile multipartFile);

	Resource loadFileAsResource(String fileName);
}
