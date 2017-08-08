package com.projeto.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.springboot.model.Pessoa;



public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
