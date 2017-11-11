package com.ctlv.controller.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Animal implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String specie;
	
	@Lob
	@Type(type="org.hibernate.type.TextType")
	private String description;
	
	@ManyToOne 
	private AnimalCategory animalCategory;
	
	public Animal() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AnimalCategory getAnimalCategory() {
		return animalCategory;
	}

	public void setAnimalCategory(AnimalCategory animalCategory) {
		this.animalCategory = animalCategory;
	}

	
	
}
