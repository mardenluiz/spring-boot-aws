package com.mrsystems.api.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsystems.api.controller.PersonController;
import com.mrsystems.api.data.v1.PersonVO;
import com.mrsystems.api.exceptions.RequiredObjectNotFoundExcption;
import com.mrsystems.api.exceptions.ResourceNotFoundException;
import com.mrsystems.api.model.Person;
import com.mrsystems.api.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ModelMapper mapper;

	private Logger logger = Logger.getLogger(PersonService.class.getName());

	public List<PersonVO> findAll() {
		
		var type  = mapper.typeMap(Person.class, PersonVO.class);
		type.addMappings(map -> map.map(Person::getId, PersonVO::setKey));
		
		List<Person> model = personRepository.findAll();
		
		var persons = model.stream().map(t ->  mapper.map(t, PersonVO.class)).toList();
		
		persons.
			forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		return persons;
	}

	public PersonVO findById(Long id) {
		
		var type  = mapper.typeMap(Person.class, PersonVO.class);
		type.addMappings(map -> map.map(Person::getId, PersonVO::setKey));

		Person model = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	
		var vo = mapper.map(model, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	
	public PersonVO create(PersonVO person) {
		
		if (person == null) throw new RequiredObjectNotFoundExcption();
		
		var find  = mapper.typeMap(PersonVO.class, Person.class);
		find.addMappings(map -> map.map(PersonVO::getKey, Person::setId));
	
		logger.info("Create one person");
		var model = mapper.map(person, Person.class);
		
		var entity = personRepository.save(model);
		
		var save = mapper.typeMap(Person.class, PersonVO.class);
		save.addMappings(map -> map.map(Person::getId, PersonVO::setKey));
		var vo = mapper.map(entity, PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
	
		
		return vo;
	}

	public PersonVO update(PersonVO person) {
		
		if (person == null) throw new RequiredObjectNotFoundExcption();
		
		logger.info("Updated person data");
		
		var entity = personRepository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		
		var vo = mapper.map(personRepository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleted one person");
		
		var entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		personRepository.delete(entity);
	}

}
