package com.thiagomoraes.foodflix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagomoraes.foodflix.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}

