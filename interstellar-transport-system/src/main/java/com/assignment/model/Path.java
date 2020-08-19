package com.assignment.model;

import java.util.LinkedList;

public class Path {
	private String source;
	private String destination;
	private String traffic;
	private LinkedList<Planet> path;

	public String getSource() {
	    return source;
	}

	public void setSource(String source) {
	    this.source = source;
	}

	public String getDestination() {
	    return destination;
	}

	public void setDestination(String destination) {
	    this.destination = destination;
	}
	
	public String getTraffic() {
	    return traffic;
	}

	public void setTraffic(String traffic) {
	    this.traffic = traffic;
	}
	
	public LinkedList<Planet> getPath() {
	    return path;
	}

	public void setPath(LinkedList<Planet> path) {
	    this.path = path;
	}
}

