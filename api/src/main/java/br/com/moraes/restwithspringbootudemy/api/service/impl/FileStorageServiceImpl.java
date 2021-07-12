package br.com.moraes.restwithspringbootudemy.api.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.moraes.restwithspringbootudemy.api.exception.FileStorageException;
import br.com.moraes.restwithspringbootudemy.api.exception.MyFileNotFoundException;
import br.com.moraes.restwithspringbootudemy.api.service.FileStorageService;
import br.com.moraes.restwithspringbootudemy.config.ApiProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService{

	private final Path fileStoragePath;

	@Autowired
	public FileStorageServiceImpl(ApiProperty apiProperty) {
		this.fileStoragePath = Paths.get(apiProperty.getFile().getUploadDir()).toAbsolutePath().normalize();
		createDirectory();
	}

	@Override
	public String storeFile(MultipartFile multipartFile) {
		try {
			final String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			if (fileName.contains("..")) {
				throw new FileStorageException("Arquico contém nome inválido " + fileName);
			}
			Path targetLocation = this.fileStoragePath.resolve(fileName);
			Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (FileStorageException e) {
			log.warn("storeFile {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			log.warn("storeFile {}", e.getMessage());
			throw new FileStorageException("Não foi possível salvar o arquivo", e);
		}
	}
	
	@Override
	public Resource loadFileAsResource(String fileName) {
		try {
			final Path filePath = this.fileStoragePath.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			}
			throw new MyFileNotFoundException("");
		} catch (Exception e) {
			log.warn("loadFileAsResource {}", e.getMessage());
			throw new MyFileNotFoundException("Não foi possível carregar o arquivo", e);
		}
	}

	private void createDirectory() {
		try {
			Files.createDirectories(this.fileStoragePath);
		} catch (Exception e) {
			log.warn("createDirectory {}", e.getMessage());
			throw new FileStorageException("Não foi possível criar o diretório.", e);
		}
	}
}
