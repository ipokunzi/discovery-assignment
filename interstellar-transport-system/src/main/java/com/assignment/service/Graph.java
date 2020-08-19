package com.assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.model.Planet;
import com.assignment.model.Route;

@Service
public class Graph {
	private final List<Planet> planets;
    private final List<Route> routes;

    public Graph(List<Planet> planets, List<Route> routes) {
        this.planets = planets;
        this.routes = routes;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
