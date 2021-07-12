package br.com.moraes.restwithspringbootudemy.api.converter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public final class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

	public static final String MEDIA_TYPE_YAML_VALUE = "application/x-yaml";

	public YamlJackson2HttpMessageConverter() {
		super(new YAMLMapper().setSerializationInclusion(Include.NON_NULL),
				MediaType.parseMediaType(MEDIA_TYPE_YAML_VALUE));
	}

}
