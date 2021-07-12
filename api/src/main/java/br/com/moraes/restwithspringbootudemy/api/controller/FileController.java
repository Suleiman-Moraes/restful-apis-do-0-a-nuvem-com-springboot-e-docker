package br.com.moraes.restwithspringbootudemy.api.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.moraes.restwithspringbootudemy.api.data.vo.UploadFileResponseVo;
import br.com.moraes.restwithspringbootudemy.api.service.FileStorageService;

@RestController
@RequestMapping("/api/file")
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping
	public ResponseEntity<UploadFileResponseVo> upload(@RequestParam MultipartFile file) {
		final UploadFileResponseVo vo = tratarFile(file);
		return ResponseEntity.ok(vo);
	}

	@PostMapping(value = "/multi")
	public ResponseEntity<List<UploadFileResponseVo>> upload(@RequestParam MultipartFile[] files) {
		return ResponseEntity
				.ok(Arrays.asList(files).stream().map(file -> tratarFile(file)).collect(Collectors.toList()));
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> download(HttpServletRequest request, @RequestParam String fileName)
			throws IOException {
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		contentType = contentType == null ? "application/octet-stream" : contentType;
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						String.format("attachment; filename=\"%s\"", resource.getFilename()))
				.body(resource);
	}

	private UploadFileResponseVo tratarFile(MultipartFile file) {
		final String fileName = fileStorageService.storeFile(file);
		final String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file?fileName=")
				.path(fileName).toUriString();
		final UploadFileResponseVo vo = new UploadFileResponseVo(fileName, fileDownloadUri, file.getContentType(),
				file.getSize());
		return vo;
	}
}
