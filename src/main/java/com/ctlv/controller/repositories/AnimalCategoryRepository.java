package com.ctlv.controller.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ctlv.controller.beans.Animal;
import com.ctlv.controller.beans.AnimalCategory;

public interface AnimalCategoryRepository extends JpaRepository<AnimalCategory,Long>{

	@Query("select t from Animal t "
			+ "where t.animalCategory.id =:_categoryId")
	public List<Animal> getAnimalsForCategory(@Param("_categoryId") Long categoryId);
	
}