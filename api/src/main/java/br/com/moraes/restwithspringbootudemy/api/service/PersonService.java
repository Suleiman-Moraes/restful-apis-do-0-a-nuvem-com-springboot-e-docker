package br.com.moraes.restwithspringbootudemy.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.moraes.restwithspringbootudemy.api.data.model.Person;
import br.com.moraes.restwithspringbootudemy.api.data.vo.PersonVo;
import br.com.moraes.restwithspringbootudemy.api.interfaces.CrudPadraoService;

public interface PersonService extends CrudPadraoService<PersonVo, Person>{

	Optional<PersonVo> updateSetEnabled(Boolean enabled, Long id);

	Page<PersonVo> findAll(Pageable pageable, String firstName);
}
