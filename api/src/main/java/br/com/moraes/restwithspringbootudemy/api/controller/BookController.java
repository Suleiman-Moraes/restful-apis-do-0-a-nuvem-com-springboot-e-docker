package br.com.moraes.restwithspringbootudemy.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.moraes.restwithspringbootudemy.api.controller.abstracts.AbstractController;
import br.com.moraes.restwithspringbootudemy.api.converter.YamlJackson2HttpMessageConverter;
import br.com.moraes.restwithspringbootudemy.api.data.vo.BookVo;
import br.com.moraes.restwithspringbootudemy.api.exception.ResourceNotFoundException;
import br.com.moraes.restwithspringbootudemy.api.service.BookService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/book")
public class BookController extends AbstractController {
	
	@Autowired
	private BookService service;

	@Autowired
	private PagedResourcesAssembler<BookVo> assembler;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<?> findAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "DESC") Direction direction) throws Exception {
		final Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "id"));
		final Page<BookVo> pag = service.findAll(pageable);
		pag.stream().forEach(p -> getLinkFindById(p));
		return ResponseEntity.ok(assembler.toResource(pag));
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<BookVo> findById(@PathVariable long id) throws Exception {
		final BookVo personVo = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!"));
		getLinkFindById(personVo);
		return ResponseEntity.ok(personVo);
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<BookVo> create(@RequestBody BookVo personVo) throws Exception {
		personVo = service.create(personVo);
		getLinkFindById(personVo);
		return ResponseEntity.status(HttpStatus.CREATED).body(personVo);
	}

	@PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<BookVo> update(@RequestBody BookVo personVo, @PathVariable long id) throws Exception {
		personVo = service.update(personVo, id);
		getLinkFindById(personVo);
		return ResponseEntity.ok(personVo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) throws Exception {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	private void getLinkFindById(BookVo personVo) {
		try {
			personVo.add(linkTo(methodOn(BookController.class).findById(personVo.getKey())).withSelfRel());
		} catch (Exception e) {
			log.warn("getLinkFindById {}", e.getMessage());
		}
	}
}
