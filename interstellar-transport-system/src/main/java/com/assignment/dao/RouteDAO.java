package com.assignment.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.model.Route;

public interface RouteDAO extends JpaRepository<Route, String> {

	Optional<Route> findByRouteid(Long routeid);

	void deleteById(long id);

	Optional<Route> findById(long id);

}
