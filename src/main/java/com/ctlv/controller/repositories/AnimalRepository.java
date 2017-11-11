package com.ctlv.controller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctlv.controller.beans.Animal;

public interface AnimalRepository extends JpaRepository<Animal,Long>{

}