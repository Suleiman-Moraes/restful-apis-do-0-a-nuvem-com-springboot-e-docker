package br.com.moraes.restwithspringbootudemy.api.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.moraes.restwithspringbootudemy.api.data.model.User;
import br.com.moraes.restwithspringbootudemy.api.data.vo.AccountCredentialsVo;
import br.com.moraes.restwithspringbootudemy.api.data.vo.UserVo;
import br.com.moraes.restwithspringbootudemy.api.interfaces.CrudPadraoService;
import br.com.moraes.restwithspringbootudemy.config.security.jwt.JwtTokenProvider;

public interface UserService extends CrudPadraoService<UserVo, User>, UserDetailsService {

	Map<String, Object> authenticate(AccountCredentialsVo accountCredentialsVo,
			AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider);
}
