package br.com.moraes.restwithspringbootudemy.api.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.moraes.restwithspringbootudemy.api.data.model.Book;
import br.com.moraes.restwithspringbootudemy.api.data.vo.BookVo;
import br.com.moraes.restwithspringbootudemy.api.repository.BookRepository;
import br.com.moraes.restwithspringbootudemy.api.service.BookService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookServiceImpl implements BookService{

	@Getter
	@Autowired
	private BookRepository repository;
	
	@Override
	public Class<Book> getClassModel() {
		return Book.class;
	}

	@Override
	public Class<BookVo> getClassVo() {
		return BookVo.class;
	}
	
	@Override
	public Logger getLogger() {
		return log;
	}
}
