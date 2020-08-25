package com.assignment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.model.Planet;
import com.assignment.model.Route;
import com.assignment.service.PlanetService;
import com.assignment.service.RouteService;

import exception.RecordNotFoundException;

@Controller
@RequestMapping("/api/v1/routes")
public class RouteController {
	private final RouteService routeService;
	private final PlanetService planetService;
	
	@Autowired
	public RouteController(RouteService routeService, PlanetService planetService) {
		this.routeService = routeService; 
		this.planetService = planetService;
	}
		
	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Route route) {
		return new ResponseEntity<>(routeService.createRoute(route), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Object> get() {
		return new ResponseEntity<>(routeService.getAllRoutes(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getRouteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		return new ResponseEntity<>(routeService.getRouteById(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody Route route) throws RecordNotFoundException {
		return new ResponseEntity<>(routeService.updateRoute(id, route), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws RecordNotFoundException { 
		Route route = routeService.getRouteById(id);
		routeService.deleteRoute(route);
		Map<String, Boolean> response = new HashMap<>();
	       response.put("deleted", Boolean.TRUE);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/add-route")
	public String showRouteForm(@ModelAttribute Route route, Model model) {
    	model.addAttribute(route);
		model.addAttribute("planets", planetService.getAllPlanets());
		return "add-route";
	}
	
	@GetMapping("/view-routes")
	public String showRoutes(Model model) {
		model.addAttribute("routes", routeService.getAllRoutes());
		return "view-routes";
	}
	
	@PostMapping("/add")
	public String addRoute(@ModelAttribute Route route, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
	    	model.addAttribute(route);
			model.addAttribute("planets", planetService.getAllPlanets());
			return "add-route";
		}
		route = routeService.createRoute(route);
		model.addAttribute("routes", routeService.getAllRoutes());
		return "view-routes";
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateRouteForm(@PathVariable("id") long id, Model model) throws RecordNotFoundException {
		List<Planet> planets = planetService.getAllPlanets();
		Route route = routeService.getRouteById(id);
		model.addAttribute("planets", planets);
		model.addAttribute("route", route);
		return "update-route";
	}
	
	@PostMapping("/update/{id}")
	public String updateRoute(@PathVariable("id") long id, @Valid Route route, BindingResult result, Model model) throws RecordNotFoundException {

		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
            return "update-route";
        }
		
		route = routeService.updateRoute(id, route);
		model.addAttribute("routes", routeService.getAllRoutes());
		return "view-routes";
		
	}
	
	@GetMapping("/delete/{id}")
	public String deleteRoute(@PathVariable("id") long id, Model model) throws RecordNotFoundException {
		Route route = routeService.getRouteById(id);
		routeService.deleteRoute(route);   
		model.addAttribute("routes", routeService.getAllRoutes());
		return "view-routes";
	}
	
	@GetMapping("/clean")
	public String deleteAll(Model model) {
		routeService.deleteAllInBatch();
		model.addAttribute("routes", routeService.getAllRoutes());
		return "view-routes";
	}
}
