package com.mrsystems.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ModelMapperConfig {

	private static final ModelMapper mapper = new ModelMapper();

	@Bean
    ModelMapper mapper() {
		return new ModelMapper();
	}

	public static <O, D> D parseMapper(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}

	public static <O, D> List<D> parseListMapper(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<>();
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}

		return destinationObjects;
	}

}
