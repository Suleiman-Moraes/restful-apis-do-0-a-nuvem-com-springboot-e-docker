package br.com.moraes.restwithspringbootudemy.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.moraes.restwithspringbootudemy.api.data.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findTopByUsername(String username);
}
