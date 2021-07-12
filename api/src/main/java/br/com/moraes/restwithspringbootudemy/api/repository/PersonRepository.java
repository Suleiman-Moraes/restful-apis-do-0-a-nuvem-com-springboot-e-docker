package br.com.moraes.restwithspringbootudemy.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.moraes.restwithspringbootudemy.api.data.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

	@Modifying
	@Query(value = "UPDATE Person p SET p.enabled = ?1 WHERE p.id = ?2")
	void updateSetEnabled(Boolean enabled, Long id);

	Page<Person> findByFirstNameIgnoreCaseContaining(String firstName, Pageable pageable);
}
