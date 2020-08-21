package com.assignment;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.assignment.InterstellarTransportSystemApplication;
import com.assignment.model.Planet;
import com.assignment.model.Route;
import com.assignment.service.PlanetService;

@SpringBootTest(classes = InterstellarTransportSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InterstellarTransportSystemApplicationTests {

	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
    private PlanetService planetService;

	@LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }
	
	@Test
	void contextLoads() {
	}
	
	@Test
    public void testCreatePlanet() {
        Planet planet = new Planet();
        planet.setNode("A");
        planet.setName("Earth");

        ResponseEntity<Planet> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/planets", planet, Planet.class);
        
        planet.setNode("B");
        planet.setName("Moon");

        postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/planets", planet, Planet.class);
        
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());
    }
	
	@Test
    public void testGetAllPlanets() {
         HttpHeaders headers = new HttpHeaders();
         HttpEntity<String> entity = new HttpEntity<String>(null, headers);

         ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/planets",
         HttpMethod.GET, entity, String.class);
  
         Assert.assertNotNull(response.getBody());
    }
	
    @Test
    public void testGetPlanetById() {
        Planet planet = restTemplate.getForObject(getRootUrl() + "/api/v1/planets/1", Planet.class);
        Assert.assertNotNull(planet);
    }

    @Test
    public void testUpdatePost() {
         int id = 1;
         Planet planet = restTemplate.getForObject(getRootUrl() + "/api/v1/planets/" + id, Planet.class);
         planet.setNode("AA");
         planet.setName("Earth Earth");

         restTemplate.put(getRootUrl() + "/api/v1/planets/" + id, planet);

         Planet updatedPlanet = restTemplate.getForObject(getRootUrl() + "/api/v1/planets/" + id, Planet.class);
         Assert.assertNotNull(updatedPlanet);
    }
    
    @Test
    public void testDeletePlanetPost() {
         int id = 1;
         Planet planet = restTemplate.getForObject(getRootUrl() + "/api/v1/planets/" + id, Planet.class);
         Assert.assertNotNull(planet);

         restTemplate.delete(getRootUrl() + "/api/v1/planets/" + id);
    
         try {
              planet = restTemplate.getForObject(getRootUrl() + "/api/v1/planets/" + id, Planet.class);
         } catch (final HttpClientErrorException e) {
        	  Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
         }
    }
         
    @Test
    public void testDeletePlanetPostError() {
         int id = 2;
         Planet planet = restTemplate.getForObject(getRootUrl() + "/api/v1/planets/" + id, Planet.class);
         Assert.assertNotNull(planet);

         restTemplate.delete(getRootUrl() + "/api/v1/planets/" + id);
    
         try {
              planet = restTemplate.getForObject(getRootUrl() + "/api/v1/planets/" + id, Planet.class);
         } catch (final HttpClientErrorException e) {
        	  Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
         }
     }
         
         @Test
         public void testCreateRoute() {
             Route route = new Route();
             route.setRouteid(1);
             route.setSource(planetService.getPlanetByNode("A"));
             route.setDestination(planetService.getPlanetByNode("B"));
             route.setDistance(0.11);
             route.setTraffic(0.22);

             ResponseEntity<Route> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/routes", route, Route.class);
             Assert.assertNotNull(postResponse);
             Assert.assertNotNull(postResponse.getBody());
         }
     	
     	@Test
         public void testGetAllRoutes() {
              HttpHeaders headers = new HttpHeaders();
              HttpEntity<String> entity = new HttpEntity<String>(null, headers);

              ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/routes",
              HttpMethod.GET, entity, String.class);
       
              Assert.assertNotNull(response.getBody());
         }
     	
         @Test
         public void testGetRouteById() {
             Route route = restTemplate.getForObject(getRootUrl() + "/api/v1/routes/1", Route.class);
             Assert.assertNotNull(route);
         }

         @Test
         public void testRoutePost() {
              int id = 1;
              Route route = restTemplate.getForObject(getRootUrl() + "/api/v1/routes/" + id, Route.class);
              
              route.setRouteid(1);
              route.setSource(planetService.getPlanetByNode("A"));
              route.setDestination(planetService.getPlanetByNode("B"));
              route.setDistance(10.11);
              route.setTraffic(10.22);

              restTemplate.put(getRootUrl() + "/api/v1/routes/" + id, route);

              Route updatedRoute = restTemplate.getForObject(getRootUrl() + "/api/v1/routes/" + id, Route.class);
              Assert.assertNotNull(updatedRoute);
         }
         
         @Test
         public void testDeleteRoutePost() {
              int id = 1;
              Route route = restTemplate.getForObject(getRootUrl() + "/api/v1/routes/" + id, Route.class);
              Assert.assertNotNull(route);

              restTemplate.delete(getRootUrl() + "/api/v1/routes/" + id);
         
              try {
                   route = restTemplate.getForObject(getRootUrl() + "/api/v1/routes/" + id, Route.class);
              } catch (final HttpClientErrorException e) {
             	  Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
              }
         }
              
         @Test
         public void testDeleteRoutePostError() {
              int id = 2;
              Route route = restTemplate.getForObject(getRootUrl() + "/api/v1/routes/" + id, Route.class);
              Assert.assertNotNull(route);

              restTemplate.delete(getRootUrl() + "/api/v1/routes/" + id);
         
              try {
                   route = restTemplate.getForObject(getRootUrl() + "/api/v1/routes/" + id, Route.class);
              } catch (final HttpClientErrorException e) {
             	  Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
          }
  }

}
