package com.assignment.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.model.Planet;
import com.assignment.model.Route;

@Service
public class GalaxyService {
//	private double LIGHT_YEAR_CONSTANT = new Double("9460730472580800");//meters
//	private double PASSENGER_TRAVEL_SPEED = new Double("7500000000000");//meters/second
	
	private boolean isTraffic;
	
	private final List<Planet> planets;
    private final List<Route> routes;
	
	private Set<Planet> settledNodes;
    private Set<Planet> unSettledNodes;
    private Map<Planet, Planet> predecessors;
    private Map<Planet, Double> distance;
    
    @Autowired
    PlanetService planetService;
	
    @Autowired
    RouteService routeService;
    
    @Autowired
    DataService dataService;
    
    public GalaxyService(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.planets = new ArrayList<Planet>(graph.getPlanets());
        this.routes = new ArrayList<Route>(graph.getRoutes());
    }

    public void execute(Planet source) {
        settledNodes = new HashSet<Planet>();
        unSettledNodes = new HashSet<Planet>();
        distance = new HashMap<Planet, Double>();
        predecessors = new HashMap<Planet, Planet>();
        distance.put(source, 0.0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
        	Planet node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Planet node) {
        List<Planet> adjacentNodes = getNeighbors(node);
        for (Planet target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private double getDistance(Planet node, Planet target) {
        for (Route route : routes) {
        	if(route.getSource() != null && route.getDestination() != null){
	        	if (route.getSource().equals(node) && route.getDestination().equals(target)) {
	        		return getIsTraffic() ? route.getDistance() + route.getTraffic() : route.getDistance();
	            }
        	}
        }
        throw new RuntimeException("****************** How *******************");
    }

    private List<Planet> getNeighbors(Planet node) {
        List<Planet> neighbors = new ArrayList<Planet>();
        for (Route route : routes) {
        	if(route.getSource() != null && route.getDestination() != null){
	            if (route.getSource().equals(node) && !isSettled(route.getDestination())) {
	                neighbors.add(route.getDestination());
	            }
        	}
        }
        return neighbors;
    }

    private Planet getMinimum(Set<Planet> planets) {
    	Planet minimum = null;
        for (Planet planet : planets) {
            if (minimum == null) {
                minimum = planet;
            } else {
                if (getShortestDistance(planet) < getShortestDistance(minimum)) {
                    minimum = planet;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Planet planet) {
        return settledNodes.contains(planet);
    }

    private double getShortestDistance(Planet destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Planet> getPath(Planet target) {
    	LinkedList<Planet> path = new LinkedList<Planet>();
    	Planet step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
    
    public boolean getIsTraffic(){
    	return isTraffic;
    }
    
    public void setIsTraffic(boolean isTraffic){
    	this.isTraffic = isTraffic;
    }

	public List<Planet> getNodes() {
		return planets;
	}
	
	public List<Route> getRoutes() {
		return routes;
	}
	
	public LinkedList<Planet> getShortestPath(Planet source, Planet destination, boolean traffic){
		LinkedList<Planet> path = new LinkedList<Planet>();
		
		Graph graph = new Graph(planetService.getAllPlanets(), routeService.getAllRoutes());
		GalaxyService galaxyService = new GalaxyService(graph);
		galaxyService.setIsTraffic(traffic);
		galaxyService.execute(source);
		
		path = galaxyService.getPath(destination);
		
		return path;
		
	}

	public void loadData() throws IOException {
		dataService.loadData();
	}

}
