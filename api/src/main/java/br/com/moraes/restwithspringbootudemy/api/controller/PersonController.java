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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.moraes.restwithspringbootudemy.api.controller.abstracts.AbstractController;
import br.com.moraes.restwithspringbootudemy.api.converter.YamlJackson2HttpMessageConverter;
import br.com.moraes.restwithspringbootudemy.api.data.vo.PersonVo;
import br.com.moraes.restwithspringbootudemy.api.exception.ResourceNotFoundException;
import br.com.moraes.restwithspringbootudemy.api.service.PersonService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/person")
public class PersonController extends AbstractController {
	
	@Autowired
	private PersonService service;

	@Autowired
	private PagedResourcesAssembler<PersonVo> assembler;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<?> findAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "DESC") Direction direction,
			@RequestParam(defaultValue = "") String firstName) throws Exception {
		final Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "id"));
		final Page<PersonVo> pag = service.findAll(pageable, firstName);
		pag.stream().forEach(p -> getLinkFindById(p));
		return ResponseEntity.ok(assembler.toResource(pag));
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<PersonVo> findById(@PathVariable long id) throws Exception {
		final PersonVo personVo = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!"));
		getLinkFindById(personVo);
		return ResponseEntity.ok(personVo);
	}

	@PatchMapping(value = "/enabled/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE, YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<PersonVo> updateSetEnabled(@PathVariable long id,
			@RequestParam(defaultValue = "false", required = false) boolean enabled) throws Exception {
		final PersonVo personVo = service.updateSetEnabled(enabled, id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!"));
		getLinkFindById(personVo);
		return ResponseEntity.ok(personVo);
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<PersonVo> create(@RequestBody PersonVo personVo) throws Exception {
		personVo = service.create(personVo);
		getLinkFindById(personVo);
		return ResponseEntity.status(HttpStatus.CREATED).body(personVo);
	}

	@PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE })
	public ResponseEntity<PersonVo> update(@RequestBody PersonVo personVo, @PathVariable long id) throws Exception {
		personVo = service.update(personVo, id);
		getLinkFindById(personVo);
		return ResponseEntity.ok(personVo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) throws Exception {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	private void getLinkFindById(PersonVo personVo) {
		try {
			personVo.add(linkTo(methodOn(PersonController.class).findById(personVo.getKey())).withSelfRel());
		} catch (Exception e) {
			log.warn("getLinkFindById {}", e.getMessage());
		}
	}
}
