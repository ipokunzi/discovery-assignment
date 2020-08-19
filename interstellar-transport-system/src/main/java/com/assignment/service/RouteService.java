package com.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.dao.RouteDAO;
import com.assignment.model.Route;

import exception.RecordNotFoundException;

@Service
public class RouteService {
	
    Logger logger = Logger.getLogger(RouteService.class.getName()); 
	
	private final RouteDAO routeDAO;
	private final PlanetService planetService;
	
	@Autowired
	RouteService(RouteDAO routeDAO, PlanetService planetService){
		this.routeDAO = routeDAO;
		this.planetService = planetService;
	}
	
	public List<Route> getAllRoutes() {    
		List<Route> routes = new ArrayList<Route>();    
		routeDAO.findAll().forEach(routes::add);
		logger.log(Level.INFO, "getAllRoutes Successful: ");
		return routes;    
	} 
	
	public Route createRoute(Route route) {
		route = routeDAO.save(route);
    	logger.log(Level.INFO, "Route Successfully Created : Route ID : " + route.getRouteid() + " Source : " + route.getSource().getNode() + " Destination : " + route.getDestination().getNode() + " Distance : " + route.getDistance() + " Traffic : " + route.getTraffic());
        return route;
	}
	
	public Route updateRoute(Long id, Route route) throws RecordNotFoundException {
		
		Optional<Route> find = routeDAO.findById(id);
        
        if(find.isPresent()) {
            Route newRoute = find.get();
            newRoute.setDestination(route.getDestination());
            newRoute.setDistance(route.getDistance());
            newRoute.setSource(route.getSource());
            newRoute.setTraffic(route.getTraffic());

            newRoute = routeDAO.save(newRoute);
            logger.log(Level.INFO, "Route Successfully Updated : Route ID : " + newRoute.getRouteid() + " Source : " + newRoute.getSource().getNode() + " Destination : " + newRoute.getDestination().getNode() + " Distance : " + newRoute.getDistance() + " Traffic : " + newRoute.getTraffic());
             
            return newRoute;
        } else {
        	throw new RecordNotFoundException("No route record exist for given id : " + id);
        }
	}  

	public Route getRouteByRouteid(Long routeid) {
		Optional<Route> route = routeDAO.findByRouteid(routeid);
        
        if(route.isPresent()) {
        	logger.log(Level.INFO, "getRouteByRouteid Successful: " + routeid);
            return route.get();
        } else {
        	logger.log(Level.INFO, "getRouteByRouteid Fail: " + routeid);
        	return null;
        }
	}
	
	public void deleteRoute(Route route) {
		routeDAO.delete(route);
		logger.log(Level.INFO, "deleteRoute Successful: ");
	}
	
	public void deleteRouteById(long id) {
		routeDAO.deleteById(id);
		logger.log(Level.INFO, "deleteRouteById Successful: " + id);
	}
	
	public void deleteRouteByRouteid(long routeid) {
		routeDAO.deleteById(routeid);
		logger.log(Level.INFO, "deleteRouteByRouteid Successful: " + routeid);
	}

	public Route getRouteById(long id) throws RecordNotFoundException {
		Optional<Route> route = routeDAO.findById(id);
        
        if(route.isPresent()) {
        	logger.log(Level.INFO, "getRouteById Successful: " + id);
            return route.get();
        } else {
        	logger.log(Level.INFO, "getRouteById Fail: " + id);
        	throw new RecordNotFoundException("No route record exist for given id : " + id);
        }
	}

	public void deleteAllInBatch() {
		routeDAO.deleteAllInBatch();
		logger.log(Level.INFO, "deleteAllInBatch Route Successful: ");
	}
	
}
