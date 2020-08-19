package com.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.dao.PlanetDAO;
import com.assignment.model.Planet;

import exception.RecordNotFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PlanetService {
	private final PlanetDAO planetDAO;
	
	// Create a Logger 
    Logger logger = Logger.getLogger(PlanetService.class.getName()); 
	
	@Autowired
	PlanetService(PlanetDAO planetDAO){
		this.planetDAO = planetDAO;
	}
	
	public List<Planet> getAllPlanets() {    
		List<Planet> planets = new ArrayList<Planet>();    
		planetDAO.findAll().forEach(planets::add);
		logger.log(Level.INFO, "getAllPlanets Successful");
		return planets;    
	} 
	
	public Planet createPlanet(Planet planet) {
		planet = planetDAO.save(planet);
		logger.log(Level.INFO, "Planet Successfully Created : Node : " + planet.getNode() + " Name : " + planet.getName());
        return planet;
	}
	
	public Planet updatePlanet(Long id, Planet planet) throws RecordNotFoundException {
		Optional<Planet> find = planetDAO.findById(id);
        
        if(find.isPresent()) {
        	Planet newPlanet = find.get(); 
        	newPlanet.setNode(planet.getNode());
        	newPlanet.setName(planet.getName());

            newPlanet = planetDAO.save(newPlanet);
            logger.log(Level.INFO, "Planet Successfully Updated : Node : " + newPlanet.getNode() + " Name : " + newPlanet.getName());
             
            return newPlanet;
        } else {
        	throw new RecordNotFoundException("No planet record exist for given id : " + id);
        }
	}  

	public Planet getPlanetByNode(String node) {
		Optional<Planet> planet = planetDAO.findByNode(node);
        
        if(planet.isPresent()) {
        	logger.log(Level.INFO, "getPlanetByNode Successful: " + node);
            return planet.get();
        } else {
        	logger.log(Level.INFO, "getPlanetByNode Fail: " + node);
            return null;
        }
	}
	
	public void deletePlanet(Planet planet) {
		planetDAO.delete(planet);
		logger.log(Level.INFO, "deletePlanet Successful: ");
	}

	public Planet getPlanetById(long id) throws RecordNotFoundException {
		Optional<Planet> planet = planetDAO.findById(id) ;
        
        if(planet.isPresent()) {
        	logger.log(Level.INFO, "getPlanetById Successful: " + id);
            return planet.get();
        } else {
        	logger.log(Level.INFO, "getPlanetById Fail: " + id);
        	throw new RecordNotFoundException("No planet record exist for given id : " + id);
        }
	}
	
	public void deleteAllInBatch() {
		planetDAO.deleteAllInBatch();
		logger.log(Level.INFO, "deleteAllInBatch Planet Successful: ");
	}

}
