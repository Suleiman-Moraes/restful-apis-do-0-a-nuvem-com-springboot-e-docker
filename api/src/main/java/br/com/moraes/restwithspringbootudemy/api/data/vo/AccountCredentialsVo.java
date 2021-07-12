package br.com.moraes.restwithspringbootudemy.api.data.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCredentialsVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
}
