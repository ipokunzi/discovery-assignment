package com.assignment.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.model.Planet;

public interface PlanetDAO extends JpaRepository<Planet, String> {

	Optional<Planet> findByNode(String node);

	Optional<Planet> findById(long id);

}
