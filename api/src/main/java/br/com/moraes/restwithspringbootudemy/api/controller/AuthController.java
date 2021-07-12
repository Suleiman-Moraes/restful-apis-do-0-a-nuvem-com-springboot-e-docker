package br.com.moraes.restwithspringbootudemy.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.moraes.restwithspringbootudemy.api.converter.YamlJackson2HttpMessageConverter;
import br.com.moraes.restwithspringbootudemy.api.data.vo.AccountCredentialsVo;
import br.com.moraes.restwithspringbootudemy.api.service.UserService;
import br.com.moraes.restwithspringbootudemy.config.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider; 

	@Autowired
	private UserService service;

	@PostMapping(value = "/signin", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<Map<String, Object>> authenticate(@RequestBody AccountCredentialsVo vo) throws Exception {
		return ResponseEntity.ok(service.authenticate(vo, authenticationManager, jwtTokenProvider));
	}
}
