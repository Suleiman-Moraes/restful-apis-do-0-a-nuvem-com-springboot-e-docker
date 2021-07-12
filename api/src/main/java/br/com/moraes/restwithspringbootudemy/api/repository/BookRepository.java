package br.com.moraes.restwithspringbootudemy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.moraes.restwithspringbootudemy.api.data.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
