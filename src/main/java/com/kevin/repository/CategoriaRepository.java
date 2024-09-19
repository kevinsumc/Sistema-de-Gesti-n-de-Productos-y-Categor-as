package com.kevin.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kevin.model.CategoriaModel;


public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
}
