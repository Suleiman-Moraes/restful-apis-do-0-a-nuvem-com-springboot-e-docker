package br.com.moraes.restwithspringbootudemy.api.controller.abstracts;

import org.springframework.http.MediaType;

import br.com.moraes.restwithspringbootudemy.api.converter.YamlJackson2HttpMessageConverter;

public abstract class AbstractController {
	
	protected static final String[] PRODUCES = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE };

	protected static final String[] CONSUMES = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			YamlJackson2HttpMessageConverter.MEDIA_TYPE_YAML_VALUE };
}
