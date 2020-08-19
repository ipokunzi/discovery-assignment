package com.assignment.controller;

import java.util.HashMap;
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
import com.assignment.service.PlanetService;

import exception.RecordNotFoundException;

@Controller
@RequestMapping("/api/v1/planets")
public class PlanetController {
	
	private final PlanetService planetService;
	
	@Autowired
	public PlanetController(PlanetService planetService) {
		this.planetService = planetService;
	} 
	
	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Planet planet) { 
	    return new ResponseEntity<>(planetService.createPlanet(planet), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Object> get() {
		return new ResponseEntity<>(planetService.getAllPlanets(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getPlanetById(@PathVariable("id") Long id) throws RecordNotFoundException {
		return new ResponseEntity<>(planetService.getPlanetById(id), HttpStatus.OK);
	}
	   
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody Planet planet) throws RecordNotFoundException {
	    return new ResponseEntity<>(planetService.updatePlanet(id, planet), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws RecordNotFoundException { 
		Planet planet = planetService.getPlanetById(id);
		planetService.deletePlanet(planet);
		
		Map<String, Boolean> response = new HashMap<>();
	       response.put("deleted", Boolean.TRUE);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/add-planet")
	public String showPlanetForm(@ModelAttribute Planet planet, Model model) {
    	model.addAttribute(planet);
		return "add-planet";
	}
	
	@GetMapping("/view-planets")
	public String listPlanets(Model model) {
		model.addAttribute("planets", planetService.getAllPlanets());
		return "view-planets";
	}
	
	
//	@GetMapping("/{id}")
//	public String getPlanet(@PathVariable("id") long id, Model model) throws RecordNotFoundException {
//		Planet planet = planetService.getPlanetById(id);
//		model.addAttribute("planet", planet);
//		return "planet";
//	}
	
	@PostMapping("/add")
	public String addPlanet(@ModelAttribute Planet planet, BindingResult result, Model model) {
		if (result.hasErrors()) {
	    	model.addAttribute(planet);
	    	model.addAttribute("planets", planetService.getAllPlanets());
			return "add-route";
		}
		
		planet = planetService.createPlanet(planet);
		model.addAttribute(planet);
		model.addAttribute("planets", planetService.getAllPlanets());
		return "view-planets";
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdatePlanetForm(@PathVariable("id") long id, Model model) throws RecordNotFoundException {
		Planet planet = planetService.getPlanetById(id);
		model.addAttribute("planet", planet);
		return "update-planet";
	}
	
	@PostMapping("/update/{id}")
	public String updatePlanet(@PathVariable("id") long id, @Valid Planet planet, BindingResult result, Model model) throws RecordNotFoundException {
		if (result.hasErrors()) {
            return "update-planet";
        }
		
		planet = planetService.updatePlanet(id, planet);
		model.addAttribute("planets", planetService.getAllPlanets());
		return "view-planets";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePlanet(@PathVariable("id") long id, Model model) throws RecordNotFoundException {
		Planet planet = planetService.getPlanetById(id);
		planetService.deletePlanet(planet);   
		model.addAttribute("planets", planetService.getAllPlanets());
		return "view-planets";
	}
	
	@GetMapping("/clean")
	public String deleteAll(Model model) {
		planetService.deleteAllInBatch(); 
		model.addAttribute("planets", planetService.getAllPlanets());
		return "view-planets";
	}
	

}
