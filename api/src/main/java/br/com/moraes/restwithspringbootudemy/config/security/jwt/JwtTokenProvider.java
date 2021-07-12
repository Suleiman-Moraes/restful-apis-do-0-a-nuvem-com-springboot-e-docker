package br.com.moraes.restwithspringbootudemy.config.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.moraes.restwithspringbootudemy.api.exception.InvalidJwtAuthenticationException;
import br.com.moraes.restwithspringbootudemy.config.ApiProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenProvider {

	@Autowired
	private ApiProperty apiProperty;

	@Autowired
	private UserDetailsService userDetailsService;

	private String secretKey;

	@PostConstruct
	public void init() {
		secretKey = Base64.getEncoder().encodeToString(apiProperty.getSecurity().getSecretKey().getBytes());
	}

	public String createToke(String username, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", roles);
		final Date now = new Date();
		final Date validity = new Date(now.getTime() + apiProperty.getSecurity().getValidityInMilliseconds());
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public Authentication getAuthentication(String token) {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public String resolveToken(HttpServletRequest request) {
		final String bearerToken = request.getHeader("Authorization");
		if(StringUtils.contains(bearerToken, "Bearer")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
	public Boolean validateToken(String token) {
		try {
			return token != null && 
					!Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token)
					.getBody()
					.getExpiration()
					.before(new Date());
		} catch (Exception e) {
			log.warn("validateToken {}", e.getMessage());
			throw new InvalidJwtAuthenticationException("Token experiado ou inválido", e);
		}
	}

	private String getUsername(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
