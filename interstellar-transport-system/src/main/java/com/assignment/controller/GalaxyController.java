package com.assignment.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.model.Path;
import com.assignment.service.GalaxyService;
import com.assignment.service.PlanetService;
import com.assignment.service.RouteService;

@Controller
@RequestMapping("/api/v1/galaxy")
public class GalaxyController {
	
	private final PlanetService planetService;
	private final RouteService routeService;
	private final GalaxyService galaxyService;
	
	@Autowired
	public GalaxyController(PlanetService planetService, RouteService routeService, GalaxyService galaxyService) {
		this.planetService = planetService;
		this.routeService = routeService;
		this.galaxyService = galaxyService; 
	} 
	
	@GetMapping("newplanet")
	public String showPlanetForm() {
		return "add-planet";
	}
	
	@GetMapping("newroute")
	public String showRouteForm() {
		return "add-route";
	}
	
	@GetMapping("load")
	public String loadData(Model model) throws IOException {
		galaxyService.loadData();
		model.addAttribute("planets", planetService.getAllPlanets());
		model.addAttribute("routes", routeService.getAllRoutes());
		return "index";
	}
	
	@GetMapping("shortestpath")
	public String showPathForm(Model model) {
		model.addAttribute("planets", planetService.getAllPlanets());
		return "shortest-path";
	}
	
	@GetMapping("/")
	public String showGalaxy(Model model) {
		model.addAttribute("planets", planetService.getAllPlanets());
		model.addAttribute("routes", routeService.getAllRoutes());
		return "index";
	}
	
	@PostMapping("calculateshortestpath")
	public String pathSubmit(@ModelAttribute Path path, Model model) {
		boolean traffic = path.getTraffic() != null ? true : false;
	    model.addAttribute("path", path);
	    model.addAttribute("shortestpath", galaxyService.getShortestPath(planetService.getPlanetByNode(path.getSource()), planetService.getPlanetByNode(path.getDestination()), traffic));
	    return "result";
	}
	
	@GetMapping("clean")
	public String deleteAll(Model model) {
		routeService.deleteAllInBatch();
		planetService.deleteAllInBatch(); 
		model.addAttribute("planets", planetService.getAllPlanets());
		model.addAttribute("routes", routeService.getAllRoutes());
		return "index";
	}
}
