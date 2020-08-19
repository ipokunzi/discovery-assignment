package com.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "planets", schema = "app")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Planet {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;
	@Column(name = "NODE", nullable = false, unique = true)
	private String node;
	@Column(name = "NAME", nullable = false, unique = true)
	private String name;
	
	public Planet() {
	}

	public Planet(String node, String name) {
		this.node = node;
		this.name = name;
	}

	public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
	
    public String getNode(){
		return node;
	}
    
	public void setNode(String node){
		this.node = node;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
