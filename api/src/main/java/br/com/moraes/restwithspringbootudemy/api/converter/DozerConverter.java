package br.com.moraes.restwithspringbootudemy.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerConverter {

	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return origin != null ? mapper.map(origin, destination) : null;
	}

	public static <O, D> List<D> parseListObjects(List<O> origins, Class<D> destination) {
		return CollectionUtils.isNotEmpty(origins)
				? origins.stream().map(o -> mapper.map(o, destination)).collect(Collectors.toList())
				: null;
	}
}
