package br.com.moraes.restwithspringbootudemy.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.moraes.restwithspringbootudemy.api.data.model.User;
import br.com.moraes.restwithspringbootudemy.api.data.vo.AccountCredentialsVo;
import br.com.moraes.restwithspringbootudemy.api.data.vo.UserVo;
import br.com.moraes.restwithspringbootudemy.api.repository.UserRepository;
import br.com.moraes.restwithspringbootudemy.api.service.UserService;
import br.com.moraes.restwithspringbootudemy.config.security.jwt.JwtTokenProvider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Getter
	@Autowired
	private UserRepository repository;

	@Override
	public Class<User> getClassModel() {
		return User.class;
	}

	@Override
	public Class<UserVo> getClassVo() {
		return UserVo.class;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findTopByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("Username \"%s\" não encontrado.", username)));
	}

	@Override
	public Map<String, Object> authenticate(AccountCredentialsVo accountCredentialsVo, AuthenticationManager authenticationManager,
			JwtTokenProvider jwtTokenProvider) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					accountCredentialsVo.getUsername(), accountCredentialsVo.getPassword()));
			final User user = repository.findTopByUsername(accountCredentialsVo.getUsername())
					.orElseThrow(() -> new UsernameNotFoundException(
							String.format("Usuário \"%s\" não encontrado.", accountCredentialsVo.getUsername())));
			final String token = jwtTokenProvider.createToke(accountCredentialsVo.getUsername(), user.getRoles());
			Map<String, Object> mapa = new HashMap<>();
			mapa.put("username", accountCredentialsVo.getUsername());
			mapa.put("token", token);
			return mapa;
		} catch (AuthenticationException e) {
			log.warn("authenticate {}", e.getMessage());
			throw new BadCredentialsException("Usuário e/ou senha inválidos!", e);
		}
	}
}
