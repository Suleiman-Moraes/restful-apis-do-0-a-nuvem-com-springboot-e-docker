package br.com.moraes.restwithspringbootudemy.api.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICrudPadraoService<VO extends IVo, MODEL> {
	
	Optional<VO> findById(Long id);

	List<VO> findAll();

	VO create(VO person);

	VO update(VO person, Long id) throws Exception;
	
	MODEL save(MODEL person);

	void delete(Long id);

	Page<VO> findAll(Pageable pageable);
}
