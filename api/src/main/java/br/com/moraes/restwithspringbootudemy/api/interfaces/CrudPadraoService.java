package br.com.moraes.restwithspringbootudemy.api.interfaces;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.moraes.restwithspringbootudemy.api.converter.DozerConverter;
import br.com.moraes.restwithspringbootudemy.api.exception.IncorrectDataEnteredException;
import br.com.moraes.restwithspringbootudemy.api.exception.ResourceNotFoundException;

public interface CrudPadraoService<VO extends IVo, MODEL> extends ICrudPadraoService<VO, MODEL> {

	JpaRepository<MODEL, Long> getRepository();

	Class<MODEL> getClassModel();

	Class<VO> getClassVo();

	Logger getLogger();

	@Override
	@Transactional(readOnly = true)
	default Optional<VO> findById(Long id) {
		return Optional.of(DozerConverter.parseObject(getRepository().findById(Long.valueOf(id)).get(), getClassVo()));
	}

	@Override
	@Transactional(readOnly = true)
	default List<VO> findAll() {
		return DozerConverter.parseListObjects(getRepository().findAll(), getClassVo());
	}

	@Override
	@Transactional(readOnly = true)
	default Page<VO> findAll(Pageable pageable) {
		final Page<MODEL> page = getRepository().findAll(pageable);
		return page.map(p -> DozerConverter.parseObject(p, getClassVo()));
	}

	@Override
	@Transactional
	default VO create(VO person) {
		return DozerConverter.parseObject(save(DozerConverter.parseObject(person, getClassModel())), getClassVo());
	}

	@Override
	@Transactional
	default VO update(VO person, Long id) throws Exception {
		if (!getRepository().existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		person.setKey(id);
		return DozerConverter.parseObject(save(DozerConverter.parseObject(person, getClassModel())), getClassVo());
	}

	@Override
	@Transactional
	default void delete(Long id) {
		getRepository().deleteById(id);
	}

	@Override
	@Transactional
	default MODEL save(MODEL model) {
		try {
			preSave(model);
			model = getRepository().save(model);
			posSave(model);
			return model;
		} catch (DataIntegrityViolationException e) {
			getLogger().warn("save " + ExceptionUtils.getRootCauseMessage(e));
			throw new IncorrectDataEnteredException("Dados inseridos de forma incorreta.", e);
		} catch (Exception e) {
			getLogger().warn("save " + ExceptionUtils.getRootCauseMessage(e));
			throw e;
		}
	}

	default void preSave(MODEL objeto) {
	}

	default void posSave(MODEL objeto) {
	}
}
