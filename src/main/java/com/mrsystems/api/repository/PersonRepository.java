package com.mrsystems.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrsystems.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
