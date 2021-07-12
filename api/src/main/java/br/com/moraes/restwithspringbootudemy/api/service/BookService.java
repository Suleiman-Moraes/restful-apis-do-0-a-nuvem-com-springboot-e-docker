package br.com.moraes.restwithspringbootudemy.api.service;

import br.com.moraes.restwithspringbootudemy.api.data.model.Book;
import br.com.moraes.restwithspringbootudemy.api.data.vo.BookVo;
import br.com.moraes.restwithspringbootudemy.api.interfaces.CrudPadraoService;

public interface BookService extends CrudPadraoService<BookVo, Book>{

}
