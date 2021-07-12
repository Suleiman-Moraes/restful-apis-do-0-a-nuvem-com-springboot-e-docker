package br.com.moraes.restwithspringbootudemy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.moraes.restwithspringbootudemy.api.data.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{

}
