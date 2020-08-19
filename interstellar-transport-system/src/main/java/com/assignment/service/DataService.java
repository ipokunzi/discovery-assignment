package com.assignment.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.assignment.model.Planet;
import com.assignment.model.Route;

import exception.RecordNotFoundException;

@Service
public class DataService {
	@Autowired
    PlanetService planetService;
	
    @Autowired
    RouteService routeService;
    
    Logger logger = Logger.getLogger(DataService.class.getName()); 
    
    private void createPlanet(String node, String name) {
    	Planet planet = new Planet();
    	planet.setNode(node);
    	planet.setName(name);
        planetService.createPlanet(planet);
        logger.log(Level.INFO, "Planet created successfully: Node = " + node + "\tName = " + name);
    }
    
    private void createRoute(long routeid,  Planet source, Planet destination, double distance, double traffic) {
    	Route route = new Route();
    	route.setRouteid(routeid);
    	route.setSource(source);
    	route.setDestination(destination);
    	route.setDistance(distance);
    	route.setTraffic(traffic);
        routeService.createRoute(route);
        logger.log(Level.INFO, "Route created successfully: RouteID = " + routeid + "\tSource = " + source + "\tDestination = " + destination + "\tDistance = " + distance + "\tTraffic = " + traffic);
    }
    
    private void updateRoute(long routeid, double traffic) throws RecordNotFoundException {
    	Route route = routeService.getRouteByRouteid(routeid);
    	route.setTraffic(traffic);
        routeService.updateRoute(route.getId(), route);
        logger.log(Level.INFO, "Route updated successfully with traffic: RouteID = " + routeid + "\tTraffic = " + traffic);
    }
	
	public void loadData() throws IOException{
		File file = null;
		try {
			file = new ClassPathResource("/data/data.xlsx").getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//File file = new File("C:\\Users\\ipokunzi\\workspace\\interstellar-transport-system\\src\\main\\resources\\data\\data.xlsx");   //creating a new file instance 
		
		//obtaining bytes from the file
		FileInputStream fis = null;
		try {
			
			fis = new FileInputStream(file);
			
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = null;
			try {
				wb = new XSSFWorkbook(fis);
				
				loadPlanetSheet(wb);
		        
				loadRouteSheet(wb);

		        loadTrafficSheet(wb);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
		         if(wb!=null) {
		        	 wb.close();
			     } 
			} 
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} finally {
	         if(fis!=null) {
	            fis.close();
	         } 
		}
        
	}

	private void loadPlanetSheet(XSSFWorkbook wb) {
		try  
		{     
			//creating first Sheet object to retrieve object
			XSSFSheet sheet = wb.getSheetAt(0); 
			//iterating over excel file
			Iterator<Row> itr = sheet.iterator();
			//skip the header row
			itr.next();
			String node = null, name = null;
			while (itr.hasNext())                 
			{  
				Row row = itr.next();
				//iterating over each column 
				Iterator<Cell> cellIterator = row.cellIterator(); 
				while (cellIterator.hasNext())   
				{  
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					 
                    switch (columnIndex) {
	                    case 0:
	                        node = cell.getStringCellValue();
	                        break;
	                    case 1:
	                    	name = cell.getStringCellValue();
                    }
				}
				createPlanet(node, name);
			}  
		}  
		catch(Exception e)  
		{  
			e.printStackTrace();  
		}
	}
	
	private void loadRouteSheet(XSSFWorkbook wb) {
		try  
		{   
			//creating second Sheet object to retrieve object
			XSSFSheet sheet = wb.getSheetAt(1);  
			//iterating over excel file 
			Iterator<Row> itr = sheet.iterator();
			//skip the header row
			itr.next();
			long routeid = 0;
			String source = null, destination = null;
			double distance = 0.0;
			double traffic = 0.0;
			
			Planet psource = null;
			Planet pdestination = null;
			
			while (itr.hasNext())                 
			{  
				Row row = itr.next();  
				//iterating over each column
				Iterator<Cell> cellIterator = row.cellIterator();  
				while (cellIterator.hasNext())   
				{  
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					 
                    switch (columnIndex) {
	                    case 0:
	                    	routeid = (long) cell.getNumericCellValue();
	                    	break;
	                    case 1:
	                        source = cell.getStringCellValue();
	                        break;
	                    case 2:
	                    	destination = cell.getStringCellValue();
	                    	break;
	                    case 3:
	                    	distance = cell.getNumericCellValue();
	                    	break;
                    }
				}
				psource = planetService.getPlanetByNode(source);
				pdestination = planetService.getPlanetByNode(destination);
				if(psource != null & pdestination != null)
					createRoute(routeid,  psource, pdestination, distance, traffic);
			}  
		}  
		catch(Exception e)  
		{  
			e.printStackTrace();  
		}
	}
	
	private void loadTrafficSheet(XSSFWorkbook wb) {
		try  
		{  
        	//creating third Sheet object to retrieve object        	
			XSSFSheet sheet = wb.getSheetAt(2);
			//iterating over excel file
			Iterator<Row> itr = sheet.iterator();     
			//skip the header row
			itr.next(); 
			long routeid = 0;
			Route route = null;
			//String source = null, destination = null;
			double traffic = 0.0;
			while (itr.hasNext())                 
			{  
				Row row = itr.next();  
				//iterating over each column
				Iterator<Cell> cellIterator = row.cellIterator();     
				while (cellIterator.hasNext())   
				{  
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					
                    switch (columnIndex) {
	                    case 0:
	                    	routeid = (long) cell.getNumericCellValue();
	                    	break;
	                    case 3:
	                    	traffic = cell.getNumericCellValue();
	                    	break;
                    }
				}
				route = routeService.getRouteByRouteid(routeid);
				if(route != null)
					updateRoute(routeid,   traffic);
			}  
		} catch(Exception e) {  
			e.printStackTrace();  
		} 
	}
}
