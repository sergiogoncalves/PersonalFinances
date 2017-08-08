package com.projeto.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.springboot.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
