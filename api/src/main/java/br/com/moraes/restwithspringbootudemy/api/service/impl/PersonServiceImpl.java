package br.com.moraes.restwithspringbootudemy.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.moraes.restwithspringbootudemy.api.converter.DozerConverter;
import br.com.moraes.restwithspringbootudemy.api.data.model.Person;
import br.com.moraes.restwithspringbootudemy.api.data.vo.PersonVo;
import br.com.moraes.restwithspringbootudemy.api.exception.ResourceNotFoundException;
import br.com.moraes.restwithspringbootudemy.api.repository.PersonRepository;
import br.com.moraes.restwithspringbootudemy.api.service.PersonService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

	@Getter
	@Autowired
	private PersonRepository repository;

	@Override
	public Class<Person> getClassModel() {
		return Person.class;
	}

	@Override
	public Class<PersonVo> getClassVo() {
		return PersonVo.class;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	@Transactional
	public Optional<PersonVo> updateSetEnabled(Boolean enabled, Long id) {
		if (!getRepository().existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		repository.updateSetEnabled(enabled == null ? Boolean.FALSE : enabled, id);
		return findById(id);
	}
	
	@Override
	public Page<PersonVo> findAll(Pageable pageable, String firstName) {
		final Page<Person> page = getRepository().findByFirstNameIgnoreCaseContaining(firstName, pageable);
		return page.map(p -> DozerConverter.parseObject(p, getClassVo()));
	}
}
