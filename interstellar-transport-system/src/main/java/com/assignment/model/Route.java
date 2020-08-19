package com.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "routes", schema = "app")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;
	
	@Column(name = "ROUTEID", nullable = false, unique = true)
	private long routeid;
	
	@OneToOne
    @JoinColumn(name = "SOURCE", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Planet source;

	@OneToOne
    @JoinColumn(name = "DESTINATION", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Planet destination;
	
	@Column(name = "DISTANCE", nullable = false)
    private double distance;
	
	@Column(name = "TRAFFIC", nullable = false)
    private double traffic;
	
	public Route() {
    }
	
    public Route(long routeid,  Planet source, Planet destination, double distance, double traffic) {
    	this.routeid = routeid;
        this.source = source;
		this.destination = destination;
        this.distance = distance;
        this.traffic = traffic;
    }

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public long getRouteid(){
		return routeid;
	}
    
    public void setRouteid(long routeid) {
        this.routeid = routeid;
    }

    public Planet getSource() {
    	return source;
    }
    
    public void setSource(Planet source) {
        this.source = source;
    }
    
    public Planet getDestination() {
    	return destination;
    }
    
    public void setDestination(Planet destination) {
        this.destination = destination;
    }
    
    public double getTraffic() {
        return traffic;
    }
    
    public void setTraffic(double traffic) {
        this.traffic = traffic;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
}
